package co.com.grupoasd.services.fixedassets.dao;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity for cities
 * 
 * @author egiraldo
 *
 */
@Document(collection = "cities")
public class City {

	private String id;
	private String name;
	private String cityCode;
	private boolean availableToAssignArea;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public boolean isAvailableToAssignArea() {
		return availableToAssignArea;
	}

	public void setAvailableToAssignArea(boolean availableToAssignArea) {
		this.availableToAssignArea = availableToAssignArea;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", cityCode=" + cityCode + ", availableToAssignArea="
				+ availableToAssignArea + "]";
	}

}
