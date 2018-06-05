package co.com.grupoasd.services.fixedassets.integrations;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Getting filters from clients and transforming them
 * 
 * @author egiraldo
 *
 */
public class GetRequestSerializer {

	private static final String FILTERS_HEADER_NAME = "searchFilters";
	private static final String ORDER_HEADER_NAME = "orderFields";
	private static final String PAGING_HEADER_NAME = "pagingInformation";

	/**
	 * Compress into a single header multiple headers
	 * 
	 * @param filters
	 * @param orderFields
	 * @param pagingInformation
	 * @return
	 * @throws InvalidHeaderParameterException
	 */
	public Map<String, String> compressHeaders(Map<String, String> filters, Map<String, SortDirection> orderFields,
			PagingInformation pagingInformation) throws InvalidHeaderParameterException {

		Map<String, String> requestHeaders = new HashMap<>();

		requestHeaders.put(FILTERS_HEADER_NAME, serialize(filters));
		requestHeaders.put(ORDER_HEADER_NAME, serialize(filters));
		requestHeaders.put(PAGING_HEADER_NAME, serialize(pagingInformation));

		return requestHeaders;
	}

	public static String serialize(PagingInformation pagingInformation) throws InvalidHeaderParameterException {

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(pagingInformation);
			return json;
		} catch (Exception e) {
			throw new InvalidHeaderParameterException("Error encoding header: " + e.getMessage());
		}

	}

	public static String serialize(Map<String, ?> filters) throws InvalidHeaderParameterException {

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(filters);
			return json;
		} catch (Exception e) {
			throw new InvalidHeaderParameterException("Error encoding header: " + e.getMessage());
		}

	}

	public static PagingInformation extractPagingInformation(String pagingInformation)
			throws InvalidHeaderParameterException {

		if (pagingInformation == null)
			return null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(pagingInformation, PagingInformation.class);

		} catch (Exception e) {
			throw new InvalidHeaderParameterException("Error decoding paging header: " + e.getMessage());
		}

	}

	public static Map<String, SortDirection> extractOrderFields(String orderFields)
			throws InvalidHeaderParameterException {

		if (orderFields == null)
			return null;

		try {

			ObjectMapper mapper = new ObjectMapper();

			Map<String, SortDirection> map = new HashMap<String, SortDirection>();
			map = mapper.readValue(orderFields, new TypeReference<Map<String, SortDirection>>() {
			});

			return map;
		} catch (Exception e) {
			throw new InvalidHeaderParameterException("Error decoding order fields header: " + e.getMessage());
		}

	}

	public static Map<String, String> extractFilters(String filters) throws InvalidHeaderParameterException {

		if (filters == null)
			return null;

		try {

			ObjectMapper mapper = new ObjectMapper();

			Map<String, String> map = new HashMap<String, String>();
			map = mapper.readValue(filters, new TypeReference<Map<String, String>>() {
			});

			return map;
		} catch (Exception e) {
			throw new InvalidHeaderParameterException("Error decoding filters header: " + e.getMessage());
		}

	}

}
