package co.com.grupoasd.services.fixedassets.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import co.com.grupoasd.services.fixedassets.types.AreaName;

/**
 * Entity for areas
 * 
 * @author egiraldo
 *
 */
@Document(collection = "areas")
public class Area {

	private String id;
	private AreaName name;
	private String cityOfAssignmentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AreaName getName() {
		return name;
	}

	public void setName(AreaName name) {
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
