package co.com.grupoasd.services.fixedassets.integrations;

/**
 * Manage invalid headers found into the request
 * 
 * @author egiraldo
 *
 */
public class InvalidHeaderParameterException extends Exception {

	private static final long serialVersionUID = 3571679413868821807L;

	private String message;
	private final String INVALID_HEADER_CODE = "service.integration.invalid.header";

	public InvalidHeaderParameterException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInvalidHeaderCode() {
		return INVALID_HEADER_CODE;
	}

	@Override
	public String toString() {
		return "InvalidHeaderParameterException [message=" + message + "]";
	}

}
