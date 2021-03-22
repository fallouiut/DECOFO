package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
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
        daoFormation.insert(formation);
        formation = daoFormation.find(formation.getId());
        daoUser.insert(userTest);
        userTest = daoUser.find(userTest.getId());

        System.err.println("ID F: " + formation.getId());
        System.err.println("ID U: " + userTest.getId());
    }

    @AfterEach
    public void after() {
        //daoFormation.delete(formation);
        //userTest.getOwnedFormations().remove(formation);
        //daoUser.update(userTest);
        //daoUser.delete(userTest);
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
    public void findOneByIdsWorks() {
        var trial = daoUser.findByEmailAndPassword(new User("decofo.test@gmail.com", "motDePasse"));

        System.err.println(trial);
        assertNotNull(trial);
    }

    @Test
    public void findOneByIdsFails() {
        var trial = new User("decofo.test@gmail.fr", "motDePasse");
        var trial1 = new User("decofo.test@gmail.com", "motDePasse2");

        assertNull(daoUser.findByEmailAndPassword(trial));
        assertNull(daoUser.findByEmailAndPassword(trial1));
    }

    @Test
    public void addFormation() {
        userTest.addOwnedFormation(formation);
        daoUser.update(userTest);

        User user2 = daoUser.find(userTest.getId());
        assertEquals(1, user2.getOwnedFormations().size());

        Formation formation1 = daoFormation.find(formation.getId());
        assertNotNull(formation1.getOwner());
    }

    @Test
    public void add2Formation() {
        userTest.addOwnedFormation(formation);
        userTest.addOwnedFormation(new Formation("M7ILD", "M3 - ILD", 5, 2, 3));
        daoUser.update(userTest);

        User user2 = daoUser.find(userTest.getId());
        assertEquals(2, user2.getOwnedFormations().size());

        Formation formation1 = daoFormation.find(formation.getId());
        assertNotNull(formation1.getOwner());
    }

    @Test
    public void updateFormation() {
        userTest.addOwnedFormation(formation);
        daoUser.update(userTest);

        // vérifie owner
        var formationTest = daoFormation.find(formation.getId());
        assertNotNull(formationTest.getOwner());

        // ajoute bloc et modifie code
        formationTest.addBloc(new Bloc("bloc", "bloc"));
        formationTest.setCode("test");
        daoFormation.update(formationTest);

        var formationAssert = daoFormation.find(formation.getId());
        assertEquals(1, formationAssert.getBlocs().size());
        assertEquals("test", formationAssert.getCode());
    }

    @Test
    public void addVisitingFormationToUser() {
        // créer deux visiteurs
        User visitor = new User("visitor1@gmail.com", "mdp1");
        User visitor2 = new User("visitor2@gmail.com", "mdp1");
        daoUser.insert(visitor);
        daoUser.insert(visitor2);
        System.err.println("v1: " + visitor.getId());
        System.err.println("v2: " + visitor2.getId());

        // ajouter visiteur
        User visitor1Add = daoUser.find(visitor.getId());
        User visitor2Add = daoUser.find(visitor2.getId());
        visitor1Add.addVisitedFormation(formation);
        visitor2Add.addVisitedFormation(formation);
        daoUser.update(visitor1Add);
        daoUser.update(visitor2Add);

        // assert
        User visitor1Assert = daoUser.find(visitor.getId());
        User visitor2Assert = daoUser.find(visitor2.getId());
        Formation formationAssert = daoFormation.find(formation.getId());

        assertEquals(1, visitor1Assert.getVisitedFormations().size());
        assertEquals(1, visitor2Assert.getVisitedFormations().size());
        assertEquals(2, formationAssert.getVisitors().size());
    }

    @Test
    public void detachVisitedFormation() {

        // créer un visiteur deux formations
        User visitor = new User("visitor5@gmail.com", "mdp1");
        Formation formation1 = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        Formation formation2 = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        daoUser.insert(visitor);
        daoFormation.insert(formation2);
        daoFormation.insert(formation1);
        System.err.println("V: " + visitor.getId());
        System.err.println("F1: " + formation1.getId());
        System.err.println("F2: " + formation2.getId());

        // lier
        User visitorUpdate =  daoUser.find(visitor.getId());
        visitorUpdate.addVisitedFormation(formation1);
        visitorUpdate.addVisitedFormation(formation2);
        daoUser.update(visitorUpdate);
        assertEquals(2, daoUser.find(visitor.getId()).getVisitedFormations().size());
        assertEquals(1, daoFormation.find(formation1.getId()).getVisitors().size());
        assertEquals(1, daoFormation.find(formation2.getId()).getVisitors().size());


        // détacher
        User visitorDetach = daoUser.find(visitor.getId());
        daoUser.unlinkVisitedFormation(visitorDetach, daoFormation.find(formation1.getId()));
        assertEquals(1, daoUser.find(visitor.getId()).getVisitedFormations().size());
        assertEquals(0, daoFormation.find(formation1.getId()).getVisitors().size());
        assertEquals(1, daoFormation.find(formation2.getId()).getVisitors().size());

    }

    @Test
    public void addFormation6() {
        Formation formation1 = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        formation1.setOwner(daoUser.find(userTest.getId()));


        daoFormation.insert(formation1);

        assertNotNull(daoFormation.find(formation1.getId()).getOwner());
        assertEquals(1,daoUser.find(userTest.getId()).getOwnedFormations().size());
    }



}
