package com.chen4du.search.service.solrtest;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {

	@Test
	public void addDocument()throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.136:8080/solr/coll");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id","啊啊啊");
		document.addField("item_title","啊啊啊");
		document.addField("item_price","199");
		solrServer.add(document);
		solrServer.commit();
	}

	@Test
	public void deleteDocumentByQuery()throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.136:8080/solr/coll");
		solrServer.deleteById("2b4c5f0a-6141-430d-94c2-09e6ff0898c2");
		solrServer.commit();

	}

	@Test
	public void queryDocument() throws  Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.136:8080/solr/coll");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse response = solrServer.query(query);
		SolrDocumentList solrDocuments = response.getResults();
		System.out.println("查询结果的记录总数:"+solrDocuments.getNumFound());
		for(SolrDocument solrDocument:solrDocuments){
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));

		}
	}
}
