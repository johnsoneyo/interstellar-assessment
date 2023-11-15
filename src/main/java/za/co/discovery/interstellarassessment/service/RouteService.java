package za.co.discovery.interstellarassessment.service;

import za.co.discovery.interstellarassessment.dto.RouteDto;

import java.util.List;

/**
 * This class contains logic used in route operations
 * @see za.co.discovery.interstellarassessment.bo.RouteBo
 */
public interface RouteService {

    void create(RouteDto route);

    List<RouteDto> findAll();
}
