package m2info.ter.decofo.manager.gestion;

import m2info.ter.decofo.classes.*;
import m2info.ter.decofo.exceptions.FormationParentNotFoundException;
import m2info.ter.decofo.exceptions.ItemExistInListException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * README
 * Veuillez lancer les tests un par un
 */
@SpringBootTest
public class BlocManagerTest {

    @Autowired
    FormationManager formationManager;

    @Autowired
    BlocManager manager;

    @Autowired
    UEManager ueManager;

    @Autowired
    OptionManager optionManager;

    private int blocId;

    @BeforeEach
    public void befa() {
        Bloc b = new Bloc("test", "test");
        b.setEffectif(new Effectif(0, 0, 0, 0));
        manager.insert(b);
        blocId = b.getId();
        System.err.println("Bloc ID = " + b);
    }

    @AfterEach
    public void after() throws Exception {
        Bloc b = manager.findOne(blocId);
        if(b!= null)
            manager.delete(b.getId());
    }

    @Test
    public void managerExists() {
        assertTrue(manager != null);
    }

    @Test public void insertOne() {
        Bloc b = new Bloc("s1-ild", "Semestre 1 ild");
        manager.insert(b);
    }

    @Test
    public void findOne() throws Exception {
        Bloc b = manager.findOne(blocId);
        assertNotNull(b);
    }

    @Test
    public void update() throws Exception {
        Bloc n = manager.findOne(blocId);
        n.setCode("s2-ild");
        n.setIntitule("Semestre 2 ild");
        n.setCout(250);
        manager.update(n);
    }

    @Test
    public void findAll() {
        Bloc b1 = new Bloc("M1-ild", "Master 1 ild");
        Bloc b2 = new Bloc("M2-ild", "Master 2 ild");

        manager.insert(b1);
        manager.insert(b2);

        List<Bloc> blocList = manager.findAll();

        assertTrue(blocList.size() >= 0);

    }

    @Test
    public void delete() throws Exception {
        this.manager.delete(blocId);
        this.manager.findAll();
    }

    // test link bloc-ue
    // ----------------------------------------------------------------

    @Test
    public void addUEWorks() throws Exception {
        // crée et insère une formation avec bloc et ue
        Formation f = new Formation("M7ILD", "M3 - ILD",250, 2, 3);
        f.addBloc(new Bloc("M2-ild", "Master 2 ild"));
        f.addUE(new UE("JEE","M2 - ILD", 2, 3,5,5,6,7,6));
        formationManager.insert(f);

        // s'assurer que le lien est fait
        Formation insertedFormation = formationManager.findOne(f.getId());
        assertTrue(insertedFormation.getUEs().size() >= 1);
        assertTrue(insertedFormation.getBlocs().size() >= 1);

        Bloc linkedBloc = insertedFormation.getBlocs().get(0);
        UE linkedUE = insertedFormation.getUEs().get(0);
        manager.linkUE(linkedBloc.getId(), linkedUE.getId());

        Bloc test = manager.findOne(linkedBloc.getId());
        assertTrue(test.getUes().contains(linkedUE));

        formationManager.delete(f.getId());
    }

    /**
     * bloc n'existe pas
     */
    @Test
    public void linkUENotWorking1() {
        assertThrows(NotFoundObjectException.class, ()-> {
           manager.linkUE(1598798456, 0);
        });
    }

    /**
     * ue n'existe pas
     */
    @Test
    public void linkUENotWorking2() throws Exception {
        Bloc b  = new Bloc("code", "intitulé");
        manager.insert(b);
        assertThrows(NotFoundObjectException.class, ()-> {
            manager.linkUE(b.getId(), 51645642);
        });

        manager.delete(b.getId());
    }


    /**
     * bloc n'a pas d'owner
     */
    @Test
    public void linkUENotWorking3() throws Exception {
        Bloc b  = new Bloc("code", "intitulé");
        manager.insert(b);
        UE ueToAdd = new UE("JEE-64g64f","M2 - ILD", 5, 2, 3,5,5,7,6);
        ueManager.insert(ueToAdd);

        assertThrows(FormationParentNotFoundException.class, () -> {
            manager.linkUE(b.getId(), ueToAdd.getId());
        });

        ueManager.delete(ueToAdd.getId());
        manager.delete(b.getId());
    }

