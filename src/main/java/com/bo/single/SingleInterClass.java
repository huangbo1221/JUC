package com.bo.single;

/**
 * @ClassName SingleInterClass
 * @Description 静态内部类实现的单例模式
 * @Author huangbo1221
 * @Date 2021/11/3 23:01
 * @Version 1.0
 */
public class SingleInterClass {

    private SingleInterClass() {

    }

    public static SingleInterClass getInstance() {
        return InterClass.SINGLE_INTER_CLASS;
    }

    public static class InterClass {
        private final static SingleInterClass SINGLE_INTER_CLASS = new SingleInterClass();
    }

}
