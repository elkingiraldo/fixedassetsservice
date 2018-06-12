package co.com.grupoasd.services.fixedassets.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.grupoasd.services.fixedassets.dtos.CityDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.service.CityService;

/**
 * Rest controller for cities management
 * 
 * @author egiraldo
 *
 */
@RestController
@RequestMapping("/fixedassets/cities/v1.0")
public class CityController {

	@Autowired
	private CityService cityService;

	/**
	 * This method will manage creation of new cities into the system
	 * 
	 * @param city,
	 *            city DTO to create a new city
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link CityDTO}, city created
	 * @throws FixedAssetsServiceException
	 *             if fails creation of new city
	 */
	@PostMapping
	public ResponseEntity<CityDTO> post(@RequestBody CityDTO city,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO newCity = cityService.create(city);

		return new ResponseEntity<CityDTO>(newCity, HttpStatus.CREATED);
	}

	/**
	 * This method will retrieve all cities
	 * 
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link CityDTO}, all cities
	 * @throws FixedAssetsServiceException
	 *             if fails searching cities
	 */
	@GetMapping
	public ResponseEntity<List<CityDTO>> get(@RequestHeader(value = "locale", required = false) String locale)
			throws FixedAssetsServiceException {

		List<CityDTO> cityFound = cityService.retrieveAllCities();

		return new ResponseEntity<List<CityDTO>>(cityFound, HttpStatus.OK);

	}

	/**
	 * This method will retrieve city by entered ID
	 * 
	 * @param id,
	 *            city ID to find city
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link CityDTO}, city found
	 * @throws FixedAssetsServiceException
	 *             if fails city search
	 */
	@GetMapping("/byId/{id}")
	public ResponseEntity<CityDTO> getById(@PathVariable(value = "id") String id,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO cityFound = cityService.retrieveById(id);

		return new ResponseEntity<CityDTO>(cityFound, HttpStatus.OK);

	}

	/**
	 * This method will retrieve city by entered name
	 * 
	 * @param name,
	 *            city name to find city
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link CityDTO}, city found
	 * @throws FixedAssetsServiceException
	 *             if fails city search
	 */
	@GetMapping("/byName/{name}")
	public ResponseEntity<CityDTO> getByName(@PathVariable(value = "name") String name,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO cityFound = cityService.retrieveByName(name);

		return new ResponseEntity<CityDTO>(cityFound, HttpStatus.OK);

	}

	/**
	 * This method will find city by entered code
	 * 
	 * @param code
	 *            city code to find city
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link CityDTO}, cities found
	 * @throws FixedAssetsServiceException
	 *             if fails city search
	 */
	@GetMapping("/byCode/{code}")
	public ResponseEntity<List<CityDTO>> getByCode(@PathVariable(value = "code") String code,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		List<CityDTO> cityFound = cityService.retrieveByCode(code);

		return new ResponseEntity<List<CityDTO>>(cityFound, HttpStatus.OK);

	}

	/**
	 * This method will update an city already created
	 * 
	 * @param city,
	 *            city to update
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link CityDTO}, city updated
	 * @throws FixedAssetsServiceException
	 *             if fails city update
	 */
	@PutMapping
	public ResponseEntity<CityDTO> put(@RequestBody CityDTO city,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO newCity = cityService.update(city);
		return new ResponseEntity<CityDTO>(newCity, HttpStatus.OK);
	}

}
