package com.jd.ESdemo;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ESTest_Doc_Insert_Batch {
    public static void main(String[] args) throws IOException {
    
        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        
        //批量插入数据
        User user1 = new User();
        user1.setName("zhangsan");
        user1.setAge(18);
        user1.setSex("nan");
        
        User user2 = new User();
        user2.setName("lisi");
        user2.setAge(20);
        user2.setSex("nv");
        
        Object obj1 = JSONArray.toJSON(user1);
        Object obj2 = JSONArray.toJSON(user2);
    
        String JsonUser1 = obj1.toString();
        String JsonUser2 = obj2.toString();
    
        BulkRequest request = new BulkRequest();
        IndexRequest request1 = new IndexRequest();
        request1.index("user").id("1001").source(JsonUser1,XContentType.JSON);
        IndexRequest request2 = new IndexRequest();
        request2.index("user").id("1002").source(JsonUser2,XContentType.JSON);
        request.add(request1);
        request.add(request2);
        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);
    
        System.out.println(response.getTook());
        System.out.println(response.getItems());
    
        //关闭客户端
        esClient.close();
    
    }
}
