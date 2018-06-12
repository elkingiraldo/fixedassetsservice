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
	 * @param area,
	 *            area entity
	 * @return {@link AreaDTO}
	 * @throws FixedAssetsServiceException
	 *             if creation asset fail
	 */
	public AreaDTO create(AreaDTO area) throws FixedAssetsServiceException {

		validationService.validateCreation(area);
		area.setName(area.getName().trim().toUpperCase());
		cityService.updateAsRoot(area.getCityOfAssignmentId(), false);

		Area savedArea = repository.save(converterService.toEntity(area));

		return converterService.toDTO(savedArea);
	}

	/**
	 * Retrieve all areas into the company
	 * 
	 * @return {@link AreaDTO}, list of dto transformed
	 */
	public List<AreaDTO> retrieveAllAreas() {
		List<Area> areaList = repository.findAll();
		return converterService.toDtos(areaList);
	}

	/**
	 * Retrieve area by ID
	 * 
	 * @param id,
	 *            ID of area to find
	 * @return {@link AreaDTO}, area found and transformed
	 * @throws FixedAssetsServiceException
	 *             if area not found
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
	 * @param name,
	 *            name of area
	 * @return {@link AreaDTO}
	 * @throws FixedAssetsServiceException
	 *             if area name not found
	 */
	public AreaDTO retrieveByName(String name) throws FixedAssetsServiceException {

		Area area = repository.findByName(name.trim().toUpperCase());

		if (area == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_NOT_FOUND);
		}

		return converterService.toDTO(area);
	}

	/**
	 * Retrieve area by assigned city ID
	 * 
	 * @param cityOfAssignmentId,
	 *            ID of the city that was assigned
	 * @return {@link AreaDTO}, AreaDTO found
	 * @throws FixedAssetsServiceException
	 *             if area to update not found
	 */
	public AreaDTO retrieveByCityOfAssignmentId(String cityOfAssignmentId) throws FixedAssetsServiceException {

		Area area = repository.findByCityOfAssignmentId(cityOfAssignmentId);

		if (area == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_UPDATE_NOT_FOUND);
		}

		return converterService.toDTO(area);
	}

	/**
	 * This method will delete an area
	 * 
	 * @param id,
	 *            ID of area to delete
	 * @throws FixedAssetsServiceException
	 *             if area to delete not found
	 */
	public void delete(String id) throws FixedAssetsServiceException {

		Optional<Area> optionalArea = repository.findById(id);

		if (!optionalArea.isPresent()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.AREA_DELETE_NOT_FOUND);
		}

		cityService.updateAsRoot(optionalArea.get().getCityOfAssignmentId(), true);

		repository.delete(optionalArea.get());

	}

	/**
	 * Update an area for company
	 * 
	 * @param area,
	 *            area DTO to update
	 * @return {@link AreaDTO}
	 * @throws FixedAssetsServiceException
	 *             if fail update a fixed asset
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
