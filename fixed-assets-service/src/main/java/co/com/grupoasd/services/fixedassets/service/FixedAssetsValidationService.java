package co.com.grupoasd.services.fixedassets.service;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FixedAssetsRepository fixedAssetsRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AreaService areaService;

	/**
	 * It will validate step by step creation request
	 * 
	 * @param dto,
	 *            fixed asset DTO to validate creation
	 * @throws FixedAssetsServiceException
	 *             if validate creation fails
	 */
	public void validateCreation(FixedAssetDTO dto) throws FixedAssetsServiceException {

		logger.info("[FixedAssetsValidationService][validateCreation]. Start validation");

		validateDto(dto);
		validateName(dto.getName());
		validateSerial(dto.getSerial());

		validateWeight(dto.getWeight());
		validateHigh(dto.getHigh());
		validateWidth(dto.getWidth());
		validateLength(dto.getLength());
		validatePurchaseValue(dto.getPurchaseValue());
		validateDates(dto);

		validateColor(dto);
		validateAssignment(dto);
		validateAssetType(dto);
		validateStockNumber(dto);

		logger.info("[FixedAssetsValidationService][validateCreation]. End validation. Everything is OK");

	}

	/**
	 * Validate color and save it with capital letters
	 * 
	 * @param dto
	 * @throws FixedAssetsServiceException
	 */
	private void validateColor(FixedAssetDTO dto) throws FixedAssetsServiceException {
		if (dto.getColor() == null || dto.getColor().isEmpty() || dto.getColor().trim().isEmpty()) {
			logger.info("[FixedAssetsValidationService][validateColor]. Fixed Asset Color Required. Current value: "
					+ dto.getColor());
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_COLOR_REQUIRED);
		}

		dto.setColor(dto.getColor().trim().toUpperCase());
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
			logger.info(
					"[FixedAssetsValidationService][validateAssignment]. Fixed Asset Assignment type Required. Current value: "
							+ dto.getAssignmentType());
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_TYPE_REQUIRED);
		}

		try {
			AssetAssignmentType assignmentType = AssetAssignmentType
					.valueOf(dto.getAssignmentType().trim().toUpperCase());

			if (assignmentType == null) {
				logger.info(
						"[FixedAssetsValidationService][validateAssignment]. Fixed Asset Assignment type invalid. Current value: "
								+ assignmentType);
				throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_TYPE_INVALID);
			}

			dto.setAssignmentType(dto.getAssignmentType().trim().toUpperCase());

			switch (assignmentType) {
			case USER:
				if (dto.getAssignmentId() == null || dto.getAssignmentId().isEmpty()
						|| dto.getAssignmentId().trim().isEmpty()) {
					logger.info(
							"[FixedAssetsValidationService][validateAssignment]. Fixed Asset Assignment ID required. Current value: "
									+ dto.getAssignmentId());
					throw new FixedAssetsServiceException(
							FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_ID_REQUIRED);
				}
				userService.retrieveByPersonalId(dto.getAssignmentId());
				dto.setStatus(AssetStatus.ASSIGNED.name());
				break;
			case AREA:
				if (dto.getAssignmentId() == null || dto.getAssignmentId().isEmpty()
						|| dto.getAssignmentId().trim().isEmpty()) {
					logger.info(
							"[FixedAssetsValidationService][validateAssignment]. Fixed Asset Assignment ID required. Current value: "
									+ dto.getAssignmentId());
					throw new FixedAssetsServiceException(
							FixedAssetsServiceErrorCodes.FIXED_ASSET_ASSIGNMENT_ID_REQUIRED);
				}
				areaService.retrieveByName(dto.getAssignmentId());
				dto.setStatus(AssetStatus.ASSIGNED.name());
				break;
			case STOCK:
				dto.setStatus(AssetStatus.ACTIVE.name());
			default:
				break;
			}

		} catch (IllegalArgumentException e) {
			logger.info("[FixedAssetsValidationService][validateAssignment]. Fixed Asset Assignment type invalid");
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
			logger.info(
					"[FixedAssetsValidationService][validateStockNumber]. Fixed Asset random stock number assigned. Current value: "
							+ dto.getStockNumber());
		}

		FixedAsset oldFixedAsset = retrieveByStockNumber(stockNumber);

		if (oldFixedAsset != null) {
			logger.info(
					"[FixedAssetsValidationService][validateStockNumber]. Fixed Asset Stock Number already exists. Old Fixed Asset: "
							+ oldFixedAsset.toString());
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
			logger.info(
					"[FixedAssetsValidationService][retrieveByStockNumber]. Fixed Asset Stock Number required. Current value: "
							+ stockNumber);
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
				logger.info(
						"[FixedAssetsValidationService][validateAssetType]. Fixed Asset type not recognized. Current value: "
								+ typeString.trim().toUpperCase());
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

		validatePurchaseDate(dto.getPurchaseDate());
		validateLeavingDate(dto.getLeavingDate());

		if (dto.getPurchaseDate().after(dto.getLeavingDate())) {
			logger.info("[FixedAssetsValidationService][validateDates]. Fixed Asset purchase date after leaving date");
			throw new FixedAssetsServiceException(
					FixedAssetsServiceErrorCodes.FIXED_ASSET_PURCHASE_DATE_NOT_LATER_LEAVING_DATE);
		}

	}

	/**
	 * Validate leaving date requirements
	 * 
	 * @param leavingDate
	 * @throws FixedAssetsServiceException
	 */
	private void validateLeavingDate(Date leavingDate) throws FixedAssetsServiceException {
		if (leavingDate == null) {
			logger.info("[FixedAssetsValidationService][validateLeavingDate]. Fixed Asset leaving date required");
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_LEAVING_DATE_REQUIRED);
		}

		if (leavingDate.before(new Date())) {
			logger.info(
					"[FixedAssetsValidationService][validateLeavingDate]. Fixed Asset leaving date can't be in the past");
			throw new FixedAssetsServiceException(
					FixedAssetsServiceErrorCodes.FIXED_ASSET_LEAVING_DATE_CANT_BE_IN_PAST_REQUIRED);
		}
	}

	/**
	 * Validate purchase date requirements
	 * 
	 * @param purchaseDate
	 * @throws FixedAssetsServiceException
	 */
	private void validatePurchaseDate(Date purchaseDate) throws FixedAssetsServiceException {
		if (purchaseDate == null) {
			logger.info("[FixedAssetsValidationService][validatePurchaseDate]. Fixed Asset purchase date required");
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_PURCHASE_DATE_REQUIRED);
		}

		if (purchaseDate.after(new Date())) {
			logger.info(
					"[FixedAssetsValidationService][validatePurchaseDate]. Fixed Asset purchase date can't be in the future");
			throw new FixedAssetsServiceException(
					FixedAssetsServiceErrorCodes.FIXED_ASSET_PURCHASE_DATE_CANT_BE_IN_FUTURE_REQUIRED);
		}
	}

	/**
	 * Validate if the purchase value is null
	 * 
	 * @param purchacheValue
	 * @throws FixedAssetsServiceException
	 */
	private void validatePurchaseValue(double purchacheValue) throws FixedAssetsServiceException {
		if (purchacheValue == 0.0) {
			logger.info("[FixedAssetsValidationService][validatePurchaseValue]. Fixed Asset purchase value required");
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
			logger.info("[FixedAssetsValidationService][validateLength]. Fixed Asset lenght required");
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
			logger.info("[FixedAssetsValidationService][validateWidth]. Fixed Asset width required");
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
			logger.info("[FixedAssetsValidationService][validateHigh]. Fixed Asset high required");
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
			logger.info("[FixedAssetsValidationService][validateWeight]. Fixed Asset weight required");
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
			logger.info(
					"[FixedAssetsValidationService][validateSerial]. Fixed Asset serial already exists. Old entity: "
							+ entity.toString());
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
			logger.info("[FixedAssetsValidationService][retrieveBySerial]. Fixed Asset serial required");
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
			logger.info("[FixedAssetsValidationService][validateName]. Fixed Asset name required");
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
			logger.info("[FixedAssetsValidationService][validateDto]. Fixed Asset dto required");
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_REQUIRED);
		}
	}

	/**
	 * This method will validate the request to update it
	 * 
	 * @param dto,
	 *            fixed asset DTO to validate update @return, {@link FixedAsset},
	 *            entity validated and found
	 * @return {@link FixedAsset}, fixed asset validated and found in DB
	 * @throws FixedAssetsServiceException
	 *             if fail validation update process
	 */
	public FixedAsset validateUpdate(FixedAssetDTO dto) throws FixedAssetsServiceException {

		logger.info("[FixedAssetsValidationService][validateUpdate]. Start validate update");

		validateDto(dto);
		FixedAsset oldFixedAsset = validateSerialForUpdating(dto.getSerial());

		validateLeavingDateForUpdating(oldFixedAsset, dto.getLeavingDate());
		validateStockNumberForUpdating(oldFixedAsset, dto.getStockNumber());

		if (oldFixedAsset.getLeavingDate().equals(dto.getLeavingDate())
				&& oldFixedAsset.getStockNumber().equals(dto.getStockNumber())) {
			logger.info("[FixedAssetsValidationService][validateUpdate]. No information for updating asset");
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.NOT_INFORMATION_FOR_UPDATING);
		}

		oldFixedAsset.setLeavingDate(dto.getLeavingDate());
		oldFixedAsset.setStockNumber(dto.getStockNumber());

		logger.info("[FixedAssetsValidationService][validateUpdate]. End validate update OK. new entity: "
				+ oldFixedAsset.toString());

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
			logger.info("[FixedAssetsValidationService][validateLeavingDateForUpdating]. Leaving date can't be null");
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_LEAVING_DATE_REQUIRED);
		}

		if (leavingDate.before(oldFixedAsset.getPurchaseDate())) {
			logger.info(
					"[FixedAssetsValidationService][validateLeavingDateForUpdating]. Leaving date can't be before purchase date");
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
			logger.info("[FixedAssetsValidationService][validateSerialForUpdating]. Fixed asset serial not exists");
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
			logger.info(
					"[FixedAssetsValidationService][validateStockNumberForUpdating]. Fixed asset tock number already exists");
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_STOCK_NUMBER_ALREADY_EXISTS);
		}

	}

}
