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
	 * @param city
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public CityDTO create(CityDTO city) throws FixedAssetsServiceException {

		validationService.validateCreation(city);

		City savedCity = repository.save(converterService.toEntity(city));

		return converterService.toDTO(savedCity);
	}

	/**
	 * Retrieve all cities
	 * 
	 * @return
	 */
	public List<CityDTO> retrieveAllCities() {

		List<City> findAll = repository.findAll();

		return converterService.toDtos(findAll);
	}

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
	 * @param name
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public CityDTO retrieveByName(String name) throws FixedAssetsServiceException {

		City entity = repository.findByName(name);

		if (entity == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NOT_FOUND);
		}

		return converterService.toDTO(entity);
	}

	/**
	 * This method will retrieve city by code
	 * 
	 * @param code
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public CityDTO retrieveByCode(String code) throws FixedAssetsServiceException {

		City entity = repository.findByCityCode(code);

		if (entity == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CITY_NOT_FOUND);
		}

		return converterService.toDTO(entity);
	}

	/**
	 * This method will update a city
	 * 
	 * @param city
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public CityDTO update(CityDTO city) throws FixedAssetsServiceException {

		validationService.validateUpdate(city);

		City saveCity = repository.save(converterService.toEntity(city));

		return converterService.toDTO(saveCity);
	}

}