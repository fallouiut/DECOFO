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
}
