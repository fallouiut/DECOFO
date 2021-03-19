package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RepartitionEffectifOptionTest {

    @Autowired
    RepartitionEffectifOption repartitionEffectifOption;

    @Test
    public void notNull() {
        assertNotNull(repartitionEffectifOption);
    }

    private Formation createFormationTest1() {
        Formation formation = new Formation();

        Bloc b2 = new Bloc();
        b2.setEffectif(new Effectif(0, 20, 0, 0));

        Bloc b3 = new Bloc();
        b3.setEffectif(new Effectif(5, 10, 0, 0));

        Option option = new Option();

        option.addUE(new UE());
        option.addUE(new UE());

        b2.addOption(option);
        b3.addOption(option);

        formation.addOption(option);
        return formation;

    }

    @Test
    public void test1() {
        Formation test = this.createFormationTest1();

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        // test effectif option par site
        assertEquals(5, test.getOptions() .get(0).getEffectifTotalParSite().getSite1());
        assertEquals(30,test.getOptions() .get(0).getEffectifTotalParSite().getSite2());
        assertEquals(0, test.getOptions() .get(0).getEffectifTotalParSite().getSite3());
        assertEquals(0, test.getOptions() .get(0).getEffectifTotalParSite().getSite4());
        // test effectif par UE
        assertEquals(3, test.getOptions() .get(0).getEffectifParUE().getSite1());
        assertEquals(15,test.getOptions() .get(0).getEffectifParUE().getSite2());
        assertEquals(0, test.getOptions() .get(0).getEffectifParUE().getSite3());
        assertEquals(0, test.getOptions() .get(0).getEffectifParUE().getSite4());
    }

    private Formation createFormationTest2() {
        Formation formation = new Formation();

        Bloc b2 = new Bloc();
        b2.setEffectif(new Effectif(0, 0, 0, 0));

        Bloc b3 = new Bloc();
        b3.setEffectif(new Effectif(0, 0, 0, 0));

        Option option = new Option();

        option.addUE(new UE());
        option.addUE(new UE());

        b2.addOption(option);
        b3.addOption(option);

        formation.addOption(option);
        return formation;

    }

    @Test
    public void test2() {
        Formation test = this.createFormationTest2();

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        // test effectif option par site
        assertEquals(0, test.getOptions() .get(0).getEffectifTotalParSite().getSite1());
        assertEquals(0,test.getOptions() .get(0).getEffectifTotalParSite().getSite2());
        assertEquals(0, test.getOptions() .get(0).getEffectifTotalParSite().getSite3());
        assertEquals(0, test.getOptions() .get(0).getEffectifTotalParSite().getSite4());
        // test effectif par UE
        assertEquals(0, test.getOptions() .get(0).getEffectifParUE().getSite1());
        assertEquals(0,test.getOptions() .get(0).getEffectifParUE().getSite2());
        assertEquals(0, test.getOptions() .get(0).getEffectifParUE().getSite3());
        assertEquals(0, test.getOptions() .get(0).getEffectifParUE().getSite4());
    }


    private Formation createFormationTest3() {
        Formation formation = new Formation();

        Bloc b1 = new Bloc();
        b1.setEffectif(new Effectif(10, 20, 0, 0));

        Bloc b2 = new Bloc();
        b2.setEffectif(new Effectif(15, 5, 0, 0));

        Bloc b3 = new Bloc();
        b3.setEffectif(new Effectif(6, 12, 0, 0));

        Option option = new Option();

        option.addUE(new UE());
        option.addUE(new UE());
        option.addUE(new UE());

        b1.addOption(option);
        b2.addOption(option);
        b3.addOption(option);

        formation.addOption(option);
        return formation;

    }

    @Test
    public void test3() {
        Formation test = this.createFormationTest3();

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        // test effectif option par site
        assertEquals(31, test.getOptions() .get(0).getEffectifTotalParSite().getSite1());
        assertEquals(37,test.getOptions() .get(0).getEffectifTotalParSite().getSite2());
        assertEquals(0, test.getOptions() .get(0).getEffectifTotalParSite().getSite3());
        assertEquals(0, test.getOptions() .get(0).getEffectifTotalParSite().getSite4());
        // test effectif par UE
        assertEquals(11, test.getOptions() .get(0).getEffectifParUE().getSite1());
        assertEquals(13,test.getOptions() .get(0).getEffectifParUE().getSite2());
        assertEquals(0, test.getOptions() .get(0).getEffectifParUE().getSite3());
        assertEquals(0, test.getOptions() .get(0).getEffectifParUE().getSite4());
    }



    private Formation createFormationTest4() {
        Formation formation = new Formation();

        Bloc b1 = new Bloc();
        b1.setEffectif(new Effectif(50, 20, 22, 12));

        Bloc b2 = new Bloc();
        b2.setEffectif(new Effectif(50, 35, 37, 5));

        Option option1 = new Option();
        option1.addUE(new UE());
        option1.addUE(new UE());

        Option option2 = new Option();
        option2.addUE(new UE());
        option2.addUE(new UE());

        b1.addOption(option1);
        b1.addOption(option2);
        b2.addOption(option1);


        formation.addOption(option1);
        formation.addOption(option2);
        return formation;

    }

    @Test
    public void test4() {
        Formation test = this.createFormationTest4();

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        // test effectif option par site option 1
        assertEquals(100, test.getOptions() .get(0).getEffectifTotalParSite().getSite1());
        assertEquals(55,test.getOptions() .get(0).getEffectifTotalParSite().getSite2());
        assertEquals(59, test.getOptions() .get(0).getEffectifTotalParSite().getSite3());
        assertEquals(17, test.getOptions() .get(0).getEffectifTotalParSite().getSite4());
        // test effectif par UE
        assertEquals(50, test.getOptions() .get(0).getEffectifParUE().getSite1());
        assertEquals(28,test.getOptions() .get(0).getEffectifParUE().getSite2());
        assertEquals(30, test.getOptions() .get(0).getEffectifParUE().getSite3());
        assertEquals(9, test.getOptions() .get(0).getEffectifParUE().getSite4());

        // test effectif option par site option 2
        assertEquals(50, test.getOptions() .get(1).getEffectifTotalParSite().getSite1());
        assertEquals(20,test.getOptions() .get(1).getEffectifTotalParSite().getSite2());
        assertEquals(22, test.getOptions() .get(1).getEffectifTotalParSite().getSite3());
        assertEquals(12, test.getOptions() .get(1).getEffectifTotalParSite().getSite4());
        // test effectif par UE
        assertEquals(25, test.getOptions() .get(1).getEffectifParUE().getSite1());
        assertEquals(10,test.getOptions() .get(1).getEffectifParUE().getSite2());
        assertEquals(11, test.getOptions() .get(1).getEffectifParUE().getSite3());
        assertEquals(6, test.getOptions() .get(1).getEffectifParUE().getSite4());
    }

}
