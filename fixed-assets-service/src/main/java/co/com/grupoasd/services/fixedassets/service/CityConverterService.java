package co.com.grupoasd.services.fixedassets.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.City;
import co.com.grupoasd.services.fixedassets.dtos.CityDTO;

/**
 * Converter service from DTO to entity and from entity to DTO for cities
 * 
 * @author egiraldo
 *
 */
@Service
public class CityConverterService {

	/**
	 * Convert entity to DTO
	 * 
	 * @param entity
	 * @return
	 */
	public CityDTO toDTO(City entity) {
		ModelMapper modelMapper = new ModelMapper();
		CityDTO dto = modelMapper.map(entity, CityDTO.class);
		return dto;
	}

	/**
	 * Convert DTO to entity
	 * 
	 * @param dto
	 * @return
	 */
	public City toEntity(CityDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		City entity = modelMapper.map(dto, City.class);
		return entity;
	}

	/**
	 * Paging multiples entities
	 * 
	 * @param entities
	 * @return
	 */

	public List<CityDTO> toDtos(List<City> entities) {

		List<CityDTO> dtoList = new ArrayList<CityDTO>();

		for (City city : entities) {
			ModelMapper modelMapper = new ModelMapper();
			CityDTO dto = modelMapper.map(city, CityDTO.class);
			dtoList.add(dto);
		}

		return dtoList;

	}

}
