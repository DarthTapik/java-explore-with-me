package ru.yandex.practicum.stats.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.stats.dto.EndpointHitDto;
import ru.yandex.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EndpointHitMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHit dtoToEndpointHit(EndpointHitDto dto) {

        LocalDateTime dateTime = LocalDateTime.parse(dto.getTimestamp(), formatter);
        return EndpointHit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(dateTime)
                .build();
    }
}
