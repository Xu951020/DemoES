package com.jd.ESdemo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

public class ESTest_Doc_Query1 {
    public static void main(String[] args) throws IOException {
    
        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
    
        /*//1.组合查询
        SearchRequest request = new SearchRequest();
        request.indices("user");
    
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        
        //在这里对组合组件进行配置
        boolQueryBuilder.must(QueryBuilders.matchQuery("age",18));
        boolQueryBuilder.must(QueryBuilders.matchQuery("sex","nan"));
        
        builder.query(boolQueryBuilder);
        builder.sort("age", SortOrder.ASC);
        request.source(builder);
    
        SearchResponse respons = esClient.search(request, RequestOptions.DEFAULT);
    
        SearchHits hits = respons.getHits();
    
        System.out.println(hits.getTotalHits());
        System.out.println(respons.getTook());
        for(SearchHit hit : hits){
            System.out.println(hit.getSourceAsString());
        }*/
        
        /*//2.范围查询
        SearchRequest request = new SearchRequest();
        request.indices("user");
    
        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
    
        //配置范围
        rangeQuery.gte(18);//大于等于30
        rangeQuery.lte(30);//小于等于30
    
        builder.query(rangeQuery);
        request.source(builder);
    
        SearchResponse respons = esClient.search(request, RequestOptions.DEFAULT);
    
        SearchHits hits = respons.getHits();
    
        System.out.println(hits.getTotalHits());
        System.out.println(respons.getTook());
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        
        //8.模糊查询
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //配置的是名字字段，差几个字符可以被查询到
        SearchSourceBuilder builder = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzziness = QueryBuilders.fuzzyQuery("name", "zhangsa").fuzziness(Fuzziness.ONE);
    
    
        builder.query(fuzziness);
        request.source(builder);
    
        SearchResponse respons = esClient.search(request, RequestOptions.DEFAULT);
    
        SearchHits hits = respons.getHits();
    
        System.out.println(hits.getTotalHits());
        System.out.println(respons.getTook());
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        
        //关闭客户端
        esClient.close();
    
    }
}
