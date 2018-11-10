package co.com.kometsales.appapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.kometsales.appapi.trucks.entity.Truck;
import co.com.kometsales.appapi.trucks.services.ITruckRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TruckRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ITruckRepository truckRepository;

	@Test
	public void saveALLTest() {
		Truck truck = getDefaultTruck();
		Truck truckeSave = entityManager.persist(truck);
		entityManager.flush();

		Optional<Truck> optional = truckRepository.findById(truck.getCarPlate());

		Truck truckId = optional.get();
		assertThat(truckId.getCarPlate()).isEqualTo(truckeSave.getCarPlate());
	}

	@Test
	public void findByIdTest() {
		Truck truck = getDefaultTruck();
		Truck truckSave = entityManager.persist(truck);
		entityManager.flush();

		Optional<Truck> optional = truckRepository.findById(truckSave.getCarPlate());

		Truck truckId = optional.get();
		assertThat(truckId.getCarPlate()).isEqualTo(truckSave.getCarPlate());
	}

	private Truck getDefaultTruck() {
		Truck truck = new Truck();

		truck.setCarPlate("DEV100");
		truck.setModel(new Long(2012));
		truck.setRegistration("ENVIGADO");
		truck.setColor("BLANCO");
		truck.setDateLoad(new Date());

		return truck;
	}
}
