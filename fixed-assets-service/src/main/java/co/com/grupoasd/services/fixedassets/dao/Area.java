package co.com.grupoasd.services.fixedassets.dao;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity for areas
 * 
 * @author egiraldo
 *
 */
@Document(collection = "areas")
public class Area {

	private String id;
	private String name;
	private String cityOfAssignmentId;

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

	public String getCityOfAssignmentId() {
		return cityOfAssignmentId;
	}

	public void setCityOfAssignmentId(String cityOfAssignmentId) {
		this.cityOfAssignmentId = cityOfAssignmentId;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", name=" + name + ", cityOfAssignmentId=" + cityOfAssignmentId + "]";
	}

}
