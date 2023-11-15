package za.co.discovery.interstellarassessment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.discovery.interstellarassessment.bo.RouteBo;
import za.co.discovery.interstellarassessment.dto.RouteDto;
import za.co.discovery.interstellarassessment.dto.UpdateRouteDto;
import za.co.discovery.interstellarassessment.exception.RouteNotFoundException;
import za.co.discovery.interstellarassessment.repository.RouteRepository;
import za.co.discovery.interstellarassessment.service.RoutePatch;
import za.co.discovery.interstellarassessment.service.RouteService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DefaultRouteService implements RouteService {

    private final RouteRepository repository;
    private final List<RoutePatch> routePatch;

    @Override
    public void create(RouteDto route) {
        repository.save(new RouteBo(route.getOrigin(), route.getDestination(), route.getDistance()));
    }

    @Override
    public List<RouteDto> findAll() {
        return repository.findAll()
                .stream()
                .map(routeBo -> mapRoute(routeBo))
                .collect(Collectors.toList());
    }

    @Override
    public RouteDto findById(Long routeId) {
        return repository.findById(routeId)
                .map(routeBo -> mapRoute(routeBo))
                .orElseThrow(() -> getRouteNotFoundException(String.format("route id %d not found", routeId)));
    }

    @Override
    public void update(Long routeId, UpdateRouteDto routeDto) {
        Optional<RouteBo> routeBoOptional = repository.findById(routeId);
        if (routeBoOptional.isEmpty()) throw getRouteNotFoundException(String.format("route id %d not found", routeId));

        RouteBo routeBo = routeBoOptional.get();
        routePatch.forEach(p -> p.patch(routeDto, routeBo));
        repository.save(routeBo);
    }

    private RouteDto mapRoute(RouteBo routeBo) {
        return new RouteDto(routeBo.getOrigin(), routeBo.getDestination(), routeBo.getDistance());
    }

    private RouteNotFoundException getRouteNotFoundException(String routeId) {
        return new RouteNotFoundException(routeId);
    }


}
