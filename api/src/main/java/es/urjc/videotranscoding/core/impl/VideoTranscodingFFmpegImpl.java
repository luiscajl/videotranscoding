package es.urjc.videotranscoding.core.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import es.urjc.videotranscoding.codecs.ConversionType;
import es.urjc.videotranscoding.core.VideoTranscodingService;
import es.urjc.videotranscoding.dto.StatusDTO;
import es.urjc.videotranscoding.entities.Conversion;
import es.urjc.videotranscoding.entities.Original;
import es.urjc.videotranscoding.exception.FFmpegException;
import es.urjc.videotranscoding.exception.FFmpegRuntimeException;
import es.urjc.videotranscoding.service.ConversionService;
import es.urjc.videotranscoding.service.impl.FileUtilsFFmpegImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Getter
public class VideoTranscodingFFmpegImpl implements VideoTranscodingService {
	/**
	 * TRACE and log4J
	 */
	private static final String TRACE_FFMPEG_NULL_OR_EMPTY = "ffmpeg.nullOrEmpty";
	private static final String TRACE_FFMPEG_NOT_FOUND = "ffmpeg.notFound";
	private static final String TRACE_FOLDER_OUTPUT_NULL_OR_EMPTY = "ffmpeg.folderOuput.nullOrEmpty";
	private static final String TRACE_FOLDER_OUPUT_NOT_EXISTS = "ffmpeg.folderOutput.notExits";
	private static final String TRACE_ORIGINAL_VIDEO_NULL = "ffmpeg.originalVideo.null";
	private static final String TRACE_ORIGINAL_VIDEO_NOT_IS_SAVE = "ffmpeg.originalVideo.notSave";
	private static final String TRACE_INTERRUP_EXCEPTION = "ffmpeg.interrupt.exception";
	private static final String TRACE_IO_EXCEPTION_BY_EXEC = "ffmpeg.io.exception.exec";
	private static final String TRACE_EXCEPTION_EXECUTOR_SERVICE = "ffmpeg.exception.executor.service";
	private static final String TRACE_COMMAND_TO_SEND = "ffmpeg.command.to.send";
	/**
	 * Paths Instalation FFMPEG
	 */
	private final String FFMPEG_INSTALLATION_CENTOS7 = "path.ffmpeg.centos";
	private final String FFMPEG_INSTALLATION_MACOSX = "path.ffmpeg.macosx";
	private final String DEFAULT_UPLOAD_FILES = "path.folder.ouput";
	/**
	 * 
	 */
	private StreamGobbler errorGobbler;
	private StreamGobbler inputGobbler;
	private StreamGobbler outputGobbler;
	private final ExecutorService executorService = Executors.newFixedThreadPool(3);

	static ExecutorService serviceConversion = Executors.newFixedThreadPool(1);

	@Resource
	private Environment propertiesFFmpeg;

	@Autowired
	private FileUtilsFFmpegImpl fileUtilsService;
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private StreamGobblerFactory streamGobblerPersistentFactory;

	/**
	 * Chack the SO and return the path of the installation of ffmpeg
	 * 
	 * @return the path of the ffmpeg
	 * @throws FFmpegException
	 */
	private String getPathOfProgram() throws FFmpegRuntimeException {
		String pathFFMPEG;
		if ((System.getProperty("os.name").equals("Mac OS X"))) {
			pathFFMPEG = propertiesFFmpeg.getProperty(FFMPEG_INSTALLATION_MACOSX);
		} else {
			pathFFMPEG = propertiesFFmpeg.getProperty(FFMPEG_INSTALLATION_CENTOS7);
		}
		if (!StringUtils.hasText(pathFFMPEG)) {
			log.error(TRACE_FFMPEG_NULL_OR_EMPTY);
			throw new FFmpegRuntimeException(FFmpegException.EX_FFMPEG_EMPTY_OR_NULL);
		}
		if (!fileUtilsService.exitsFile(pathFFMPEG)) {
			log.error(TRACE_FFMPEG_NOT_FOUND, pathFFMPEG);
			throw new FFmpegRuntimeException(FFmpegException.EX_FFMPEG_NOT_FOUND, List.of(pathFFMPEG));
		}
		return pathFFMPEG;
	}

	/**
	 * Get the path for save the video of ffmpeg
	 * 
	 * @return the path
	 * @throws FFmpegException
	 */
	private String getPathToSaveFiles() throws FFmpegRuntimeException {
		String folderOutput = propertiesFFmpeg.getProperty(DEFAULT_UPLOAD_FILES);
		if (!StringUtils.hasText(folderOutput)) {
			log.error(TRACE_FOLDER_OUTPUT_NULL_OR_EMPTY);
			throw new FFmpegRuntimeException(FFmpegException.EX_FOLDER_OUTPUT_EMPTY_OR_NULL);
		}
		if (!fileUtilsService.exitsDirectory(folderOutput)) {
			log.error(TRACE_FOLDER_OUPUT_NOT_EXISTS, new String[] { folderOutput }, null);
			new File(folderOutput).mkdirs();
			// throw new FFmpegRuntimeException(FFmpegException.EX_FOLDER_OUTPUT_NOT_EXITS,
			// List.of(folderOutput));
		}
		return folderOutput;
	}

