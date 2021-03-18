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
    RepartitionEffectifOption repartitionEffectifOption;

    @Autowired
    CalculEffectifUE calculEffectifUE;

    @Test
    public void notNull() {
        assertNotNull(calculEffectifUE);
    }

    private Formation createFormationTest1() {
        Formation formation = new Formation();

        Bloc b1 = new Bloc();
        b1.setEffectif(new Effectif(10, 0, 0, 0));

        Bloc b2 = new Bloc();
        b2.setEffectif(new Effectif(0, 20, 0, 0));

        Bloc b3 = new Bloc();
        b3.setEffectif(new Effectif(5, 10, 0, 0));

        Option option = new Option();

        UE ue1 = new UE();
        UE ue2 = new UE();

        b1.addUE(ue1);

        b2.addOption(option);
        b3.addOption(option);

        option.addUE(ue1);
        option.addUE(ue2);

        formation.addBloc(b1);
        formation.addBloc(b2);
        formation.addBloc(b3);
        formation.addOption(option);
        formation.addUE(ue1);
        formation.addUE(ue2);

        repartitionEffectifOption.calculerEffectifOption(formation);

        return repartitionEffectifOption.getFormation();

    }

    @Test
    public void test1() {
        Formation test = this.createFormationTest1();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        // assert effectif ue1
        assertEquals(13, test.getUEs().get(0).getEffectifTotalSite1());
        assertEquals(15, test.getUEs().get(0).getEffectifTotalSite2());
        assertEquals(0,  test.getUEs().get(0).getEffectifTotalSite3());
        assertEquals(0,  test.getUEs().get(0).getEffectifTotalSite4());

        // assert effectif ue2
        assertEquals(3,  test.getUEs().get(1).getEffectifTotalSite1());
        assertEquals(15, test.getUEs().get(1).getEffectifTotalSite2());
        assertEquals(0,  test.getUEs().get(1).getEffectifTotalSite3());
        assertEquals(0,  test.getUEs().get(1).getEffectifTotalSite4());

    }


    private Formation createFormationTest2() {
        Formation formation = new Formation();

        Bloc b1 = new Bloc();
        b1.setEffectif(new Effectif(10, 20, 0, 0));

        Bloc b2 = new Bloc();
        b2.setEffectif(new Effectif(15, 5, 0, 0));

        Bloc b3 = new Bloc();
        b3.setEffectif(new Effectif(6, 12, 0, 0));

        Option option = new Option();

        UE ue1 = new UE();
        UE ue2 = new UE();
        UE ue3 = new UE();

        b1.addUE(ue1);
        b3.addUE(ue3);

        b1.addOption(option);
        b2.addOption(option);
        b3.addOption(option);

        option.addUE(ue1);
        option.addUE(ue2);
        option.addUE(ue3);

        formation.addBloc(b1);
        formation.addBloc(b2);
        formation.addBloc(b3);
        formation.addOption(option);
        formation.addUE(ue1);
        formation.addUE(ue2);
        formation.addUE(ue3);

        repartitionEffectifOption.calculerEffectifOption(formation);

        return repartitionEffectifOption.getFormation();

    }

    @Test
    public void test2() {
        Formation test = this.createFormationTest2();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        // test effectif option par site
        // assert effectif ue1
        assertEquals(21, test.getUEs().get(0).getEffectifTotalSite1());
        assertEquals(33, test.getUEs().get(0).getEffectifTotalSite2());
        assertEquals(0,  test.getUEs().get(0).getEffectifTotalSite3());
        assertEquals(0,  test.getUEs().get(0).getEffectifTotalSite4());

        // assert effectif ue2
        assertEquals(11, test.getUEs().get(1).getEffectifTotalSite1());
        assertEquals(13, test.getUEs().get(1).getEffectifTotalSite2());
        assertEquals(0,  test.getUEs().get(1).getEffectifTotalSite3());
        assertEquals(0,  test.getUEs().get(1).getEffectifTotalSite4());

        // assert effectif ue3
        assertEquals(17, test.getUEs().get(2).getEffectifTotalSite1());
        assertEquals(25, test.getUEs().get(2).getEffectifTotalSite2());
        assertEquals(0,  test.getUEs().get(2).getEffectifTotalSite3());
        assertEquals(0,  test.getUEs().get(2).getEffectifTotalSite4());
    }



    private Formation createFormationTest3() {
        Formation formation = new Formation();

        Bloc b1 = new Bloc();
        b1.setEffectif(new Effectif(50, 20, 22, 12));

        Bloc b2 = new Bloc();
        b2.setEffectif(new Effectif(50, 35, 37, 5));

        Option option1 = new Option();
        Option option2 = new Option();

        UE ue1 = new UE();
        UE ue2 = new UE();

        formation.addBloc(b1);
        formation.addBloc(b2);
        formation.addOption(option1);
        formation.addOption(option2);
        formation.addUE(ue1);
        formation.addUE(ue2);

        b1.addOption(option1);
        b1.addOption(option2);
        b2.addOption(option1);

        b1.addUE(ue2);

        option1.addUE(ue1);
        option1.addUE(ue2);
        option2.addUE(ue1);
        option2.addUE(ue2);

        repartitionEffectifOption.calculerEffectifOption(formation);

        return repartitionEffectifOption.getFormation();

    }

    @Test
    public void test3() {
        Formation test = this.createFormationTest3();

        calculEffectifUE.calculerEffectifUEs(test);
        test = calculEffectifUE.getFormation();

        // test effectif option par site
        // assert effectif ue1
        assertEquals(75, test.getUEs().get(0).getEffectifTotalSite1());
        assertEquals(38, test.getUEs().get(0).getEffectifTotalSite2());
        assertEquals(41,  test.getUEs().get(0).getEffectifTotalSite3());
        assertEquals(15,  test.getUEs().get(0).getEffectifTotalSite4());

        // assert effectif ue2
        assertEquals(125, test.getUEs().get(1).getEffectifTotalSite1());
        assertEquals(58, test.getUEs().get(1).getEffectifTotalSite2());
        assertEquals(63,  test.getUEs().get(1).getEffectifTotalSite3());
        assertEquals(27,  test.getUEs().get(1).getEffectifTotalSite4());
    }

}
