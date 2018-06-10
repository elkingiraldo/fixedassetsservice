package co.com.grupoasd.services.fixedassets.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.grupoasd.services.fixedassets.dtos.FixedAssetDTO;
import co.com.grupoasd.services.fixedassets.dtos.PageResponseDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.integrations.GetRequestSerializer;
import co.com.grupoasd.services.fixedassets.integrations.InvalidHeaderParameterException;
import co.com.grupoasd.services.fixedassets.service.FixedAssetsService;

/**
 * Controller for management of fixed assets into the company
 * 
 * @author egiraldo
 *
 */
@RestController
@RequestMapping("/fixedassets/v1.0")
public class FixedAssetsController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FixedAssetsService fixedAssetsService;

	@PostMapping
	public ResponseEntity<FixedAssetDTO> post(@RequestBody FixedAssetDTO fixedAsset,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		logger.debug("FixedAssetDTO request:" + fixedAsset.toString());

		FixedAssetDTO newFixedAsset = fixedAssetsService.create(fixedAsset);

		logger.debug("FixedAssetDTO saved response:" + newFixedAsset.toString());

		return new ResponseEntity<FixedAssetDTO>(newFixedAsset, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<PageResponseDTO<FixedAssetDTO>> get(
			@RequestParam(value = "searchFilters", required = false) String searchFilters,
			@RequestParam(value = "orderFields", required = false) String orderFields,
			@RequestParam(value = "pagingInformation", required = false) String pagingInformation,
			@RequestHeader(value = "locale", required = false) String locale)
			throws FixedAssetsServiceException, InvalidHeaderParameterException {

		logger.debug("Search Filters: " + searchFilters + ", Order Fields: " + orderFields + "Paging Information: "
				+ pagingInformation);

		logger.info("Search Filters: " + searchFilters + ", Order Fields: " + orderFields + "Paging Information: "
				+ pagingInformation);

		PageResponseDTO<FixedAssetDTO> fixedAssetsFound = fixedAssetsService.get(
				GetRequestSerializer.extractFilters(searchFilters),
				GetRequestSerializer.extractOrderFields(orderFields),
				GetRequestSerializer.extractPagingInformation(pagingInformation));

		logger.debug("Search Response: " + fixedAssetsFound.toString());

		return new ResponseEntity<PageResponseDTO<FixedAssetDTO>>(fixedAssetsFound, HttpStatus.OK);

	}

	@PutMapping
	public ResponseEntity<FixedAssetDTO> put(@RequestBody FixedAssetDTO fixedAsset,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		FixedAssetDTO newFixedAsset = fixedAssetsService.update(fixedAsset);
		return new ResponseEntity<FixedAssetDTO>(newFixedAsset, HttpStatus.OK);
	}

}
