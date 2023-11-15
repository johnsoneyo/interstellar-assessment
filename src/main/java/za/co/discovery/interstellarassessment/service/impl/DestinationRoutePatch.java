package za.co.discovery.interstellarassessment.service.impl;


import org.springframework.stereotype.Service;
import za.co.discovery.interstellarassessment.bo.Planet;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.dto.UpdateRouteDto;
import za.co.discovery.interstellarassessment.service.RoutePatch;

import java.util.Objects;

@Service
public class DestinationRoutePatch implements RoutePatch {

    @Override
    public void patch(UpdateRouteDto routeDto, RouteBo routeBo) {

        Planet destination = routeDto.getDestination();
        if (Objects.nonNull(destination)) {
            routeBo.setDestination(destination);
        }
    }
}
