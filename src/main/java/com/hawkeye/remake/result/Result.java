package com.hawkeye.remake.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 返回给前端的结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private T data;
    public static <T> Result<T> success(T data){
        return new Result<T>(Code.SUCCESS.getCode(),data);
    }


    public static <T> Result<T> failed(T data){
        return new Result<T>(Code.FAILED.getCode(),data);
    }

}
