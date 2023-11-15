package za.co.discovery.interstellarassessment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.repository.RouteRepository;
import za.co.discovery.interstellarassessment.service.RouteService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DefaultRouteService implements RouteService {

    private final RouteRepository repository;

    @Override
    public void create(RouteDto route) {
        repository.save(new RouteBo(route.getOrigin(), route.getDestination(), route.getDistance()));
    }

    @Override
    public List<RouteDto> findAll() {
        return repository.findAll()
                .stream().map(routeBo -> new RouteDto(routeBo.getOrigin(),
                        routeBo.getDestination(), routeBo.getDistance()))
                .collect(Collectors.toList());
    }
}
