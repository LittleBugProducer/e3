package com.e3mall.sso.service;

import com.e3mall.common.utils.E3Result;

public interface LoginService {

	E3Result userLogin(String username,String password);
}
