package es.urjc.videotranscoding.core.impl;

import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.urjc.videotranscoding.codecs.ConversionType;
import es.urjc.videotranscoding.codecs.ConversionTypeBasic;
import es.urjc.videotranscoding.core.VideoTranscodingService;
import es.urjc.videotranscoding.entities.Conversion;
import es.urjc.videotranscoding.entities.Original;
import es.urjc.videotranscoding.exception.FFmpegException;
import es.urjc.videotranscoding.exception.FFmpegRuntimeException;
import es.urjc.videotranscoding.service.OriginalService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VideoTranscodingFFmpegImplTest {

	private final String VIDEO_DEMO = "path.video.demo";
	private final static String FOLDER_OUPUT_ORIGINAL = "path.folder.original";
	private final static String FOLDER_OUTPUT_TRANSCODE = "path.folder.ouput";

	@Autowired
	private VideoTranscodingService transcoding;
	@Autowired
	private OriginalService originalService;

	@Resource
	private Properties propertiesFFmpegTest;
	@Resource
	private Properties propertiesFFmpeg;
	@Resource
	private Properties propertiesFicheroCore;

	@BeforeEach
	public void setUp() throws IOException {
		createFolder(propertiesFFmpeg.getProperty(FOLDER_OUPUT_ORIGINAL));
		createFolder(propertiesFFmpeg.getProperty(FOLDER_OUTPUT_TRANSCODE));

	}

	private void createFolder(String string) {
		File folder = new File(string);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	@AfterEach
	public void afterClass() {
		File tempFolderOuput = new File("/temp");
		tempFolderOuput.delete();

	}

	@Test
	@Disabled
	public void transcodeSucess() {
		Original video = new Original("Perico", "/src/main/resources/resources/big_buck_bunny.mp4", "User1");
		Conversion newVideo = new Conversion(ConversionType.MKV_H264360_COPY, video);
		List<Conversion> lista = new ArrayList<>();
		lista.add(newVideo);
		video.setConversions(lista);
		originalService.save(video);
		try {
			transcoding.transcodeVideo(video);
			Thread.sleep(1000000000);
		} catch (FFmpegException e) {
			e.printStackTrace();
			fail("No should fail");
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("No should fail");
		} catch (FFmpegRuntimeException e) {
			e.printStackTrace();
			fail("No should fail");
		}
	}

}
