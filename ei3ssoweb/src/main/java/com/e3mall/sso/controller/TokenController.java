package com.e3mall.sso.controller;

import com.e3mall.common.utils.E3Result;
import com.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;

	@RequestMapping(value = "/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback){
		E3Result result = tokenService.getUserByToken(token);
		if(!StringUtils.isNoneEmpty(callback)){
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}


}
