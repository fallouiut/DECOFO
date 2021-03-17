package m2info.ter.decofo.manager.gestion.BlocUpdateBug;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.manager.gestion.BlocManager;
import m2info.ter.decofo.manager.gestion.FormationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BlocUpdateManager {


    /**
     * Lorsqu'on met à jour un bloc daoBloc.update(Bloc)
     * il est supprimé de sa formation, bug a regler
     */

    @Autowired
    FormationManager formationManager;
    @Autowired
    BlocManager blocManager;

    private int formationId;

    @BeforeEach
    public void befa() throws Exception {
        Formation f = new Formation("M7ILD", 250, "M3 - ILD", 5, 2, 3);
        formationManager.insert(f);
        formationId = f.getId();

        Bloc bloc1 = new Bloc("bloc i", "intitulé 1", 0);
        Bloc bloc2 = new Bloc("bloc 2", "intitulé 2", 0);

        f.addBloc(bloc1);
        f.addBloc(bloc2);

        formationManager.update(f);

    }

    @AfterEach
    public void after() throws Exception {
        Formation f = formationManager.findOne(formationId);
        if(f!= null)
            formationManager.delete(f.getId());
    }


    @Test
    public void provoquerErreur() throws Exception {
        Formation f = formationManager.findOne(formationId);

        Bloc b = f.getBlocs().get(0);

        b.setIntitule("intitutlé test");
        blocManager.update(b);

        Formation test = formationManager.findOne(formationId);

        assertTrue (test.getBlocs().size() == 2);

    }

}
