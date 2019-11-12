package alexrm84.myMarket;

import alexrm84.entities.User;
import alexrm84.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoriesTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void userRepositoryTest() {
        User newUser = new User();
        newUser.setPhone("1231231231");
        newUser.setFirstName("sdfsdfsd");
        entityManager.persist(newUser);
        entityManager.flush();

        User user = userRepository.findOneByPhone("1231231231");
        System.out.println(user);
    }
}
