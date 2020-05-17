package es.urjc.videotranscoding.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luisca
 * @Since 0.5
 */
public class FFmpegException extends Exception {
	private static final long serialVersionUID = 5434866761753051706L;

	/**
	 * EXCEPTIONS
	 */
	public static final String EX_FFMPEG_EMPTY_OR_NULL = "FFmpeg path is null or empty.";
	public static final String EX_FFMPEG_NOT_FOUND = "FFmpeg not found on this computer.";
	public static final String EX_FOLDER_OUTPUT_EMPTY_OR_NULL = "Folder output for files is null or empty";
	public static final String EX_FOLDER_OUTPUT_NOT_EXITS = "Folder output for files not exits;";
	public static final String EX_ORIGINAL_VIDEO_NULL = "Original Video is null.";
	public static final String EX_ORIGINAL_VIDEO_NOT_IS_SAVE = "Original Video is not save.";
	public static final String EX_EXECUTION_EXCEPTION = "The current thread to convert the video has been Stringerrupt for other that was waiting.";
	public static final String EX_IO_EXCEPTION_BY_EXEC = "The command not exec correctly.";
	public static final String EX_IO_EXCEPTION_GENERAL = "No conversion type found for this video.";

	public static final String EX_NO_CONVERSION_TYPE_FOUND = "Line failed to read.";
	public static final String EX_VIDEO_EXITS = "The video exits already";
	public static final String EX_NO_VIDEO_FOUND = "No video found for this id.";
	public static final String EX_NOT_VIDEO_FILE = "The file not is a video file.";

	private List<String> params = new ArrayList<>();

	/**
	 * Default constructor
	 */
	public FFmpegException() {
	}

	/**
	 * Constructor
	 * 
	 * @param message message for the exception
	 */
	public FFmpegException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param message   message for the exception
	 * @param throwable with the cause of the exception
	 * 
	 */
	public FFmpegException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * Constructor
	 * 
	 * @param cause Throwable Cause of the exception
	 */
	public FFmpegException(Throwable throwable) {
		super(throwable);
	}

	public FFmpegException(String exception, List<String> params) {
		super(exception);
		this.params = params;
	}

	public List<String> getParams() {
		return this.params;
	}

}
