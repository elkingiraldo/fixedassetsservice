package co.com.grupoasd.services.fixedassets.exception;

import org.springframework.http.HttpStatus;

/**
 * Errors found into the service
 * 
 * @author egiraldo
 *
 */
public enum FixedAssetsServiceErrorCodes implements IFixedAssetsServiceErrorMsg {

	GENERAL_EXCEPTION("general.exception", HttpStatus.INTERNAL_SERVER_ERROR),

	CRITERIA_NOT_FOUND("criteria.not.found.exception", HttpStatus.BAD_REQUEST);

	private String message;
	private HttpStatus httpStatus;
	private String errorDetail;

	private FixedAssetsServiceErrorCodes(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	private FixedAssetsServiceErrorCodes(String message, String errorDetail, HttpStatus httpStatus) {
		this.message = message;
		this.errorDetail = errorDetail;
		this.httpStatus = httpStatus;
	}

	private FixedAssetsServiceErrorCodes(String message) {
		this.message = message;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
