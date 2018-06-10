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

@RestController
@RequestMapping("/fixedassets/cities/v1.0")
public class CityController {

	@Autowired
	private CityService cityService;

	@PostMapping
	public ResponseEntity<CityDTO> post(@RequestBody CityDTO city,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO newCity = cityService.create(city);

		return new ResponseEntity<CityDTO>(newCity, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<CityDTO>> get(@RequestHeader(value = "locale", required = false) String locale)
			throws FixedAssetsServiceException {

		List<CityDTO> cityFound = cityService.retrieveAllCities();

		return new ResponseEntity<List<CityDTO>>(cityFound, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<CityDTO> getById(@PathVariable(value = "id") String id,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO cityFound = cityService.retrieveById(id);

		return new ResponseEntity<CityDTO>(cityFound, HttpStatus.OK);

	}

	@GetMapping("/{name}")
	public ResponseEntity<CityDTO> getByName(@PathVariable(value = "name") String name,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO cityFound = cityService.retrieveByName(name);

		return new ResponseEntity<CityDTO>(cityFound, HttpStatus.OK);

	}

	@GetMapping("/{code}")
	public ResponseEntity<CityDTO> getByCode(@PathVariable(value = "code") String code,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO cityFound = cityService.retrieveByCode(code);

		return new ResponseEntity<CityDTO>(cityFound, HttpStatus.OK);

	}

	@PutMapping
	public ResponseEntity<CityDTO> put(@RequestBody CityDTO city,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		CityDTO newCity = cityService.update(city);
		return new ResponseEntity<CityDTO>(newCity, HttpStatus.OK);
	}

}
