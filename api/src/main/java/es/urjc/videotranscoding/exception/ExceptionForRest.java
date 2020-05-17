package es.urjc.videotranscoding.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is for construct a ResponseBody for the rest Controller in case
 * throw exception.
 * 
 * @author luisca
 * @Since 0.5
 */
@Data
@AllArgsConstructor
public class ExceptionForRest {

	private String message;

	private List<String> params;

}
