package co.com.grupoasd.services.fixedassets.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.User;
import co.com.grupoasd.services.fixedassets.dao.UserRepository;
import co.com.grupoasd.services.fixedassets.dtos.UserDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceErrorCodes;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;

/**
 * This service will validate all related to Users
 * 
 * @author egiraldo
 *
 */
@Service
public class UserValidationService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private CityService cityService;

	/**
	 * Validate creation for users
	 * 
	 * @param user,
	 *            user DTO to validate creation
	 * @throws FixedAssetsServiceException
	 *             if validate creation fails
	 */
	public void validateCreation(UserDTO user) throws FixedAssetsServiceException {

		validateUser(user);
		validateFirstName(user.getFirstName());
		validateLastName(user.getLastName());
		validatePersonalId(user.getPersonalId());
		validateCityWorkId(user.getCityWorkId());

	}

	/**
	 * Validate if city ID belongs to a valid city
	 * 
	 * @param cityWorkId
	 * @throws FixedAssetsServiceException
	 */
	private void validateCityWorkId(String cityWorkId) throws FixedAssetsServiceException {
		cityService.retrieveById(cityWorkId);
	}

	/**
	 * Validate is personal ID exists in request and DB
	 * 
	 * @param personalId
	 * @throws FixedAssetsServiceException
	 */
	private void validatePersonalId(String personalId) throws FixedAssetsServiceException {
		if (personalId == null || personalId.isEmpty() || personalId.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_PERSONAL_ID_REQUIRED);
		}

		User entity = repository.findByPersonalId(personalId);

		if (entity != null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_PERSONAL_ID_ALREADY_EXISTS);
		}

	}

	/**
	 * Validate if first name exists
	 * 
	 * @param firstName
	 */
	private void validateFirstName(String firstName) throws FixedAssetsServiceException {
		if (firstName == null || firstName.isEmpty() || firstName.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_FIRST_NAME_REQUIRED);
		}
	}

	/**
	 * Validate if first name exists
	 * 
	 * @param firstName
	 */
	private void validateLastName(String lastName) throws FixedAssetsServiceException {
		if (lastName == null || lastName.isEmpty() || lastName.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_LAST_NAME_REQUIRED);
		}
	}

	/**
	 * Validate if user exists
	 * 
	 * @param user
	 * @throws FixedAssetsServiceException
	 */
	private void validateUser(UserDTO user) throws FixedAssetsServiceException {
		if (user == null) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_REQUIRED);
		}
	}

	/**
	 * It will validate update an user
	 * 
	 * @param user,
	 *            user DTO to validate update
	 * @throws FixedAssetsServiceException
	 *             if validate update fails
	 */
	public void validateUpdate(UserDTO user) throws FixedAssetsServiceException {

		validateId(user.getId());
		validateUser(user);
		validateFirstName(user.getFirstName());
		validateLastName(user.getLastName());
		validateCityWorkId(user.getCityWorkId());
		validateUpdatePersonalId(user);

	}

	/**
	 * Validate User ID exists
	 * 
	 * @param id
	 * @throws FixedAssetsServiceException
	 */
	private void validateId(String id) throws FixedAssetsServiceException {

		if (id == null || id.isEmpty() || id.trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_ID_REQUIRED);
		}

		Optional<User> optionalEntity = repository.findById(id);
		if (!optionalEntity.isPresent()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_UPDATE_NOT_FOUND);
		}
	}

	/**
	 * Validate unique ID in DB
	 * 
	 * @param dto
	 * @throws FixedAssetsServiceException
	 */
	private void validateUpdatePersonalId(UserDTO dto) throws FixedAssetsServiceException {

		if (dto.getPersonalId() == null || dto.getPersonalId().isEmpty() || dto.getPersonalId().trim().isEmpty()) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_PERSONAL_ID_REQUIRED);
		}

		User userToUpdate = repository.findById(dto.getId()).get();
		User user = repository.findByPersonalId(dto.getPersonalId());

		if (user != null && !user.getPersonalId().equals(userToUpdate.getPersonalId())) {
			throw new FixedAssetsServiceException(FixedAssetsServiceErrorCodes.USER_PERSONAL_ID_ALREADY_EXISTS);
		}

	}

}
