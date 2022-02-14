package com.hawkeye.remake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    private String name;

    private double birthRate;

    private double population;
}
