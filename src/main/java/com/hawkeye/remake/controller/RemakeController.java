package com.hawkeye.remake.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hawkeye.remake.entity.CountAndPercent;
import com.hawkeye.remake.entity.ResultDto;
import com.hawkeye.remake.entity.Segment;
import com.hawkeye.remake.result.Result;
import com.hawkeye.remake.util.RemakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class RemakeController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping(value = "/remake",method = RequestMethod.GET)
    public Result<Map<String,Object>> remake(){
        StpUtil.login(LocalDateTime.now().toString());
        Map<String, Object> result = new HashMap<>();
        String token = StpUtil.getTokenValue();
        if(token != null) {
            result.put("token",token);
            return Result.success(result);
        }else {
            result.put("message","failed!");
            return Result.failed(result);
        }
    }

    private final Random random = new Random();

    @RequestMapping(value = "/start",method = RequestMethod.GET)
    public Result<Map<String,Object>> start(String token){
        Map<String, Object> result = new HashMap<>();
        ValueOperations<String, String> operations = this.stringRedisTemplate.opsForValue();
        int i = random.nextInt(RemakeUtil.pre);
        String name = null;
        //计算本次重开的国家
        if (i < RemakeUtil.pre) {
            for (Segment s : RemakeUtil.segments) {
                if (s.inThisSegment(i)) {
                    name = RemakeUtil.map.get(s);
                }
            }
        }
        //i == pre
        else {
            name = RemakeUtil.map.get(RemakeUtil.segments.get(RemakeUtil.segments.size() - 1));
        }
        //查看是否是第一次重开
        String v = operations.get(token);
        //第一次重开
        if(v == null){
            operations.set(token+name,"1",60*60*24, TimeUnit.SECONDS);
            operations.set(token,"1",60*60*24, TimeUnit.SECONDS);
            result.put("country",name);
            result.put("total",1);
        }else{
            //非第一次重开
            //当前国家是第一次重开到
            if(operations.get(token+name) == null){
                //设置为1次
                operations.set(token+name,"1",60*60*24, TimeUnit.SECONDS);
            }else{
                //若不是第一次重开到 自增1
                operations.increment(token+name);
            }
            //总重开次数+1
            operations.increment(token);
            result.put("country",name);
            result.put("total",operations.get(token));
        }
        return Result.success(result);
    }
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    public Result<List<ResultDto>> result(String token){
        ValueOperations<String, String> operations = this.stringRedisTemplate.opsForValue();
        List<ResultDto> res = new ArrayList<>();
        // *号 必须要加，否则无法模糊查询
        Set<String> keys = this.stringRedisTemplate.keys(token + "*");
        for(String k : Objects.requireNonNull(keys)){
            if(!token.equals(k)){
                int len = token.length();
                String country = k.substring(len);
                //根据token获得的是全部重开次数
                int total = Integer.parseInt(Objects.requireNonNull(operations.get(token)));
                //根据token+country获得country的重开次数
                int count = Integer.parseInt(Objects.requireNonNull(operations.get(k)));
                double percent = (double)count/total;
                DecimalFormat df = new DecimalFormat("0.000");
                String per = df.format(BigDecimal.valueOf(percent));
                ResultDto dto = new ResultDto(country,new CountAndPercent(count,per));
                res.add(dto);
            }
        }
        return Result.success(res);
    }
}
