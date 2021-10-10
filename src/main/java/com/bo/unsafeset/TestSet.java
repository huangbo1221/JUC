package com.bo.unsafeset;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TestSet
 * @Description TODO
 * @Author huangbo1221
 * @Date 2021/10/10 18:21
 * @Version 1.0
 */
public class TestSet {

    public static void main(String[] args) {
//        Set<String> sets = new HashSet<>();
//        Set<String> sets = Collections.synchronizedSet(new HashSet<>());
        Set<String> sets = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                sets.add(UUID.randomUUID().toString());
            }, String.valueOf(i)).start();
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sets.size());
    }
}
