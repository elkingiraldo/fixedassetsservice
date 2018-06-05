package co.com.grupoasd.services.fixedassets.service;

import java.text.ParseException;
import java.util.Map;

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

	@Autowired
	private FixedAssetsValidationService fixedAssetsValidationService;

	@Autowired
	private FixedAssetsConverterService fixedAssetsConverterService;

	@Autowired
	private FixedAssetsRepository fixedAssetsRepository;

	/**
	 * Service for creating a new fixed asset
	 * 
	 * @param fixedAsset
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public FixedAssetDTO create(FixedAssetDTO fixedAsset) throws FixedAssetsServiceException {

		fixedAsset = fixedAssetsValidationService.validateCreation(fixedAsset);

		FixedAsset savedFixedAsset = fixedAssetsRepository.save(fixedAssetsConverterService.toEntity(fixedAsset));

		return fixedAssetsConverterService.toDTO(savedFixedAsset);
	}

	/**
	 * Get fixed assets depending on user filters
	 * 
	 * @param searchFilters
	 * @param orderFields
	 * @param pagingInformation
	 * @return
	 * @throws FixedAssetsServiceException
	 * @throws ParseException
	 */
	public PageResponseDTO<FixedAssetDTO> get(Map<String, String> searchFilters, Map<String, SortDirection> orderFields,
			PagingInformation pagingInformation) throws FixedAssetsServiceException {

		Page<FixedAsset> entities = fixedAssetsRepository.search(searchFilters, orderFields, pagingInformation);

		return fixedAssetsConverterService.toDtos(entities);
	}

	/**
	 * This method will update fixed asset and save it in DB
	 * 
	 * @param fixedAsset
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public FixedAssetDTO update(FixedAssetDTO dto) throws FixedAssetsServiceException {

		FixedAsset newEntity = fixedAssetsValidationService.validateUpdate(dto);

		fixedAssetsRepository.save(newEntity);

		return fixedAssetsConverterService.toDTO(newEntity);
	}

}
