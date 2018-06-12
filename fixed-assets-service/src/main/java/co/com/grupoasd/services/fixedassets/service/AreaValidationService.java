package co.com.grupoasd.services.fixedassets.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.Area;
import co.com.grupoasd.services.fixedassets.dao.AreaRepository;
import co.com.grupoasd.services.fixedassets.dtos.AreaDTO;
import co.com.grupoasd.services.fixedassets.dtos.CityDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;

/**
 * This service will validate all request for areas
 * 
 * @author egiraldo
 *
 */
@Service
public class AreaValidationService {

	@Autowired
	private CityService cityService;

	@Autowired
	private AreaRepository repository;

	/**
	 * Validate creation requests
	 * 
	 * @param area,
	 *            area DTO to create
	 * @throws FixedAssetsServiceException
	 *             if fail validation for DTO in creation
	 */
	public void validateCreation(AreaDTO area) throws FixedAssetsServiceException {

		validateArea(area);
		validateName(area.getName());
		validateCityOfAssignmentId(area.getCityOfAssignmentId());

	}

	/**
	 * Validate if the city ID is different of null and if it exists in DB
	 * 
	 * @param cityOfAssignmentId
	 * @throws FixedAssetsServiceException
	 */
	private void validateCityOfAssignmentId(String cityOfAssignmentId) throws FixedAssetsServiceException {

		if (cityOfAssignmentId == null || cityOfAssignmentId.isEmpty() || cityOfAssignmentId.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_ASSIGNED_CITY_ID_REQUIRED);
		}

		CityDTO city = cityService.retrieveById(cityOfAssignmentId);

		if (!city.isAvailableToAssignArea()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_ALREADY_HAS_ASSIGNMENT_AREA);
		}

	}

	/**
	 * Validate if name exist and it is valid
	 * 
	 * @param name
	 * @throws FixedAssetsServiceException
	 */
	private void validateName(String name) throws FixedAssetsServiceException {

		if (name == null || name.isEmpty() || name.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_NAME_REQUIRED);
		}

		Area area = repository.findByName(name.trim().toUpperCase());

		if (area != null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_ALREADY_EXISTS);
		}

	}

	/**
	 * Validate if area is different of null
	 * 
	 * @param area
	 * @throws FixedAssetsServiceException
	 */
	private void validateArea(AreaDTO area) throws FixedAssetsServiceException {
		if (area == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_REQUIRED);
		}
	}

	/**
	 * This method will validate update of an area
	 * 
	 * @param area,
	 *            area DTO to update
	 * @throws FixedAssetsServiceException
	 *             if fail validation of DTO in the update
	 */
	public void validateUpdate(AreaDTO area) throws FixedAssetsServiceException {

		validateArea(area);
		validateId(area.getId());
		validateNameUpdate(area);
		validateCityOfAssignmentIdUpdate(area);

	}

	/**
	 * Validate assigned city ID when update
	 * 
	 * @param area
	 * @throws FixedAssetsServiceException
	 */
	private void validateCityOfAssignmentIdUpdate(AreaDTO dto) throws FixedAssetsServiceException {

		if (dto.getCityOfAssignmentId() == null || dto.getCityOfAssignmentId().isEmpty()
				|| dto.getCityOfAssignmentId().trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_ASSIGNED_CITY_ID_REQUIRED);
		}

		Area oldArea = repository.findById(dto.getId()).get();

		if (!oldArea.getCityOfAssignmentId().equals(dto.getCityOfAssignmentId())) {
			CityDTO newCity = cityService.retrieveById(dto.getCityOfAssignmentId());
			if (!newCity.isAvailableToAssignArea()) {
				throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_ALREADY_HAS_ASSIGNMENT_AREA);
			}
		}

	}

	/**
	 * Validate name when update area
	 * 
	 * @param area
	 * @throws FixedAssetsServiceException
	 */
	private void validateNameUpdate(AreaDTO dto) throws FixedAssetsServiceException {

		if (dto.getName() == null || dto.getName().isEmpty() || dto.getName().trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_NAME_REQUIRED);
		}

		Area oldArea = repository.findByName(dto.getName().trim().toUpperCase());

		if (oldArea != null && !oldArea.getId().equals(dto.getId())) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_ALREADY_EXISTS);
		}

	}

	/**
	 * Validate if the ID already exists
	 * 
	 * @param id
	 * @throws FixedAssetsServiceException
	 */
	private void validateId(String id) throws FixedAssetsServiceException {

		Optional<Area> optionalArea = repository.findById(id);

		if (!optionalArea.isPresent()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_UPDATE_NOT_FOUND);
		}

	}

}
