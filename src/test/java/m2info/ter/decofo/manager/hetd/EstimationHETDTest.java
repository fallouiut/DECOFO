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

    // afficher data pour chaque

    @Test
    public void testNotNull() {
        assertNotNull(estimationHETD);
    }

    private Formation createFormationTest1() {

        Formation formation = new Formation("Formation 1", "F1", 0,30, 30);

        Bloc bloc = new Bloc("Bloc 1", "B1");
        bloc.setEffectif(new Effectif(90, 25, 0, 0));

        UE ue = new UE("UE 1", "U1", 14, 8, 6, 6);

        formation.addBloc(bloc);
        formation.addUE(ue);

        bloc.addUE(ue);


        return formation;
    }

    @Test
    public void test1() {

        Formation formation = this.createFormationTest1();
        estimationHETD.calculerHETD(formation);
        formation = estimationHETD.getFormation();

        assertEquals(98, formation.getUEs().get(0).getCout());
        assertEquals(98, formation.getBlocs().get(0).getCout());
        assertEquals(98, formation.getCout());

    }


    private Formation createFormationTest2() {

        Formation formation = new Formation("Formation 1", "F1", 0,25, 15);

        Bloc bloc = new Bloc("Bloc 1", "B1");
        bloc.setEffectif(new Effectif(45, 22, 31, 9));

        Bloc bloc2 = new Bloc("Bloc 2", "B2");
        bloc2.setEffectif(new Effectif(25, 15, 32, 8));

        UE ue = new UE("UE 1", "U1", 24, 12, 10, 6);

        formation.addBloc(bloc);
        formation.addBloc(bloc2);
        formation.addUE(ue);

        bloc.addUE(ue);
        bloc2.addUE(ue);


        return formation;
    }

    @Test
    public void test2() {
        Formation formation = this.createFormationTest2();
        estimationHETD.calculerHETD(formation);
        formation = estimationHETD.getFormation();

        System.err.println("----------");
        System.err.println("Grp TD Site 1: " + formation.getUEs().get(0).getNombreGroupeTDSite1());
        System.err.println("Grp TD Site 2: " + formation.getUEs().get(0).getNombreGroupeTDSite2());
        System.err.println("Grp TD Site 3: " + formation.getUEs().get(0).getNombreGroupeTDSite3());
        System.err.println("Grp TD Site 4: " + formation.getUEs().get(0).getNombreGroupeTDSite4());


        assertEquals(402, formation.getUEs().get(0).getCout());
        assertEquals(230, (int)formation.getBlocs().get(0).getCout());
        assertEquals(171, (int)formation.getBlocs().get(1).getCout());
        assertEquals(402, formation.getCout());

    }

}
