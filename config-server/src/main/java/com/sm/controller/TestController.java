package com.sm.controller;

import com.sm.domain.MoSearch;
import es.EsRestHighLevelClient;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import util.PropertiesHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2019/4/22.
 */
@RestController
public class TestController {

    static Logger logger = Logger.getLogger(TestController.class);
    ThreadLocal<Integer> local = new ThreadLocal();

    @GetMapping("/testLatch/{num}")
    public String testLatch(@PathVariable int num) throws InterruptedException {

        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < num; i++) {
            new Thread(new MyRun(i, semaphore)).start();
        }

        System.out.println("已结束...");

//        CountDownLatch countDownLatch = new CountDownLatch(num);
//
//        ExecutorService executor = Executors.newFixedThreadPool(num);
//        IntStream.range(0, num).forEach(b -> {
//            executor.execute(() -> {
//                try {
//                    System.out.println(new Date() + "start:" + b);
//                    TimeUnit.SECONDS.sleep(b);
//                    System.out.println(new Date() + "end:" + b);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
////                    countDownLatch.countDown();
//                }
//            });
//        });
//
//        System.out.println("等待结束...");
//        countDownLatch.await(2, TimeUnit.SECONDS);
//        System.out.println("已结束...");

        return "";
    }

    @GetMapping("/hello/{nickname}")
    public String getHello(@PathVariable String nickname) {
        String str = String.format("你好，%s", nickname);
        logger.debug(str);
        logger.info(str);
        logger.error(str);
        return str;
    }

    @PostMapping("/list")
    public List<Map<String, Object>> list(@RequestBody MoSearch search) throws ParseException {

        PropertiesHelper propertiesHelper = new PropertiesHelper("eslog.properties");
        //es hosts
        String strHosts = propertiesHelper.getProperty("es.links", "");
        //es日志索引
        String esLogIndex = propertiesHelper.getProperty("es.indexName", "eslog");
        EsRestHighLevelClient es = new EsRestHighLevelClient(strHosts);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> where = new HashMap<String, Object>() {
            {
                put("data", search.getKeyword01());
                put("timeStamp", new HashMap<String, Date>() {
                    {
                        put("start", format.parse(search.getStartDate()));
                        put("end", format.parse(search.getEndDate()));
                    }
                });
            }
        };
        Map<String, Boolean> sortFieldsToAsc = new HashMap<String, Boolean>() {
            {
                put("timeStamp", false);
            }
        };

//        return es.searchIndex(esLogIndex, 0, where);

        return es.searchIndex(esLogIndex, search.getFrom(), search.getSize(),
                where,
                sortFieldsToAsc,
//                "timeStamp,data,level".split(","),
                ArrayUtils.EMPTY_STRING_ARRAY,
                "serverIp".split(","),
//                ArrayUtils.EMPTY_STRING_ARRAY,
                60);
    }

    class MyRun implements Runnable {

        public MyRun(int num) {
            this.num = num;
        }

        private int num;
        private Semaphore semaphore;

        public MyRun(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                this.semaphore.acquire();
                System.out.println(Thread.currentThread() + "-" + this.num + "获取许可");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.semaphore.release();
                System.out.println(Thread.currentThread() +
                        "-" +
                        this.num +
                        "归还许可,剩余可用：" +
                        this.semaphore.availablePermits());
            }
        }
    }
}


