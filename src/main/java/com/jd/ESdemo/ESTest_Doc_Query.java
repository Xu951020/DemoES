package com.jd.ESdemo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

public class ESTest_Doc_Query {
    public static void main(String[] args) throws IOException {
    
        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        
        //条件查询
        SearchRequest request = new SearchRequest();
        request.indices("user");
        
        //配置查询条件,查询年龄18
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age",18)));
    
        SearchResponse respons = esClient.search(request, RequestOptions.DEFAULT);
    
        SearchHits hits = respons.getHits();
    
        System.out.println(hits.getTotalHits());
        System.out.println(respons.getTook());
        for(SearchHit hit : hits){
            System.out.println(hit.getSourceAsString());
        }
    
        //分页查询
        SearchRequest request1 = new SearchRequest();
        request1.indices("user");
    
        //配置查询条件,查询年龄18
        //全量查询，配置显示第一页，每页一条数据
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.from(0);
        builder.size(1);
        request1.source(builder);
    
        SearchResponse respons1 = esClient.search(request1, RequestOptions.DEFAULT);
    
        SearchHits hits1 = respons1.getHits();
    
        System.out.println(hits.getTotalHits());
        System.out.println(respons.getTook());
        for(SearchHit hit : hits1){
            System.out.println(hit.getSourceAsString());
        }
    
        //过滤字段用的方法
        //String exclude = {};   不看的字段
        //String include = {};    只看的字段
        //builder.fetchSource(include,exclude);
        
        //查询排序
        SearchRequest request2 = new SearchRequest();
        request2.indices("user");
        
        SearchSourceBuilder builder1 = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder1.sort("age", SortOrder.ASC);
        request2.source(builder1);
    
        SearchResponse respons2 = esClient.search(request2, RequestOptions.DEFAULT);
    
        SearchHits hits2 = respons2.getHits();
    
        System.out.println(hits2.getTotalHits());
        System.out.println(respons2.getTook());
        for(SearchHit hit : hits2){
            System.out.println(hit.getSourceAsString());
        }
    
    
        //关闭客户端
        esClient.close();
    
    }
}
