package co.com.grupoasd.services.fixedassets.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.grupoasd.services.fixedassets.dao.Area;
import co.com.grupoasd.services.fixedassets.dtos.AreaDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.service.AreaService;

/**
 * Rest controller for areas management
 * 
 * @author egiraldo
 *
 */
@RestController
@RequestMapping("/fixedassets/areas/v1.0")
public class AreaController {

	@Autowired
	private AreaService areaService;

	/**
	 * This method is in change of post a new area
	 * 
	 * @param area,
	 *            area DTO to create
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link Area DTO}, area created
	 * @throws FixedAssetsServiceException
	 *             if fails creation of new area
	 */
	@PostMapping
	public ResponseEntity<AreaDTO> post(@RequestBody AreaDTO area,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO newArea = areaService.create(area);

		return new ResponseEntity<AreaDTO>(newArea, HttpStatus.CREATED);
	}

	/**
	 * This method will retrieve all areas
	 * 
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link Area DTO}, area found
	 * @throws FixedAssetsServiceException
	 *             if fails searching request
	 */
	@GetMapping
	public ResponseEntity<List<AreaDTO>> get(@RequestHeader(value = "locale", required = false) String locale)
			throws FixedAssetsServiceException {

		List<AreaDTO> areaFound = areaService.retrieveAllAreas();

		return new ResponseEntity<List<AreaDTO>>(areaFound, HttpStatus.OK);

	}

	/**
	 * This method will retrieve area by entered ID
	 * 
	 * @param id,
	 *            area ID to find area
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link Area DTO}, area found
	 * @throws FixedAssetsServiceException
	 *             if fails searching request
	 */
	@GetMapping("/byId/{id}")
	public ResponseEntity<AreaDTO> getById(@PathVariable(value = "id") String id,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO areaFound = areaService.retrieveById(id);

		return new ResponseEntity<AreaDTO>(areaFound, HttpStatus.OK);

	}

	/**
	 * This method will retrieve area by entered name
	 * 
	 * @param name,
	 *            area name to find area
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link Area DTO}, area created
	 * @throws FixedAssetsServiceException
	 *             if fails searching request
	 */
	@GetMapping("/byName/{name}")
	public ResponseEntity<AreaDTO> getByName(@PathVariable(value = "name") String name,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO areaFound = areaService.retrieveByName(name);

		return new ResponseEntity<AreaDTO>(areaFound, HttpStatus.OK);

	}

	/**
	 * This method will retrieve area by entered city ID assigned
	 * 
	 * @param cityOfAssignmentId,
	 *            city ID of assigned city
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link Area DTO}, area found
	 * @throws FixedAssetsServiceException
	 *             if fails searching request
	 */
	@GetMapping("/byCityOfAssignmentId/{cityOfAssignmentId}")
	public ResponseEntity<AreaDTO> getByCode(@PathVariable(value = "cityOfAssignmentId") String cityOfAssignmentId,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO areaFound = areaService.retrieveByCityOfAssignmentId(cityOfAssignmentId);

		return new ResponseEntity<AreaDTO>(areaFound, HttpStatus.OK);

	}

	/**
	 * This method will update an area
	 * 
	 * @param area,
	 *            area with ID to update area
	 * @param locale,
	 *            language the user wants to use
	 * @return {@link Area DTO}, area updated
	 * @throws FixedAssetsServiceException
	 *             if fails updating old area
	 */
	@PutMapping
	public ResponseEntity<AreaDTO> put(@RequestBody AreaDTO area,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO newArea = areaService.update(area);
		return new ResponseEntity<AreaDTO>(newArea, HttpStatus.OK);
	}

	/**
	 * This method will delete an area
	 * 
	 * @param id,
	 *            area ID to delete
	 * @param locale,
	 *            language the user wants to use
	 * @return Void, returns only the status OK
	 * @throws FixedAssetsServiceException
	 *             if fails delete area
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") String id,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {
		areaService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
