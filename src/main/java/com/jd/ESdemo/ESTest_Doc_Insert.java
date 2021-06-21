package com.jd.ESdemo;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ESTest_Doc_Insert {
    public static void main(String[] args) throws IOException {
    
        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        
        //插入数据
        IndexRequest request = new IndexRequest();
        request.index("user").id("1001");
        
        //创建对象转成JSON请求体
        User user = new User();
        user.setName("zhangsan");
        user.setAge(30);
        user.setSex("男");
        
        //向ES插入数据需要转为JSON
        Object obj = JSONArray.toJSON(user);
        String userJson = obj.toString();
        //放入request
        request.source(userJson, XContentType.JSON);
    
        IndexResponse index = esClient.index(request, RequestOptions.DEFAULT);
    
        System.out.println(index.getResult());
    
        //关闭客户端
        esClient.close();
    
    }
}
