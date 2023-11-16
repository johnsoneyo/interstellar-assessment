package za.co.discovery.assignment.service;

import za.co.discovery.assignment.bo.RouteBo;
import za.co.discovery.assignment.dto.UpdateRouteDto;

/**
 * The Use route patchers are setters of the business object using a chain design pattern
 * This is an over engineering of a basic setter field but the essence is important
 * The motivation again is to keep the implementation closed and the modification logic open for extension
 */
public interface RoutePatch {

    void patch(UpdateRouteDto routeDto, RouteBo routeBo);
}
