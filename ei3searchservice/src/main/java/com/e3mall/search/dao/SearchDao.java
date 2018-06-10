package com.e3mall.search.dao;

import com.e3mall.common.pojo.SearchItem;
import com.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {

	@Autowired
	private SolrServer solrServer;

	public SearchResult search(SolrQuery query)throws Exception{
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		long numFound = solrDocumentList.getNumFound();
		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		Map<String,Map<String,List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for(SolrDocument solrDocument:solrDocumentList){
			SearchItem item = new SearchItem();
			item.setId(solrDocument.get("id").toString());
			item.setCategory_name(solrDocument.get("item_category_name").toString());
			item.setImage(solrDocument.get("item_image").toString());
			String inStr = solrDocument.get("item_price").toString();
			String outStr = inStr.substring(1,inStr.length()-1);
			item.setPrice(Long.valueOf(outStr));
			item.setSell_point(solrDocument.get("item_sell_point")==null?"":solrDocument.get("item_sell_point").toString());
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list!=null&&list.size()>0){
				title = list.get(0);
			}else{
				title = (String)solrDocument.get("item_title");
			}
			item.setTitle(title);
			itemList.add(item);
		}
		result.setItemList(itemList);
		return result;
	}
}
