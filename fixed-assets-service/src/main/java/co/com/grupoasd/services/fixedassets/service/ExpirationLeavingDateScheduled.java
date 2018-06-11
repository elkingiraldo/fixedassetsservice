package co.com.grupoasd.services.fixedassets.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.com.grupoasd.services.fixedassets.constants.FixedAssetsServiceConstants;
import co.com.grupoasd.services.fixedassets.dtos.FixedAssetDTO;
import co.com.grupoasd.services.fixedassets.dtos.PageResponseDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.types.AssetAssignmentType;
import co.com.grupoasd.services.fixedassets.types.AssetStatus;

/**
 * Service for update fixed asset when leaving date is in the past
 * 
 * @author egiraldo
 *
 */
@Component
public class ExpirationLeavingDateScheduled {

	SimpleDateFormat format = new SimpleDateFormat(FixedAssetsServiceConstants.DATE_QUERY_FORMAT);

	@Autowired
	private FixedAssetsService fixedAssetService;

	@Scheduled(cron = FixedAssetsServiceConstants.CRON_LEAVING_DATE)
	public void expirateLeavingDate() throws FixedAssetsServiceException {

		Map<String, String> searchFilters = new HashMap<>();
		searchFilters.put("LEAVING_DATE", format.format(new Date()));

		PageResponseDTO<FixedAssetDTO> pageResponseDTO = fixedAssetService.get(searchFilters, null, null);

		if (pageResponseDTO.getSize() > 0) {

			for (FixedAssetDTO dto : pageResponseDTO.getContent()) {

				switch (AssetAssignmentType.valueOf(dto.getAssignmentType())) {
				case USER:
				case AREA:
					dto.setAssignmentType(AssetAssignmentType.STOCK.name());
					break;
				case STOCK:
				default:
					break;
				}

				dto.setAssignmentId(null);
				dto.setStatus(AssetStatus.RETIRED.name());

				fixedAssetService.updateExpirateLeavingDate(dto);

			}

		}

	}

}
