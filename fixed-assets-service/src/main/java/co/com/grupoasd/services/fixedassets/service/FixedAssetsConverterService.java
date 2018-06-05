package co.com.grupoasd.services.fixedassets.service;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import co.com.grupoasd.services.fixedassets.dao.FixedAsset;
import co.com.grupoasd.services.fixedassets.dtos.FixedAssetDTO;
import co.com.grupoasd.services.fixedassets.dtos.PageResponseDTO;
import co.com.grupoasd.services.fixedassets.paging.PageInformation;
import co.com.grupoasd.services.fixedassets.paging.Sort;

/**
 * Converter service from DTO to entity and from entity to DTO
 * 
 * @author egiraldo
 *
 */
@Service
public class FixedAssetsConverterService {

	/**
	 * Convert entity to DTO
	 * 
	 * @param entity
	 * @return
	 */
	public FixedAssetDTO toDTO(FixedAsset entity) {
		ModelMapper modelMapper = new ModelMapper();
		FixedAssetDTO dto = modelMapper.map(entity, FixedAssetDTO.class);
		return dto;
	}

	/**
	 * Convert DTO to entity
	 * 
	 * @param dto
	 * @return
	 */
	public FixedAsset toEntity(FixedAssetDTO dto) {
		ModelMapper modelMapper = new ModelMapper();
		FixedAsset entity = modelMapper.map(dto, FixedAsset.class);
		return entity;
	}

	/**
	 * Paging multiples entities
	 * 
	 * @param entities
	 * @return
	 */
	public PageResponseDTO<FixedAssetDTO> toDtos(Page<FixedAsset> entities) {

		ModelMapper modelMapper = new ModelMapper();

		PageResponseDTO<FixedAssetDTO> page = new PageResponseDTO<FixedAssetDTO>(
				(entities.getContent().stream().map(entity -> {
					FixedAssetDTO dto = toDTO(entity);
					return dto;
				}).collect(Collectors.toList())), entities.getTotalElements());

		page.setPageable(modelMapper.map(entities.getPageable(), PageInformation.class));
		page.setTotalElements(entities.getTotalElements());
		page.setLast(entities.isLast());
		page.setFirst(entities.isFirst());
		page.setNumberOfElements(entities.getNumberOfElements());
		page.setSort(modelMapper.map(entities.getSort(), Sort.class));
		page.setSize(entities.getSize());
		page.setNumber(entities.getNumber());
		page.setTotalPages(entities.getTotalPages());
		page.getPageable().setPaged(entities.getPageable().isPaged());
		return page;

	}

}
