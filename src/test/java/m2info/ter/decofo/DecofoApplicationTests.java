package m2info.ter.decofo;

import m2info.ter.decofo.classes.Formation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
class DecofoApplicationTests {
/*
	@Autowired
	Manager manager;

	@Test
	void addFormation() {
		Formation f = new Formation(1, "M2ILD", 150, "M2 - ILD", 5, 2, 3);

		assertThat(manager.insert(f));

	}

	@Test
	public void findAll() {
		List<Formation> formations = manager.findAll();

		assertThat(formations.size() == 3);

	}

	@Test
	public void findOneWorks() {
		assertThat(manager.findOne(1) != null);
	}

	@Test
	public void findOneNotWorks() {
		assertThat(manager.findOne(5) == null);
	}*/
}
