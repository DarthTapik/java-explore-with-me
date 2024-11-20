package ru.yandex.practicum.stats.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.stats.dto.StatsDto;
import ru.yandex.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsStorage {

    private final StatsRepository statsRepository;

    public void addHit(EndpointHit endpointHit) {
        statsRepository.save(endpointHit);
    }

    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, boolean unique) {
        if (unique) {
            return statsRepository.getStatsUnique(start, end, uri);
        } else {
            return statsRepository.getStats(start, end, uri);
        }
    }

}