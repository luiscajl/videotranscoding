package es.urjc.videotranscoding.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.videotranscoding.entities.Conversion;
import es.urjc.videotranscoding.entities.Original;
import es.urjc.videotranscoding.repository.ConversionRepository;
import es.urjc.videotranscoding.service.ConversionService;
import es.urjc.videotranscoding.service.FileUtilsFFmpeg;
import es.urjc.videotranscoding.service.OriginalService;

@Service
public class ConversionServiceImpl implements ConversionService {

	@Autowired
	private ConversionRepository conversionRepository;
	@Autowired
	private FileUtilsFFmpeg fileUtilsService;
	@Autowired
	private OriginalService originalService;

	
	public void save(Conversion video) {
		conversionRepository.save(video);
	}

	public Optional<Conversion> findOneConversion(Integer id) {
		return conversionRepository.findById(id);
	}

	public String deleteConversion(Original original, Conversion conversion, String u) {
		// if (u.isAdmin()) {
		fileUtilsService.deleteFile(conversion.getPath());
		original.removeConversion(conversion);
		originalService.save(original);
		conversionRepository.deleteById(conversion.getConversionId());
		// } else {
		// if (u.getListVideos().contains(original)) {
		fileUtilsService.deleteFile(conversion.getPath());
		original.removeConversion(conversion);
		fileUtilsService.deleteFile(conversion.getPath());
		conversionRepository.deleteById(conversion.getConversionId());
		// }
		// }
		return u;
	}

}
