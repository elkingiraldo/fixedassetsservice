package co.com.grupoasd.services.fixedassets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.FixedAsset;
import co.com.grupoasd.services.fixedassets.dao.FixedAssetsRepository;
import co.com.grupoasd.services.fixedassets.dtos.FixedAssetDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;

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

}
