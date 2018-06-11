package com.e3mall.sso.service.impl;

import com.e3mall.common.utils.E3Result;
import com.e3mall.mapper.TbUserMapper;
import com.e3mall.pojo.TbUser;
import com.e3mall.pojo.TbUserExample;
import com.e3mall.sso.service.RegisterService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;

	@Override
	public E3Result checkData(String param, int type) {
		TbUserExample example = new TbUserExample();
		TbUserExample.Criteria criteria = example.createCriteria();
		if(type==1){
			criteria.andUsernameEqualTo(param);
		}else if(type==2){
			criteria.andPhoneEqualTo(param);
		}else if(type==3){
			criteria.andEmailEqualTo(param);
		}else{
			return E3Result.build(400,"数据类型错误");
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}

	@Override
	public E3Result register(TbUser user) {
		if(StringUtils.isNullOrEmpty(user.getUsername())
				||StringUtils.isNullOrEmpty(user.getPassword())||StringUtils.isNullOrEmpty(user.getPhone())){
			return E3Result.build(400,"用户数据不完整，注册失败");
		}
		E3Result result = checkData(user.getUsername(),1);
		if(!(boolean)result.getData()){
			return E3Result.build(400,"此用户名已经被占用");
		}
		result = checkData(user.getPhone(),2);
		if(!(boolean)result.getData()){
			return E3Result.build(400,"手机号已经被占用");
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		userMapper.insert(user);
		return E3Result.ok();
	}
}
