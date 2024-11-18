package ru.yandex.practicum.stats.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.stats.dto.StatsDto;
import ru.yandex.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.yandex.practicum.stats.dto.StatsDto(e.app, e.uri, COUNT(e.ip)) " +
            "FROM Endpointhits e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<StatsDto> getStatsForAll(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new ru.yandex.practicum.stats.dto.StatsDto(e.app, e.uri, COUNT(e.ip)) " +
            "FROM Endpointhits e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND e.uri in :uri " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<StatsDto> getStats(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uri") List<String> uri);

    @Query("SELECT new ru.yandex.practicum.stats.dto.StatsDto(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
            "FROM Endpointhits e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(DISTINCT e.ip) DESC")
    List<StatsDto> getStatsForAllUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new ru.yandex.practicum.stats.dto.StatsDto(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
            "FROM Endpointhits e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND e.uri in :uri " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(DISTINCT e.ip) DESC")
    List<StatsDto> getStatsUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uri") List<String> uri);
}