package co.com.grupoasd.services.fixedassets.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import co.com.grupoasd.services.fixedassets.constants.FixedAssetsServiceConstants;
import co.com.grupoasd.services.fixedassets.criteria.FixedAssetCriteria;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.integrations.PagingInformation;
import co.com.grupoasd.services.fixedassets.integrations.SortDirection;

/**
 * Implementation for fixed asset repository
 * 
 * @author egiraldo
 *
 */
public class FixedAssetsCustomRepositoryImpl implements FixedAssetsCustomRepository {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	SimpleDateFormat format = new SimpleDateFormat(FixedAssetsServiceConstants.DATE_QUERY_FORMAT);

	@Value("${fixed.assets.repository.maxpage.size:100}")
	private int maxPageSize;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Page<FixedAsset> search(Map<String, String> searchFilters, Map<String, SortDirection> orderFields,
			PagingInformation pagingInformation) throws FixedAssetsServiceException {

		logger.info("[FixedAssetsCustomRepositoryImpl][search]. Starting searching in DB");

		Query query = new Query();

		filterSearch(searchFilters, query);

		pagingInformation = fixPagination(pagingInformation);

		PageRequest pageable = orderResults(orderFields, pagingInformation);

		query.with(pageable);
		List<FixedAsset> campaigns = mongoTemplate.find(query, FixedAsset.class);

		logger.info("[FixedAssetsCustomRepositoryImpl][search]. End search in DB.");

		return PageableExecutionUtils.getPage(campaigns, pageable, () -> mongoTemplate.count(query, FixedAsset.class));
	}

	/**
	 * This method will find the filters for search in MongoDB template
	 * 
	 * @param searchFilters,
	 *            entered filters
	 * @param query,
	 *            generated query
	 * @throws FixedAssetsServiceException
	 *             if fails searching filters
	 */
	private void filterSearch(Map<String, String> searchFilters, Query query) throws FixedAssetsServiceException {

		if (searchFilters != null) {

			for (String filterStr : searchFilters.keySet()) {

				FixedAssetCriteria filter = getCriteria(filterStr);
				String filterValue = searchFilters.get(filterStr);
				switch (filter) {

				case SERIAL:
					query.addCriteria(Criteria.where("serial").is(filterValue));
					break;
				case TYPE:
					query.addCriteria(Criteria.where("type").is(filterValue));
					break;
				case STATUS:
					query.addCriteria(Criteria.where("status").is(filterValue));
					break;
				case NOT_STATUS:
					query.addCriteria(Criteria.where("status").ne(filterValue));
					break;
				case STOCK_NUMBER:
					query.addCriteria(Criteria.where("stockNumber").is(filterValue));
					break;
				case COLOR:
					query.addCriteria(Criteria.where("color").regex(filterValue, "i"));
					break;
				case LEAVING_DATE:
					try {
						query.addCriteria(Criteria.where("leavingDate")
								.lt(format.parse(searchFilters.get(FixedAssetCriteria.LEAVING_DATE.name()))));
					} catch (ParseException e) {
						throw new FixedAssetsServiceException(
								FixedAssetsServiceErrorCodes.PARSE_EXCEPTION_LEAVING_DATE);
					}
					break;
				default:
					break;
				}
			}

			if (searchFilters.get(FixedAssetCriteria.PURCHASE_DATE_FROM.name()) != null
					&& searchFilters.get(FixedAssetCriteria.PURCHASE_DATE_TO.name()) != null) {

				Criteria startDate = null;
				try {
					startDate = Criteria.where("purchaseDate")
							.gte(format.parse(searchFilters.get(FixedAssetCriteria.PURCHASE_DATE_FROM.name())));
				} catch (ParseException e) {
					throw new FixedAssetsServiceException(
							FixedAssetsServiceErrorCodes.PARSE_EXCEPTION_PURCHASE_DATE_FROM);
				}

				Criteria endDate = null;
				try {
					endDate = Criteria.where("purchaseDate")
							.lte(format.parse(searchFilters.get(FixedAssetCriteria.PURCHASE_DATE_TO.name())));
				} catch (ParseException e) {
					throw new FixedAssetsServiceException(
							FixedAssetsServiceErrorCodes.PARSE_EXCEPTION_PURCHASE_DATE_TO);
				}

				query.addCriteria(new Criteria().andOperator(startDate, endDate));
			}

		}

	}

