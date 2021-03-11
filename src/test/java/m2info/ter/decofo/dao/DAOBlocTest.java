package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
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
    public void addUE() {
        // récupérer un bloc
        Bloc bloc = daoBloc.find(BlocId);

        // ajouter ue
        UE ue = this.createAndInsertUE();

        // ajouter ue au bloc
        bloc.addUE(ue);
        daoBloc.update(bloc);

        Bloc test = daoBloc.find(bloc.getId());
        assertTrue(test.getUes().size() >= 1);

    }

    /**
     * detach UE without removing it
     */
    @Test
    public void removeUE() {
        // récupérer un bloc
        Bloc bloc = daoBloc.find(BlocId);

        // créer ue
        UE ue = this.createAndInsertUE();
        int ueId = ue.getId();

        // ajouter ue au bloc
        bloc.addUE(ue);
        daoBloc.update(bloc);

        // assurer UE existe
        Bloc blocWithUE = daoBloc.find(bloc.getId());
        assertTrue(blocWithUE.getUes().size() > 0);
        int blocUeSize = blocWithUE.getUes().size();

        // enlever ue
        System.err.println("-------------------");
        System.err.println("Bloc before removing ue " + blocWithUE.getUes().size());
        //blocWithUE.setUes(new ArrayList<>());
        blocWithUE.removeUE(ue);
        System.err.println("Bloc after removing ue " + blocWithUE.getUes().size());
        assertTrue(blocWithUE.getUes().size() == 0);
        System.err.println("-------------------");
        daoBloc.update(blocWithUE);

        // vérifier ue est détaché
        Bloc blocWithoutUE = daoBloc.find(bloc.getId());
        System.err.println("-------------------");
        System.err.println("Actual size: " + blocWithoutUE.getUes().size());
        System.err.println("Expected: " + (blocUeSize -1));
        System.err.println("-------------------");
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
    public void addOption() {
        // récupérer un bloc
        Bloc bloc = daoBloc.find(BlocId);

        // ajouter ue
        Option option = this.createAndInsertOption();

        // ajouter ue au bloc
        bloc.addOption(option);
        daoBloc.update(bloc);

        Bloc test = daoBloc.find(bloc.getId());

        assertTrue(test.getOptions().size() >= 1);

    }

    /**
     * detach Option without removing it
     */
    @Test
    public void removeOption() {
        // récupérer un bloc
        Bloc bloc = daoBloc.find(BlocId);

        // créer option
        Option option = this.createAndInsertOption();
        int optionId = option.getId();

        // ajouter option au bloc
        bloc.addOption(option);
        daoBloc.update(bloc);

        // assurer Option existe
        Bloc blocWithOption = daoBloc.find(bloc.getId());
        assertTrue(blocWithOption.getOptions().size() > 0);
        int blocOptionSize = blocWithOption.getOptions().size();

        // enlever option
        System.err.println("-------------------");
        System.err.println("Bloc before removing Option " + blocWithOption.getOptions().size());
        //blocWithUE.setUes(new ArrayList<>());
        blocWithOption.removeOption(option);
        System.err.println("Bloc after removing Option " + blocWithOption.getOptions().size());
        assertTrue(blocWithOption.getOptions().size() == 0);
        System.err.println("-------------------");
        daoBloc.update(blocWithOption);

        // vérifier option est détaché
        Bloc blocWithoutOption = daoBloc.find(bloc.getId());
        System.err.println("-------------------");
        System.err.println("Actual size: " + blocWithoutOption.getOptions().size());
        System.err.println("Expected: " + (blocOptionSize -1));
        System.err.println("-------------------");
        assertTrue(blocWithoutOption.getOptions().size() == (blocOptionSize -1));
        assertTrue(blocWithoutOption.getOptions().contains(option) == false);

        // vérifier ue non supprimé
        Option ueFound = daoOption.find(optionId);
        assertNotNull(ueFound);

    }

}

