package m2info.ter.decofo.manager.gestion;


import m2info.ter.decofo.classes.UE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * README
 * Veuillez lancer les tests un par un
 */
@SpringBootTest
public class UEManagerTest {

    @Autowired
    UEManager ueManager;

    // lancer test findAll() pour trouver un ID
    private static int ueId;

    @BeforeEach
    public void trouverId() {
        ueId = ueManager.findAll().get(0).getId();
        System.err.println("UEid = " + ueId);
    }

    @Test
    public void testInsert() {

        UE ue = new UE("JEE","M2 - ILD", 5, 2, 3,5,5,6,7,6);
        ueManager.insert(ue);

        ueId = ue.getId();
        System.err.println("-----------------------------");

    }

    @Test
    public void findOne() {
        UE ue = this.ueManager.findOne(ueId);
        assertNotNull(ue);
        System.err.println("Find one : + " + ue.toString());
        System.err.println("-----------------------------");
    }

    @Test
    public void testFindAll() {
        UE ue = new UE("Systeme embarqué","M2 - ILD", 9, 2, 3,5,5,6,7,6);
        ueManager.insert(ue);

        List<UE> UEList = ueManager.findAll();

        System.err.println("Size of tab: " + UEList.size());
        assertTrue( UEList.size() >= 2);
        System.err.println("-----------------------------");
    }

    @Test
    public void testUpdate() throws Exception {
        UE ue = ueManager.findOne(ueId);

        ue.setCode("Genie logiciel");
        ue.setCout(30);

        this.ueManager.update(ue);

        UE ue1 = ueManager.findOne(ueId);
        System.err.println(ue1.toString());
    }

    @Test
    public void deleteUEs() throws Exception {
        this.ueManager.delete(ueId);
        System.err.println("-----------------------------");
    }
}
