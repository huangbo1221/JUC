package com.bo.single;

/**
 * @ClassName EnumDemo1
 * @Description 利用枚举类来设计单例模式
 * @Author huangbo1221
 * @Date 2021/11/4 8:23
 * @Version 1.0
 */
public enum EnumDemo1 {
    INSTANCE;

    public static EnumDemo1 getInstance() {
        return INSTANCE;
    }
}
