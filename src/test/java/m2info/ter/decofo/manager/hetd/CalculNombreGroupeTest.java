package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Effectif;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.UE;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CalculNombreGroupeTest {

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
        return formation;
    }

    @Test
    public void test1() {

        Formation formationBefore = createFormationTest1();
        calculNombreGroupesService.calculerNombreGroupesCMTDTP(formationBefore);
        Formation formationAfter = calculNombreGroupesService.getFormation();

        // confirmer nb groupes CM pour chaque site
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite1());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite2());
        assertEquals(0, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite3());
        assertEquals(0, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite4());

        // confirmer nb groupes TD pour chaque site
        assertEquals(3, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite1());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite2());
        assertEquals(0, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite3());
        assertEquals(0, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite4());

        // confirmer nb groupes CM pour chaque site
        assertEquals(3, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite1());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite2());
        assertEquals(0, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite3());
        assertEquals(0, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite4());

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
        return formation;
    }

    @Test
    public void test2() {

        Formation formationBefore = createFormationTest2();
        calculNombreGroupesService.calculerNombreGroupesCMTDTP(formationBefore);
        Formation formationAfter = calculNombreGroupesService.getFormation();

        // confirmer nb groupes CM pour chaque site
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite1());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite2());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite3());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite4());

        // confirmer nb groupes CM pour chaque site
        assertEquals(4, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite1());
        assertEquals(3, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite2());
        assertEquals(2, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite3());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite4());

        // confirmer nb groupes CM pour chaque site
        assertEquals(7, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite1());
        assertEquals(5, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite2());
        assertEquals(4, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite3());
        assertEquals(2, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite4());

        // confirmer total
        assertEquals(4,formationAfter.getUEs().get(0).getNombreGroupesCM());
        assertEquals(10,formationAfter.getUEs().get(0).getNombreGroupesTD());
        assertEquals(18,formationAfter.getUEs().get(0).getNombreGroupesTP());
    }


    private Formation createFormationTest3() {
        Formation formation = new Formation();
        formation.setTailleGroupeCM(0);
        formation.setTailleGroupeTD(25);
        formation.setTailleGroupeTP(10);

        Bloc bloc = new Bloc();
        bloc.setEffectif(new Effectif(125, 100, 40, 10));

        UE ue = new UE();

        bloc.addUE(ue);
        formation.addBloc(bloc);
        formation.addUE(ue);
        return formation;
    }

    @Test
    public void test3() {

        Formation formationBefore = createFormationTest3();
        calculNombreGroupesService.calculerNombreGroupesCMTDTP(formationBefore);
        Formation formationAfter = calculNombreGroupesService.getFormation();

        // confirmer nb groupes CM pour chaque site
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite1());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite2());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite3());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesCMSite4());

        // confirmer nb groupes CM pour chaque site
        assertEquals(5, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite1());
        assertEquals(4, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite2());
        assertEquals(2, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite3());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTDSite4());

        // confirmer nb groupes CM pour chaque site
        assertEquals(13, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite1());
        assertEquals(10, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite2());
        assertEquals(4, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite3());
        assertEquals(1, formationAfter.getBlocs().get(0).getEffectif().getNbGroupesTPSite4());

        // confirmer total
        assertEquals(4,formationAfter.getUEs().get(0).getNombreGroupesCM());
        assertEquals(12,formationAfter.getUEs().get(0).getNombreGroupesTD());
        assertEquals(28,formationAfter.getUEs().get(0).getNombreGroupesTP());

    }
}
