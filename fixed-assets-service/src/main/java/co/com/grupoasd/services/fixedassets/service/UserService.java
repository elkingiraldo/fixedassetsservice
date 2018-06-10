package co.com.grupoasd.services.fixedassets.service;

import java.util.List;
import java.util.Optional;

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
	private UserConverterSerice converterService;

	@Autowired
	private UserValidationService validationService;

	/**
	 * This method will create new users
	 * 
	 * @param user
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public UserDTO create(UserDTO user) throws FixedAssetsServiceException {
		validationService.validateCreation(user);
		return null;
	}

	/**
	 * This method will retrieve all users
	 * 
	 * @return
	 */
	public List<UserDTO> retrieveAllUsers() {
		List<User> entities = repository.findAll();
		return converterService.toDtos(entities);
	}

	/**
	 * This method will retrieve User by personal ID
	 * 
	 * @param personalId
	 * @return
	 * @throws FixedAssetsServiceException
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
	 * @param user
	 * @return
	 * @throws FixedAssetsServiceException
	 */
	public UserDTO update(UserDTO user) throws FixedAssetsServiceException {

		validationService.validateUpdate(user);

		User savedUser = repository.save(converterService.toEntity(user));

		return converterService.toDTO(savedUser);
	}

	/**
	 * This method will delete an user
	 * 
	 * @param id
	 * @throws FixedAssetsServiceException
	 */
	public void delete(String id) throws FixedAssetsServiceException {

		Optional<User> optionalUser = repository.findById(id);

		if (!optionalUser.isPresent()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_NOT_FOUND);
		}

		repository.delete(optionalUser.get());

	}

}
