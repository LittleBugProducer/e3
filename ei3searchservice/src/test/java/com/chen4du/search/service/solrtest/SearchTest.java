package com.chen4du.search.service.solrtest;

import com.e3mall.common.pojo.SearchItem;
import com.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchTest {

	@Test
	public void search() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.136:8080/solr/coll");
		SolrQuery query = new SolrQuery();
		query.setQuery("q34234");
		query.setStart(0);
		query.setRows(60);
		query.set("df","item_title");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		long numFound = solrDocumentList.getNumFound();
		System.out.println(numFound);
		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		Map<String,Map<String,List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for(SolrDocument solrDocument:solrDocumentList){
			SearchItem item = new SearchItem();
			item.setId((String)solrDocument.get("id"));
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
		System.out.println(result);
	}
}
