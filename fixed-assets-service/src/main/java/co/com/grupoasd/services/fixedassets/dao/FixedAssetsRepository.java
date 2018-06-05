package co.com.grupoasd.services.fixedassets.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for fixed assets
 * 
 * @author egiraldo
 *
 */
public interface FixedAssetsRepository extends MongoRepository<FixedAsset, String>, FixedAssetsCustomRepository {

	public FixedAsset findBySerial(String serial);

}
