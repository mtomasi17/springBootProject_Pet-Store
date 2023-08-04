package pet.store.controller.error;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/*The GlobalErrorHandler class is a global error handler for the application, 
 * responsible for handling exceptions that occur during the processing of 
 * HTTP requests. When a specific exception, in this case, NoSuchElementException, 
 * occurs, this handler will intercept it and provide a customized response to the client.
 */

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNoSuchElementException(NoSuchElementException ex) {
		log.error("Pet store not found: {}", ex.getMessage());
		return "Pet store not found";
	}
	
	}


