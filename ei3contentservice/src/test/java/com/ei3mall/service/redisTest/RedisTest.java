package com.ei3mall.service.redisTest;

import com.e3mall.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {

	@Test
	public void testJedis()throws Exception{
		Jedis jedis = new Jedis("192.168.25.131",6379);
		jedis.append("hello","123");
		String result = jedis.get("hello");
		System.out.println(result);
	}

	@Test
	public void testJedisPoop()throws Exception{
		JedisPool jedisPool = new JedisPool("192.168.25.131",6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("jedis","test");
		String result = jedis.get("jedis");
		System.out.println(result);
		jedis.close();
		jedisPool.close();
	}

	@Test
	public void testJedisClient()throws Exception{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("first","100");
		String result = jedisClient.get("first");
		System.out.println(result);
	}


}
