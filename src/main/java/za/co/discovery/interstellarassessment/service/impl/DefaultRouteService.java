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

/**
 * FIXME - logging of services upon entry and exit with {@link Slf4j}
 * <p></p>
 * {@link org.springframework.transaction.annotation.Transactional decorator not neccesary as persistence is on only one entity}
 */
@RequiredArgsConstructor
@Service
public class DefaultRouteService implements RouteService {

    private final RouteRepository repository;
    private final List<RoutePatch> routePatch;

    @Override
    public void create(final RouteDto route) {
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
    public RouteDto findById(final Long routeId) {
        Optional<RouteBo> routeBoOptional = getRoute(routeId);
        return mapRoute(routeBoOptional.get());
    }

    @Override
    public void update(final Long routeId, final UpdateRouteDto routeDto) {
        Optional<RouteBo> routeBoOptional = getRoute(routeId);
        RouteBo routeBo = routeBoOptional.get();
        routePatch.forEach(p -> p.patch(routeDto, routeBo));
        repository.save(routeBo);
    }

    @Override
    public void delete(final Long routeId) throws RouteNotFoundException {
        getRoute(routeId).ifPresent(repository::delete);
    }

    /**
     * Very simple business object to dto mapper
     * For fields more than 5, using reflection or mapping libraries would be smarter ;)
     * <p></p>
     *
     * @param routeBo route business object in managed state
     * @return dto of routebo
     */
    private RouteDto mapRoute(final RouteBo routeBo) {
        return new RouteDto(routeBo.getOrigin(), routeBo.getDestination(), routeBo.getDistance());
    }

    /**
     * Logic reused by 3 other services
     * <p></p>
     *
     * @param routeId
     * @return
     */
    private Optional<RouteBo> getRoute(Long routeId) {
        Optional<RouteBo> routeBoOptional = repository.findById(routeId);
        if (routeBoOptional.isEmpty()) throw routeNotFoundException(String.format("route id %d not found", routeId));
        return routeBoOptional;
    }

    private RouteNotFoundException routeNotFoundException(String routeId) {
        return new RouteNotFoundException(routeId);
    }


}
