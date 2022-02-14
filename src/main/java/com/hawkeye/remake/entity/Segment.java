package com.hawkeye.remake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Segment {
    private int begin;

    private int end;

    public boolean inThisSegment(int d) {
        if (d >= begin && d < end) {
            return true;
        } else {
            return false;
        }
    }
}
