package alexrm84.services;

import alexrm84.entities.User;
import alexrm84.utils.SystemUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService extends UserDetailsService {
    User findByPhone(String phone);
    User save(SystemUser systemUser);
    User save(Map<String, String> params);
    boolean isUserExist(String phone);
}
