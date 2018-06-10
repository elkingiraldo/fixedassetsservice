package co.com.grupoasd.services.fixedassets.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Data transfer Object for people
 * 
 * @author egiraldo
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Information about people")
public class UserDTO implements Serializable {

	private static final long serialVersionUID = -8026856795673907114L;

	@ApiModelProperty(notes = "The user id. it is generated by the service. It won't be updated", required = false)
	private String id;

	@ApiModelProperty(notes = "The user first name", required = true)
	private String firstName;

	@ApiModelProperty(notes = "The user last name", required = true)
	private String lastName;

	@ApiModelProperty(notes = "The user unique ID", required = true)
	private String personalId;

	@ApiModelProperty(notes = "The city, the person works", required = true)
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
		return "PersonDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", personalId="
				+ personalId + ", cityWorkId=" + cityWorkId + "]";
	}

}