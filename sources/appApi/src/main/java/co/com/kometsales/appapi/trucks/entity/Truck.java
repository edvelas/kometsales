package co.com.kometsales.appapi.trucks.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import co.com.kometsales.appapi.exception.ServiceException;
import co.com.kometsales.appapi.trucks.dto.TruckDTO;
import co.com.kometsales.appapi.utils.Utils;

/**
 * Clase que hace referencia a la informacion del sistema
 */
@Entity
@Table(name = "TKOM_TRUCKS")
public class Truck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CDCAR_PLATE", length = 6, nullable = false, updatable = false, unique = true)
	private String carPlate;

	@Column(name = "NMMODEL", length = 4)
	private Long model;

	@Column(name = "DSREGISTRATION", nullable = false, length = 100)
	private String registration;

	@Column(name = "DSCOLOR", nullable = false, length = 100)
	private String color;

	@Column(name = "DATELOAD", nullable = false)
	private Date dateLoad;

	public Truck() {
	}

	public Truck(TruckDTO truckDTO) throws ServiceException {
		this.carPlate = truckDTO.getCarPlate();
		this.model = Long.parseLong(truckDTO.getModel());
		this.registration = truckDTO.getRegistration();
		this.color = truckDTO.getColor();
		this.dateLoad = Utils.getStringToDate(truckDTO.getDateLoad());
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public Long getModel() {
		return model;
	}

	public void setModel(Long model) {
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

	public Date getDateLoad() {
		return dateLoad;
	}

	public void setDateLoad(Date dateLoad) {
		this.dateLoad = dateLoad;
	}

}
