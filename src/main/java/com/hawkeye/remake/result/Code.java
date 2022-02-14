package com.hawkeye.remake.result;

/**
 * @description 结果代码
 */

public enum Code {
    /**
     * 成功
     */
    SUCCESS(20000),
    /**
     * 失败
     */
    FAILED(60204);
    private int code;
    Code(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }

}
