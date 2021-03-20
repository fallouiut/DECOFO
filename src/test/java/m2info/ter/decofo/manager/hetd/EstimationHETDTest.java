package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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

        Formation formation = new Formation("Formation 1", "F1", 200,30, 30);

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

        System.err.println("-------------------------------");
        System.out.println(estimationHETD.getAlertMessages());

        assertEquals(98, formation.getUEs().get(0).getCout());
        assertEquals(98, formation.getBlocs().get(0).getCout());
        assertEquals(98, formation.getCout());

    }


    private Formation createFormationTest2() {

        Formation formation = new Formation("Formation 1", "F1", 200,25, 15);

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

        System.err.println("-------------------------------");
        System.out.println(estimationHETD.getAlertMessages());

        assertEquals(402, formation.getUEs().get(0).getCout());
        assertEquals(230, (int)formation.getBlocs().get(0).getCout());
        assertEquals(171, (int)formation.getBlocs().get(1).getCout());
        assertEquals(402, formation.getCout());

    }


    private Formation createFormationTest3() {

        Formation formation = new Formation("Formation 1", "F1", 200,30, 20);

        Bloc b1 = new Bloc("Bloc 1", "B1");
        b1.setEffectif(new Effectif(40, 20, 11, 5));

        Bloc b2 = new Bloc("Bloc 2", "B2");
        b2.setEffectif(new Effectif(35, 12, 8, 9));

        Bloc b3 = new Bloc("Bloc 3", "B3");
        b3.setEffectif(new Effectif(60, 30, 15, 11));

        Option option = new Option("Option 1", "O1", 6);

        UE ue1 = new UE("UE 1", "UE1", 20, 16, 8, 6);
        UE ue2 = new UE("UE 2", "UE2", 24, 18, 12, 6);

        UE ueVide1 = new UE("UE vide 1", "UEV1", 0, 0, 0, 0);
        UE ueVide2 = new UE("UE vide 2", "UEV2", 0, 0, 0, 0);
        UE ueVide3 = new UE("UE vide 3", "UEV3", 0, 0, 0, 0);

        formation.addBloc(b1);
        formation.addBloc(b2);
        formation.addBloc(b3);
        formation.addOption(option);
        formation.addUE(ue1);
        formation.addUE(ue2);
        formation.addUE(ueVide1);
        formation.addUE(ueVide2);
        formation.addUE(ueVide3);

        b1.addUE(ue1);
        b1.addUE(ue2);

        b2.addOption(option);
        b2.addUE(ue1);

        b3.addOption(option);

        option.addUE(ue2);
        option.addUE(ueVide1);
        option.addUE(ueVide2);
        option.addUE(ueVide3);

        return formation;
    }

    @Test
    public void test3() {
        Formation formation = this.createFormationTest3();
        estimationHETD.calculerHETD(formation);
        formation = estimationHETD.getFormation();

        System.err.println("-------------------------------");
        System.out.println(estimationHETD.getAlertMessages());

        assertEquals(296, (int)formation.getUEs().get(0).getCout());
        assertEquals(366, (int)formation.getUEs().get(1).getCout());
        assertEquals(0,   (int)formation.getUEs().get(2).getCout());
        assertEquals(0,   (int)formation.getUEs().get(3).getCout());
        // hetd option
        assertEquals(138,   (int)formation.getOptions().get(0).getCout());
        // hetd bloc
        assertEquals(388, (int)formation.getBlocs().get(0).getCout());
        assertEquals(184, (int)formation.getBlocs().get(1).getCout());
        assertEquals(88, (int)formation.getBlocs().get(2).getCout());
        // hetd formation
        assertEquals(662, (int)formation.getCout());

    }

    private Formation createFormationTest4() {

        Formation formation = new Formation("Formation 1", "F1", 200,30, 20);

        Bloc b1 = new Bloc("Bloc 1", "B1");
        b1.setEffectif(new Effectif(0, 0, 0, 0));

        Bloc b2 = new Bloc("Bloc 2", "B2");
        b2.setEffectif(new Effectif(35, 12, 8, 9));

        Bloc b3 = new Bloc("Bloc 3", "B3");
        b3.setEffectif(new Effectif(60, 30, 15, 11));

        Option option = new Option("Option 1", "O1", 6);

        UE ue1 = new UE("UE 1", "UE1", 20, 16, 8, 6);
        UE ue2 = new UE("UE 2", "UE2", 24, 18, 12, 6);

        UE ueVide1 = new UE("UE vide 1", "UEV1", 0, 0, 0, 0);
        UE ueVide2 = new UE("UE vide 2", "UEV2", 0, 0, 0, 0);
        UE ueVide3 = new UE("UE vide 3", "UEV3", 0, 0, 0, 0);

        formation.addBloc(b1);
        formation.addBloc(b2);
        formation.addBloc(b3);
        formation.addOption(option);
        formation.addUE(ue1);
        formation.addUE(ue2);
        formation.addUE(ueVide1);
        formation.addUE(ueVide2);
        formation.addUE(ueVide3);

        b1.addUE(ue1);
        b1.addUE(ue2);

        b2.addOption(option);
        b2.addUE(ue1);

        b3.addOption(option);

        option.addUE(ue2);
        option.addUE(ueVide1);
        option.addUE(ueVide2);
        option.addUE(ueVide3);

        return formation;
    }

    @Test
    public void test4() {
        Formation formation = this.createFormationTest4();
        estimationHETD.calculerHETD(formation);
        formation = estimationHETD.getFormation();

        System.err.println("-------------------------------");

        estimationHETD.getAlertMessages().forEach((message) -> {
            System.out.println("- " + message);
        });

        assertTrue(estimationHETD.getAlertMessages().size() > 0);
        /*
        assertEquals(296, (int)formation.getUEs().get(0).getCout());
        assertEquals(366, (int)formation.getUEs().get(1).getCout());
        assertEquals(0,   (int)formation.getUEs().get(2).getCout());
        assertEquals(0,   (int)formation.getUEs().get(3).getCout());
        // hetd option
        assertEquals(138,   (int)formation.getOptions().get(0).getCout());
        // hetd bloc
        assertEquals(388, (int)formation.getBlocs().get(0).getCout());
        assertEquals(184, (int)formation.getBlocs().get(1).getCout());
        assertEquals(88, (int)formation.getBlocs().get(2).getCout());
        // hetd formation
        assertEquals(662, (int)formation.getCout());*/

    }

}
