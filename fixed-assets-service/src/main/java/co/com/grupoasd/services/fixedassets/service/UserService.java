package co.com.grupoasd.services.fixedassets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.User;
import co.com.grupoasd.services.fixedassets.dao.UserRepository;
import co.com.grupoasd.services.fixedassets.dtos.UserDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;

/**
 * This service will handle all users
 * 
 * @author egiraldo
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserConverterService converterService;

	@Autowired
	private UserValidationService validationService;

	/**
	 * This method will create new users
	 * 
	 * @param user,
	 *            user DTO to create
	 * @return {@link UserDTO}, user created
	 * @throws FixedAssetsServiceException
	 *             if fails creation of a new user
	 */
	public UserDTO create(UserDTO user) throws FixedAssetsServiceException {

		validationService.validateCreation(user);

		User savedUser = repository.save(converterService.toEntity(user));

		return converterService.toDTO(savedUser);
	}

	/**
	 * This method will retrieve all users
	 * 
	 * @return {@link UserDTO}, all users in DB
	 */
	public List<UserDTO> retrieveAllUsers() {
		List<User> entities = repository.findAll();
		return converterService.toDtos(entities);
	}

	/**
	 * This method will retrieve User by personal ID
	 * 
	 * @param personalId,
	 *            user personal ID to find User
	 * @return {@link UserDTO}, user found
	 * @throws FixedAssetsServiceException
	 *             if user not found
	 */
	public UserDTO retrieveByPersonalId(String personalId) throws FixedAssetsServiceException {

		User entity = repository.findByPersonalId(personalId);

		if (entity == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_NOT_FOUND);
		}

		return converterService.toDTO(entity);
	}

	/**
	 * This method will update information for User
	 * 
	 * @param user,
	 *            user DTO to update
	 * @return {@link UserDTO}, user updated
	 * @throws FixedAssetsServiceException
	 *             if fails update of entered user
	 */
	public UserDTO update(UserDTO user) throws FixedAssetsServiceException {

		validationService.validateUpdate(user);

		User savedUser = repository.save(converterService.toEntity(user));

		return converterService.toDTO(savedUser);
	}

	/**
	 * This method will delete an user
	 * 
	 * @param id,
	 *            user ID to delete
	 * @throws FixedAssetsServiceException
	 *             if user not found
	 */
	public void delete(String id) throws FixedAssetsServiceException {

		User user = repository.findByPersonalId(id);

		if (user == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_NOT_FOUND);
		}

		repository.delete(user);

	}

}