	/**
	 * This method will sort results by entered filters
	 * 
	 * @param orderFields,
	 *            filters for sort
	 * @param pagingInformation,
	 *            filters for paging
	 * @return {@link PageRequest}
	 * @throws FixedAssetsServiceException
	 *             if sort filters fails
	 */
	private PageRequest orderResults(Map<String, SortDirection> orderFields, PagingInformation pagingInformation)
			throws FixedAssetsServiceException {
		if (orderFields != null) {
			List<Order> orders = new ArrayList<>();

			for (String criteriaStr : orderFields.keySet()) {
				FixedAssetCriteria criteria = getCriteria(criteriaStr);
				SortDirection criteriaValue = orderFields.get(criteriaStr);
				switch (criteria) {

				case SERIAL:
					if (criteriaValue.equals(SortDirection.DESC)) {
						orders.add(new Order(Direction.DESC, "serial"));
					} else {
						orders.add(new Order(Direction.ASC, "serial"));
					}
					break;
				case TYPE:
					if (criteriaValue.equals(SortDirection.DESC)) {
						orders.add(new Order(Direction.DESC, "type"));
					} else {
						orders.add(new Order(Direction.ASC, "type"));
					}
					break;
				case STATUS:
					if (criteriaValue.equals(SortDirection.DESC)) {
						orders.add(new Order(Direction.DESC, "status"));
					} else {
						orders.add(new Order(Direction.ASC, "status"));
					}
					break;
				case NOT_STATUS:
					if (criteriaValue.equals(SortDirection.DESC)) {
						orders.add(new Order(Direction.DESC, "status"));
					} else {
						orders.add(new Order(Direction.ASC, "status"));
					}
					break;
				case STOCK_NUMBER:
					if (criteriaValue.equals(SortDirection.DESC)) {
						orders.add(new Order(Direction.DESC, "stockNumber"));
					} else {
						orders.add(new Order(Direction.ASC, "stockNumber"));
					}
					break;
				case COLOR:
					if (criteriaValue.equals(SortDirection.DESC)) {
						orders.add(new Order(Direction.DESC, "color"));
					} else {
						orders.add(new Order(Direction.ASC, "color"));
					}
					break;
				case PURCHASE_DATE:
					if (criteriaValue.equals(SortDirection.DESC)) {
						orders.add(new Order(Direction.DESC, "purchaseDate"));
					} else {
						orders.add(new Order(Direction.ASC, "purchaseDate"));
					}
					break;

				default:
					break;
				}

			}
			return PageRequest.of(pagingInformation.getStart(), pagingInformation.getLimit(), Sort.by(orders));
		} else {
			return PageRequest.of(pagingInformation.getStart(), pagingInformation.getLimit());
		}
	}

	/**
	 * This method will get criteria from user request
	 * 
	 * @param criteriaString,
	 *            entered criteria
	 * @return {@link FixedAssetCriteria}, criteria found
	 * @throws FixedAssetsServiceException
	 *             if fails finding criteria
	 */
	private FixedAssetCriteria getCriteria(String criteriaString) throws FixedAssetsServiceException {
		try {
			FixedAssetCriteria criteria = FixedAssetCriteria.valueOf(criteriaString);

			if (criteria == null) {
				throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CRITERIA_NOT_FOUND);
			}

			return criteria;
		} catch (IllegalArgumentException e) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.CRITERIA_NOT_FOUND);
		}

	}

	/**
	 * This method will paging
	 * 
	 * @param pagingInformation,
	 *            paging entered information
	 * @return {@link PagingInformation}, paging information for searching
	 */
	private PagingInformation fixPagination(PagingInformation pagingInformation) {

		if (pagingInformation == null) {
			pagingInformation = new PagingInformation();
			pagingInformation.setStart(0);
			pagingInformation.setLimit(maxPageSize);
		}

		if (pagingInformation.getLimit() > maxPageSize) {
			pagingInformation.setLimit(maxPageSize);
		}

		return pagingInformation;

	}

}
