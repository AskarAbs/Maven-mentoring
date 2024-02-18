package services;


import com.askar.videoLibrary.entity.Users;
import com.askar.videoLibrary.entity.enums.Role;
import com.askar.videoLibrary.util.HibernateTestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class UsersServiceIT {

    @Test
    void checkHibernateCfgTest() {
        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var expectedUser = Users.builder()
                    .username("Askar")
                    .password("1210!")
                    .email("askar@gmail.com")
                    .role(Role.USER)
                    .build();
            session.beginTransaction();

            session.persist(expectedUser);
            var actualUser = session.get(Users.class, 1L);

            expectedUser.setId(1L);
            assertThat(expectedUser).isEqualTo(actualUser);

            session.getTransaction().commit();
        }
    }
}