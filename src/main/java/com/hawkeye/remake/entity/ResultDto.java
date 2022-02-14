package com.hawkeye.remake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private String country;

    private CountAndPercent countAndPercent;
}
