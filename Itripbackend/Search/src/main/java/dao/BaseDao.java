package dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import pojo.ItripHotelVO;
import pojo.Page;
import pojo.SearchHotCityVO;

import java.io.IOException;
import java.util.List;

public class BaseDao<T> {
    public static String url="http://localhost:8080/solr-4.9.1/hotel";
    HttpSolrClient httpSolrClient;
    public BaseDao(){
        httpSolrClient=new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
        httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间
    }
    //不带分页的底层Solr方法
    public List<T> getList(SolrQuery query) throws IOException, SolrServerException {
        /*//初始化SolrQuery
        SolrQuery query = new SolrQuery("*:*");
        query.setSort("id", SolrQuery.ORDER.desc);
        query.setStart(0);
        //一页显示多少条
        query.setRows(20);*/
       /* query.setQuery("cityId:"+)
        query.setRows(6);*/
        QueryResponse queryResponse = null;
        queryResponse = httpSolrClient.query(query);
        List<ItripHotelVO> list = queryResponse.getBeans(ItripHotelVO.class);
        return (List<T>) list;
    }

    //带分页的底层Solr方法
    public Page<T> getLimit(SolrQuery query, int index, int pageCount) throws IOException, SolrServerException {
        QueryResponse queryResponse=null;
        //起始位置
        query.setStart((index-1)*pageCount);
        //结束位置
        query.setRows(pageCount);
        queryResponse=httpSolrClient.query(query);
        List<T> list= (List<T>) queryResponse.getBeans(ItripHotelVO.class);
        SolrDocumentList solrDocuments=((QueryResponse)queryResponse).getResults();
        Page page=new Page(index,pageCount, new Long(solrDocuments.getNumFound()).intValue());
        page.setRows(list);
        return page;
    }
}
