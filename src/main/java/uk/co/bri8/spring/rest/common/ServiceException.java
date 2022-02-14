package uk.co.bri8.spring.rest.common;

@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {

	private String errorMessage;

	public ServiceException(String message) {
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
