package ru.yandex.practicum.stats.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yandex.practicum.stats.dto.EndpointHitDto;
import ru.yandex.practicum.stats.dto.StatsDto;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class StatsClient {

    private final RestTemplate restTemplate;

    @Value("${stats-server.url}")
    private String statsServerUrl;

    public StatsClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void hit(EndpointHitDto hit) {
        URI uri = UriComponentsBuilder.fromHttpUrl(statsServerUrl + "/hit")
                .build()
                .toUri();
        restTemplate.postForEntity(uri, hit, Void.class);
    }

    public List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        URI uri = UriComponentsBuilder.fromHttpUrl(statsServerUrl + "/stats")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("unique", unique)
                .build()
                .toUri();

        if (uris != null && !uris.isEmpty()) {
            uri = UriComponentsBuilder.fromUri(uri)
                    .queryParam("uris", String.join(",", uris))
                    .build()
                    .toUri();
        }

        ResponseEntity<StatsDto[]> response = restTemplate.getForEntity(uri, StatsDto[].class);
        if (response.getBody() != null) {
            return Arrays.asList(response.getBody());
        } else {
            return Collections.emptyList();
        }
    }
}