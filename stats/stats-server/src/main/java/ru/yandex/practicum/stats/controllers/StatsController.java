package ru.yandex.practicum.stats.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.stats.dto.EndpointHitDto;
import ru.yandex.practicum.stats.dto.StatsDto;
import ru.yandex.practicum.stats.service.StatsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.OK)
    public EndpointHitDto hit(@RequestBody EndpointHitDto hit){
        statsService.endpointHit(hit);
        return hit;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<StatsDto> getStats(@RequestParam String start, @RequestParam String end,
                                   @RequestParam(required = false) ArrayList<String> uris,
                                   @RequestParam(defaultValue = "false") String unique){

        return statsService.getStats(start, end, uris, unique);
    }
}
