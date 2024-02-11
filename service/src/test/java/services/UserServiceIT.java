package services;

import entity.Users;
import entity.enums.Role;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;
import static org.assertj.core.api.Assertions.*;

class UserServiceIT {

    @Test
    public void checkHibernateCfgTest() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var expectedUser = Users.builder()
                    .username("Askar")
                    .email("askar@gmail.com")
                    .role(Role.USER)
                    .build();
            session.beginTransaction();

//            session.persist(expectedUser);
            var actualUser = session.get(Users.class, 1L);

            expectedUser.setId(1L);
            assertThat(expectedUser).isEqualTo(actualUser);

            session.getTransaction().commit();
        }
    }
}