package com.onurcelik.readingisgood.statistics.service;

import com.onurcelik.readingisgood.statistics.dto.StatisticsOutput;

import java.util.List;

public interface StatisticsService {

    List<StatisticsOutput> getStatistics();
}
