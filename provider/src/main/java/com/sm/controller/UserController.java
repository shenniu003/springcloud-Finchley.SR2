package com.sm.controller;

import com.sm.component.JedisCom;
import com.sm.dao.TeacherRepository;
import com.sm.domain.MoTeacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2019/1/7.
 */
@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${server.port}")
    private int Port;

    @Autowired
    JedisCom jedisCom;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/createIndex")
    public MoTeacher createIndex() {
        boolean isCreate = elasticsearchTemplate.createIndex(MoTeacher.class);
        System.out.println("创建index：" + isCreate);

        MoTeacher moTeacher = new MoTeacher();
//        moTeacher.setId(4L);
        moTeacher.setUserName("shenniu");
        moTeacher.setNickName("神牛");
        moTeacher.setRemak("备注");

        return teacherRepository.save(moTeacher);
    }

    @GetMapping("/delIndex/{id}")
    public String delIndex(@PathVariable String id) {
        return elasticsearchTemplate.delete(MoTeacher.class, id);
    }

    @PostMapping("/uploadphoto")
    public String uploadPhoto(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        String contentType = multipartFile.getContentType();

        Path path = Paths.get("D:\\my_project\\my_test\\logs\\Console_Test\\2019\\2\\", multipartFile.getOriginalFilename());
        Path outPath = Files.write(path,
                multipartFile.getBytes());

        return outPath.toString();
    }

    @GetMapping("/setnx/{key}/{val}")
    public boolean setnx(@PathVariable String key, @PathVariable String val) {
        return jedisCom.setnx(key, val);
    }

    @GetMapping("/delnx/{key}/{val}")
    public int delnx(@PathVariable String key, @PathVariable String val) {
        return jedisCom.delnx(key, val);
    }

    //总库存
    private long nKuCuen = 0;
    //商品key名字
    private String shangpingKey = "computer_key";
    //获取锁的超时时间 秒
    private int timeout = 30 * 1000;

    @GetMapping("/qiangdan")
    public List<String> qiangdan() {

        //抢到商品的用户
        List<String> shopUsers = new ArrayList<>();

        //构造很多用户
        List<String> users = new ArrayList<>();
        IntStream.range(0, 100000).parallel().forEach(b -> {
            users.add("神牛-" + b);
        });

        //初始化库存
        nKuCuen = 10;

        //模拟开抢
        users.parallelStream().forEach(b -> {
            String shopUser = qiang(b);
            if (!StringUtils.isEmpty(shopUser)) {
                shopUsers.add(shopUser);
            }
        });

        return shopUsers;
    }

    /**
     * 模拟抢单动作
     *
     * @param b
     * @return
     */
    private String qiang(String b) {
        //用户开抢时间
        long startTime = System.currentTimeMillis();

        //未抢到的情况下，30秒内继续获取锁
        while ((startTime + timeout) >= System.currentTimeMillis()) {
            //商品是否剩余
            if (nKuCuen <= 0) {
                break;
            }
            if (jedisCom.setnx(shangpingKey, b)) {
                //用户b拿到锁
                logger.info("用户{}拿到锁...", b);
                try {
                    //商品是否剩余
                    if (nKuCuen <= 0) {
                        break;
                    }

                    //模拟生成订单耗时操作，方便查看：神牛-50 多次获取锁记录
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //抢购成功，商品递减，记录用户
                    nKuCuen -= 1;

                    //抢单成功跳出
                    logger.info("用户{}抢单成功跳出...所剩库存：{}", b, nKuCuen);

                    return b + "抢单成功，所剩库存：" + nKuCuen;
                } finally {
                    logger.info("用户{}释放锁...", b);
                    //释放锁
                    jedisCom.delnx(shangpingKey, b);
                }
            } else {
                //用户b没拿到锁，在超时范围内继续请求锁，不需要处理
//                if (b.equals("神牛-50") || b.equals("神牛-69")) {
//                    logger.info("用户{}等待获取锁...", b);
//                }
            }
        }
        return "";
    }

    @GetMapping("/list")
    public List<MoUser> getList() {
        List<MoUser> users = new ArrayList<>();

        IntStream.range(1, 10).forEach(b -> {
            users.add(new MoUser(b, "神牛00" + b + "，端口：" + Port));
        });

        return users;
    }

    class MoUser {

        private int id;
        private String name;

        public MoUser(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

