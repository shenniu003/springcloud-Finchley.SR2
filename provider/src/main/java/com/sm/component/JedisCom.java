package com.sm.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Administrator on 2019/3/27.
 */
@Component
public class JedisCom {
//
//    @Bean
//    public JedisCluster getJedisCluster() {
//
//        Set<HostAndPort> nodes = new HashSet<>();
//        nodes.add(new HostAndPort("127.0.0.1", 6378));
//        JedisCluster jedisCluster = new JedisCluster(nodes);
//        return jedisCluster;
//    }

    @Bean
    public JedisPool getJedisPool() {
        return new JedisPool("127.0.0.1", 6378);
    }

    @Autowired
    JedisPool jedisPool;

//    @Autowired
//    JedisCluster jedisCluster;

    public boolean setnx(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis == null) {
                return false;
            }
            return jedis.set(key, val, "NX", "PX", 1000 * 60).
                    equalsIgnoreCase("ok");
        } catch (Exception ex) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public int delnx(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis == null) {
                return 0;
            }

            //if redis.call('get','orderkey')=='1111' then return redis.call('del','orderkey') else return 0 end
            StringBuilder sbScript = new StringBuilder();
            sbScript.append("if redis.call('get','").append(key).append("')").append("=='").append(val).append("'").
                    append(" then ").
                    append("    return redis.call('del','").append(key).append("')").
                    append(" else ").
                    append("    return 0").
                    append(" end");

            return Integer.valueOf(jedis.eval(sbScript.toString()).toString());
        } catch (Exception ex) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
}
