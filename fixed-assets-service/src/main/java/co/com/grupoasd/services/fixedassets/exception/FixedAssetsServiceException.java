package co.com.grupoasd.services.fixedassets.exception;

/**
 * Errors management service
 * 
 * @author egiraldo
 *
 */
public class FixedAssetsServiceException extends Exception {

	private static final long serialVersionUID = -1722016220373726063L;

	private final FixedAssetsServiceErrorCodes code;

	public FixedAssetsServiceException(FixedAssetsServiceErrorCodes code) {
		super(code.getErrorDetail());
		this.code = code;
	}

	public FixedAssetsServiceException(String message, Throwable cause, FixedAssetsServiceErrorCodes code) {
		super(message, cause);
		this.code = code;
	}

	public FixedAssetsServiceException(String message, FixedAssetsServiceErrorCodes code) {
		super(message);
		this.code = code;
	}

	public FixedAssetsServiceException(Throwable cause, FixedAssetsServiceErrorCodes code) {
		super(code.getErrorDetail(), cause);
		this.code = code;
	}

	public FixedAssetsServiceErrorCodes getCode() {
		return code;
	}

}
