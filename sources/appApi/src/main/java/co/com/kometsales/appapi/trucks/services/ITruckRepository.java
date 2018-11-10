package co.com.kometsales.appapi.trucks.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.kometsales.appapi.trucks.entity.Truck;

/**
 * Class for access to the truck entity database.
 * 
 */
@Repository
public interface ITruckRepository extends JpaRepository<Truck, String> {

}
