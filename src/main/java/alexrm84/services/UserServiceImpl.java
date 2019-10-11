package alexrm84.services;

import alexrm84.entities.Role;
import alexrm84.entities.User;
import alexrm84.repositories.RoleRepository;
import alexrm84.repositories.UserRepository;
import alexrm84.utils.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User findByPhone(String phone) {
        return userRepository.findOneByPhone(phone);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findOneByPhone(phone);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User save(SystemUser systemUser) {
        if (findByPhone(systemUser.getPhone())!=null){
            throw new RuntimeException("User with phone " + systemUser.getPhone() + " is already exist");
        }
        User user = new User(
                systemUser.getPhone(),
                passwordEncoder.encode(systemUser.getPassword()),
                systemUser.getEmail(),
                systemUser.getFirstName(),
                systemUser.getLastName(),
                Arrays.asList(roleRepository.findOneByName("ROLE_CUSTOMER")));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User save(Map<String, String> params) {
        User user = findByPhone(params.get("phone"));
        if (user == null){
            user = userRepository.save(new User(
                    params.get("phone"),
                    params.get("firstName"),
                    Arrays.asList(roleRepository.findOneByName("ROLE_CUSTOMER"))));

        }
        return user;
    }

    @Override
    public boolean isUserExist(String phone) {
        return userRepository.existsByPhone(phone);
    }
}
