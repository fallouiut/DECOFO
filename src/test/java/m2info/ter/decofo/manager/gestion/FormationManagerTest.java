package m2info.ter.decofo.manager.gestion;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.dao.DAOBloc;
import m2info.ter.decofo.dao.DAOUe;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.exceptions.ItemExistInListException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import org.junit.jupiter.api.AfterEach;
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
    public void befa() {
        Formation f = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        manager.insert(f);
        formationId = f.getId();
        System.err.println("Formation ID = " + formationId);
    }


    @AfterEach
    public void after() throws Exception {
        Formation f = manager.findOne(formationId);
        if(f!= null)
            manager.delete(f.getId());
    }

    @Test
    public void testInsertAndDelete() throws Exception {

        Formation f = new Formation("M2ILD", "M2 - ILD", 5, 2, 3);
        manager.insert(f);
        assertTrue( manager.findOne(f.getId()) != null);
    }

    @Test
    public void testFindAll() {
        Formation f = new Formation("M3ILD", "M3 - ILD", 5, 2, 3);
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
        assertTrue(fu.getCode() == f.getCode());
    }

    @Test
    public void deleteWorks() throws Exception {
        this.manager.delete(formationId);
        assertTrue(manager.findOne(formationId) == null);
    }

    // test link formation-bloc
    // ----------------------------------------------------------------
    @Test
    public void addBlocWorks() throws Exception {
        Bloc b = new Bloc("test", "intitule");

        manager.addBloc(formationId, b);
        assertTrue(manager.findOne(formationId).getBlocs().size() >= 1);
    }

    /**
     * formation existe pas
     */
    @Test
    public void addBlocFailsFormationNotExist() {

        Bloc b = new Bloc("test", "intitule");

        assertThrows(NotFoundObjectException.class, () -> {
            manager.addBloc(7974648, b);
        });

        assertTrue(manager.findOne(formationId).getBlocs().size() == 0);
    }

    /**
     * bloc existe deja
     */
    @Test
    public void addBlocFailsBlocAlreadyExiste() throws NotFoundObjectException, ItemExistInListException {

        Bloc b = new Bloc("test", "intitule");
        manager.addBloc(formationId, b);

        Bloc found = manager.findOne(formationId).getBlocs().get(0);

        assertThrows(ItemExistInListException.class, () -> {
            manager.addBloc(formationId, found);
        });

    }


    @Test
    public void removeBlocWorks() throws DecofoException {

        Bloc b = new Bloc("test", "intitule");

        manager.addBloc(formationId, b);
        assertTrue(manager.findOne(formationId).getBlocs().size() >= 1);

        Bloc bFound = manager.findOne(formationId).getBlocs().get(0);
        manager.removeBloc(formationId, bFound.getId());

        assertTrue(manager.findOne(formationId).getBlocs().size() == 0);

    }

    /**
     * formation existe pas
     */
    @Test
    public void removeBlocFailsFormationNotExist() {

        Bloc b = new Bloc("test", "intitule");


        assertThrows(NotFoundObjectException.class, () -> {
            manager.addBloc(7974648, null);
        });

    }
    /**
     * bloc existe pas
     */
    @Test
    public void removeBlocFailsBlocNotExist() throws NotFoundObjectException, ItemExistInListException {

        assertThrows(NotFoundObjectException.class, () -> {
            manager.removeBloc(formationId, 75555);
        });

    }
    /**
     * bloc existe pas
     */
    @Test
    public void removeBlocFailsBlocNotExisteOnList() throws NotFoundObjectException, ItemExistInListException {

        Bloc b = new Bloc("test", "intitule");

        assertThrows(NotFoundObjectException.class, () -> {
            manager.removeBloc(formationId, b.getId());
        });

    }

    // test link formation-Option
    // ----------------------------------------------------------------

    @Test
    public void addOptionWorks() throws Exception {
        Option option = new Option("test", "intitule", 0);

        manager.addOption(formationId, option);
        assertTrue(manager.findOne(formationId).getOptions().size() >= 1);
    }

    /**
     * formation existe pas
     */
    @Test
    public void addOptionFailsFormationNotExist() {

        Option option = new Option("test", "intitule", 0);

        assertThrows(NotFoundObjectException.class, () -> {
            manager.addOption(7974648, option);
        });

        assertTrue(manager.findOne(formationId).getOptions().size() == 0);
    }

    /**
     * option existe deja
     */
    @Test
    public void addOptionFailsOptionAlreadyExiste() throws NotFoundObjectException, ItemExistInListException {
        Option option = new Option("test", "intitule", 0);

        manager.addOption(formationId, option);
        Option found = manager.findOne(formationId).getOptions().get(0);

        assertThrows(ItemExistInListException.class, () -> {
            manager.addOption(formationId, found);
        });

    }


    @Test
    public void removeOptionWorks() throws DecofoException {

        Option option = new Option("test", "intitule", 0);

        manager.addOption(formationId, option);
        assertTrue(manager.findOne(formationId).getOptions().size() >= 1);

        Option oFound = manager.findOne(formationId).getOptions().get(0);
        manager.removeOption(formationId, oFound.getId());

        assertTrue(manager.findOne(formationId).getOptions().size() == 0);

    }

    /**
     * formation existe pas
     */
    @Test
    public void removeOptionFailsFormationNotExist() {
        assertThrows(NotFoundObjectException.class, () -> {
            manager.addOption(7974648, null);
        });

    }
    /**
     * option existe pas
     */
    @Test
    public void removeOptionFailsBlocNotExist() throws NotFoundObjectException, ItemExistInListException {

        assertThrows(NotFoundObjectException.class, () -> {
            manager.removeOption(formationId, 75555);
        });

    }
    /**
     * option existe pas dans formation
     */
    @Test
    public void removeOptionFailsBlocNotExisteOnList() throws NotFoundObjectException, ItemExistInListException {

        Option option = new Option("test", "intitule", 0);

        assertThrows(NotFoundObjectException.class, () -> {
            manager.removeOption(formationId, option.getId());
        });

    }

    // test link formation-ue
    // ----------------------------------------------------------------

    @Test
    public void addUEWorks() throws Exception {
        UE ue = new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6);

        manager.addUE(formationId, ue);
        assertTrue(manager.findOne(formationId).getUEs().size() >= 1);
    }

    /**
     * formation existe pas
     */
    @Test
    public void addUEFailsFormationNotExist() {

        UE ue = new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6);

        assertThrows(NotFoundObjectException.class, () -> {
            manager.addUE(7974648, ue);
        });

        assertTrue(manager.findOne(formationId).getUEs().size() == 0);
    }

    /**
     * option existe deja
     */
    @Test
    public void addUEFailsOptionAlreadyExiste() throws NotFoundObjectException, ItemExistInListException {
        UE ue = new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6);

        manager.addUE(formationId, ue);
        UE found = manager.findOne(formationId).getUEs().get(0);

        assertThrows(ItemExistInListException.class, () -> {
            manager.addUE(formationId, found);
        });

    }


    @Test
    public void removeUEWorks() throws DecofoException {

        UE ue = new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6);

        // ajout ue
        manager.addUE(formationId, ue);
        assertTrue(manager.findOne(formationId).getUEs().size() >= 1);


        Formation test = manager.findOne(formationId);
        System.err.println(test.getUEs().toString());

        //tester ue exist
        UE eFound = manager.findOne(formationId).getUEs().get(0);
        System.err.println("Ue " + eFound.toString());
        assertTrue(manager.findOne(formationId).getUEs().contains(eFound));

        //remove ue
        manager.removeUE(formationId, eFound.getId());
        //assuere
        assertTrue(manager.findOne(formationId).getUEs().size() == 0);

    }

    /**
     * formation existe pas
     */
    @Test
    public void removeUEFailsFormationNotExist() {
        assertThrows(NotFoundObjectException.class, () -> {
            manager.addUE(7974648, null);
        });

    }
    /**
     * option existe pas
     */
    @Test
    public void removeUEFailsBlocNotExist() throws NotFoundObjectException, ItemExistInListException {

        assertThrows(NotFoundObjectException.class, () -> {
            manager.removeUE(formationId, 75555);
        });

    }
    /**
     * option existe pas dans formation
     */
    @Test
    public void removeUEFailsBlocNotExisteOnList() throws NotFoundObjectException, ItemExistInListException {

        Option option = new Option("test", "intitule", 0);

        assertThrows(NotFoundObjectException.class, () -> {
            manager.removeUE(formationId, option.getId());
        });

    }


}
