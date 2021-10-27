package com.bo.function;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @ClassName FunctionDemo
 * @Description 再识函数式编程
 * @Author huangbo1221
 * @Date 2021/10/27 23:10
 * @Version 1.0
 */

// 函数式编程的另外例子可看java_learning项目
public class FunctionDemo {
    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1() {
        Function function = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        };
        System.out.println(function.apply("huangbo1221"));
    }

    /**
     * lambda表达式+函数式编程
     */
    public static void test2() {
        Function function = (str) -> {return str;};
        System.out.println(function.apply("liubo1221"));
    }
}
