package com.sm.schduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

/**
 * Created by Administrator on 2019/4/10.
 */
@EnableScheduling
@Component
public class MyTask {

    @Autowired
    RestTemplate restTemplate;

//    @Scheduled(fixedRate = 1000)
    public void postLog() {

        IntStream.range(0, 200).parallel().forEach(b -> {

            StringBuilder sbLog = new StringBuilder();

            sbLog.append("第").append(b).append("：");
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity<String> entity = new HttpEntity<>("{\"orderName\":0,\"commodityType\":\"01\",\"orderStyle\":\"desc\",\"keyWord\":\"\",\"brandName\":\"\",\"auditStatus\":\"0\",\"payStatus\":\"\",\"regionID\":\"\",\"subID\":\"\",\"orderType\":\"01\",\"firstSalesType\":\"\",\"transferType\":\"\",\"dataIsCancel\":\"\",\"isOutStock\":\"\",\"pageNo\":1,\"pageSize\":20,\"startSize\":0,\"sessionId\":\"UsedCar.YiXin_UserToken_zymddz\",\"requestSource\":\"2\",\"bundleID\":\"com.hckk.mendianERP\",\"requestGroupID\":\"0\",\"loginUserID\":\"zymddz\",\"clientIP\":\"192.168.181.12\",\"endDeviceNumber\":\"6B9CB460-D6B1-40DB-B947-662338B2F8C6\",\"cityName\":\"\"}",
                        headers);

                String result = restTemplate.postForObject("http://uatmendian-api.taoche.com/api/Turnover/GetMobileTurnoverLists",
                        entity,
                        String.class);

                sbLog.append("返回：").append(result.contains("\"code\":0"));

            } catch (Exception ex) {
                sbLog.append("异常：").append(ex.getMessage());
            } finally {
                sbLog.append("\r\n");
                System.out.println(sbLog);
            }
        });
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

}