    /**
     * bloc contient deje UE
     */
    @Test
    public void linkUENotWorking4() throws Exception {
        // créer
        Formation f = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        f.addBloc(new Bloc("M2-ild", "Master 2 ild"));
        f.addUE(new UE("JEE","M2 - ILD", 5, 2, 3,5,5,7,6));
        formationManager.insert(f);

        // LIER
        Formation insertedFormation = formationManager.findOne(f.getId());
        Bloc linkedBloc = insertedFormation.getBlocs().get(0);
        UE linkedUE = insertedFormation.getUEs().get(0);
        manager.linkUE(linkedBloc.getId(), linkedUE.getId());

        // réitérer (fail normalement)
        assertThrows(ItemExistInListException.class, () -> {
           manager.linkUE(linkedBloc.getId(), linkedUE.getId());
        });

        formationManager.delete(f.getId());
    }

    @Test
    public void unlinkUEWorks() throws Exception {
        // créer
        Formation f = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        f.addBloc(new Bloc("M2-ild", "Master 2 ild"));
        f.addUE(new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6));
        formationManager.insert(f);

        // LIER
        Formation insertedFormation = formationManager.findOne(f.getId());
        Bloc linkedBloc = insertedFormation.getBlocs().get(0);
        UE linkedUE = insertedFormation.getUEs().get(0);
        manager.linkUE(linkedBloc.getId(), linkedUE.getId());
        // tester
        Bloc test = manager.findOne(linkedBloc.getId());
        assertTrue(test.getUes().contains(linkedUE));

