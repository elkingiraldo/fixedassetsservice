package co.com.grupoasd.services.fixedassets.dao;

/**
 * Entity for cities
 * 
 * @author egiraldo
 *
 */
public class City {

	private String id;
	private String name;
	private String cityCode;

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

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", cityCode=" + cityCode + "]";
	}

}
