package m2info.ter.decofo.manager.gestion.BlocUpdateBug;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.controllers.BlocController;
import m2info.ter.decofo.controllers.FormationController;
import m2info.ter.decofo.manager.gestion.BlocManager;
import m2info.ter.decofo.manager.gestion.FormationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BlocUpdateController {

    @Autowired
    FormationController formationController;

    @Autowired
    FormationManager formationManager;


    @Autowired
    BlocController blocController;

    int formationId = 0;

    @BeforeEach
    public void befa() throws Exception {
        Formation f = new Formation("M7ILD", 250, "M3 - ILD", 5, 2, 3);

        formationController.createFormation(f);

        formationId = f.getId();

        Bloc bloc1 = new Bloc("bloc i", "intitulé 1", 0);
        Bloc bloc2 = new Bloc("bloc 2", "intitulé 2", 0);

        f.addBloc(bloc1);
        f.addBloc(bloc2);

        formationController.createBlocOnFormation(formationId, bloc1);
        formationController.createBlocOnFormation(formationId, bloc2);

        Formation test = formationManager.findOne(formationId);

        assertNotNull(test);
        assertTrue(test.getBlocs().size() == 2);


    }

    @AfterEach
    public void after() throws Exception {
        Formation f = formationManager.findOne(formationId);
        if(f!= null)
            formationManager.delete(f.getId());
    }


    @Test
    public void testOk() {
        Formation f = formationManager.findOne(formationId);

        Bloc b = f.getBlocs().get(0);

        b.setIntitule("test");
        b.setCode("test");

        blocController.updateBloc(b, b.getId());


        Formation f2 = formationManager.findOne(formationId);

        assert(f2.getBlocs().size() == 2);



    }

}
