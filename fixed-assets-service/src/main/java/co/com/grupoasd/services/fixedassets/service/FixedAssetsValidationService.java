package co.com.grupoasd.services.fixedassets.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.FixedAsset;
import co.com.grupoasd.services.fixedassets.dao.FixedAssetsRepository;
import co.com.grupoasd.services.fixedassets.dtos.FixedAssetDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.types.AssetAssignmentType;
import co.com.grupoasd.services.fixedassets.types.AssetStatus;
import co.com.grupoasd.services.fixedassets.types.AssetType;

/**
 * Service for validating all request
 * 
 * @author egiraldo
 *
 */
@Service
public class FixedAssetsValidationService {

	@Autowired
	private FixedAssetsRepository fixedAssetsRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AreaService areaService;

	/**
	 * It will validate step by step creation request
	 * 
	 * @param fixedAsset
	 * @throws FixedAssetsServiceException
	 */
	public void validateCreation(FixedAssetDTO dto) throws FixedAssetsServiceException {

		validateDto(dto);
		validateName(dto.getName());
		validateSerial(dto.getSerial());

		validateWeight(dto.getWeight());
		validateHigh(dto.getHigh());
		validateWidth(dto.getWidth());
		validateLength(dto.getLength());
		validatePurchaseValue(dto.getPurchaseValue());
		validateDates(dto);

		validateAssignment(dto);
		validateAssetType(dto);
		validateStockNumber(dto);

	}

	/**
	 * This method will validate the assignment of fixed asset
	 * 
	 * @param dto
	 * @throws FixedAssetsServiceException
	 */
	private void validateAssignment(FixedAssetDTO dto) throws FixedAssetsServiceException {

		if (dto.getAssignmentType() == null || dto.getAssignmentType().isEmpty()
				|| dto.getAssignmentType().trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_TYPE_REQUIRED);
		}

		try {
			AssetAssignmentType assignmentType = AssetAssignmentType
					.valueOf(dto.getAssignmentType().trim().toUpperCase());

			if (assignmentType == null) {
				throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_TYPE_INVALID);
			}

			dto.setAssignmentType(dto.getAssignmentType().trim().toUpperCase());

