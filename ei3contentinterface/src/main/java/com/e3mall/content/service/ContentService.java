package com.e3mall.content.service;

import com.e3mall.common.utils.E3Result;
import com.e3mall.pojo.TbContent;

import java.util.List;

public interface ContentService {

	E3Result addContent(TbContent content);

	List<TbContent> getContentListByCid(long cid);
}
