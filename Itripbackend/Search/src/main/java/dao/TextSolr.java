package dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import pojo.ItripHotelVO;

import java.io.IOException;
import java.util.List;

public class TextSolr {

    public static void main(String[] args) throws IOException, SolrServerException {
        String url="http://localhost:8080/solr-4.9.1/hotel";
        //初始化HttpSolrClient
        HttpSolrClient httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
        httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间
        //初始化SolrQuery
        SolrQuery query = new SolrQuery("*:*");
        query.setSort("id", SolrQuery.ORDER.desc);
        query.setStart(0);
        //一页显示多少条
        query.setRows(20);
        QueryResponse queryResponse = null;
        queryResponse = httpSolrClient.query(query);
        List<ItripHotelVO> list = queryResponse.getBeans(ItripHotelVO.class);
        for (ItripHotelVO hotel:list){
            System.out.println(hotel.getAddress()+hotel.getId());
        }
    }

}
