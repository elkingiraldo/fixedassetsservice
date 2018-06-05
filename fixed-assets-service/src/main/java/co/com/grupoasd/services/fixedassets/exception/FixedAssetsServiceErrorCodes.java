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
	CRITERIA_NOT_FOUND("criteria.not.found.exception", HttpStatus.BAD_REQUEST),

	FIXED_ASSET_REQUIRED("fixed.asset.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_NAME_REQUIRED("fixed.asset.name.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_SERIAL_REQUIRED("fixed.asset.serial.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_SERIAL_ALREADY_EXISTS("fixed.asset.serial.already.exists.exception", HttpStatus.BAD_REQUEST),
	
	FIXED_ASSET_WEIGHT_REQUIRED("fixed.asset.weight.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_HIGH_REQUIRED("fixed.asset.high.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_WIDTH_REQUIRED("fixed.asset.width.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LENGTH_REQUIRED("fixed.asset.length.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_PURCHASE_VALUE_REQUIRED("fixed.asset.purchase.value.required.exception", HttpStatus.BAD_REQUEST),
	
	FIXED_ASSET_PURCHASE_DATE_REQUIRED("fixed.asset.purchase.date.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_PURCHASE_DATE_CANT_BE_IN_FUTURE_REQUIRED("fixed.asset.purchase.date.cant.be.in.future.exception", HttpStatus.BAD_REQUEST),
	
	PARSE_EXCEPTION_PURCHASE_DATE_FROM("fixed.asset.purchase.date.from.parse.exception", HttpStatus.BAD_REQUEST),
	PARSE_EXCEPTION_PURCHASE_DATE_TO("fixed.asset.purchase.date.to.parse.exception", HttpStatus.BAD_REQUEST);

	
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
