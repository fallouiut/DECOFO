package m2info.ter.decofo;

import m2info.ter.decofo.classes.Formation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
class DecofoApplicationTests {

	@Test
	void addFormation() {
		List<Integer> integers = new ArrayList<>();

		double test = 2.3;

		int parse = (int)Math.ceil(test);


		System.out.println("Parsed value: " + parse);


	}
}
