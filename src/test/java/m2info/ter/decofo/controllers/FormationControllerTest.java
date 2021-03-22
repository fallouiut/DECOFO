package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.exceptions.FormationParentNotFoundException;
import m2info.ter.decofo.exceptions.ItemExistInListException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.manager.gestion.BlocManager;
import m2info.ter.decofo.manager.gestion.FormationManager;
import m2info.ter.decofo.manager.gestion.OptionManager;
import m2info.ter.decofo.manager.gestion.UEManager;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FormationControllerTest {

    @Autowired
    FormationManager formationManager;

    @Autowired
    BlocManager blocManager;

    @Autowired
    OptionManager optionManager;

    @Autowired
    UEManager ueManager;

    Formation formationTest;

    /**
     * crée une formation de test
     */
    @BeforeEach
    public void beforeEach() throws NotFoundObjectException, ItemExistInListException {
        Formation f = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        formationManager.insert(f);
        formationTest = formationManager.findOne(f.getId());

        Bloc bloc1 = new Bloc("B1", "Bloc 1");
        Bloc bloc2 = new Bloc("B2", "Bloc 2");

        Option o1 = new Option("O1", "option 1", 5);
        Option o2 = new Option("O1", "option 1", 5);

        UE ue1 = new UE("UE1", "UE 1", 0, 0, 0, 0, 0, 0, 5);
        UE ue2 = new UE("UE2", "UE 2", 0, 0, 0, 0, 0, 0, 5);

        System.err.println("ID Formation: " + f.getId());
        formationManager.addBloc(formationTest.getId(), bloc1);
        formationManager.addBloc(formationTest.getId(), bloc2);
        formationManager.addOption(formationTest.getId(), o1);
        formationManager.addOption(formationTest.getId(), o2);
        formationManager.addUE(formationTest.getId(), ue1);
        formationManager.addUE(formationTest.getId(), ue2);

        formationTest = formationManager.findOne(f.getId());

    }

    @AfterEach
    public void afterEach() throws Exception {
        formationManager.delete(formationTest.getId());
    }

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(formationTest);
    }

    /**
     * ON CREE UNE FORMATION
     * UN BLOC
     * DEUX UES
     * ON LIE LES DEUX UES AU BLOC
     * ON TEST SUPPRESSION BLOC
     */
    @Test
    public void test1() throws NotFoundObjectException, ItemExistInListException, FormationParentNotFoundException {

        int blocId = formationTest.getBlocs().get(0).getId();
        int ueId1  = formationTest.getUEs().get(0).getId();
        int ueId2  = formationTest.getUEs().get(1).getId();

        blocManager.linkUE(blocId, ueId1);
        blocManager.linkUE(blocId, ueId2);

        // verif bloc
        Bloc bloctest = blocManager.findOne(blocId);
        assertEquals(2, bloctest.getUes().size());

        // remove bloc
        formationManager.removeBloc(formationTest.getId(), blocId);
        Formation test1 = formationManager.findOne(formationTest.getId());
        assertEquals (test1.getBlocs().size(), 1);

        // VÉRIFIE QUE LUE NA PLUS DE BLOC
        assertEquals(0, ueManager.findOne(ueId1).getBlocs().size());
        assertEquals(0, ueManager.findOne(ueId2).getBlocs().size());
    }

    /**
     * ON CREE UNE FORMATION
     * UN BLOC
     * DEUX UES
     * ON LIE LES DEUX UES AU BLOC
     * ON TEST SUPPRESSION UE
     */
    @Test
    public void test2() throws NotFoundObjectException, ItemExistInListException, FormationParentNotFoundException {

        int blocId = formationTest.getBlocs().get(0).getId();
        int ueId1 = formationTest.getUEs().get(0).getId();
        int ueId2 = formationTest.getUEs().get(1).getId();

        blocManager.linkUE(blocId, ueId1);
        blocManager.linkUE(blocId, ueId2);

        // remove UE
        formationManager.removeUE(formationTest.getId(), ueId1);
        Formation test1 = formationManager.findOne(formationTest.getId());
        assertEquals (test1.getUEs().size(), 1);

        // verif bloc
        Bloc bloctest = blocManager.findOne(blocId);
        assertEquals(1, bloctest.getUes().size());
    }

    /**
     * ON CREE UNE FORMATION
     * UN BLOC
     * DEUX OTIONS
     * ON LIE LES DEUX UES AU BLOC
     * ON TEST SUPPRESSION OPTION
     */
    @Test
    public void createFormation3() throws NotFoundObjectException, ItemExistInListException, FormationParentNotFoundException {

        int blocId = formationTest.getBlocs().get(0).getId();
        int o1Id = formationTest.getOptions().get(0).getId();
        int o2Id = formationTest.getOptions().get(1).getId();

        blocManager.linkOption(blocId, o1Id);
        blocManager.linkOption(blocId, o2Id);

        // remove UE
        formationManager.removeOption(formationTest.getId(), o1Id);
        Formation test1 = formationManager.findOne(formationTest.getId());
        assertEquals (test1.getOptions().size(), 1);

        // verif bloc
        Bloc bloctest = blocManager.findOne(blocId);
        assertEquals(1, bloctest.getOptions().size());
    }

    /**
     * supprime option quii contient des UES
     */
    @Test
    public void createFormation4() throws DecofoException {
        int oid = formationTest.getOptions().get(0).getId();
        int ueId1 = formationTest.getUEs().get(0).getId();
        int ueId2 = formationTest.getUEs().get(1).getId();

        // lier options - ue1, ue2
        optionManager.linkUE(oid, ueId1);
        optionManager.linkUE(oid, ueId2);
        Option optionTest = optionManager.findOne(oid);
        assertEquals(2, optionTest.getUes().size());

        // supprimer l'option
        formationManager.removeOption(formationTest.getId(), oid);

        // vérif qu'il n'y a plus d'options
        assertEquals(1, formationManager.findOne(formationTest.getId()).getOptions().size());
        assertEquals(2, formationManager.findOne(formationTest.getId()).getUEs().size());
        assertEquals(0, ueManager.findOne(ueId1).getOptions().size());
        assertEquals(0, ueManager.findOne(ueId2).getOptions().size());

    }

    /**
     * supprime UE quii contient une option
     */
    @Test
    public void createFormation5() throws DecofoException {
        int oid = formationTest.getOptions().get(0).getId();
        int ueId1 = formationTest.getUEs().get(0).getId();
        int ueId2 = formationTest.getUEs().get(1).getId();

        // lier options - ue1, ue2
        optionManager.linkUE(oid, ueId1);
        optionManager.linkUE(oid, ueId2);
        Option optionTest = optionManager.findOne(oid);
        assertEquals(2, optionTest.getUes().size());

        // supprimer l'option
        optionManager.unlinkUE(oid, ueId1);

        // vérif qu'il n'y a plus d'options
        assertEquals(1, optionManager.findOne(oid).getUes().size());
        assertEquals(2, formationManager.findOne(formationTest.getId()).getUEs().size());
        assertEquals(0, ueManager.findOne(ueId1).getOptions().size());
        assertEquals(1, ueManager.findOne(ueId2).getOptions().size());

    }

}
