package ru.yandex.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.stats.dto.EndpointHitDto;
import ru.yandex.practicum.stats.dto.StatsDto;
import ru.yandex.practicum.stats.mapper.EndpointHitMapper;
import ru.yandex.practicum.stats.model.EndpointHit;
import ru.yandex.practicum.stats.storage.StatsStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final StatsStorage statsStorage;
    private final EndpointHitMapper endpointHitMapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public void endpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.dtoToEndpointHit(endpointHitDto);
        statsStorage.addHit(endpointHit);
    }

    public List<StatsDto> getStats(String start, String end, List<String> uris, String unique) {

        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);

        boolean uniqueBool = Boolean.valueOf(unique);

        ArrayList statsDto;

        if (uris == null || uris.isEmpty()) {
            statsDto = new ArrayList(statsStorage.getStatsForAll(startTime, endTime, uniqueBool));
        } else {
            statsDto = new ArrayList(statsStorage.getStats(startTime, endTime, uris, uniqueBool));
        }
        return statsDto;
    }
}
