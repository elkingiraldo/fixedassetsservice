package co.com.grupoasd.services.fixedassets.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.City;
import co.com.grupoasd.services.fixedassets.dao.CityRepository;
import co.com.grupoasd.services.fixedassets.dtos.CityDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;

@Service
public class CityValidationService {

	@Autowired
	private CityRepository repository;

	/**
	 * This method will validate correct creation if cities
	 * 
	 * @param city
	 * @throws FixedAssetsServiceException
	 */
	public void validateCreation(CityDTO city) throws FixedAssetsServiceException {

		validateCity(city);
		validateName(city.getName());
		validateCityCode(city.getCityCode());

	}

	/**
	 * This Method will validate if city code is different of null and if the city
	 * code already exists in DB
	 * 
	 * @param cityCode
	 * @throws FixedAssetsServiceException
	 */
	private void validateCityCode(String cityCode) throws FixedAssetsServiceException {
		if (cityCode == null || cityCode.isEmpty() || cityCode.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_CODE_REQUIRED);
		}
	}

	/**
	 * This method will validate if name is different of null and if the name
	 * already exists in DB
	 * 
	 * @param name
	 * @throws FixedAssetsServiceException
	 */
	private void validateName(String name) throws FixedAssetsServiceException {
		if (name == null || name.isEmpty() || name.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NAME_REQUIRED);
		}

		City findByName = repository.findByName(name.trim().toUpperCase());

		if (findByName != null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NAME_ALREADY_EXISTS);
		}

	}

	/**
	 * This method will validate if city is different of null
	 * 
	 * @param city
	 * @throws FixedAssetsServiceException
	 */
	private void validateCity(CityDTO city) throws FixedAssetsServiceException {
		if (city == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_REQUIRED);
		}
	}

	/**
	 * This method will validate the update for a city
	 * 
	 * @param city
	 * @throws FixedAssetsServiceException
	 */
	public void validateUpdate(CityDTO city) throws FixedAssetsServiceException {

		validateCity(city);

		validateAtributes(city);

	}

	/**
	 * Validate if the city id exists
	 * 
	 * @param id
	 * @throws FixedAssetsServiceException
	 */
	private void validateAtributes(CityDTO dto) throws FixedAssetsServiceException {

		if (dto.getId() == null || dto.getId().isEmpty() || dto.getId().trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_ID_REQUIRED);
		}

		Optional<City> oldCityOptional = repository.findById(dto.getId());

		if (!oldCityOptional.isPresent()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NOT_FOUND_UPDATE);
		}

		if (dto.getName() == null || dto.getName().isEmpty() || dto.getName().trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NAME_REQUIRED);
		}

		if (dto.getCityCode() == null || dto.getCityCode().isEmpty() || dto.getCityCode().trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_CODE_REQUIRED);
		}

		City findByName = repository.findByName(dto.getName().trim().toUpperCase());
		if (findByName != null && !findByName.getId().equals(oldCityOptional.get().getId())) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NAME_ALREADY_EXISTS);
		}

		if ((oldCityOptional.get().isAvailableToAssignArea() && !dto.isAvailableToAssignArea())
				|| (!oldCityOptional.get().isAvailableToAssignArea() && dto.isAvailableToAssignArea())) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_AVAILABILITY_CANNOT_BE_UPDATED);
		}

	}

}
