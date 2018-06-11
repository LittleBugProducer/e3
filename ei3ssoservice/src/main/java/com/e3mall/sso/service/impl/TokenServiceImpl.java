package com.e3mall.sso.service.impl;

import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.utils.E3Result;
import com.e3mall.common.utils.JsonUtils;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.service.TokenService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;

	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	@Override
	public E3Result getUserByToken(String token) {
		String json = jedisClient.get("SESSION:"+token);
		if(StringUtils.isNullOrEmpty(json)){
			return E3Result.build(20,"用户登陆已经过期");
		}
		jedisClient.expire("SESSION"+token,SESSION_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(json,TbUser.class);
		return E3Result.ok(user);
	}
}
