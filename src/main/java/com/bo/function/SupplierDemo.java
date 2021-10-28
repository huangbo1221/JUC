package com.bo.function;

import java.util.function.Supplier;

/**
 * @ClassName SupplierDemo
 * @Description 供给型函数式接口
 * @Author huangbo1221
 * @Date 2021/10/28 8:14
 * @Version 1.0
 */
public class SupplierDemo {
    public static void main(String[] args) {
        test1();
        test2();
        // 输出如下：
        /**
         * huangbo1221
         * liubo1221
         */
    }

    public static void test1() {
        Supplier<String> supplier = new Supplier<String>() {

            @Override
            public String get() {
                return "huangbo1221";
            }
        };
        System.out.println(supplier.get());
    }

    public static void test2() {
        Supplier<String> supplier = () -> {return "liubo1221";};
        System.out.println(supplier.get());
    }
}
