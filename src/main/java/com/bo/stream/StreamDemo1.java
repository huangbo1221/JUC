package com.bo.stream;

/**
 * @ClassName StreamDemo1
 * @Description TODO
 * @Author 了解流式计算
 * @Date 2021/10/28 8:58
 * @Version 1.0
 */

import java.util.Arrays;
import java.util.Comparator;

/**
 * 题目要求：一分钟内完成此题，只能用一行代码实现
 * 现在有六个用户需要筛选
 * 1、ID必须是偶数
 * 2、年龄必须大于23岁
 * 3、用户名转为大写字母
 * 4、用户名字母倒着排序
 * 5、只输出一个用户
 *
 */
public class StreamDemo1 {
    public static void main(String[] args) {
        User a = new User(1, 21, "a");
        User b = new User(2, 22, "b");
        User c = new User(3, 23, "c");
        User d = new User(4, 24, "d");
        User e = new User(5, 25, "e");
        User f = new User(6, 26, "f");

        // 需要详细分析下流式计算，每一个都用到了函数式接口，这也叫链式编程
        Arrays.asList(a, b, c, d, e, f).stream()
            .filter(user -> {return user.getId() % 2 == 0;})
            .filter(user -> {return user.getAge() > 23;})
            .map(user -> {return user.getName().toUpperCase();})
            .sorted((u1, u2) -> {return u2.compareTo(u1);})
            .limit(1)
            .forEach(System.out::println);
        // 最终输出F
    }
}
