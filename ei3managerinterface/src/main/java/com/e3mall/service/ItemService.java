package com.e3mall.service;

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page,int rows);
}
