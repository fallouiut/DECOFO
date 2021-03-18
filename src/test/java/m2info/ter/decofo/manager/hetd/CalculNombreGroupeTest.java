package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CalculNombreGroupeTest {

    @Autowired
    RepartitionEffectifOption repartitionEffectifOption;

    @Autowired
    CalculEffectifUE calculEffectifUE;

    @Autowired
    CalculNombreGroupesUE calculNombreGroupesService;

    @Test
    public void notNull() {
        assertNotNull(calculNombreGroupesService);
    }

    private Formation createFormationTest1() {
        Formation formation = new Formation();
        formation.setTailleGroupeCM(0);
        formation.setTailleGroupeTD(30);
        formation.setTailleGroupeTP(30);

        Bloc bloc = new Bloc();
        bloc.setEffectif(new Effectif(90, 25, 0, 0));

        UE ue = new UE();

        bloc.addUE(ue);
        formation.addBloc(bloc);
        formation.addUE(ue);

        repartitionEffectifOption.calculerEffectifOption(formation);
        calculEffectifUE.calculerEffectifUEs(repartitionEffectifOption.getFormation());
        calculNombreGroupesService.calculerNombreGroupesCMTDTP(calculEffectifUE.getFormation());

        return calculNombreGroupesService.getFormation();
    }

    @Test
    public void test1() {

        Formation formationAfter = createFormationTest1();

        // confirmer nb groupes CM pour chaque site
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite1());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite2());
        assertEquals(0, formationAfter.getUEs().get(0).getNombreGroupeCMSite3());
        assertEquals(0, formationAfter.getUEs().get(0).getNombreGroupeCMSite4());

        // confirmer nb groupes TD pour chaque site
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTDSite1());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeTDSite2());
        assertEquals(0, formationAfter.getUEs().get(0).getNombreGroupeTDSite3());
        assertEquals(0, formationAfter.getUEs().get(0).getNombreGroupeTDSite4());

        // confirmer nb groupes TD pour chaque site
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTPSite1());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeTPSite2());
        assertEquals(0, formationAfter.getUEs().get(0).getNombreGroupeTPSite3());
        assertEquals(0, formationAfter.getUEs().get(0).getNombreGroupeTPSite4());

        // confirmer total
        assertEquals(2,formationAfter.getUEs().get(0).getNombreGroupesCM());
        assertEquals(4,formationAfter.getUEs().get(0).getNombreGroupesTD());
        assertEquals(4,formationAfter.getUEs().get(0).getNombreGroupesTP());

    }


    private Formation createFormationTest2() {
        Formation formation = new Formation();
        formation.setTailleGroupeCM(0);
        formation.setTailleGroupeTD(30);
        formation.setTailleGroupeTP(15);

        Bloc bloc = new Bloc();
        bloc.setEffectif(new Effectif(100, 75, 50, 20));

        UE ue = new UE();

        bloc.addUE(ue);
        formation.addBloc(bloc);
        formation.addUE(ue);

        repartitionEffectifOption.calculerEffectifOption(formation);
        calculEffectifUE.calculerEffectifUEs(repartitionEffectifOption.getFormation());
        calculNombreGroupesService.calculerNombreGroupesCMTDTP(calculEffectifUE.getFormation());

        return calculNombreGroupesService.getFormation();
    }

    @Test
    public void test2() {

        Formation formationAfter = createFormationTest2();

        // confirmer nb groupes CM pour chaque site
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite1());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite2());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite3());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite4());

        // confirmer nb groupes TD pour chaque site
        assertEquals(4, formationAfter.getUEs().get(0).getNombreGroupeTDSite1());
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTDSite2());
        assertEquals(2, formationAfter.getUEs().get(0).getNombreGroupeTDSite3());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeTDSite4());

        // confirmer nb groupes TD pour chaque site
        assertEquals(7, formationAfter.getUEs().get(0).getNombreGroupeTPSite1());
        assertEquals(5, formationAfter.getUEs().get(0).getNombreGroupeTPSite2());
        assertEquals(4, formationAfter.getUEs().get(0).getNombreGroupeTPSite3());
        assertEquals(2, formationAfter.getUEs().get(0).getNombreGroupeTPSite4());

        // confirmer total
        assertEquals(4,formationAfter.getUEs().get(0).getNombreGroupesCM());
        assertEquals(10,formationAfter.getUEs().get(0).getNombreGroupesTD());
        assertEquals(18,formationAfter.getUEs().get(0).getNombreGroupesTP());

    }

    private Formation createFormationTest3() {
        Formation formation = new Formation();
        formation.setTailleGroupeCM(0);
        formation.setTailleGroupeTD(25);
        formation.setTailleGroupeTP(15);

        Bloc bloc = new Bloc();
        bloc.setEffectif(new Effectif(70, 50, 15, 5));

        Bloc bloc2 = new Bloc();
        bloc2.setEffectif(new Effectif(40, 35, 20, 10));

        UE ue = new UE();

        bloc.addUE(ue);
        bloc2.addUE(ue);

        formation.addBloc(bloc);
        formation.addBloc(bloc2);
        formation.addUE(ue);

        repartitionEffectifOption.calculerEffectifOption(formation);
        calculEffectifUE.calculerEffectifUEs(repartitionEffectifOption.getFormation());
        calculNombreGroupesService.calculerNombreGroupesCMTDTP(calculEffectifUE.getFormation());

        return calculNombreGroupesService.getFormation();
    }

    @Test
    public void test3() {

        Formation formationAfter = createFormationTest3();

        // confirmer nb groupes CM pour chaque site
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite1());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite2());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite3());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite4());

        // confirmer nb groupes TD pour chaque site
        assertEquals(5, formationAfter.getUEs().get(0).getNombreGroupeTDSite1());
        assertEquals(4, formationAfter.getUEs().get(0).getNombreGroupeTDSite2());
        assertEquals(2, formationAfter.getUEs().get(0).getNombreGroupeTDSite3());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeTDSite4());

        // confirmer nb groupes TD pour chaque site
        assertEquals(8, formationAfter.getUEs().get(0).getNombreGroupeTPSite1());
        assertEquals(6, formationAfter.getUEs().get(0).getNombreGroupeTPSite2());
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTPSite3());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeTPSite4());

        // confirmer total
        assertEquals(4,formationAfter.getUEs().get(0).getNombreGroupesCM());
        assertEquals(12,formationAfter.getUEs().get(0).getNombreGroupesTD());
        assertEquals(18,formationAfter.getUEs().get(0).getNombreGroupesTP());

    }


    private Formation createFormationTest4() {
        Formation formation = new Formation();
        formation.setTailleGroupeCM(0);
        formation.setTailleGroupeTD(25);
        formation.setTailleGroupeTP(20);

        Bloc bloc = new Bloc();
        bloc.setEffectif(new Effectif(50, 30, 15, 45));

        Bloc bloc2 = new Bloc();
        bloc2.setEffectif(new Effectif(20, 30, 44, 72));

        Bloc bloc3 = new Bloc();
        bloc3.setEffectif(new Effectif(26, 2, 17, 41));

        Option option = new Option();

        UE ue = new UE();
        UE ue2 = new UE();

        formation.addBloc(bloc);
        formation.addBloc(bloc2);
        formation.addBloc(bloc3);
        formation.addOption(option);

        bloc.addUE(ue);
        bloc2.addUE(ue);
        bloc2.addUE(ue2);

        bloc3.addOption(option);


        option.addUE(ue2);
        option.addUE(new UE());

        formation.addUE(ue);
        formation.addUE(ue2);

        repartitionEffectifOption.calculerEffectifOption(formation);
        calculEffectifUE.calculerEffectifUEs(repartitionEffectifOption.getFormation());
        calculNombreGroupesService.calculerNombreGroupesCMTDTP(calculEffectifUE.getFormation());

        return calculNombreGroupesService.getFormation();
    }

    @Test
    public void test4() {

        Formation formationAfter = createFormationTest4();

        // confirmer nb groupes CM pour chaque site UE1
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite1());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite2());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite3());
        assertEquals(1, formationAfter.getUEs().get(0).getNombreGroupeCMSite4());
        // confirmer nb groupes TD pour chaque site
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTDSite1());
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTDSite2());
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTDSite3());
        assertEquals(5, formationAfter.getUEs().get(0).getNombreGroupeTDSite4());
        // confirmer nb groupes TD pour chaque site
        assertEquals(4, formationAfter.getUEs().get(0).getNombreGroupeTPSite1());
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTPSite2());
        assertEquals(3, formationAfter.getUEs().get(0).getNombreGroupeTPSite3());
        assertEquals(6, formationAfter.getUEs().get(0).getNombreGroupeTPSite4());
        // confirmer total
        assertEquals(4,formationAfter.getUEs().get(0).getNombreGroupesCM());
        assertEquals(14,formationAfter.getUEs().get(0).getNombreGroupesTD());
        assertEquals(16,formationAfter.getUEs().get(0).getNombreGroupesTP());

        // confirmer nb groupes CM pour chaque site UE2
        assertEquals(1, formationAfter.getUEs().get(1).getNombreGroupeCMSite1());
        assertEquals(1, formationAfter.getUEs().get(1).getNombreGroupeCMSite2());
        assertEquals(1, formationAfter.getUEs().get(1).getNombreGroupeCMSite3());
        assertEquals(1, formationAfter.getUEs().get(1).getNombreGroupeCMSite4());
        // confirmer nb groupes TD pour chaque site
        assertEquals(2, formationAfter.getUEs().get(1).getNombreGroupeTDSite1());
        assertEquals(2, formationAfter.getUEs().get(1).getNombreGroupeTDSite2());
        assertEquals(3, formationAfter.getUEs().get(1).getNombreGroupeTDSite3());
        assertEquals(4, formationAfter.getUEs().get(1).getNombreGroupeTDSite4());
        // confirmer nb groupes TD pour chaque site
        assertEquals(2, formationAfter.getUEs().get(1).getNombreGroupeTPSite1());
        assertEquals(2, formationAfter.getUEs().get(1).getNombreGroupeTPSite2());
        assertEquals(3, formationAfter.getUEs().get(1).getNombreGroupeTPSite3());
        assertEquals(5, formationAfter.getUEs().get(1).getNombreGroupeTPSite4());
        // confirmer total
        assertEquals(4,formationAfter.getUEs().get(1).getNombreGroupesCM());
        assertEquals(11,formationAfter.getUEs().get(1).getNombreGroupesTD());
        assertEquals(12,formationAfter.getUEs().get(1).getNombreGroupesTP());

    }


}
