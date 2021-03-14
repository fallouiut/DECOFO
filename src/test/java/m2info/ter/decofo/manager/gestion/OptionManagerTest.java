package m2info.ter.decofo.manager.gestion;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.exceptions.FormationParentNotFoundException;
import m2info.ter.decofo.exceptions.ItemExistInListException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;

/**
 * README
 * Veuillez lancer les tests un par un
 */
@SpringBootTest
public class OptionManagerTest {

    @Autowired
    FormationManager formationManager;

    @Autowired
    OptionManager optionManager;

    @Autowired
    UEManager ueManager;

    private static int optionId;

    @BeforeEach
    public void trouverId() {
        optionId = optionManager.findAll().get(0).getId();
        System.err.println("UEid = " + optionId);
    }

    @Test
    public void testInsert(){
        Option option = new Option("test1","Test1.1",5, 8);
        this.optionManager.insert(option);

    }

    @Test
    public void findOne() {
        Option option = this.optionManager.findOne(optionId);
        assertNotNull(option);
        System.err.println("Find one : + " + option.toString());
        System.err.println("-----------------------------");
    }

    @Test
    public void testFindAll(){
        Option option = new Option("test2","test2.2",8,7);
        this.optionManager.insert(option);

        List<Option> optionList = optionManager.findAll();
        System.err.println("Size of tab: " + optionList.size());
        assertTrue( optionList.size() >= 2);
    }

    @Test
    public void testUpdate() throws Exception {
        Option option = optionManager.findOne(optionId);

        option.setIntitule("Test1.1 update");
        option.setCout(20);

        this.optionManager.update(option);
    }

    @Test
    public void deleteOptions() throws Exception {
        this.optionManager.delete(optionId);
    }


    // test link bloc-ue
    // ----------------------------------------------------------------

    @Test
    public void addUEWorks() throws Exception {
        // crée et insère une formation avec bloc et ue
        Formation f = new Formation("M7ILD", 250, "M3 - ILD", 5, 2, 3);
        f.addOption(new Option("test2","test2.2",8,7));
        f.addUE(new UE("JEE","M2 - ILD", 5, 2, 3,5,5,6,7,6));
        formationManager.insert(f);

        // s'assurer que le lien est fait
        Formation insertedFormation = formationManager.findOne(f.getId());
        assertTrue(insertedFormation.getUEs().size() >= 1);
        assertTrue(insertedFormation.getOptions().size() >= 1);

        Option linkedOption = insertedFormation.getOptions().get(0);
        UE linkedUE = insertedFormation.getUEs().get(0);
        optionManager.linkUE(linkedOption.getId(), linkedUE.getId());

        Option test = optionManager.findOne(linkedOption.getId());
        assertTrue(test.getUes().contains(linkedUE));

        formationManager.delete(f.getId());
    }

    /**
     * bloc n'existe pas
     */
    @Test
    public void linkUENotWorking1() {
        assertThrows(NotFoundObjectException.class, ()-> {
            optionManager.linkUE(1598798456, 0);
        });
    }

    /**
     * ue n'existe pas
     */
    @Test
    public void linkUENotWorking2() throws Exception {
        Option o = new Option("test2","test2.2",8,7);
        optionManager.insert(o);
        assertThrows(NotFoundObjectException.class, ()-> {
            optionManager.linkUE(o.getId(), 51645642);
        });

        optionManager.delete(o.getId());
    }


    /**
     * bloc n'a pas d'owner
     */
    @Test
    public void linkUENotWorking3() throws Exception {
        Option o = new Option("test2","test2.2",8,7);
        optionManager.insert(o);
        UE ueToAdd = new UE("JEE-64g64f","M2 - ILD", 5, 2, 3,5,5,6,7,6);
        ueManager.insert(ueToAdd);

        assertThrows(FormationParentNotFoundException.class, () -> {
            optionManager.linkUE(o.getId(), ueToAdd.getId());
        });

        ueManager.delete(ueToAdd.getId());
        optionManager.delete(o.getId());
    }

    /**
     * bloc contient deje UE
     */
    @Test
    public void linkUENotWorking4() throws Exception {
        // créer
        Formation f = new Formation("M7ILD", 250, "M3 - ILD", 5, 2, 3);
        f.addOption(new Option("test2","test2.2",8,7));
        f.addUE(new UE("JEE","M2 - ILD", 5, 2, 3,5,5,6,7,6));
        formationManager.insert(f);

        // LIER
        Formation insertedFormation = formationManager.findOne(f.getId());
        Option linkedOption = insertedFormation.getOptions().get(0);
        UE linkedUE = insertedFormation.getUEs().get(0);
        optionManager.linkUE(linkedOption.getId(), linkedUE.getId());

        // réitérer (fail normalement)
        assertThrows(ItemExistInListException.class, () -> {
            optionManager.linkUE(linkedOption.getId(), linkedUE.getId());
        });

        formationManager.delete(f.getId());
    }

    @Test
    public void unlinkUEWorks() throws Exception {
        // créer
        Formation f = new Formation("M7ILD", 250, "M3 - ILD", 5, 2, 3);
        f.addOption(new Option("test2","test2.2",8,7));
        f.addUE(new UE("JEE","M2 - ILD", 5, 2, 3,5,5,6,7,6));
        formationManager.insert(f);

        // LIER
        Formation insertedFormation = formationManager.findOne(f.getId());
        Option linkedOption = insertedFormation.getOptions().get(0);
        UE linkedUE = insertedFormation.getUEs().get(0);
        optionManager.linkUE(linkedOption.getId(), linkedUE.getId());
        // tester
        Option test = optionManager.findOne(linkedOption.getId());
        assertTrue(test.getUes().contains(linkedUE));

        // délier
        optionManager.unlinkUE(linkedOption.getId(), linkedUE.getId());
        Option testUnlinked = optionManager.findOne(linkedOption.getId());
        // tester laisaion marche pas
        assertTrue(testUnlinked.getUes().contains(linkedUE) == false);

        formationManager.delete(f.getId());
    }

    /**
     * bloc n'existe pas
     */
    @Test
    public void unlinkUENotWorking1() {
        assertThrows(NotFoundObjectException.class, () -> {
            optionManager.unlinkUE(46876466, 0);
        });
    }

    /**
     * ue n'existe pas
     */
    @Test
    public void unlinkUENotWorking2() throws Exception {
        Option o = new Option("test2","test2.2",8,7);
        optionManager.insert(o);
        assertThrows(NotFoundObjectException.class, () -> {
            optionManager.unlinkUE(o.getId(), 0);
        });

        optionManager.delete(o.getId());
    }

    /**
     * bloc contient pas ue
     */
    @Test
    public void unlinkUENotWorking3() throws Exception {
        Option o = new Option("test2","test2.2",8,7);
        optionManager.insert(o);
        UE ue = new UE("JEE","M2 - ILD", 5, 2, 3,5,5,6,7,6);
        ueManager.insert(ue);

        assertThrows(NotFoundObjectException.class, () -> {
            optionManager.unlinkUE(o.getId(), ue.getId());
        });

        optionManager.delete(o.getId());
        ueManager.delete(ue.getId());
    }

}
