package es.urjc.videotranscoding.core.impl;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.urjc.videotranscoding.entities.Conversion;
import es.urjc.videotranscoding.repository.ConversionRepository;

@Component
public class StreamGobblerFactory {

	@Autowired
	private ConversionRepository conversionRepository;

	@Resource
	private Properties propertiesFicheroCore;

	/**
	 * Factory for stream Globber
	 * 
	 * @param is         the inputstream of the command line
	 * @param type       type of input stream
	 * @param conversion on ffmpeg
	 * @return streamglobber object with all parmams initizalize and with the
	 *         context of spring
	 */
	public StreamGobbler getStreamGobblerPersistent(InputStream is, String type, Conversion conversion) {
		return new StreamGobbler(is, type, conversion, conversionRepository);

	}

}
