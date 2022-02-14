package com.hawkeye.remake;

import com.hawkeye.remake.util.RemakeUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RemakeApplication {

    public static void main(String[] args) {
        RemakeUtil.getData();
        SpringApplication.run(RemakeApplication.class, args);
    }

}
