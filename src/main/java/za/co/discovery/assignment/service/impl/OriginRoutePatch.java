package za.co.discovery.assignment.service.impl;

import org.springframework.stereotype.Service;
import za.co.discovery.assignment.bo.Planet;
import za.co.discovery.assignment.bo.RouteBo;
import za.co.discovery.assignment.dto.UpdateRouteDto;
import za.co.discovery.assignment.service.RoutePatch;

import java.util.Objects;

@Service
public class OriginRoutePatch implements RoutePatch {

    @Override
    public void patch(UpdateRouteDto routeDto, RouteBo routeBo) {

        Planet origin = routeDto.getOrigin();
        if (Objects.nonNull(origin)) {
            routeBo.setOrigin(origin);
        }
    }
}
