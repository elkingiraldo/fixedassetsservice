package co.com.grupoasd.services.fixedassets.dtos;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.com.grupoasd.services.fixedassets.types.AssetStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Information about Fixed Assets")
public class FixedAssetDTO implements Serializable {

	private static final long serialVersionUID = -3279908863966378897L;

	@ApiModelProperty(notes = "The fixed asset name", required = true)
	private String name;

	@ApiModelProperty(notes = "The fixed asset description", required = false)
	private String description;

	@ApiModelProperty(notes = "The fixed asset serial", required = true)
	private String serial;

	@ApiModelProperty(notes = "The fixed asset inventory number", required = false)
	private String stockNumber;

	@ApiModelProperty(notes = "The fixed asset color", required = false)
	private String color;

	@ApiModelProperty(notes = "The fixed asset weight", required = true)
	private double weight;

	@ApiModelProperty(notes = "The fixed asset high", required = true)
	private double high;

	@ApiModelProperty(notes = "The fixed asset width", required = true)
	private double width;

	@ApiModelProperty(notes = "The fixed asset length", required = true)
	private double length;

	@ApiModelProperty(notes = "The fixed asset purchase value", required = true)
	private double purchaseValue;

	@ApiModelProperty(notes = "The fixed asset purchase date", required = true)
	private Date purchaseDate;

	@ApiModelProperty(notes = "The fixed asset leaving date", required = false)
	private Date leavingDate;

	@ApiModelProperty(notes = "The fixed asset status", required = false)
	private AssetStatus status;

	@ApiModelProperty(notes = "The fixed asset status", required = true)
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getPurchaseValue() {
		return purchaseValue;
	}

	public void setPurchaseValue(double purchaseValue) {
		this.purchaseValue = purchaseValue;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}

	public AssetStatus getStatus() {
		return status;
	}

	public void setStatus(AssetStatus status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FixedAssetDTO [name=" + name + ", description=" + description + ", serial=" + serial + ", stockNumber="
				+ stockNumber + ", color=" + color + ", weight=" + weight + ", high=" + high + ", width=" + width
				+ ", length=" + length + ", purchaseValue=" + purchaseValue + ", purchaseDate=" + purchaseDate
				+ ", leavingDate=" + leavingDate + ", status=" + status + ", type=" + type + "]";
	}

}
