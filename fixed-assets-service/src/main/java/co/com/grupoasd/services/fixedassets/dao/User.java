package co.com.grupoasd.services.fixedassets.dao;

/**
 * Entity for people
 * 
 * @author egiraldo
 *
 */
public class User {

	private String id;
	private String firstName;
	private String lastName;
	private String personalId;
	private String cityWorkId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getCityWorkId() {
		return cityWorkId;
	}

	public void setCityWorkId(String cityWorkId) {
		this.cityWorkId = cityWorkId;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", personalId=" + personalId
				+ ", cityWorkId=" + cityWorkId + "]";
	}
}