        // délier
        manager.unlinkUE(linkedBloc.getId(), linkedUE.getId());
        Bloc testUnlinked = manager.findOne(linkedBloc.getId());
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
           manager.unlinkUE(46876466, 0);
        });
    }

    /**
     * ue n'existe pas
     */
    @Test
    public void unlinkUENotWorking2() throws Exception {
        Bloc b = new Bloc("intitule", "code");
        manager.insert(b);
        assertThrows(NotFoundObjectException.class, () -> {
            manager.unlinkUE(b.getId(), 0);
        });

        manager.delete(b.getId());
    }

    /**
     * bloc contient pas ue
     */
    @Test
    public void unlinkUENotWorking3() throws Exception {
        Bloc b = new Bloc("intitule", "code");
        manager.insert(b);
        UE ue = new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6);
        ueManager.insert(ue);

        assertThrows(NotFoundObjectException.class, () -> {
            manager.unlinkUE(b.getId(), ue.getId());
        });

        manager.delete(b.getId());
        ueManager.delete(ue.getId());
    }


    // test link bloc-option
    // ----------------------------------------------------------------

    @Test
    public void addOptionWorks() throws Exception {
        // crée et insère une formation avec bloc et ue
        Formation f = new Formation("M7ILD",  "M3 - ILD", 5, 2, 3);
        f.addBloc(new Bloc("M2-ild", "Master 2 ild"));
        f.addOption(new Option("test1","Test1.1", 8));
        formationManager.insert(f);

        // s'assurer que le lien est fait
        Formation insertedFormation = formationManager.findOne(f.getId());
        assertTrue(insertedFormation.getOptions().size() >= 1);
        assertTrue(insertedFormation.getBlocs().size() >= 1);

        Bloc linkedBloc = insertedFormation.getBlocs().get(0);
        Option linkedOption = insertedFormation.getOptions().get(0);
        manager.linkOption(linkedBloc.getId(), linkedOption.getId());

        Bloc test = manager.findOne(linkedBloc.getId());
        assertTrue(test.getOptions().contains(linkedOption));

        formationManager.delete(f.getId());
    }

    /**
     * bloc n'existe pas
     */
    @Test
    public void linkOptionNotWorking1() {
        assertThrows(NotFoundObjectException.class, ()-> {
            manager.linkOption(1598798456, 0);
        });
    }

    /**
     * Option n'existe pas
     */
    @Test
    public void linkOptionNotWorking2() throws Exception {
        Bloc b  = new Bloc("code", "intitulé");
        manager.insert(b);
        assertThrows(NotFoundObjectException.class, ()-> {
            manager.linkOption(b.getId(), 51645642);
        });

        manager.delete(b.getId());
    }


    /**
     * bloc n'a pas d'owner
     */
    @Test
    public void linkOptionNotWorking3() throws Exception {
        Bloc b  = new Bloc("code", "intitulé");
        manager.insert(b);
        Option optionToAdd = new Option("test1","Test1.1", 8);
        optionManager.insert(optionToAdd);

        assertThrows(FormationParentNotFoundException.class, () -> {
            manager.linkOption(b.getId(), optionToAdd.getId());
        });

        manager.delete(b.getId());
        optionManager.delete(optionToAdd.getId());
    }

    /**
     * bloc contient deje UE
     */
    @Test
    public void linkOptionNotWorking4() throws Exception {
        // créer
        Formation f = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        f.addBloc(new Bloc("M2-ild", "Master 2 ild"));
        f.addOption(new Option("test1","Test1.1", 8));
        formationManager.insert(f);

        // LIER
        Formation insertedFormation = formationManager.findOne(f.getId());
        Bloc linkedBloc = insertedFormation.getBlocs().get(0);
        Option linkedOption = insertedFormation.getOptions().get(0);
        manager.linkOption(linkedBloc.getId(), linkedOption.getId());

        // réitérer (fail normalement)
        assertThrows(ItemExistInListException.class, () -> {
            manager.linkOption(linkedBloc.getId(), linkedOption.getId());
        });

        formationManager.delete(f.getId());
    }

    @Test
    public void unlinkOptionWorks() throws Exception {
        // créer
        Formation f = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        f.addBloc(new Bloc("M2-ild", "Master 2 ild"));
        f.addOption(new Option("test1","Test1.1",8));
        formationManager.insert(f);

        // LIER
        Formation insertedFormation = formationManager.findOne(f.getId());
        Bloc linkedBloc = insertedFormation.getBlocs().get(0);
        Option linkedOption = insertedFormation.getOptions().get(0);
        manager.linkOption(linkedBloc.getId(), linkedOption.getId());
        // tester
        Bloc test = manager.findOne(linkedBloc.getId());
        assertTrue(test.getOptions().contains(linkedOption));

        // délier
        manager.unlinkOption(linkedBloc.getId(), linkedOption.getId());
        Bloc testUnlinked = manager.findOne(linkedBloc.getId());
        // tester laisaion marche pas
        assertTrue(testUnlinked.getOptions().contains(linkedOption) == false);

        formationManager.delete(f.getId());
    }

    /**
     * bloc n'existe pas
     */
    @Test
    public void unlinkOptionNotWorking1() {
        assertThrows(NotFoundObjectException.class, () -> {
            manager.unlinkOption(46876466, 0);
        });
    }

    /**
     * ue n'existe pas
     */
    @Test
    public void unlinkOptionNotWorking2() throws Exception {
        Bloc b = new Bloc("intitule", "code");
        manager.insert(b);
        assertThrows(NotFoundObjectException.class, () -> {
            manager.unlinkOption(b.getId(), 0);
        });
        manager.delete(b.getId());
    }

    /**
     * bloc contient pas ue
     */
    @Test
    public void unlinkOptionNotWorking3() throws Exception {
        Bloc b = new Bloc("intitule", "code");
        manager.insert(b);
        Option o = new Option("test1","Test1.1", 8);
        optionManager.insert(o);

        assertThrows(NotFoundObjectException.class, () -> {
            manager.unlinkOption(b.getId(), o.getId());
        });

        manager.delete(b.getId());
        optionManager.delete(o.getId());
    }

}
