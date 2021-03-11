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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DAOBlocTest {

    @Autowired
    DAOBloc daoBloc;

    @Autowired
    DAOOption daoOption;

    @Autowired
    DAOUe daoUe;

    @Autowired
    DAOFormation daoFormation;

    private int BlocId;

    @BeforeEach
    public void befa() {
        Bloc bloc = new Bloc("test ag4*6nlmgregation", "ok", 0);
        daoBloc.insert(bloc);
        BlocId = bloc.getId();
    }

    @AfterEach
    public void after() {
        Bloc b = daoBloc.find(BlocId);
        if(b!= null)
            daoBloc.delete(daoBloc.find(BlocId));
    }

    @Test
    public void autowireWorkds() {

        assertNotNull(daoBloc);
    }

    @Test
    public void insert() {
        Bloc b = new Bloc("s1-ild", "Semestre 1 ild", 20.0);
        daoBloc.insert(b);
    }

    @Test
    public void findOne() {
        Bloc b = daoBloc.find(BlocId);
        assertNotNull(b);
    }

    @Test
    public void findAll() {
        List<Bloc> BlocList = daoBloc.findAll();
        assertTrue( BlocList.size() > 0);
    }

    @Test
    public void update() {
        Bloc toUpdate = daoBloc.find(BlocId);

        toUpdate.setCode("TO_UPDATE");
        daoBloc.update(toUpdate);

        Bloc updated = daoBloc.find(BlocId);
        assertEquals(updated.getCode(), toUpdate.getCode());
    }

    @Test
    public void delete() {
        Bloc toDelete = daoBloc.find(BlocId);

        daoBloc.delete(toDelete);

        Bloc deleted = daoBloc.find(BlocId);

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
        Bloc b = daoBloc.find(BlocId);
        UE ue = this.createAndInsertUE();
        daoBloc.linkUE(b, ue);

        Bloc test = daoBloc.find(BlocId);
        assertTrue(test.getUes().size() >= 1);
    }

    /**
     * detach UE without removing it
     */
    @Test
    public void unlinkUE() {
        // récupérer un bloc
        Bloc bloc = daoBloc.find(BlocId);

        // créer ue
        UE ue = this.createAndInsertUE();
        int ueId = ue.getId();

        // ajouter ue au bloc
        daoBloc.linkUE(bloc, ue);

        // assurer UE existe
        Bloc blocWithUE = daoBloc.find(bloc.getId());
        assertTrue(blocWithUE.getUes().size() > 0);
        int blocUeSize = blocWithUE.getUes().size();

        // enlever ue=
        daoBloc.unlinkUE(blocWithUE, ue);
        assertTrue(blocWithUE.getUes().size() == 0);

        // vérifier ue est détaché
        Bloc blocWithoutUE = daoBloc.find(bloc.getId());
        assertTrue(blocWithoutUE.getUes().size() == (blocUeSize -1));
        assertTrue(blocWithoutUE.getUes().contains(ue) == false);

        // vérifier ue non supprimé
        UE ueFound = daoUe.find(ueId);
        assertNotNull(ueFound);
    }

    /**
     * Créer et insérer une option de test
     * @return
     */
    private Option createAndInsertOption() {
        // ajouter ue
        Option optionToAdd = new Option("TEST CASCADE", "intitule", 0, 0);
        daoOption.insert(optionToAdd);
        // récupérer ue
        Option option = daoOption.find(optionToAdd.getId());
        return option;
    }

    @Test
    public void linkOption() {
        // ajouter ue
        Bloc b = daoBloc.find(BlocId);
        Option o = this.createAndInsertOption();

        daoBloc.linkOption(b, o);

        Bloc test = daoBloc.find(BlocId);
        assertTrue(test.getOptions().size() >= 1);
    }

    /**
     * detach UE without removing it
     */
    @Test
    public void unlinkOption() {
        // récupérer un bloc
        Bloc bloc = daoBloc.find(BlocId);

        // créer option
        Option o = this.createAndInsertOption();
        int optionId = o.getId();

        // ajouter option au bloc
        daoBloc.linkOption(bloc, o);

        // assurer option existe
        Bloc blocWithOption = daoBloc.find(bloc.getId());
        assertTrue(blocWithOption.getOptions().size() > 0);
        int blocOptionSize = blocWithOption.getOptions().size();

        // enlever option=
        daoBloc.unlinkOption(blocWithOption, o);
        assertTrue(blocWithOption.getOptions().size() == 0);

        // vérifier option est détaché
        Bloc blocWithoutUE = daoBloc.find(bloc.getId());
        assertTrue(blocWithoutUE.getOptions().size() == (blocOptionSize -1));
        assertTrue(blocWithoutUE.getOptions().contains(o) == false);

        // vérifier option non supprimé
        Option optionFound = daoOption.find(optionId);
        assertNotNull(optionFound);
    }

}

