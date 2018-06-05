package co.com.grupoasd.services.fixedassets.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.grupoasd.services.fixedassets.dtos.FixedAssetDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
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

	@Autowired
	private FixedAssetsService fixedAssetsService;

	@PostMapping
	public ResponseEntity<FixedAssetDTO> post(@RequestBody FixedAssetDTO fixedAsset,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		FixedAssetDTO newFixedAsset = fixedAssetsService.create(fixedAsset);

		return new ResponseEntity<FixedAssetDTO>(newFixedAsset, HttpStatus.CREATED);
	}

}
