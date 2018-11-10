package co.com.kometsales.appapi.trucks.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "carPlate", "model", "registration", "color", "dateLoad" })
public class TruckDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String carPlate;

	private String model;

	private String registration;

	private String color;

	private String dateLoad;

	/**
	 * Indica si el registro cumplio formato y obligatoridad
	 * Por defaul todos cumplen
	 */
	private boolean valid = true;

	/**
	 * Carga todas las incosistencias generadas en el registro
	 */
	private List<String> inconsistencies;
	/**
	 * Constructos del objeto
	 */
	public TruckDTO() {
		//se crea por defaul
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDateLoad() {
		return dateLoad;
	}

	public void setDateLoad(String dateLoad) {
		this.dateLoad = dateLoad;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<String> getInconsistencies() {
		return inconsistencies;
	}

	public void setInconsistencies(List<String> inconsistencies) {
		this.inconsistencies = inconsistencies;
	}

	public String getLine() {
		return "[" + carPlate + "," + model + "," + registration + "," + color + "," + dateLoad + "]";
	}
	
	

}
