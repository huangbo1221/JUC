package com.bo.unsafemap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TestMap
 * @Description TODO
 * @Author huangbo1221
 * @Date 2021/10/11 8:23
 * @Version 1.0
 */
public class TestMap {
    public static void main(String[] args) {
//        Map<String, String> maps = new HashMap<>();
        // 如果是hashmap，下面的输出为     说明有并发异常
        // 9996

//        Map<String, String> maps = Collections.synchronizedMap(new HashMap<>());
        // 输出为
        // 10000

        Map<String, String> maps = new ConcurrentHashMap<>();
        // 输出为
        // 10000
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                maps.put(Thread.currentThread().getName(), UUID.randomUUID().toString());
            }, String.valueOf(i)).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(maps.size());
    }
}
