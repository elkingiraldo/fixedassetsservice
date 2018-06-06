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
	FIXED_ASSET_SERIAL_NOT_EXISTS("fixed.asset.serial.not.exists.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_SERIAL_ALREADY_EXISTS("fixed.asset.serial.already.exists.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_STOCK_NUMBER_REQUIRED("fixed.asset.stock.number.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_STOCK_NUMBER_ALREADY_EXISTS("fixed.asset.stock.number.already.exists.for.updating.exception", HttpStatus.BAD_REQUEST),
	
	FIXED_ASSET_WEIGHT_REQUIRED("fixed.asset.weight.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_HIGH_REQUIRED("fixed.asset.high.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_WIDTH_REQUIRED("fixed.asset.width.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LENGTH_REQUIRED("fixed.asset.length.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_PURCHASE_VALUE_REQUIRED("fixed.asset.purchase.value.required.exception", HttpStatus.BAD_REQUEST),
	
	FIXED_ASSET_PURCHASE_DATE_REQUIRED("fixed.asset.purchase.date.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_PURCHASE_DATE_CANT_BE_IN_FUTURE_REQUIRED("fixed.asset.purchase.date.cant.be.in.future.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LEAVING_DATE_REQUIRED("fixed.asset.leaving.date.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LEAVING_DATE_CANT_BE_IN_PAST_REQUIRED("fixed.asset.leaving.date.cant.be.in.past.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_PURCHASE_DATE_NOT_LATER_LEAVING_DATE("fixed.asset.purchase.date.not.later.leaving.date.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LEAVING_DATE_NOT_BEFORE_PURCHASE_DATE("fixed.asset.leaving.date.not.before.purchase.date.exception", HttpStatus.BAD_REQUEST),
	
	PARSE_EXCEPTION_PURCHASE_DATE_FROM("fixed.asset.purchase.date.from.parse.exception", HttpStatus.BAD_REQUEST),
	PARSE_EXCEPTION_PURCHASE_DATE_TO("fixed.asset.purchase.date.to.parse.exception", HttpStatus.BAD_REQUEST),
	
	NOT_INFORMATION_FOR_UPDATING("fixed.asset.not.information.for.updating.exception", HttpStatus.BAD_REQUEST);

	
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
