package com.bo.single;

/**
 * @ClassName HungryDemo1
 * @Description 饿汉式单例模式
 * @Author huangbo1221
 * @Date 2021/11/3 22:15
 * @Version 1.0
 */
public class HungryDemo1 {

    // 单例模式的构造方法必须私有！
    private HungryDemo1() {

    }

    private final static HungryDemo1 HUNGRYDEMO1 = new HungryDemo1();

    public static HungryDemo1 getInstance() {
        return HUNGRYDEMO1;
    }

}
