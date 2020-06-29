package es.urjc.videotranscoding.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import es.urjc.videotranscoding.entities.Original;

/**
 * Integration test for original repository
 * 
 * @author luisca
 *
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OriginalRepositoryTest {
	private Original original;
	@Autowired
	private OriginalRepository originalRepository;

	@Test
	@Disabled
	public void repositoryCheckOriginal() {
		try {
			Optional<Original> originalFind = originalRepository.findById(original.getId());
			if (originalFind.isPresent()) {
				assertEquals(originalFind.get().getName(), original.getName());
				assertEquals(originalFind.get().getPath(), original.getPath());
			} else {
				fail("Not video found");
			}
		} catch (Exception e) {
			fail("Exception on search original");
		}
	}

	@Test
	@Disabled
	public void repositoryEditOriginal() {
		try {
			Optional<Original> originalFind = originalRepository.findById(original.getId());
			if (originalFind.isPresent()) {
				originalFind.get().setName("New name");
				originalFind.get().setPath("New path");
				originalRepository.save(originalFind.get());
			} else {
				fail("Not video found");
			}
		} catch (Exception e) {
			fail("Exception on edit original");
		}
	}

	@Test
	@Disabled
	public void repositorySameOriginalSaved() {
		try {
			Original original2 = new Original("originalVideo", "newPath", "User1");
			originalRepository.save(original2);
			originalRepository.findAll();
			fail("No throwed exception");
		} catch (Exception e) {

		}
	}

}
