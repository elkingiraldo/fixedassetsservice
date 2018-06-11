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
	FIXED_ASSET_STOCK_NUMBER_ALREADY_EXISTS("fixed.asset.stock.number.already.exists.exception", HttpStatus.BAD_REQUEST),
	
	FIXED_ASSET_COLOR_REQUIRED("fixed.asset.color.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_WEIGHT_REQUIRED("fixed.asset.weight.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_HIGH_REQUIRED("fixed.asset.high.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_WIDTH_REQUIRED("fixed.asset.width.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LENGTH_REQUIRED("fixed.asset.length.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_PURCHASE_VALUE_REQUIRED("fixed.asset.purchase.value.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_ASSIGNMENT_ID_REQUIRED("fixed.asset.assignment.id.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_ASSIGNMENT_TYPE_REQUIRED("fixed.asset.assignment.type.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_ASSIGNMENT_TYPE_INVALID("fixed.asset.assignment.type.invalid.exception", HttpStatus.BAD_REQUEST),
	
	FIXED_ASSET_PURCHASE_DATE_REQUIRED("fixed.asset.purchase.date.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_PURCHASE_DATE_CANT_BE_IN_FUTURE_REQUIRED("fixed.asset.purchase.date.cant.be.in.future.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LEAVING_DATE_REQUIRED("fixed.asset.leaving.date.required.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LEAVING_DATE_CANT_BE_IN_PAST_REQUIRED("fixed.asset.leaving.date.cant.be.in.past.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_PURCHASE_DATE_NOT_LATER_LEAVING_DATE("fixed.asset.purchase.date.not.later.leaving.date.exception", HttpStatus.BAD_REQUEST),
	FIXED_ASSET_LEAVING_DATE_NOT_BEFORE_PURCHASE_DATE("fixed.asset.leaving.date.not.before.purchase.date.exception", HttpStatus.BAD_REQUEST),
	
	PARSE_EXCEPTION_PURCHASE_DATE_FROM("fixed.asset.purchase.date.from.parse.exception", HttpStatus.INTERNAL_SERVER_ERROR),
	PARSE_EXCEPTION_PURCHASE_DATE_TO("fixed.asset.purchase.date.to.parse.exception", HttpStatus.INTERNAL_SERVER_ERROR),
	PARSE_EXCEPTION_LEAVING_DATE("fixed.asset.leaving.date.parse.exception", HttpStatus.INTERNAL_SERVER_ERROR),
	
	NOT_INFORMATION_FOR_UPDATING("fixed.asset.not.information.for.updating.exception", HttpStatus.BAD_REQUEST),
	
	CITY_NOT_FOUND("city.not.found.exception", HttpStatus.NOT_FOUND),
	CITY_NOT_FOUND_UPDATE("city.not.found.update.exception", HttpStatus.NOT_FOUND),
	CITY_REQUIRED("city.required.exception", HttpStatus.BAD_REQUEST),
	CITY_ID_REQUIRED("city.id.required.exception", HttpStatus.BAD_REQUEST),
	CITY_NAME_REQUIRED("city.name.required.exception", HttpStatus.BAD_REQUEST),
	CITY_CODE_REQUIRED("city.code.required.exception", HttpStatus.BAD_REQUEST),
	CITY_NAME_ALREADY_EXISTS("city.name.already.exists.exception", HttpStatus.BAD_REQUEST),
	CITY_AVAILABILITY_CANNOT_BE_UPDATED("city.availability.cannot.be.updated.exception", HttpStatus.BAD_REQUEST),
	CITY_ALREADY_HAS_ASSIGNMENT_AREA("city.already.has.assigned.area.exception", HttpStatus.BAD_REQUEST),

	USER_NOT_FOUND("user.not.found.exception", HttpStatus.NOT_FOUND),
	USER_UPDATE_NOT_FOUND("user.update.not.found.exception", HttpStatus.NOT_FOUND),
	USER_REQUIRED("user.required.exception", HttpStatus.BAD_REQUEST),
	USER_ID_REQUIRED("user.id.required.exception", HttpStatus.BAD_REQUEST),
	USER_FIRST_NAME_REQUIRED("user.first.name.required.exception", HttpStatus.BAD_REQUEST),
	USER_LAST_NAME_REQUIRED("user.last.name.required.exception", HttpStatus.BAD_REQUEST),
	USER_PERSONAL_ID_REQUIRED("user.personal.id.required.exception", HttpStatus.BAD_REQUEST),
	USER_PERSONAL_ID_ALREADY_EXISTS("user.personal.id.already.exists.exception", HttpStatus.BAD_REQUEST),
	
	AREA_NOT_FOUND("area.not.found.exception", HttpStatus.NOT_FOUND),
	AREA_DELETE_NOT_FOUND("area.delete.not.found.exception", HttpStatus.NOT_FOUND),
	AREA_UPDATE_NOT_FOUND("area.update.not.found.exception", HttpStatus.NOT_FOUND),
	AREA_REQUIRED("area.required.exception", HttpStatus.BAD_REQUEST),
	AREA_ALREADY_EXISTS("area.already.exists.exception", HttpStatus.BAD_REQUEST),
	AREA_NAME_REQUIRED("area.name.required.exception", HttpStatus.BAD_REQUEST),
	AREA_ASSIGNED_CITY_ID_REQUIRED("area.assigned.city.id.required.exception", HttpStatus.BAD_REQUEST),
	;

	
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
