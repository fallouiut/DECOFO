package m2info.ter.decofo.manager.auth;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.dao.DAOFormation;
import m2info.ter.decofo.dao.DAOUser;
import m2info.ter.decofo.exceptions.DecofoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RolesControleurTest {

    @Autowired
    RolesManager rolesManager;

    @Autowired
    DAOFormation daoFormation;

    @Autowired
    DAOUser daoUser;

    Formation formationTest;

    User owner;

    @Test
    public void assertok() {
        assertNotNull(rolesManager);
    }

    @BeforeEach
    public void beforeEach() {
        User user = new User("fallou.seye@outlook.fr", "okok");
        daoUser.insert(user);
        user = daoUser.find(user.getId());

        formationTest = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        formationTest.setOwner(user);
        daoFormation.insert(formationTest);
        formationTest = daoFormation.find(formationTest.getId());
    }

    @Test
    public void add3VisitorsWorkd() throws DecofoException {
        User user1 = new User("user1@gmail.com", "okok");
        User user2 = new User("user2@gmail.com", "okok");
        User user3 = new User("user3@gmail.com", "okok");

        daoUser.insert(user1);
        daoUser.insert(user2);
        daoUser.insert(user3);

        rolesManager.addVisitor(user1.getId(), formationTest.getId());
        rolesManager.addVisitor(user2.getId(), formationTest.getId());
        rolesManager.addVisitor(user3.getId(), formationTest.getId());

        assertEquals(3, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(1, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user3.getId()).getVisitedFormations().size());

    }

    @Test
    public void remove3VisitorsWorkd() throws DecofoException {
        User user1 = new User("user1@gmail.com", "okok");
        User user2 = new User("user2@gmail.com", "okok");
        User user3 = new User("user3@gmail.com", "okok");

        daoUser.insert(user1);
        daoUser.insert(user2);
        daoUser.insert(user3);

        rolesManager.addVisitor(user1.getId(), formationTest.getId());
        rolesManager.addVisitor(user2.getId(), formationTest.getId());
        rolesManager.addVisitor(user3.getId(), formationTest.getId());

        assertEquals(3, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(1, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user3.getId()).getVisitedFormations().size());

        rolesManager.removeVisitor(user1.getId(), formationTest.getId());
        assertEquals(2, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(0, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user3.getId()).getVisitedFormations().size());

        rolesManager.removeVisitor(user2.getId(), formationTest.getId());
        assertEquals(1, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(0, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(0, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user3.getId()).getVisitedFormations().size());

        rolesManager.removeVisitor(user3.getId(), formationTest.getId());
        assertEquals(0, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(0, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(0, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(0, daoUser.find(user3.getId()).getVisitedFormations().size());


    }

    @Test
    public void removeNonExistingUser() throws DecofoException {
        User user1 = new User("user1@gmail.com", "okok");
        User user2 = new User("user2@gmail.com", "okok");
        User user3 = new User("user3@gmail.com", "okok");

        daoUser.insert(user1);
        daoUser.insert(user2);
        daoUser.insert(user3);

        rolesManager.addVisitor(user1.getId(), formationTest.getId());
        rolesManager.addVisitor(user2.getId(), formationTest.getId());
        rolesManager.addVisitor(user3.getId(), formationTest.getId());

        assertEquals(3, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(1, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user3.getId()).getVisitedFormations().size());

        rolesManager.removeVisitor(user1.getId(), formationTest.getId());
        assertEquals(2, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(0, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user3.getId()).getVisitedFormations().size());

        assertThrows(Exception.class, () -> {

            rolesManager.removeVisitor(555555, formationTest.getId());
        });

    }

    @Test
    public void removeNonExistingFormation() throws DecofoException {
        User user1 = new User("user1@gmail.com", "okok");
        User user2 = new User("user2@gmail.com", "okok");
        User user3 = new User("user3@gmail.com", "okok");

        daoUser.insert(user1);
        daoUser.insert(user2);
        daoUser.insert(user3);

        rolesManager.addVisitor(user1.getId(), formationTest.getId());
        rolesManager.addVisitor(user2.getId(), formationTest.getId());
        rolesManager.addVisitor(user3.getId(), formationTest.getId());

        assertEquals(3, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(1, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user3.getId()).getVisitedFormations().size());

        rolesManager.removeVisitor(user1.getId(), formationTest.getId());
        assertEquals(2, daoFormation.find(formationTest.getId()).getVisitors().size());
        assertEquals(0, daoUser.find(user1.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user2.getId()).getVisitedFormations().size());
        assertEquals(1, daoUser.find(user3.getId()).getVisitedFormations().size());

        assertThrows(Exception.class, () -> {
            rolesManager.removeVisitor(user1.getId(), formationTest.getId() + 55);
        });

    }

}
