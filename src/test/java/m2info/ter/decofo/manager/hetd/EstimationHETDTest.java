package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Effectif;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.UE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EstimationHETDTest {

    @Autowired
    EstimationHETD estimationHETD;

    @Test
    public void testNotNull() {
        assertNotNull(estimationHETD);
    }

    private Formation createFormationTest1() {
        /*
        Formation f = new Formation("Formation 1", "F1", 0,0, 0);
        f.setTailleGroupeCM(100);
        f.setTailleGroupeTD(20);
        f.setTailleGroupeTP(20);

        UE ue1 = new UE();
        UE ue2 = new UE();

        ue1.setNombreHeureCM(14);
        ue1.setNombreHeureTD(8);
        ue1.setNombreHeureTP(8);

        ue2.setNombreHeureCM(14);
        ue2.setNombreHeureTD(8);
        ue2.setNombreHeureTP(8);

        f.addUE(ue1);
        f.addUE(ue2);

        return f;
*/ return null;
    }

    @Test
    public void test1() {
/*
        Formation formation = this.createFormationTest1();
        estimationHETD.calculerHETD(formation);
        formation = estimationHETD.getFormation();


        assertEquals(154, formation.getUEs().get(0).getCout());
        assertEquals(420, formation.getUEs().get(1).getCout());
*/
    }

}
