package za.co.discovery.assignment.service.impl;

import org.springframework.stereotype.Service;
import za.co.discovery.assignment.bo.RouteBo;
import za.co.discovery.assignment.dto.UpdateRouteDto;
import za.co.discovery.assignment.service.RoutePatch;

import java.util.Objects;

@Service
public class DistanceRoutePatch implements RoutePatch {

    @Override
    public void patch(UpdateRouteDto routeDto, RouteBo routeBo) {

        Double distance = routeDto.getDistance();
        if (Objects.nonNull(distance)) {
            routeBo.setDistance(distance);
        }

    }
}
