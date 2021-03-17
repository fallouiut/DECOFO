package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CalculEffectifUEsTest {

    @Autowired
    CalculEffectifUE calculEffectifUE;

    @Test
    public void notNull() {
        assertNotNull(calculEffectifUE);
    }

    private Formation createFormationTest1() {
        Formation formation = new Formation();

        Bloc bloc = new Bloc();
        bloc.setEffectif(new Effectif(50, 20, 30, 20));

        Option option = new Option();
        option.setEffectifParUe(20);

        UE ue = new UE();

        bloc.addUE(ue);
        option.addUE(ue);

        formation.addBloc(bloc);
        formation.addUE(ue);

        return formation;

    }

    @Test
    public void test1() {
        Formation test = this.createFormationTest1();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        assertEquals(140, test.getUEs().get(0).getEffectifTotal());
    }

    private Formation createFormationTest2() {
        Formation formation = new Formation();

        Bloc bloc1 = new Bloc();
        bloc1.setEffectif(new Effectif(50, 50, 50, 50));

        Bloc bloc2 = new Bloc();
        bloc2.setEffectif(new Effectif(40, 40, 40, 40));

        UE ue = new UE();

        formation.addUE(ue);
        bloc1.addUE(ue);
        bloc2.addUE(ue);

        formation.addBloc(bloc1);
        formation.addBloc(bloc2);

        return formation;

    }

    @Test
    public void test2() {
        Formation test = this.createFormationTest2();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        assertEquals(360, test.getUEs().get(0).getEffectifTotal());
    }

    private Formation createFormationTest3() {
        Formation formation = new Formation();

        Bloc bloc1 = new Bloc();
        bloc1.setEffectif(new Effectif(100, 50, 100, 20));

        Bloc bloc2 = new Bloc();
        bloc2.setEffectif(new Effectif(70, 120, 50, 30));

        Option option = new Option();
        option.setEffectifParUe(20);


        UE ue = new UE();

        bloc1.addUE(ue);
        bloc2.addUE(ue);
        option.addUE(ue);

        formation.addBloc(bloc1);
        formation.addBloc(bloc2);
        formation.addOption(option);
        formation.addUE(ue);

        return formation;
    }

    @Test
    public void test3() {
        Formation test = this.createFormationTest3();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        assertEquals(560, test.getUEs().get(0).getEffectifTotal());
    }


    private Formation createFormationTest4() {
        Formation formation = new Formation();

        Bloc bloc1 = new Bloc();
        bloc1.setEffectif(new Effectif(50, 40, 50, 20));

        Bloc bloc2 = new Bloc();
        bloc2.setEffectif(new Effectif(35, 50, 70, 10));

        Option option = new Option();
        option.setEffectifParUe(15);


        UE ue1 = new UE();
        UE ue2 = new UE();

        bloc1.addUE(ue1);
        bloc2.addUE(ue2);
        option.addUE(ue1);
        option.addUE(ue2);

        formation.addBloc(bloc1);
        formation.addBloc(bloc2);
        formation.addOption(option);
        formation.addUE(ue1);
        formation.addUE(ue2);

        return formation;
    }

    @Test
    public void test4() {
        Formation test = this.createFormationTest4();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        assertEquals(175, test.getUEs().get(0).getEffectifTotal());
        assertEquals(180, test.getUEs().get(1).getEffectifTotal());
    }


    private Formation createFormationTest5() {
        Formation formation = new Formation();

        Bloc bloc1 = new Bloc();
        bloc1.setEffectif(new Effectif(20, 30, 0, 0));

        Bloc bloc2 = new Bloc();
        bloc2.setEffectif(new Effectif(50, 25, 15, 7));

        Option option1 = new Option();
        option1.setEffectifParUe(15);

        Option option2 = new Option();
        option2.setEffectifParUe(22);

        UE ue1 = new UE();
        UE ue2 = new UE();
        UE ue3 = new UE();
        UE ue4 = new UE();

        bloc1.addUE(ue1);
        bloc2.addUE(ue1);
        bloc2.addUE(ue3);

        option1.addUE(ue1);
        option1.addUE(ue2);

        option2.addUE(ue2);
        option2.addUE(ue3);

        formation.addBloc(bloc1);
        formation.addBloc(bloc2);
        formation.addOption(option1);
        formation.addOption(option2);

        formation.addUE(ue1);
        formation.addUE(ue2);
        formation.addUE(ue3);
        formation.addUE(ue4);

        return formation;
    }

    @Test
    public void test5() {
        Formation test = this.createFormationTest5();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        assertEquals(162, test.getUEs().get(0).getEffectifTotal());
        assertEquals(37 , test.getUEs().get(1).getEffectifTotal());
        assertEquals(119, test.getUEs().get(2).getEffectifTotal());
        assertEquals(0  , test.getUEs().get(3).getEffectifTotal());
    }
    
    private Formation createFormationTest6() {
        Formation formation = new Formation();

        Bloc bloc1 = new Bloc();
        bloc1.setEffectif(new Effectif(0, 0, 0, 0));

        Bloc bloc2 = new Bloc();
        bloc2.setEffectif(new Effectif(0, 0, 0, 0));

        Option option1 = new Option();
        option1.setEffectifParUe(0);

        Option option2 = new Option();
        option2.setEffectifParUe(0);

        UE ue1 = new UE();
        UE ue2 = new UE();
        UE ue3 = new UE();
        UE ue4 = new UE();

        bloc1.addUE(ue1);
        bloc2.addUE(ue1);
        bloc2.addUE(ue3);

        option1.addUE(ue1);
        option1.addUE(ue2);

        option2.addUE(ue2);
        option2.addUE(ue3);

        formation.addBloc(bloc1);
        formation.addBloc(bloc2);
        formation.addOption(option1);
        formation.addOption(option2);

        formation.addUE(ue1);
        formation.addUE(ue2);
        formation.addUE(ue3);
        formation.addUE(ue4);

        return formation;
    }

    @Test
    public void test6() {
        Formation test = this.createFormationTest6();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        assertEquals(0, test.getUEs().get(0).getEffectifTotal());
        assertEquals(0, test.getUEs().get(1).getEffectifTotal());
        assertEquals(0, test.getUEs().get(2).getEffectifTotal());
        assertEquals(0, test.getUEs().get(3).getEffectifTotal());
    }
}
