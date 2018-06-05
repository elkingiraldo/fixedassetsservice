package co.com.grupoasd.services.fixedassets.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dtos.FixedAssetDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.types.AssetType;

/**
 * Service for validating all request
 * 
 * @author egiraldo
 *
 */
@Service
public class FixedAssetsValidationService {

	/**
	 * It will validate step by step creation request
	 * 
	 * @param fixedAsset
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public FixedAssetDTO validateCreation(FixedAssetDTO dto) throws FixedAssetsServiceException {

		validateDto(dto);
		validateName(dto.getName());
		validateSerial(dto.getSerial());

		validateWeight(dto.getWeight());
		validateHigh(dto.getHigh());
		validateWidth(dto.getWidth());
		validateLength(dto.getLength());
		validatePurchaseValue(dto.getPurchaseValue());
		validatePurchaseDate(dto.getPurchaseDate());

		dto = validateAndAutocompleteAssetType(dto);

		// TODO
		// dto = validateAndAutocompleteStockNumber(dto);
		// dto = autocompleteStatus(dto);
		// persona o area y ciudad

		return dto;
	}

	/**
	 * Validate if the type is null
	 * 
	 * @param dto
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	private FixedAssetDTO validateAndAutocompleteAssetType(FixedAssetDTO dto) throws FixedAssetsServiceException {

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

		return dto;
	}

	/**
	 * Validate if the purchase date is null or it is an invalid date
	 * 
	 * @param purchaseDate
	 * @throws FixedAssetsServiceException
	 */
	private void validatePurchaseDate(Date purchaseDate) throws FixedAssetsServiceException {
		if (purchaseDate == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_PURCHASE_DATE_REQUIRED);
		}
		if (purchaseDate.after(new Date())) {
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
		if (serial == null || serial.isEmpty() || serial.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.FIXED_ASSET_SERIAL_REQUIRED);
		}
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

}
