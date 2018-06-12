package co.com.grupoasd.services.fixedassets.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.Area;
import co.com.grupoasd.services.fixedassets.dtos.AreaDTO;

/**
 * Converter service for Areas from DTO to entity and from entity to DTO
 * 
 * @author egiraldo
 *
 */
@Service
public class AreaConverterService {

	/**
	 * Convert entity to DTO
	 * 
	 * @param entity,
	 *            area entity
	 * @return {@link AreaDTO}
	 */
	public AreaDTO toDTO(Area entity) {
		ModelMapper modelMapper = new ModelMapper();
		AreaDTO dto = modelMapper.map(entity, AreaDTO.class);
		return dto;
	}

	/**
	 * Convert DTO to entity
	 * 
	 * @param dto,
	 *            area entered DTO
	 * @return {@link Area}
	 */
	public Area toEntity(AreaDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		Area entity = modelMapper.map(dto, Area.class);
		return entity;
	}

	/**
	 * Paging multiples entities
	 * 
	 * @param entities,
	 *            entities to transform
	 * @return {@link AreaDTO}
	 */

	public List<AreaDTO> toDtos(List<Area> entities) {

		List<AreaDTO> dtoList = new ArrayList<AreaDTO>();

		for (Area Area : entities) {
			ModelMapper modelMapper = new ModelMapper();
			AreaDTO dto = modelMapper.map(Area, AreaDTO.class);
			dtoList.add(dto);
		}

		return dtoList;

	}

}
