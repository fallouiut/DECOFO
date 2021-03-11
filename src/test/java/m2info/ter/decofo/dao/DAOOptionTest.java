package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Option;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
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

    private int optionId;

    @BeforeEach
    public void befa() {
        optionId = dapOption.findAll().get(0).getId();
        System.err.println("Option ID = " + optionId);
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
}

