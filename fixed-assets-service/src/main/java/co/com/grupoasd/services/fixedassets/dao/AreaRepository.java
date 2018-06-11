package co.com.grupoasd.services.fixedassets.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for areas into the company
 * 
 * @author egiraldo
 *
 */
public interface AreaRepository extends MongoRepository<Area, String> {

	public Area findByName(String name);

	public Area findByCityOfAssignmentId(String cityOfAssignmentId);

}
