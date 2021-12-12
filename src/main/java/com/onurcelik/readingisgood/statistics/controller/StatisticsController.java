package com.onurcelik.readingisgood.statistics.controller;

import com.onurcelik.readingisgood.core.dto.ResponseDTO;
import com.onurcelik.readingisgood.statistics.dto.StatisticsOutput;
import com.onurcelik.readingisgood.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Statistics")
@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseDTO<List<StatisticsOutput>> getStatistics() {
        return new ResponseDTO<>(HttpStatus.OK, statisticsService.getStatistics());
    }
}