			switch (assignmentType) {
			case USER:
				if (dto.getAssignmentId() == null || dto.getAssignmentId().isEmpty()
						|| dto.getAssignmentId().trim().isEmpty()) {
					throw new FixedAssetsServiceException(
							FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_ID_REQUIRED);
				}
				userService.retrieveByPersonalId(dto.getAssignmentId());
				dto.setStatus(AssetStatus.ASSINGNED.name());
				break;
			case AREA:
				if (dto.getAssignmentId() == null || dto.getAssignmentId().isEmpty()
						|| dto.getAssignmentId().trim().isEmpty()) {
					throw new FixedAssetsServiceException(
							FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_ID_REQUIRED);
				}
				areaService.retrieveByName(dto.getAssignmentId());
				dto.setStatus(AssetStatus.ASSINGNED.name());
				break;
			case STOCK:
				dto.setStatus(AssetStatus.ACTIVE.name());
			default:
				break;
			}

		} catch (IllegalArgumentException e) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_TYPE_INVALID);
		}

	}

	/**
	 * Validate and autocomplete stock number
	 * 
	 * @param dto
	 * @throws FixedAssetsServiceException
	 */
	private void validateStockNumber(FixedAssetDTO dto) throws FixedAssetsServiceException {

		String stockNumber = dto.getStockNumber();

		if (stockNumber == null || stockNumber.isEmpty() || stockNumber.trim().isEmpty()) {
			dto.setStockNumber(UUID.randomUUID().toString());
		}

		FixedAsset oldFixedAsset = retrieveByStockNumber(stockNumber);

		if (oldFixedAsset != null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_STOCK_NUMBER_ALREADY_EXISTS);
		}

	}

	/**
	 * This method will retrieve fixed asset by stockNumber
	 * 
	 * @param stockNumber
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	private FixedAsset retrieveByStockNumber(String stockNumber) throws FixedAssetsServiceException {

		if (stockNumber == null || stockNumber.isEmpty() || stockNumber.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_STOCK_NUMBER_REQUIRED);
		}

		return fixedAssetsRepository.findByStockNumber(stockNumber);
	}

	/**
	 * Validate if the type is null
	 * 
	 * @param dto
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	private void validateAssetType(FixedAssetDTO dto) throws FixedAssetsServiceException {

		String typeString = dto.getType();

		if (typeString == null || typeString.isEmpty() || typeString.trim().isEmpty()) {
			dto.setType(AssetType.OTHERS.name());
		} else {

			switch (typeString.trim().toUpperCase()) {
			case "PROPERTIES":
				dto.setType(AssetType.PROPERTIES.name());
				break;
			case "MACHINERY_AND_EQUIPMENTS":
				dto.setType(AssetType.MACHINERY_AND_EQUIPMENTS.name());
				break;
			case "OFFICE_SUPPLIES":
				dto.setType(AssetType.OFFICE_SUPPLIES.name());
				break;
			case "VEHICLES":
				dto.setType(AssetType.VEHICLES.name());
				break;

			default:
				dto.setType(AssetType.OTHERS.name());
				break;
			}
		}
	}

	/**
	 * Validate requirements for dates
	 * 
	 * @param dto
	 * @throws FixedAssetsServiceException
	 */
	private void validateDates(FixedAssetDTO dto) throws FixedAssetsServiceException {

		Date purchaseDate = validatePurchaseDate(dto.getPurchaseDate());
		Date leavingDate = validateLeavingDate(dto.getLeavingDate());

		if (purchaseDate.after(leavingDate)) {
			throw new FixedAssetsServiceException(
					FixedAssetsServiceErrorCodes.FIXED_ASSET_PURCHASE_DATE_NOT_LATER_LEAVING_DATE);
		}

	}

	/**
	 * Validate leaving date requirements and return it
	 * 
	 * @param leavingDate
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	private Date validateLeavingDate(Date leavingDate) throws FixedAssetsServiceException {
		if (leavingDate == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_LEAVING_DATE_REQUIRED);
		}

		if (leavingDate.before(new Date())) {
			throw new FixedAssetsServiceException(
					FixedAssetsServiceErrorCodes.FIXED_ASSET_LEAVING_DATE_CANT_BE_IN_PAST_REQUIRED);
		}

		return leavingDate;
	}

	/**
	 * Validate purchase date requirements and return it
	 * 
	 * @param purchaseDate
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	private Date validatePurchaseDate(Date purchaseDate) throws FixedAssetsServiceException {
		if (purchaseDate == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_PURCHASE_DATE_REQUIRED);
		}

		if (purchaseDate.after(new Date())) {
			throw new FixedAssetsServiceException(
					FixedAssetsServiceErrorCodes.FIXED_ASSET_PURCHASE_DATE_CANT_BE_IN_FUTURE_REQUIRED);
		}

		return purchaseDate;
	}

	/**
	 * Validate if the purchase value is null
	 * 
	 * @param purchacheValue
	 * @throws FixedAssetsServiceException
	 */
	private void validatePurchaseValue(double purchacheValue) throws FixedAssetsServiceException {
		if (purchacheValue == 0.0) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_PURCHASE_VALUE_REQUIRED);
		}
	}

	/**
	 * Validate if the length is null
	 * 
	 * @param length
	 */
	private void validateLength(double length) throws FixedAssetsServiceException {
		if (length == 0.0) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_LENGTH_REQUIRED);
		}
	}

	/**
	 * Validate if the width is null
	 * 
	 * @param width
	 */
	private void validateWidth(double width) throws FixedAssetsServiceException {
		if (width == 0.0) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_WIDTH_REQUIRED);
		}
	}

	/**
	 * Validate if the high is null
	 * 
	 * @param high
	 */
	private void validateHigh(double high) throws FixedAssetsServiceException {
		if (high == 0.0) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_HIGH_REQUIRED);
		}
	}

	/**
	 * Validate if the weight is null
	 * 
	 * @param weight
	 * @throws FixedAssetsServiceException
	 */
	private void validateWeight(double weight) throws FixedAssetsServiceException {
		if (weight == 0.0) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_WEIGHT_REQUIRED);
		}
	}

	/**
	 * Validate if the serial is null
	 * 
	 * @param serial
	 * @throws FixedAssetsServiceException
	 */
	private void validateSerial(String serial) throws FixedAssetsServiceException {

		FixedAsset entity = retrieveBySerial(serial);

		if (entity != null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_SERIAL_ALREADY_EXISTS);
		}

	}

	/**
	 * This method will retrieve fixed asset by serial
	 * 
	 * @param serial
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	private FixedAsset retrieveBySerial(String serial) throws FixedAssetsServiceException {

		if (serial == null || serial.isEmpty() || serial.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_SERIAL_REQUIRED);
		}

		return fixedAssetsRepository.findBySerial(serial);
	}

	/**
	 * Validate if the name is null
	 * 
	 * @param name
	 * @throws FixedAssetsServiceException
	 */
	private void validateName(String name) throws FixedAssetsServiceException {
		if (name == null || name.isEmpty() || name.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_NAME_REQUIRED);
		}
	}

	/**
	 * Validate if the dto is null
	 * 
	 * @param fixedAsset
	 * @throws FixedAssetsServiceException
	 */
	private void validateDto(FixedAssetDTO dto) throws FixedAssetsServiceException {
		if (dto == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_REQUIRED);
		}
	}

	/**
	 * This method will validate the request to update it
	 * 
	 * @param fixedAsset
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public FixedAsset validateUpdate(FixedAssetDTO dto) throws FixedAssetsServiceException {

		validateDto(dto);
		FixedAsset oldFixedAsset = validateSerialForUpdating(dto.getSerial());

		validateLeavingDateForUpdating(oldFixedAsset, dto.getLeavingDate());
		validateStockNumberForUpdating(oldFixedAsset, dto.getStockNumber());

		if (oldFixedAsset.getLeavingDate().equals(dto.getLeavingDate())
				&& oldFixedAsset.getStockNumber().equals(dto.getStockNumber())) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.NOT_INFORMATION_FOR_UPDATING);
		}

		oldFixedAsset.setLeavingDate(dto.getLeavingDate());
		oldFixedAsset.setStockNumber(dto.getStockNumber());

		return oldFixedAsset;
	}

	/**
	 * 
	 * @param oldFixedAsset
	 * @param leavingDate
	 * @throws FixedAssetsServiceException
	 */
	private void validateLeavingDateForUpdating(FixedAsset oldFixedAsset, Date leavingDate)
			throws FixedAssetsServiceException {

		if (leavingDate == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_LEAVING_DATE_REQUIRED);
		}

		if (leavingDate.before(oldFixedAsset.getPurchaseDate())) {
			throw new FixedAssetsServiceException(
					FixedAssetsServiceErrorCodes.FIXED_ASSET_LEAVING_DATE_NOT_BEFORE_PURCHASE_DATE);
		}

	}

	/**
	 * This method will validate that the fixed asset the user wants to update
	 * exists in DB
	 * 
	 * @param serial
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	private FixedAsset validateSerialForUpdating(String serial) throws FixedAssetsServiceException {

		FixedAsset entity = retrieveBySerial(serial);

		if (entity == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_SERIAL_NOT_EXISTS);
		} else {
			return entity;
		}
	}

	/**
	 * Validate the stock number request and the stock number in DB, and depending
	 * on values it will return a value, or null, or an exception
	 * 
	 * @param oldFixedAsset
	 * 
	 * @param stockNumber
	 * @throws FixedAssetsServiceException
	 */
	private void validateStockNumberForUpdating(FixedAsset oldFixedAsset, String stockNumber)
			throws FixedAssetsServiceException {

		FixedAsset entity = retrieveByStockNumber(stockNumber);

		if (entity != null && !oldFixedAsset.getStockNumber().equals(stockNumber)) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_STOCK_NUMBER_ALREADY_EXISTS);
		}

	}

}
