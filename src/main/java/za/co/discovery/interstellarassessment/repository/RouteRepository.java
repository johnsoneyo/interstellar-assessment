package za.co.discovery.interstellarassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.discovery.interstellarassessment.bo.RouteBo;

public interface RouteRepository extends JpaRepository<RouteBo, Long> {
}
