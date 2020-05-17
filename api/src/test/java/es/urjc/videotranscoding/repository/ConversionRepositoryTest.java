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

import es.urjc.videotranscoding.codecs.ConversionType;
import es.urjc.videotranscoding.entities.Conversion;
import es.urjc.videotranscoding.entities.Original;

/**
 * Integration test for conversion repository
 * 
 * @author luisca
 *
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ConversionRepositoryTest {
	private Original original;
	private Conversion conversion;
	@Autowired
	private OriginalRepository originalRepository;
	@Autowired
	private ConversionRepository conversionRepository;

	@BeforeEach
	public void beforeTest() {

		original = new Original("originalVideo", "newPath", "UserTest");
		originalRepository.save(original);
		conversion = new Conversion(ConversionType.MKV_H264360_AAC, original);
		conversionRepository.save(conversion);
	}

	@Test
	@Disabled
	public void repositoryCheckConversion() {
		try {
			Optional<Conversion> conversionF = conversionRepository.findById(conversion.getConversionId());
			if (conversionF.isPresent()) {
				assertEquals(conversionF.get().getName(), conversion.getName());
				assertEquals(conversionF.get().getPath(), conversion.getPath());
			} else {
				fail("Not conversion found");
			}
		} catch (Exception e) {
			fail("Exception on search conversion");
		}
	}

	@Test
	@Disabled
	public void repositoryEditConversion() {
		try {
			Optional<Conversion> conversionF = conversionRepository.findById(conversion.getConversionId());
			if (conversionF.isPresent()) {
				conversionF.get().setConversionType(ConversionType.MKV_H264480_COPY);
				conversionF.get().setFinished(true);
				conversionF.get().setPath("New path edit");
				conversionRepository.save(conversionF.get());
			} else {
				fail("Not conversion found");
			}
		} catch (Exception e) {
			fail("Exception on search conversion");
		}
	}

	@Test
	@Disabled
	public void repositoryDeleteConversion() {
		try {
			Original original2 = new Original("New name2", "new pth 2", "User2");
			originalRepository.save(original2);
			Conversion conversionNew = new Conversion(ConversionType.MKV_HEVC360_AAC, original2);
			conversionRepository.save(conversionNew);
			Optional<Conversion> conversionFind2 = conversionRepository.findById(conversionNew.getConversionId());
			if (conversionFind2.isPresent()) {
				Original originalT = conversionFind2.get().getParent();
				originalT.removeConversion(conversionFind2.get());
				conversionRepository.delete(conversionFind2.get());
				originalRepository.save(originalT);
			} else {
				fail("Not video found");
			}
		} catch (Exception e) {
			fail("Exception on delete original");
		}
	}

	/**
	 * The conversion is overwrited
	 */
	@Test
	@Disabled
	public void repositorySameConversionSaved() {
		try {
			Conversion conversion2 = new Conversion(ConversionType.MKV_H264360_AAC, original);
			conversionRepository.save(conversion2);
			conversionRepository.findAll();
		} catch (Exception e) {
			fail("No exception");
		}
	}

}
