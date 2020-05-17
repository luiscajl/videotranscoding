package es.urjc.videotranscoding.exception;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import es.urjc.videotranscoding.utils.ApplicationContextProvider;

public class FFmpegRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 5434866761753051706L;
	private List<String> params;
	private static final String FICH_ERRORES = "fichero.mensajes.errores";
	private static final String PROP_FICH_CORE = "propertiesFicheroCore";
	private static final String FFMPEG_RESOURCE_BUNDLE = "ffmpegResourceBundle";
	private static final String ERROR = "error.";
	/**
	 * EXCEPTIONS
	 */
	public static final String EX_IO_EXCEPTION_READ_LINE = "Exception reading file line";

	public static final String EX_FFMPEG_EMPTY_OR_NULL = "FFmpeg path is null or empty.";
	public static final String EX_FFMPEG_NOT_FOUND = "FFmpeg {} not found on this computer.";
	public static final String EX_FOLDER_OUTPUT_EMPTY_OR_NULL = "Folder output for files is null or empty: {}";
	public static final String EX_FOLDER_OUTPUT_NOT_EXITS = "Folder output {} for files not exits;";
	public static final String EX_ORIGINAL_VIDEO_NULL = "Original Video is null.";
	public static final String EX_ORIGINAL_VIDEO_NOT_IS_SAVE = "Original Video {} is not save.";
	public static final String EX_EXECUTION_EXCEPTION = "The current thread to convert the video has been Stringerrupt for other that was waiting.";
	public static final String EX_IO_EXCEPTION_BY_EXEC = "The command {} not exec correctly.";
	public static final String EX_IO_EXCEPTION_GENERAL = "No conversion type found for this video {}.";

	public static final String EX_NO_CONVERSION_TYPE_FOUND = "Line failed to read.";
	public static final String EX_VIDEO_EXITS = "15010";
	public static final String EX_NO_VIDEO_FOUND = "No video found for this id {}.";
	public static final String EX_NOT_VIDEO_FILE = "The file {} not is a video file.";

	/**
	 * Default constructor
	 */
	public FFmpegRuntimeException() {
	}

	/**
	 * Constructor
	 * 
	 * @param message message for the exception
	 */
	public FFmpegRuntimeException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param message   message for the exception
	 * @param throwable with the cause of the exception
	 * 
	 */
	public FFmpegRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * Constructor
	 * 
	 * @param cause Throwable Cause of the exception
	 */
	public FFmpegRuntimeException(Throwable throwable) {
		super(throwable);
	}

	public FFmpegRuntimeException(String message, List<String> params) {
		super(message);
		this.params = params;
	}

	public FFmpegRuntimeException(String message, List<String> params, Exception e) {
		super(message, e);
		this.params = params;
	}

	public List<String> getParams() {
		return this.params;
	}

}
