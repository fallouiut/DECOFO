package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.UE;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DAOUeTest {

    @Autowired
    DAOBloc daoBloc;

    @Autowired
    DAOUe daoUe;

    private int ueId;

    @BeforeEach
    public void befa() {
        ueId = daoUe.findAll().get(0).getId();
        System.err.println("UE ID = " + ueId);
    }

    @Test
    public void autowireWorkds() {

        assertNotNull(daoUe);
    }

    @Test
    public void insert() {
        UE ue = new UE("JEE","M2 - ILD", 5, 2, 3,5,5,6,7,6);
        daoUe.insert(ue);
    }

    @Test
    public void findOne() {
        UE o = daoUe.find(ueId);
        assertNotNull(o);
    }

    @Test
    public void findAll() {
        List<UE> UEs = daoUe.findAll();
        assertTrue( UEs.size() > 0);
    }

    @Test
    public void update() {
        UE toUpdate = daoUe.find(ueId);

        toUpdate.setCode("TO_UPDATE");
        daoUe.update(toUpdate);

        UE updated = daoUe.find(ueId);
        assertEquals(updated.getCode(), toUpdate.getCode());
    }

    @Test
    public void delete() {
        UE toDelete = daoUe.find(ueId);

        daoUe.delete(toDelete);

        UE deleted = daoUe.find(ueId);

        assertNull(deleted);

    }

}

