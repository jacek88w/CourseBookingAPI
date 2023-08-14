package pl.jacek.coursebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jacek.coursebooking.entity.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
