package com.teamflow.Planus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CalendarStatsDTO {
    private int total;
    private int done;
    private int todo;
    private double rate;
}
