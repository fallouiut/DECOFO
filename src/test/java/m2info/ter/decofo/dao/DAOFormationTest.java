package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
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

    private int formationId;

    @BeforeEach
    public void befa() {
        formationId = daoFormation.findAll().get(0).getId();
        System.err.println("Formation ID = " + formationId);
    }

    @Test
    public void autowireWorkds() {

        assertNotNull(daoFormation);
    }

    @Test
    public void insert() {
        Formation f = new Formation("M7ILD", 250, "M3 - ILD", 5, 2, 3);
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
        Formation f = new Formation("M3ILD", 250, "M3 - ILD", 5, 2, 3);
        f.addBloc(new Bloc("s1", "semestre", 0));
        f.addBloc(new Bloc("s1", "semestre", 0));

        daoFormation.insert(f);

        Formation found = daoFormation.find(f.getId());

        assertTrue(found.getBlocs().size() >= 2);

    }

    @Test
    public void addOptions() {
        Formation f = new Formation("M3ILD", 250, "M3 - ILD", 5, 2, 3);
        f.addOption(new Option("code", "intitule", 0, 0));
        f.addOption(new Option("code", "intitule", 0, 0));

        daoFormation.insert(f);

        Formation found = daoFormation.find(f.getId());

        assertTrue(found.getOptions().size() >= 1);
    }

    @Test
    public void addUE() {
        Formation f = new Formation("M3ILD", 250, "M3 - ILD", 5, 2, 3);
        f.addUE(new UE("JEE","M2 - ILD", 5, 2, 3,5,5,6,7,6));
        f.addUE(new UE("JEE","M2 - ILD", 5, 2, 3,5,5,6,7,6));

        daoFormation.insert(f);

        Formation found = daoFormation.find(f.getId());

        assertTrue(found.getUEs().size() >= 1);

    }
}

