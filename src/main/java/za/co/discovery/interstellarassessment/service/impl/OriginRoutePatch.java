package za.co.discovery.interstellarassessment.service.impl;

import org.springframework.stereotype.Service;
import za.co.discovery.interstellarassessment.bo.Planet;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.dto.UpdateRouteDto;
import za.co.discovery.interstellarassessment.service.RoutePatch;

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
