package za.co.discovery.interstellarassessment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.repository.RouteRepository;
import za.co.discovery.interstellarassessment.service.RouteService;

@RequiredArgsConstructor
@Service
@Slf4j
public class DefaultRouteService implements RouteService {

    private final RouteRepository repository;

    @Override
    public void create(RouteDto route) {
        repository.save(mapToBo(route));
    }

    /**
     * Function to map dto to business object
     * @param route
     * @return
     */
    private RouteBo mapToBo (final RouteDto route) {
        return new RouteBo(route.getOrigin(), route.getDestination(), route.getDistance());
    }
}
