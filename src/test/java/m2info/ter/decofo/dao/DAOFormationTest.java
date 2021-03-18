package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
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
public class DAOFormationTest {

    @Autowired
    DAOFormation daoFormation;

    @Autowired
    DAOBloc daoBloc;

    @Autowired
    DAOBloc daoOption;

    @Autowired
    DAOUe daoUE;

    private int formationId;

    @BeforeEach
    public void befa() {
        Formation f = new Formation("M7ILD", "M3 - ILD", 5, 2, 3);
        daoFormation.insert(f);
        formationId = f.getId();
        System.err.println("Formation ID = " + formationId);
    }


    @AfterEach
    public void after() {
        Formation f = daoFormation.find(formationId);
        if(f!= null)
            daoFormation.delete(f);
    }

    @Test
    public void autowireWorkds() {

        assertNotNull(daoFormation);
    }

    @Test
    public void insert() {
        Formation f = new Formation("M7ILD","M3 - ILD", 5, 2, 3);
        daoFormation.insert(f);
    }

    @Test
    public void findOne() {
        Formation f = daoFormation.find(formationId);
        assertNotNull(f);
    }

    @Test
    public void findAll() {
        List<Formation> formationList = daoFormation.findAll();
        assertTrue( formationList.size() > 0);
    }

    @Test
    public void update() {
        Formation toUpdate = daoFormation.find(formationId);

        toUpdate.setCode("TO_UPDATE");
        daoFormation.update(toUpdate);

        Formation updated = daoFormation.find(formationId);
        assertEquals(updated.getCode(), toUpdate.getCode());
    }

    @Test
    public void delete() {
        Formation toDelete = daoFormation.find(formationId);

        daoFormation.delete(toDelete);

        System.err.println("Before find deleted");
        Formation deleted = daoFormation.find(formationId);

        assertNull(deleted);
    }

    @Test
    public void addBlocs() {
        Formation formationTest = daoFormation.find(formationId);
        daoFormation.addBloc(formationTest, new Bloc("s1", "semestre"));
        daoFormation.addBloc(formationTest, new Bloc("s1", "semestre"));

        Formation found = daoFormation.find(formationId);

        assertTrue(found.getBlocs().size() >= 2);

    }

    @Test
    public void addOptions() {
        Formation formationTest = daoFormation.find(formationId);
        daoFormation.addOption(formationTest, new Option("code", "intitule", 0));
        daoFormation.addOption(formationTest, new Option("code", "intitule", 0));

        Formation found = daoFormation.find(formationId);

        assertTrue(found.getOptions().size() >= 1);
    }

    @Test
    public void addUE() {
        Formation formationTest = daoFormation.find(formationId);
        daoFormation.addUE(formationTest, new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6));
        daoFormation.addUE(formationTest, new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6));

        Formation found = daoFormation.find(formationId);
        assertTrue(found.getUEs().size() >= 1);

    }

    @Test
    public void deleteCascade() {
        Formation formationTest = daoFormation.find(formationId);
        daoFormation.addBloc(formationTest, new Bloc("s1", "semestre"));
        daoFormation.addUE(formationTest, new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6));
        daoFormation.addOption(formationTest, new Option("code", "intitule", 0));


        Formation found = daoFormation.find(formationId);

        int ueId = found.getUEs().get(0).getId();
        int optionId = found.getOptions().get(0).getId();
        int blocId = found.getBlocs().get(0).getId();

        daoFormation.delete(found);
        assertTrue(daoFormation.find(formationId) == null);

        assertNotNull(daoBloc.find(blocId) == null);
        assertNotNull(daoOption.find(optionId) == null);
        assertNotNull(daoUE.find(ueId) == null);
    }


    @Test
    public void deleteBloc() {
        Formation formationTest = daoFormation.find(formationId);
        daoFormation.addBloc(formationTest, new Bloc("s1", "semestre"));

        Formation found = daoFormation.find(formationId);
        Bloc b = found.getBlocs().get(0);
        int blocId = b.getId();

        daoFormation.removeBloc(found, b);

        assertNull(daoBloc.find(blocId));
    }

    @Test
    public void deleteOption() {
        Formation formationTest = daoFormation.find(formationId);
        daoFormation.addOption(formationTest, new Option("code", "intitule", 0));

        Formation found = daoFormation.find(formationId);
        Option o = found.getOptions().get(0);
        int id = o.getId();

        daoFormation.removeOption(found, o);

        assertNull(daoBloc.find(id));
    }

    @Test
    public void deleteUE() {
        Formation formationTest = daoFormation.find(formationId);
        daoFormation.addUE(formationTest, new UE("JEE","M2 - ILD", 5, 2, 3,5,6,7,6));

        Formation found = daoFormation.find(formationId);
        UE ue = found.getUEs().get(0);
        int id = ue.getId();

        daoFormation.removeUE(found, ue);
        assertNull(daoBloc.find(id));
    }

}

