package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.exceptions.ServerErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class FormationManagerTest {
/*
    @Autowired
    FormationManager manager;


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
    public void testUpdate() {
        Formation f = manager.findOne(1);

        f.setIntitule("Update3");
        f.setCout(300);

        this.manager.update(f);

        Formation fu = manager.findOne(1);

        //this.manager.findAll();

        assertTrue(fu.getIntitule().equals(f.getIntitule()));
        assertTrue(fu.getCout() == f.getCout());
    }

    @Test
    public void checkUpdate() {
        this.manager.findAll();
    }

    @Test
    public void deleteWorks() throws Exception {
        this.manager.delete(9);
        this.manager.findAll();
    }

    @Test
    public void deleteNotWorks() {
        assertThrows(ServerErrorResponse.class, () -> {
            this.manager.delete(15);
            this.manager.findAll();
        });
    }
*/
}
