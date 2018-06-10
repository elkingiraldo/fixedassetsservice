package co.com.grupoasd.services.fixedassets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.Area;
import co.com.grupoasd.services.fixedassets.dao.AreaRepository;
import co.com.grupoasd.services.fixedassets.dtos.AreaDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.types.AreaName;

/**
 * This service will manage areas into the company
 * 
 * @author egiraldo
 *
 */
@Service
public class AreaService {

	@Autowired
	private AreaRepository repository;

	@Autowired
	private CityService cityService;

	@Autowired
	private AreaConverterService converterService;

	@Autowired
	private AreaValidationService validationService;

	/**
	 * Method for creating a new area into the company
	 * 
	 * @param area
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public AreaDTO create(AreaDTO area) throws FixedAssetsServiceException {

		validationService.validateCreation(area);
		cityService.updateAsRoot(area.getCityOfAssignmentId(), false);

		Area savedArea = repository.save(converterService.toEntity(area));

		return converterService.toDTO(savedArea);
	}

	/**
	 * Retrieve all areas into the company
	 * 
	 * @return
	 */
	public List<AreaDTO> retrieveAllAreas() {
		List<Area> areaList = repository.findAll();
		return converterService.toDtos(areaList);
	}

	/**
	 * Retrieve area by ID
	 * 
	 * @param id
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public AreaDTO retrieveById(String id) throws FixedAssetsServiceException {

		Optional<Area> optionalArea = repository.findById(id);

		if (!optionalArea.isPresent()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_NOT_FOUND);
		}

		return converterService.toDTO(optionalArea.get());
	}

	/**
	 * Retrieve area by name
	 * 
	 * @param name
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public AreaDTO retrieveByName(String name) throws FixedAssetsServiceException {

		Area area = new Area();

		switch (name.trim().toUpperCase()) {
		case "COMMERCIAL":
			area = repository.findByName(AreaName.COMMERCIAL);
			break;
		case "DEVELOPMENT":
			area = repository.findByName(AreaName.DEVELOPMENT);
			break;
		case "ADMINISTRATIVE":
			area = repository.findByName(AreaName.ADMINISTRATIVE);
			break;
		case "HUMAN_RESOURCES":
			area = repository.findByName(AreaName.HUMAN_RESOURCES);
			break;
		case "BILLING":
			area = repository.findByName(AreaName.BILLING);
			break;
		case "ROUTING":
			area = repository.findByName(AreaName.ROUTING);
			break;
		default:
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_NAME_DOES_NOT_EXISTS);
		}

		if (area == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_NOT_FOUND);
		}

		return converterService.toDTO(area);
	}

	/**
	 * Retrieve area by assigned city ID
	 * 
	 * @param cityOfAssignmentId
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public AreaDTO retrieveByCityOfAssignmentId(String cityOfAssignmentId) throws FixedAssetsServiceException {

		Area area = repository.findByCityOfAssignmentId(cityOfAssignmentId);

		if (area == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_NOT_FOUND);
		}

		return converterService.toDTO(area);
	}

	/**
	 * This method will delete an area
	 * 
	 * @param id
	 * @throws FixedAssetsServiceException
	 */
	public void delete(String id) throws FixedAssetsServiceException {

		Optional<Area> optionalArea = repository.findById(id);

		if (!optionalArea.isPresent()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_NOT_FOUND);
		}

		cityService.updateAsRoot(optionalArea.get().getCityOfAssignmentId(), true);

		repository.delete(optionalArea.get());

	}

	/**
	 * Update an area for company
	 * 
	 * @param area
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public AreaDTO update(AreaDTO area) throws FixedAssetsServiceException {

		validationService.validateUpdate(area);
		area.setName(area.getName().trim().toUpperCase());

		Area oldArea = repository.findById(area.getId()).get();

		// Update availability of old city
		cityService.updateAsRoot(oldArea.getCityOfAssignmentId(), true);

		// Update availability of new city
		cityService.updateAsRoot(area.getCityOfAssignmentId(), false);

		Area newArea = repository.save(converterService.toEntity(area));

		return converterService.toDTO(newArea);
	}

}
