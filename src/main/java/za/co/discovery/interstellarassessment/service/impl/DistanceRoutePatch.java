package za.co.discovery.interstellarassessment.service.impl;

import org.springframework.stereotype.Service;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.dto.UpdateRouteDto;
import za.co.discovery.interstellarassessment.service.RoutePatch;

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
