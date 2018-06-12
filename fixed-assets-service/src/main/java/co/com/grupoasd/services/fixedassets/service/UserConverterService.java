package co.com.grupoasd.services.fixedassets.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.User;
import co.com.grupoasd.services.fixedassets.dtos.UserDTO;

/**
 * Converter service for users from DTO to entity and from entity to DTO
 * 
 * @author egiraldo
 *
 */
@Service
public class UserConverterService {

	/**
	 * Convert entity to DTO
	 * 
	 * @param entity,
	 *            user entity to transform
	 * @return {@link UserDTO}, user transformed in DTO
	 */
	public UserDTO toDTO(User entity) {
		ModelMapper modelMapper = new ModelMapper();
		UserDTO dto = modelMapper.map(entity, UserDTO.class);
		return dto;
	}

	/**
	 * Convert DTO to entity
	 * 
	 * @param dto,
	 *            user DTO to transform
	 * @return {@link User}, user entity transformed
	 */
	public User toEntity(UserDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		User entity = modelMapper.map(dto, User.class);
		return entity;
	}

	/**
	 * Paging multiples entities
	 * 
	 * @param entities,
	 *            user entities to transform
	 * @return {@link UserDTO}, user DTOs transformed
	 */

	public List<UserDTO> toDtos(List<User> entities) {

		List<UserDTO> dtoList = new ArrayList<UserDTO>();

		for (User User : entities) {
			ModelMapper modelMapper = new ModelMapper();
			UserDTO dto = modelMapper.map(User, UserDTO.class);
			dtoList.add(dto);
		}

		return dtoList;

	}

}
