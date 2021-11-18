package Controller;

import com.alibaba.fastjson.JSONArray;
import common.Dto;
import common.DtoUtil;
import dao.BaseDao;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.ItripHotelVO;
import pojo.Page;
import pojo.SearchHotCityVO;
import pojo.SearchHotelVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SearchController {
    @RequestMapping(value="/hotellist/searchItripHotelListByHotCity",produces="application/json; charset=utf-8")
    @ResponseBody
    public Dto getLogin(@RequestBody SearchHotCityVO vo, HttpServletRequest request) throws Exception {
        BaseDao dao=new BaseDao();
        SolrQuery query=new SolrQuery();
        query.setQuery("*:*");
        query.addFilterQuery("cityId:"+vo.getCityId());
        query.setRows(vo.getCount());
        List<SearchHotCityVO> list=dao.getList(query);
        return DtoUtil.returnDataSuccess(list);
    }

    @RequestMapping(value="/hotellist/searchItripHotelPage",produces="application/json; charset=utf-8")
    @ResponseBody
    public Dto getList(@RequestBody SearchHotelVO vo, HttpServletRequest request) throws Exception {
        BaseDao dao=new BaseDao();
        SolrQuery query=new SolrQuery();
        query.setQuery("*:*");
        if(vo.getPageNo()==null){
            vo.setPageNo(1);
        }
        if(vo.getPageSize()==null){
            vo.setPageSize(6);
        }
        //市区
        if(vo.getDestination()!=null&&vo.getDestination()!=""){
            query.addFilterQuery("destination:"+vo.getDestination());
        }
        //酒店关键词
        if(vo.getKeywords()!=""){
            query.addFilterQuery("keyword:"+vo.getKeywords());
        }else{
            query.addFilterQuery("keyword:*");
        }
        //评分、价格、星级排序
        if(vo.getAscSort()!=null){
            query.addSort(vo.getAscSort(),SolrQuery.ORDER.asc);
        }
        //酒店星级查询
        if(vo.getHotelLevel()!=null){
            query.addFilterQuery("hotelLevel:"+vo.getHotelLevel());
        }

        //Solr:minPrice:[0 TO 500]
        //前台:maxPrice: 450 | minPrice: 301
        if(vo.getMaxPrice()!=null){
            query.addFilterQuery("minPrice:[* TO "+vo.getMaxPrice()+"]");
        }
        if(vo.getMinPrice()!=null){
            query.addFilterQuery("minPrice:["+vo.getMinPrice()+" TO *]");
        }
        //位置
        //tradingAreaIds:*,3528,* or tradingAreaIds:*,3528,*
        if(vo.getTradeAreaIds()!=null&&vo.getTradeAreaIds()!=""){
            String str="tradingAreaIds:";

            String[] names=vo.getTradeAreaIds().split(",");
            for(int i=0;i<names.length;i++){
                if(i==0){
                    str+="*,"+names[i]+",*";
                }else{
                    str+=" or tradingAreaIds:*,"+names[i]+",*";
                }
            }
            query.addFilterQuery(str);

        }
        //酒店特色
        //featureIds:*,120,* or featureIds:*,119,*
        if(vo.getFeatureIds()!=null&&vo.getFeatureIds()!=""){
            String str="featureIds:";

            String[] names=vo.getFeatureIds().split(",");
            for(int i=0;i<names.length;i++){
                if(i==0){
                    str+="*,"+names[i]+",*";
                }else{
                    str+=" or featureIds:*,"+names[i]+",*";
                }
            }
            System.out.println(str);
            query.addFilterQuery(str);

        }

        //query.addFilterQuery("keyword:"+vo.getKeywords());
        Page<ItripHotelVO> page=dao.getLimit(query,vo.getPageNo(),vo.getPageSize());
        return DtoUtil.returnDataSuccess(page);
    }


}
