package za.co.discovery.assignment.service;

import za.co.discovery.assignment.bo.RouteBo;
import za.co.discovery.assignment.dto.RouteDto;
import za.co.discovery.assignment.dto.UpdateRouteDto;
import za.co.discovery.assignment.exception.RouteConflictException;
import za.co.discovery.assignment.exception.RouteNotFoundException;

import java.util.List;

/**
 * This class contains logic used in route operations
 *
 * @see RouteBo
 */
public interface RouteService {

    /**
     * @param route payload to create a route, requires all fields during creation
     * @throws za.co.discovery.assignment.exception.RouteConflictException if origin destination pair is not unique
     */
    void create(RouteDto route) throws RouteConflictException;

    /**
     * @return lists of all routes
     */
    List<RouteDto> findAll();

    /**
     * @param routeId primary key identifer for the record in {@link RouteBo}
     * @return mapped route data
     * @throws RouteNotFoundException
     */
    RouteDto findById(Long routeId) throws RouteNotFoundException;

    /**
     * @param routeId  primary key identifer for the record in {@link RouteBo}
     * @param routeDto patch update payload for the request
     * @throws RouteNotFoundException should be thrown for every implementation
     */
    void update(Long routeId, UpdateRouteDto routeDto) throws RouteNotFoundException;

    /**
     * @param routeId
     */
    void delete(Long routeId) throws RouteNotFoundException;
}
