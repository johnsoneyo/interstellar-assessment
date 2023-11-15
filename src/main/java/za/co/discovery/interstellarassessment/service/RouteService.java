package za.co.discovery.interstellarassessment.service;

import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.dto.UpdateRouteDto;
import za.co.discovery.interstellarassessment.exception.RouteNotFoundException;

import java.util.List;

/**
 * This class contains logic used in route operations
 *
 * @see za.co.discovery.interstellarassessment.bo.RouteBo
 */
public interface RouteService {

    /**
     *
     * @param route payload to create a route, requires all fields during creation
     */
    void create(RouteDto route);

    /**
     *
     * @return lists of all routes
     */
    List<RouteDto> findAll();

    /**
     *
     * @param routeId primary key identifer for the record in {@link za.co.discovery.interstellarassessment.bo.RouteBo}
     * @return mapped route data
     * @throws za.co.discovery.interstellarassessment.exception.RouteNotFoundException
     */
    RouteDto findById(Long routeId) throws RouteNotFoundException;

    /**
     *
     * @param routeId primary key identifer for the record in {@link za.co.discovery.interstellarassessment.bo.RouteBo}
     * @param routeDto patch update payload for the request
     * @throws za.co.discovery.interstellarassessment.exception.RouteNotFoundException should be thrown for every implementation
     */
    void update(Long routeId, UpdateRouteDto routeDto) throws RouteNotFoundException;
}
