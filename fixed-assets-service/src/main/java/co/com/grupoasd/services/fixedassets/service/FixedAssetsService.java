package co.com.grupoasd.services.fixedassets.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.FixedAsset;
import co.com.grupoasd.services.fixedassets.dao.FixedAssetsRepository;
import co.com.grupoasd.services.fixedassets.dtos.FixedAssetDTO;
import co.com.grupoasd.services.fixedassets.dtos.PageResponseDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.integrations.PagingInformation;
import co.com.grupoasd.services.fixedassets.integrations.SortDirection;

/**
 * Manage all services for fixed assets
 * 
 * @author egiraldo
 *
 */
@Service
public class FixedAssetsService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FixedAssetsValidationService fixedAssetsValidationService;

	@Autowired
	private FixedAssetsConverterService fixedAssetsConverterService;

	@Autowired
	private FixedAssetsRepository fixedAssetsRepository;

	/**
	 * Service for creating a new fixed asset
	 * 
	 * @param fixedAsset,
	 *            fixed asset DTO to create
	 * @return {@link FixedAssetDTO}, fixed asset created
	 * @throws FixedAssetsServiceException
	 *             if fails creation of new fixed asset
	 */
	public FixedAssetDTO create(FixedAssetDTO fixedAsset) throws FixedAssetsServiceException {

		logger.info("[FixedAssetsService][create]. Start creation");

		fixedAssetsValidationService.validateCreation(fixedAsset);

		FixedAsset savedFixedAsset = fixedAssetsRepository.save(fixedAssetsConverterService.toEntity(fixedAsset));

		logger.info("[FixedAssetsService][create]. End creation. New Fixed Asset: " + savedFixedAsset.toString());

		return fixedAssetsConverterService.toDTO(savedFixedAsset);
	}

	/**
	 * Get fixed assets depending on user filters
	 * 
	 * @param searchFilters,
	 *            filers for searching fixed assets
	 * @param orderFields,
	 *            filters for order results
	 * @param pagingInformation,
	 *            information about page
	 * @return {@link FixedAssetDTO}, fixed asset found
	 * @throws FixedAssetsServiceException
	 *             if fails getting fixed assets
	 */
	public PageResponseDTO<FixedAssetDTO> get(Map<String, String> searchFilters, Map<String, SortDirection> orderFields,
			PagingInformation pagingInformation) throws FixedAssetsServiceException {

		logger.info("[FixedAssetsService][get]. Start searching");

		Page<FixedAsset> entities = fixedAssetsRepository.search(searchFilters, orderFields, pagingInformation);

		logger.info("[FixedAssetsService][get]. End searching. There are: " + entities.getTotalElements()
				+ " elements in search");

		return fixedAssetsConverterService.toDtos(entities);
	}

	/**
	 * This method will update fixed asset and save it in DB
	 * 
	 * @param dto,
	 *            fixed asset DTO to update
	 * @return {@link FixedAssetDTO}, fixed asset updated
	 * @throws FixedAssetsServiceException
	 *             if fails update of entered fixed asset
	 */
	public FixedAssetDTO update(FixedAssetDTO dto) throws FixedAssetsServiceException {

		logger.info("[FixedAssetsService][update]. Start update");

		FixedAsset newEntity = fixedAssetsValidationService.validateUpdate(dto);

		fixedAssetsRepository.save(newEntity);

		logger.info("[FixedAssetsService][update]. End update. Updated asset: " + newEntity.toString());

		return fixedAssetsConverterService.toDTO(newEntity);
	}

	/**
	 * Update fixed asset automatically when leaving date is in the past
	 * 
	 * @param dto,
	 *            fixed asset to expiration Date is arriving
	 */
	public void updateExpirateLeavingDate(FixedAssetDTO dto) {

		logger.info("[FixedAssetsService][updateExpirateLeavingDate]. Start update expiration leaving date");

		fixedAssetsRepository.save(fixedAssetsConverterService.toEntity(dto));

	}

}
