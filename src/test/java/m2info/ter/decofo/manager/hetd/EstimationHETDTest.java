package m2info.ter.decofo.manager.hetd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EstimationHETDTest {

    @Autowired
    EstimationHETD estimationHETD;

    @Test
    public void testNotNull() {
        assertNotNull(estimationHETD);
    }

}