	/**
	 * 
	 * @see
	 */
	public void transcodeVideo(Original original) throws FFmpegRuntimeException {
		if (original == null) {
			log.error(TRACE_ORIGINAL_VIDEO_NULL);
			throw new FFmpegRuntimeException(FFmpegException.EX_ORIGINAL_VIDEO_NULL);
		}
		if (!fileUtilsService.exitsFile(original.getPath())) {
			log.error(TRACE_ORIGINAL_VIDEO_NOT_IS_SAVE, new String[] { original.getPath() }, null);
			throw new FFmpegRuntimeException(FFmpegException.EX_ORIGINAL_VIDEO_NOT_IS_SAVE,
					List.of(original.getPath()));
		}
		// String pathFFmpeg = getPathOfProgram();
		// TODO: Check this method to optimize it
		String pathFFmpeg = "ffmpeg";
		

		File fileOV = new File(original.getPath());
		String pathSaveConvertedVideos = getPathToSaveFiles();
		serviceConversion.execute(new Runnable() {
			public void run() {
				original.getConversions().forEach((originalV -> {
					if (!originalV.isActive()) {
						String command = getCommand(pathFFmpeg, fileOV, pathSaveConvertedVideos, originalV);
						try {
							conversionFinal(command, originalV);
						} catch (FFmpegRuntimeException e) {
							log.error(TRACE_EXCEPTION_EXECUTOR_SERVICE, e);
							throw e;
						}
					}
				}));
			}
		});
	}

	/**
	 * Call to ffmpeg to convert the video on realtime
	 * 
	 * @param command to send ffmpeg
	 * @param video   for convert it
	 * @throws FFmpegRuntimeException
	 */
	private void conversionFinal(String command, Conversion video) throws FFmpegRuntimeException {
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			video.setActive(true);
			conversionService.save(video);
			errorGobbler = streamGobblerPersistentFactory.getStreamGobblerPersistent(proc.getErrorStream(), "ERROR",
					video);
			inputGobbler = streamGobblerPersistentFactory.getStreamGobblerPersistent(proc.getInputStream(), "INPUT",
					video);
			outputGobbler = streamGobblerPersistentFactory.getStreamGobblerPersistent(proc.getInputStream(), "OUTPUT",
					video);
			executorService.execute(errorGobbler);
			executorService.execute(inputGobbler);
			executorService.execute(outputGobbler);
			int exitVal = proc.waitFor();
			if (exitVal == 0) {
				video.setFinished(true);
			} else {
				video.setFinished(false);
			}
		} catch (InterruptedException e) {
			video.setFinished(false);
			video.setActive(false);
			log.error(TRACE_INTERRUP_EXCEPTION);
			throw new FFmpegRuntimeException(FFmpegRuntimeException.EX_EXECUTION_EXCEPTION, e);
		} catch (IOException e) {
			FFmpegRuntimeException ex = new FFmpegRuntimeException(FFmpegRuntimeException.EX_IO_EXCEPTION_BY_EXEC,
					List.of(command), e);
			log.error(TRACE_IO_EXCEPTION_BY_EXEC, new String[] { command }, ex);
			video.setFinished(false);
			video.setActive(false);
			throw ex;
		} finally {
			conversionService.save(video);
		}
	}

	/**
	 * Get the command for ffmpeg on bash
	 * 
	 * @param pathFFMPEG   installation of ffmpeg
	 * @param fileInput    the file to convert. Needs the name
	 * @param folderOutput where save the video
	 * @param conversion   for the type of conversion
	 * @return the command ready to send it
	 */
	private String getCommand(String pathFFMPEG, File fileInput, String folderOutput, Conversion conversion) {

		String finalPath = folderOutput + getFinalNameFile(fileInput, conversion.getConversionType(),
				conversion.getConversionType().getContainerType());
		conversion.setPath(finalPath);
		conversionService.save(conversion);
		String command = pathFFMPEG + " -i " + fileInput.toString() + conversion.getConversionType().getCodecAudioType()
				+ conversion.getConversionType().getCodecVideoType() + finalPath;

		log.debug(TRACE_COMMAND_TO_SEND, new String[] { command }, null);
		return command;

	}

	/**
	 * Get the final name for the command
	 * 
	 * @param fileInput      of file to converted.
	 * @param conversionType
	 * @param extension      of the futher nameFile
	 * @return String with the final name of the file
	 */
	private String getFinalNameFile(File fileInput, ConversionType conversionType, String extension) {
		return FilenameUtils.getBaseName(fileInput.getName()) + "_" + conversionType + extension;
	}

	@Override
	public StatusDTO getStatus() {
		return StatusDTO.builder().progress(errorGobbler.getProgress()).build();
	}

}
