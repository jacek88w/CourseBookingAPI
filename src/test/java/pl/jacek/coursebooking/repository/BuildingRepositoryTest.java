package pl.jacek.coursebooking.repository;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.jacek.coursebooking.entity.Building;
import pl.jacek.coursebooking.model.Address;

@DataJpaTest
class BuildingRepositoryTest {

    @Autowired
    BuildingRepository buildingRepository;

    @Test
    public void whenBuildingSavedThenAlsoAddressSaved() {

        Address address = Address.builder()
                .streetNumber(23)
                .city("Opole")
                .zipCode("46-040")
                .street("Lipcowa")
                .build();

        Building building = Building.builder()
                .name("Hogwart")
                .address(address)
                .build();
        buildingRepository.save(building);
    }
}