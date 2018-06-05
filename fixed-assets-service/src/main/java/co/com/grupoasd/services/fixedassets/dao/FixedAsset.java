package co.com.grupoasd.services.fixedassets.dao;

import java.util.Date;

import co.com.grupoasd.services.fixedassets.types.AssetStatus;
import co.com.grupoasd.services.fixedassets.types.AssetType;

public class FixedAsset {

	private String id;
	private String name;
	private String description;
	private String serial;
	private String stockNumber;
	private String color;
	private double weight;
	private double high;
	private double width;
	private double length;
	private double purchaseValue;
	private Date purchaseDate;
	private Date leavingDate;
	private AssetStatus status;
	private AssetType type;

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

	public AssetType getType() {
		return type;
	}

	public void setType(AssetType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FixedAsset [id=" + id + ", name=" + name + ", description=" + description + ", serial=" + serial
				+ ", stockNumber=" + stockNumber + ", color=" + color + ", weight=" + weight + ", high=" + high
				+ ", width=" + width + ", length=" + length + ", purchaseValue=" + purchaseValue + ", purchaseDate="
				+ purchaseDate + ", leavingDate=" + leavingDate + ", status=" + status + ", type=" + type + "]";
	}

}
