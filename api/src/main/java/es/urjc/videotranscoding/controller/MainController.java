package es.urjc.videotranscoding.controller;

import java.security.Principal;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.urjc.videotranscoding.codecs.ConversionTypeBasic;
import es.urjc.videotranscoding.core.VideoTranscodingService;
import es.urjc.videotranscoding.entities.Original;
import es.urjc.videotranscoding.exception.FFmpegException;
import es.urjc.videotranscoding.service.OriginalService;

@Controller
public class MainController {
	@Autowired
	private VideoTranscodingService videoTranscodingService;

	@Autowired
	private OriginalService originalService;
	@Resource
	private Properties propertiesFFmpeg;

	/**
	 * Index page
	 * 
	 * @param m for fill the index page
	 * @return the template of the index page
	 */
	@GetMapping(value = "/")
	public String getIndex(Model m) {
		List<Enum> typeConversionBasic = ConversionTypeBasic.getAllTypesBasic();
		EnumSet<ConversionTypeBasic.Types> explain = EnumSet.allOf(ConversionTypeBasic.Types.class);
		m.addAttribute("conversionType", typeConversionBasic);
		m.addAttribute("explain", explain);
		return "index";
	}

	/**
	 * Status page
	 * 
	 * @return the template with the status page
	 */
	@GetMapping(value = "/status")
	public String getStatus() {
		return "status";
	}

	/**
	 * Uploaded page
	 * 
	 * @param file      with the file to convert it
	 * @param model     for fill the fields on the template
	 * @param params    with the list of conversion types
	 * @param principal the user logged
	 * @return the template with the page of file uploaded
	 * @throws FFmpegException
	 */
	@PostMapping(value = "/uploadFile")
	public String singleFileUpload(@RequestParam("fileupload") MultipartFile file, Model model,
			@RequestParam(value = "conversionType") List<String> params, @AuthenticationPrincipal OidcUser user)
			throws FFmpegException {
		Original original = originalService.addOriginalBasic(user.getName(), file, params);
		videoTranscodingService.transcodeVideo(original);
		model.addAttribute("message",
				"You successfully uploaded '" + file.getOriginalFilename() + "' and your file is being transcode");
		return "fileUploaded";
	}

	/**
	 * Call for see the status of the progress
	 * 
	 * @return the string with the % progress
	 */
	@GetMapping(value = "/ajaxCall")
	public String getStatusAjax() {
		try {
			String progress = videoTranscodingService.getStatus().getProgress().replace(",", ".");
			return progress;
		} catch (NullPointerException e) {
			return "Vacio";
		}
	}
}
