package co.com.grupoasd.services.fixedassets.dao;

import java.text.ParseException;
import java.util.Map;

import org.springframework.data.domain.Page;

import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.paging.PagingInformation;
import co.com.grupoasd.services.fixedassets.paging.SortDirection;

/**
 * Custom repository to search
 * 
 * @author egiraldo
 *
 */
public interface FixedAssetsCustomRepository {

	public Page<FixedAsset> search(Map<String, String> searchFilters, Map<String, SortDirection> orderFields,
			PagingInformation pagingInformation) throws FixedAssetsServiceException, ParseException;

}
