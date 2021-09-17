# 进程和线程

## java默认有几个线程？

* 默认两个线程：main线程和GC线程，即最少两个线程

## 线程有几种状态？
可以去看Thread.State的源码，总共有六种状态：

public enum State {

NEW,

RUNNABLE,

BLOCKED,

WAITING,

TIMED_WAITING,

TERMINATED;

}

## wait和sleep的区别？
* wait来自于Object类，sleep来自于Thread类；
* wait会释放锁，sleep不会释放锁；
* wait必须用在同步代码块中，而sleep可以用在任何地方；
* wait不需要捕获异常，而sleep必须捕获异常（可能存在超时等待）
