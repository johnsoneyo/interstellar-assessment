package za.co.discovery.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.discovery.assignment.bo.Planet;
import za.co.discovery.assignment.bo.RouteBo;

public interface RouteRepository extends JpaRepository<RouteBo, Long> {

    boolean existsByOriginAndDestination(Planet origin, Planet destination);
}
