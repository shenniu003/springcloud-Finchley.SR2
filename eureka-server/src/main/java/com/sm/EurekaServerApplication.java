package com.sm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(EurekaServerApplication.class, args);

//        SpringApplication springApplication = new SpringApplication(EurekaServerApplication.class);
//        springApplication.setBannerMode(Banner.Mode.OFF);
//        springApplication.setWebApplicationType(WebApplicationType.SERVLET);
//        springApplication.run(args);


//        ExecutorService executorService = new ThreadPoolExecutor(
//                2,
//                2,
//                10,
//                TimeUnit.MINUTES,
//                new LinkedBlockingDeque<>(),
//                new ThreadPoolExecutor.AbortPolicy());
//
//        executorService.execute(() -> {
//            System.out.println("线程池:" + System.currentTimeMillis());
//        });
//
//        executorService.shutdown();
//
//        ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(2);
//        executor1.scheduleWithFixedDelay(() -> {
//            System.out.println("定时线程:" + System.currentTimeMillis());
//        }, 30, 30, TimeUnit.SECONDS);
//        executor1.shutdown();
//
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//
//        threadLocal.set("nihao");
//
//        System.out.println(threadLocal.get());
//
//        Lock lock = new ReentrantLock();
//
//        if (lock.tryLock()) {
//            try {
//                System.out.println("测试呢。。。");
//            } catch (Exception ex) {
//
//            } finally {
//                lock.unlock();
//            }
//        }
//
//        List<String> list = Arrays.asList("1Geeks", "2FOR", "3GEEKSQUIZ",
//                "4Computer", "5Science", "6gfg");
//
//        list.parallelStream().map(b -> {
//            return b.toUpperCase();
//        }).forEach(b -> {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
//
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(format.format(System.currentTimeMillis()) + ":" + StringUtils.lowerCase(b));
//        });
//
//
//        //队列
//        LinkedBlockingQueue queue = new LinkedBlockingQueue(2);
//
//        Executors.newSingleThreadExecutor().execute(() -> {
//            for (int i = 0; i < 100; i++) {
//                try {
//                    queue.put(i);
//                    System.out.println("入列：" + i);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        for (; ; ) {
//            System.out.println("出列:" + queue.take());
//            TimeUnit.SECONDS.sleep(5);
//        }

//        IntStream.range(1, 10).forEach(b -> {
//            queue.offer(b);
//        });

//        System.out.println("长度：" + queue.size());
//        System.out.println("消费：" + queue.peek());
//        System.out.println("长度：" + queue.size());
//
//        for (; ; ) {
//            if (queue.isEmpty()) {
//                break;
//            }
//            System.out.println("消费：" + queue.poll());
//        }
//        System.out.println("长度：" + queue.size());
//
//        System.out.println("是否空" + StringUtils.isNotBlank("张帅"));
//        System.out.println("是否空" + StringUtils.equalsIgnoreCase("wangyiming", "WangyiMing"));
//
//        Test t = new Test01();
//
//        Executors.newScheduledThreadPool(1, Executors.defaultThreadFactory());
//
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                2,
//                2,
//                1000,
//                TimeUnit.SECONDS,
//                new LinkedBlockingQueue(),
//                new ThreadPoolExecutor.AbortPolicy());
//
//
//        executor.execute(() -> {
//            System.out.println("execute执行");
//        });
//
//        executor.submit(() -> {
//            System.out.println("submit执行");
//        });
//        executor.shutdown();
    }
}

class Test {

    static int sort = 7;

    static {
        System.out.println(2);
        System.out.println(sort);
    }

    Test() {
        System.out.println(1);
    }

    static void xx() {
        System.out.println(3);
    }
}

class Test01 extends Test {

    static {
        System.out.println(4);
    }

    Test01() {
        System.out.println(5);
    }

    static void xx() {
        System.out.println(6);
    }
}


