package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BlocManagerTest {

    @Autowired
    BlocManager manager;

    @Test
    public void managerExists() {
        assertTrue(manager != null);
        assertTrue(manager.entityManager() != null);
    }

    @Test public void insertOne() {
        Bloc b = new Bloc("s1-ild", "Semestre 1 ild", 20.0);
        manager.insert(b);
    }

    @Test
    public void findOne() throws Exception {
        Bloc b = manager.findOne(22);
        System.err.println(b);
        assertNotNull(b);
    }

    @Test
    public void update() throws Exception {
        Bloc n = manager.findOne(22);
        n.setCode("s2-ild");
        n.setIntitule("Semestre 2 ild");
        n.setCout(250);
        manager.update(n);
    }

    @Test
    public void findAll() {
        Bloc b1 = new Bloc("M1-ild", "Master 1 ild", 1000.0);
        Bloc b2 = new Bloc("M2-ild", "Master 2 ild", 1000.0);

        manager.insert(b1);
        manager.insert(b2);

        List<Bloc> blocList = manager.findAll();

        assertTrue(blocList.size() >= 3);

    }

    @Test
    public void delete() throws Exception {
        this.manager.delete(22);
        this.manager.findAll();
    }


}
