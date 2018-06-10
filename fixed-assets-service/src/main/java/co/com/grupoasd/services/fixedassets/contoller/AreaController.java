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

import co.com.grupoasd.services.fixedassets.dtos.AreaDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.service.AreaService;

@RestController
@RequestMapping("/fixedassets/areas/v1.0")
public class AreaController {

	@Autowired
	private AreaService areaService;

	@PostMapping
	public ResponseEntity<AreaDTO> post(@RequestBody AreaDTO area,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO newArea = areaService.create(area);

		return new ResponseEntity<AreaDTO>(newArea, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<AreaDTO>> get(@RequestHeader(value = "locale", required = false) String locale)
			throws FixedAssetsServiceException {

		List<AreaDTO> areaFound = areaService.retrieveAllAreas();

		return new ResponseEntity<List<AreaDTO>>(areaFound, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<AreaDTO> getById(@PathVariable(value = "id") String id,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO areaFound = areaService.retrieveById(id);

		return new ResponseEntity<AreaDTO>(areaFound, HttpStatus.OK);

	}

	@GetMapping("/{name}")
	public ResponseEntity<AreaDTO> getByName(@PathVariable(value = "name") String name,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO areaFound = areaService.retrieveByName(name);

		return new ResponseEntity<AreaDTO>(areaFound, HttpStatus.OK);

	}

	@GetMapping("/{cityOfAssignmentId}")
	public ResponseEntity<AreaDTO> getByCode(@PathVariable(value = "cityOfAssignmentId") String cityOfAssignmentId,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO areaFound = areaService.retrieveByCityOfAssignmentId(cityOfAssignmentId);

		return new ResponseEntity<AreaDTO>(areaFound, HttpStatus.OK);

	}

	@PutMapping
	public ResponseEntity<AreaDTO> put(@RequestBody AreaDTO area,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		AreaDTO newArea = areaService.update(area);
		return new ResponseEntity<AreaDTO>(newArea, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") String id,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {
		areaService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
