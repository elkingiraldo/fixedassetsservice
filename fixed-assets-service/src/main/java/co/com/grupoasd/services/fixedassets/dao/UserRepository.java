package co.com.grupoasd.services.fixedassets.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

	public User findByPersonalId(String personalId);

}
