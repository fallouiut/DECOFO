package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * README
 * Veuillez lancer les tests un par un
 */
@SpringBootTest
public class OptionManagerTest {

    @Autowired
    OptionManager optionManager;

    private static int optionId;

    @BeforeEach
    public void trouverId() {
        optionId = optionManager.findAll().get(0).getId();
        System.err.println("UEid = " + optionId);
    }

    @Test
    public void testInsert(){
        Option option = new Option("test1","Test1.1",5, 8);
        this.optionManager.insert(option);

    }

    @Test
    public void findOne() {
        Option option = this.optionManager.findOne(optionId);
        assertNotNull(option);
        System.err.println("Find one : + " + option.toString());
        System.err.println("-----------------------------");
    }

    @Test
    public void testFindAll(){
        Option option = new Option("test2","test2.2",8,7);
        this.optionManager.insert(option);

        List<Option> optionList = optionManager.findAll();
        System.err.println("Size of tab: " + optionList.size());
        assertTrue( optionList.size() >= 2);
    }

    @Test
    public void testUpdate() throws Exception {
        Option option = optionManager.findOne(optionId);

        option.setIntitule("Test1.1 update");
        option.setCout(20);

        this.optionManager.update(option);
    }

    @Test
    public void deleteOptions() throws Exception {
        this.optionManager.delete(optionId);
    }

}
