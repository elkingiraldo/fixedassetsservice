package co.com.grupoasd.services.fixedassets.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.grupoasd.services.fixedassets.types.AreaName;

/**
 * Repository for areas into the company
 * 
 * @author egiraldo
 *
 */
public interface AreaRepository extends MongoRepository<Area, String> {

	public Area findByName(AreaName name);

	public Area findByCityOfAssignmentId(String cityOfAssignmentId);

}
