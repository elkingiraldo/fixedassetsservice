package co.com.grupoasd.services.fixedassets.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for cities
 * 
 * @author egiraldo
 *
 */
public interface CityRepository extends MongoRepository<City, String> {

	public City findByName(String name);

	public City findByCityCode(String cityCode);

}
