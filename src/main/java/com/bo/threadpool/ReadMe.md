## 掌握三大方法（add/remove、offer/poll、put/take）、线程池七大参数、四种拒绝策略
详细请看BlockQueueTest.java和ThreadPoolExecutorDemo.java

## Executors创建线程池方式的底层源码分析
### Executors.newSingleThreadExecutor()
```java
public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }
```
实际上还是采用了ThreadPoolExecutor来创建线程

### Executors.newFixedThreadPool(5);
```java
public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
```
实际上也是采用了ThreadPoolExecutor来创建线程

### Executors.newCachedThreadPool();
```java
public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }
```
可知，还是ThreadPoolExecutor来创建线程

## ThreadPoolExecutor来创建线程的七大参数
```java
public ThreadPoolExecutor(int corePoolSize,// 核心线程数量，就像联合国里的常任理事国一样！
                              int maximumPoolSize,// 最大线程数量
                              long keepAliveTime,// 超时了没人调用就会释放线程的时间，不会释放核心线程
                              TimeUnit unit,// 超时单位
                              BlockingQueue<Runnable> workQueue,// 阻塞队列
                              ThreadFactory threadFactory,// 线程工厂，创建线程的，一般不用动
                              RejectedExecutionHandler handler // 拒绝策略) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.acc = System.getSecurityManager() == null ?
                null :
                AccessController.getContext();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
```