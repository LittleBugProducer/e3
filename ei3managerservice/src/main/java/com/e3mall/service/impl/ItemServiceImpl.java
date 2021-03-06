package com.e3mall.service.impl;

import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.utils.E3Result;
import com.e3mall.common.utils.IDUtils;
import com.e3mall.common.utils.JsonUtils;
import com.e3mall.mapper.TbItemDescMapper;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.pojo.TbItemExample;
import com.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemdescMapper;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;

	@Value("${ITEM_CACHE_EXPIRE}")
	private Integer ITEM_CACHE_EXPIRE;

	@Resource
	private Destination topicDestination;

	@Override
	public TbItem getItemById(long itemId) {
		try{
			String json = jedisClient.get(REDIS_ITEM_PRE+":"+itemId+":BASE");
			if(!StringUtils.isNullOrEmpty(json)){
				TbItem item = JsonUtils.jsonToPojo(json,TbItem.class);
				return item;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			TbItem item = list.get(0);
			try{
				jedisClient.set(REDIS_ITEM_PRE+":"+itemId+":BASE",JsonUtils.objectToJson(item));
				jedisClient.expire(REDIS_ITEM_PRE+":"+itemId+":BASE",ITEM_CACHE_EXPIRE);

			}catch (Exception e){
				e.printStackTrace();
			}
			return item;
		}
		return null;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {

		PageHelper.startPage(page,rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte)1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemdescMapper.insert(itemDesc);

		//发送一个商品添加消息
		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId+"");
				return textMessage;
			}
		});
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		try{
			String json = jedisClient.get(REDIS_ITEM_PRE+":"+itemId+":DESC");
			if(!StringUtils.isNullOrEmpty(json)){
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json,TbItemDesc.class);
				return itemDesc;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemdescMapper.selectByPrimaryKey(itemId);
		try{
			jedisClient.set(REDIS_ITEM_PRE+":"+itemId+":DESC",JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(REDIS_ITEM_PRE+":"+itemId+":DESC",ITEM_CACHE_EXPIRE);

		}catch (Exception e){
			e.printStackTrace();
		}
		return itemDesc;
	}
}
