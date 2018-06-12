package co.com.grupoasd.services.fixedassets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.City;
import co.com.grupoasd.services.fixedassets.dao.CityRepository;
import co.com.grupoasd.services.fixedassets.dtos.CityDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;

/**
 * Service for cities
 * 
 * @author egiraldo
 *
 */
@Service
public class CityService {

	@Autowired
	private CityValidationService validationService;

	@Autowired
	private CityRepository repository;

	@Autowired
	private CityConverterService converterService;

	/**
	 * This method will create a new city
	 * 
	 * @param city,
	 *            city DTO to create
	 * @return {@link CityDTO}, city DTO created
	 * @throws FixedAssetsServiceException
	 *             if fail creation
	 */
	public CityDTO create(CityDTO city) throws FixedAssetsServiceException {

		validationService.validateCreation(city);
		city.setName(city.getName().trim().toUpperCase());
		city.setAvailableToAssignArea(true);

		City savedCity = repository.save(converterService.toEntity(city));

		return converterService.toDTO(savedCity);
	}

	/**
	 * Retrieve all cities
	 * 
	 * @return {@link CityDTO}, retrieved cities
	 */
	public List<CityDTO> retrieveAllCities() {

		List<City> findAll = repository.findAll();

		return converterService.toDtos(findAll);
	}

	/**
	 * Retrieve a city by ID
	 * 
	 * @param id,
	 *            city ID to find
	 * @return {@link CityDTO}, city DTO found
	 * @throws FixedAssetsServiceException
	 *             if city not found
	 */
	public CityDTO retrieveById(String id) throws FixedAssetsServiceException {

		Optional<City> optionalCity = repository.findById(id);

		if (!optionalCity.isPresent()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NOT_FOUND);
		}

		return converterService.toDTO(optionalCity.get());
	}

	/**
	 * This method will retrieve city by name
	 * 
	 * @param name,
	 *            city name to find
	 * @return {@link CityDTO}, retrieved city
	 * @throws FixedAssetsServiceException
	 *             if city not found
	 */
	public CityDTO retrieveByName(String name) throws FixedAssetsServiceException {

		City entity = repository.findByName(name.trim().toUpperCase());

		if (entity == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NOT_FOUND);
		}

		return converterService.toDTO(entity);
	}

	/**
	 * This method will retrieve city by code
	 * 
	 * @param code,
	 *            city code to find
	 * @return {@link CityDTO}, retrieved city
	 * @throws FixedAssetsServiceException
	 *             if city not found
	 */
	public List<CityDTO> retrieveByCode(String code) throws FixedAssetsServiceException {

		List<City> entities = repository.findByCityCode(code);

		if (entities.size() == 0) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NOT_FOUND);
		}

		return converterService.toDtos(entities);
	}

	/**
	 * This method will update a city
	 * 
	 * @param city,
	 *            city DTO to update
	 * @return {@link CityDTO}, city updated
	 * @throws FixedAssetsServiceException
	 *             if fails update of the entered city
	 */
	public CityDTO update(CityDTO city) throws FixedAssetsServiceException {

		validationService.validateUpdate(city);
		city.setName(city.getName().trim().toUpperCase());

		City saveCity = repository.save(converterService.toEntity(city));

		return converterService.toDTO(saveCity);
	}

	/**
	 * This method can update city availability status too
	 * 
	 * @param id,
	 *            city ID to update as root
	 * @param isAvailableToAssignArea,
	 *            flag to know if city is available to assign
	 * @throws FixedAssetsServiceException
	 *             if can't update city
	 */
	public void updateAsRoot(String id, boolean isAvailableToAssignArea) throws FixedAssetsServiceException {

		City city = repository.findById(id).get();
		city.setAvailableToAssignArea(isAvailableToAssignArea);

		repository.save(city);

	}

}
