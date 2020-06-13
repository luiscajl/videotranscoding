package es.urjc.videotranscoding.restController;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.urjc.videotranscoding.codecs.CodecAudio;
import es.urjc.videotranscoding.codecs.ConversionType;
import es.urjc.videotranscoding.codecs.ConversionTypeBasic;
import es.urjc.videotranscoding.core.VideoTranscodingService;
import es.urjc.videotranscoding.entities.Conversion;
import es.urjc.videotranscoding.entities.Original;
import es.urjc.videotranscoding.exception.ExceptionForRest;
import es.urjc.videotranscoding.exception.FFmpegException;
import es.urjc.videotranscoding.service.OriginalService;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/conversion")
// @Api(tags = "Conversion Operations")
@CrossOrigin({ "http://localhost:4200", "https://old.videotranscoding.es" })
public class ConversionRestController {

	@Autowired
	private OriginalService originalService;
	@Autowired
	private VideoTranscodingService videoTranscodingService;

	public interface Details extends Original.Basic, Original.Details, Conversion.Basic, Conversion.Details {
	}

	/**
	 * Get the types of conversion
	 * 
	 * @param principal user Logged
	 * @return the list of conversion basic
	 * @throws FFmpegException
	 */
	@GetMapping(value = "/basic", produces = MediaType.APPLICATION_JSON_VALUE)
	// @ApiOperation(value = "Get types basic of conversion")
	public ResponseEntity<List<Enum>> getConversionBasic(Principal principal) {
		List<Enum> o = ConversionTypeBasic.getAllTypesBasic();
		o.forEach(fff -> System.out.println(fff));

		return ResponseEntity.ok(ConversionTypeBasic.getAllTypesBasic());

	}

	/**
	 * Take a MultipartFile and create an original video and her conversions with
	 * list of conversion as params
	 * 
	 * 
	 * @param params    with the types of conversion videos ready for converter
	 * @param file      with the file to converter
	 * @param principal user Logged
	 * @return the original video with conversionVideos for the video / exception
	 * @throws FFmpegException
	 */
	@PostMapping(value = "/basic")
	// @ApiOperation(value = "Send the video for conversion basic with his
	// conversion type")
	// @JsonView(Details.class)
	public ResponseEntity<Object> addConversionBasic(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "conversionType") List<String> conversionList, Principal principal)
			throws FFmpegException {

		Original original = originalService.addOriginalBasic(principal.getName(), file, conversionList);
		videoTranscodingService.transcodeVideo(original);
		return new ResponseEntity<>(original, HttpStatus.CREATED);
	}

	/**
	 * Types of conversions for the api expert
	 * 
	 * @param principal logged user
	 * @return All types of conversion for the api expert
	 */
	// @ApiOperation(value = "Get types expert of conversion")
	@GetMapping(value = "/expert")
	public ResponseEntity<List<ConversionType>> getAllVideoConversionsType(Principal principal) {
		// User u = userService.findOneUser(principal.getName());
		// if (u == null) {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
		List<ConversionType> conversionTypes = Arrays.asList(ConversionType.values());
		return new ResponseEntity<List<ConversionType>>(conversionTypes, HttpStatus.OK);
	}

	/**
	 * Take a MultipartFile and create an original video and her conversions with
	 * the multivaluemap
	 * 
	 * 
	 * @param params    with the types of conversion(conversionType) videos ready
	 *                  for converter
	 * @param file      with the file to converter
	 * @param principal user Logged
	 * @return the original video with conversionVideos for the video / exception
	 * @throws FFmpegException
	 */
	@PostMapping(value = "/expert")
	// @ApiOperation(value = "Send the video for conversion expert with his
	// conversion type")
	// @JsonView(Details.class)
	public ResponseEntity<Original> addConversionExpert(@RequestParam(value = "conversionType") List<String> params,
			@RequestParam(value = "file") MultipartFile file, Principal principal) throws FFmpegException {
		// User u = userService.findOneUser(principal.getName());
		// if (u == null) {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
		Original original = originalService.addOriginalExpert(principal.getName(), file, params);
		params.forEach(System.out::println);
		videoTranscodingService.transcodeVideo(original);
		return new ResponseEntity<>(original, HttpStatus.CREATED);
	}

	/**
	 * Handler for the exceptions
	 * 
	 * @param e exception
	 * @return a ExceptionForRest with the exception
	 */
	@ExceptionHandler(FFmpegException.class)
	public ResponseEntity<ExceptionForRest> exceptionHandler(FFmpegException e) {
		ExceptionForRest error = new ExceptionForRest(e.getLocalizedMessage(), e.getParams());
		return new ResponseEntity<ExceptionForRest>(error, HttpStatus.BAD_REQUEST);
	}

}
