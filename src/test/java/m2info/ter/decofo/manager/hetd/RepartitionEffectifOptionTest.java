package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
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

    private Formation createFormationTest(int effectifTotal, int nombreUe) {
        Formation formation = new Formation();

        Option option = new Option();
        option.setEffectifTotal(effectifTotal);

        for(int i = 0; i < nombreUe; ++i) {
            option.addUE(new UE());
        }

        formation.addOption(option);
        return formation;

    }

    @Test
    public void test1() {
        Formation test = this.createFormationTest(100, 3);

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        assertEquals(33, test.getOptions().get(0).getEffectifParUe());

    }

    @Test
    public void test2() {
        Formation test = this.createFormationTest(100, 5);

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        assertEquals(20, test.getOptions().get(0).getEffectifParUe());

    }

    @Test
    public void test3() {
        Formation test = this.createFormationTest(200, 4);

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        assertEquals(50, test.getOptions().get(0).getEffectifParUe());

    }

    @Test
    public void test4() {
        Formation test = this.createFormationTest(100, 6);

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        assertEquals(16, test.getOptions().get(0).getEffectifParUe());

    }

    @Test
    public void test5() {
        Formation test = this.createFormationTest(60, 6);

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        assertEquals(10, test.getOptions().get(0).getEffectifParUe());

    }

    @Test
    public void test6() {
        Formation test = this.createFormationTest(60, 0);

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        assertEquals(0, test.getOptions().get(0).getEffectifParUe());

    }

    @Test
    public void test7() {
        Formation test = this.createFormationTest(20, 1);

        repartitionEffectifOption.calculerEffectifOption(test);
        test = repartitionEffectifOption.getFormation();

        assertEquals(20, test.getOptions().get(0).getEffectifParUe());

    }

}
