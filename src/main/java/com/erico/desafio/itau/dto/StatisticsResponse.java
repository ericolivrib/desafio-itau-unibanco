package com.erico.desafio.itau.dto;

import java.util.DoubleSummaryStatistics;

public record StatisticsResponse(
  long count,
  double sum,
  double avg,
  double min,
  double max
) implements StatisticsResponseSpec {

  public StatisticsResponse(DoubleSummaryStatistics statistics) {
    this(statistics.getCount(), statistics.getSum(), statistics.getAverage(),
      statistics.getMin(), statistics.getMax());
  }
}
