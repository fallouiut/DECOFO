package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;

/**
 * README
 * Veuillez lancer les tests un par un
 */
@SpringBootTest
public class FormationManagerTest {

    @Autowired FormationManager manager;
    @Autowired BlocManager blocManager;

    private int formationId;

    @BeforeEach
    public void trouverId() {
        formationId = manager.findAll().get(0).getId();
        System.err.println("Formation ID = " + formationId);
    }

    @Test
    public void testInsert() {

        Formation f = new Formation("M2ILD", 150, "M2 - ILD", 5, 2, 3);
        manager.insert(f);

        //assertTrue( em.find(Formation.class, f.getId()) != null);
    }

    @Test
    public void testFindAll() {
        Formation f = new Formation("M3ILD", 250, "M3 - ILD", 5, 2, 3);
        manager.insert(f);

        List<Formation> formationList = manager.findAll();

        System.err.println("Size of tab: " + formationList.size());
        assertTrue( formationList.size() >= 2);
    }

    @Test
    public void testUpdate() throws Exception {
        Formation f = manager.findOne(formationId);

        f.setIntitule("Update3");
        f.setCout(300);

        this.manager.update(f);

        Formation fu = manager.findOne(formationId);


        assertTrue(fu.getIntitule().equals(f.getIntitule()));
        assertTrue(fu.getCout() == f.getCout());
    }

    @Test
    public void deleteWorks() throws Exception {
        this.manager.delete(formationId);
        this.manager.findAll();
    }

    /**
     * Test n'echoue pas car les formations et blocs existent
     * @throws Exception
     */
    @Test
    public void linksBlocWorks() throws Exception {
        Bloc b = new Bloc();
        b.setCout(0);
        b.setIntitule("ok");
        b.setCode("B1S1");
        blocManager.insert(b);

        System.err.println("id: " + b.getId());

        manager.linkBloc(formationId, b.getId());
    }

    /**
     * Test echoue car les formations et blocs existent pas
     * @throws Exception
     */
    @Test
    public void linksBlocFails() throws Exception {
        Bloc b = new Bloc();
        b.setCout(0);
        b.setIntitule("ok");
        b.setCode("B1S1");
        blocManager.insert(b);

        System.err.println("id: " + b.getId());

        assertThrows(Exception.class, () -> {
            manager.linkBloc(150000, 1500000);
        });
    }

    /**
     * bloc existe déjà dans une autre formation
     */
    @Test
    public void linksBlocFailsBecauseBlocExistOnOtherFormation() throws Exception {
        Formation f2 = this.manager.findAll().get(1);
        Formation f1 = this.manager.findAll().get(0);

        Bloc b = new Bloc();
        b.setCout(0);
        b.setIntitule("test doublon");
        b.setCode("B1S1");
        blocManager.insert(b);

        this.manager.linkBloc(f1.getId(), b.getId());


        assertThrows(Exception.class, () -> {
           this.manager.linkBloc(f2.getId(), b.getId());
        });
    }

    @Test public void testDoubleLinkNotWorking() throws Exception {
        Formation f1 = this.manager.findAll().get(0);

        Bloc b = new Bloc();
        b.setCout(0);
        b.setIntitule("test doublon");
        b.setCode("B1S1");
        blocManager.insert(b);

        this.manager.linkBloc(f1.getId(), b.getId());

        // try again
        assertThrows(Exception.class, () -> {
            this.manager.linkBloc(f1.getId(), b.getId());
        });
    }

    @Test
    public void removeBlocWorks() throws Exception {
        Formation f1 = this.manager.findAll().get(0);

        Bloc b = new Bloc();
        b.setCout(0);
        b.setIntitule("test doublon");
        b.setCode("B1S1jo");
        blocManager.insert(b);

        this.manager.unlinkBloc(f1.getId(), b.getId());
        Formation assertRemoved = this.manager.findOne(f1.getId());
        assertTrue(assertRemoved.getBlocs().contains(b) == false);
    }

    @Test
    public void doubleRemoveHasNotProblem() throws Exception {
        Formation f1 = this.manager.findAll().get(0);

        Bloc b = new Bloc();
        b.setCout(0);
        b.setIntitule("test doublonplkp");
        b.setCode("B1S1");
        blocManager.insert(b);

        this.manager.linkBloc(f1.getId(), b.getId());

        f1.removeBloc(b);
        f1.removeBloc(b);

        Formation fbis = this.manager.findAll().get(0);

        assertTrue(fbis.getBlocs().contains(b) == false);
    }

}
