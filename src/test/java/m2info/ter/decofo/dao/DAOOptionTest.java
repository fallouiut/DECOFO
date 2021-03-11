package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * README
 * Veuillez lancer les tests un par un
 */
@SpringBootTest
public class DAOOptionTest {

    @Autowired
    DAOOption dapOption;

    @Autowired
    DAOUe daoUe;

    private int optionId;

    @BeforeEach
    public void befa() {
        Option o = new Option("cide", "intitule, ", 0, 0);
        dapOption.insert(o);
        optionId = o.getId();
        System.err.println("Option ID = " + optionId);
    }

    @AfterEach
    public void after() {
        Option o = dapOption.find(optionId);
        if(o!= null)
            dapOption.delete(o);
    }

    @Test
    public void autowireWorkds() {

        assertNotNull(dapOption);
    }

    @Test
    public void insert() {
        Option o = new Option("test1","Test1.1",5, 8);
        dapOption.insert(o);
    }

    @Test
    public void findOne() {
        Option o = dapOption.find(optionId);
        assertNotNull(o);
    }

    @Test
    public void findAll() {
        List<Option> options = dapOption.findAll();
        assertTrue( options.size() > 0);
    }

    @Test
    public void update() {
        Option toUpdate = dapOption.find(optionId);

        toUpdate.setCode("TO_UPDATE");
        dapOption.update(toUpdate);

        Option updated = dapOption.find(optionId);
        assertEquals(updated.getCode(), toUpdate.getCode());
    }

    @Test
    public void delete() {
        Option toDelete = dapOption.find(optionId);

        dapOption.delete(toDelete);

        Option deleted = dapOption.find(optionId);

        assertNull(deleted);

    }

    /**
     * Créer et insérer une UE de test
     * @return
     */
    private UE createAndInsertUE() {
        // ajouter ue
        UE ueToAdd = new UE("JEE-64g64f","M2 - ILD", 5, 2, 3,5,5,6,7,6);
        daoUe.insert(ueToAdd);
        // récupérer ue
        UE ue = daoUe.find(ueToAdd.getId());
        return ue;
    }


    @Test
    public void linkUE() {
        // ajouter ue
        Option o = dapOption.find(optionId);
        UE ue = this.createAndInsertUE();
        dapOption.linkUE(o, ue);

        Option test = dapOption.find(optionId);
        assertTrue(test.getUes().size() >= 1);
    }

    /**
     * detach UE without removing it
     */
    @Test
    public void unlinkUE() {
        // récupérer un bloc
        Option option = dapOption.find(optionId);

        // créer ue
        UE ue = this.createAndInsertUE();
        int ueId = ue.getId();

        // ajouter ue au bloc
        dapOption.linkUE(option, ue);

        // assurer UE existe
        Option optionWithUE = dapOption.find(option.getId());
        assertTrue(optionWithUE.getUes().size() > 0);
        int optionUeSize = optionWithUE.getUes().size();

        // enlever ue=
        dapOption.unlinkUE(optionWithUE, ue);
        assertTrue(optionWithUE.getUes().size() == 0);

        // vérifier ue est détaché
        Option optionWithoutUE = dapOption.find(option.getId());
        assertTrue(optionWithoutUE.getUes().size() == (optionUeSize -1));
        assertTrue(optionWithoutUE.getUes().contains(ue) == false);

        // vérifier ue non supprimé
        UE ueFound = daoUe.find(ueId);
        assertNotNull(ueFound);
    }
}

