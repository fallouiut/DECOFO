package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DAOUserTest {

    @Autowired
    DAOUser daoUser;
    User userTest = new User("decofo.test@gmail.com", "motDePasse");

    @Autowired
    DAOFormation daoFormation;
    Formation formation = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);

    @Test
    public void testNotNull() {
        System.err.println("null: " + daoUser);
        assertNotNull(daoUser);
    }

    @BeforeEach
    public void before() {
        //daoFormation.insert(formation);
        daoUser.insert(userTest);
    }

    @AfterEach
    public void after() {
        //daoFormation.delete(formation);
        daoUser.delete(userTest);
    }

    @Test
    public void testFind() {
        User user2 = daoUser.find(userTest.getId());
        assertNotNull(user2);
    }

    @Test
    public void testUpdateUser() {

        userTest.setEmail("decofo.test@gmail.fr");
        userTest.setMotDePasse("motDePasseUpdate");

        daoUser.update(userTest);
        System.err.println("User : " + userTest);

        User user2 = daoUser.find(userTest.getId());

        assertEquals(user2.getEmail(), "decofo.test@gmail.fr");
        assertEquals(user2.getMotDePasse(), "motDePasseUpdate");
    }

    @Test
    public void addFormation() {
        //userTest.getFormations().add(formation);
        //daoUser.update(userTest);

        //User user2 = daoUser.find(userTest.getId());
        //assertTrue(user2.getFormations().size() >= 1);

    }

}
