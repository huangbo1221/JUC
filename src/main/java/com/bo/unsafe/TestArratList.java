package com.bo.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TestArratList
 * @Description 了解几种解决arraylist并发不安全的方法
 * @Author huangbo1221
 * @Date 2021/9/30 7:55
 * @Version 1.0
 */
public class TestArratList {
    public static void main(String[] args) {
//        List<String> stringList = new ArrayList<>();
        // 上面new了一个ArrayList，可能的输出如下：不安全
        /**
         * final size: 9994
         */

//        List<String> stringList = new Vector<>();
        List<String> stringList = Collections.synchronizedList(new ArrayList<>());
//        List<String> stringList = new CopyOnWriteArrayList<>();
        // 上面三行的写法，可以保证并发修改stringList的安全性
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                stringList.add(UUID.randomUUID().toString().substring(0 ,5));
//                System.out.println(stringList.size());
            }).start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("final size: " + stringList.size());
    }
}
