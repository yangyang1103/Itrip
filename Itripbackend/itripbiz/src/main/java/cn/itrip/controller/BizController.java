package cn.itrip.controller;

import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripHotel.ItripHotelMapper;
import cn.itrip.dao.itripHotelRoom.ItripHotelRoomMapper;
import cn.itrip.dao.itripImage.ItripImageMapper;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.dao.itripUserLinkUser.ItripUserLinkUserMapper;
import cn.itrip.pojo.*;
import common.DateUtil;
import common.Dto;
import common.DtoUtil;
import common.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.RequestHandler;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.*;

@Controller
@RequestMapping("/api")
public class BizController {
    //联系人查询
    @Resource
    ItripUserLinkUserMapper dao;
    @RequestMapping(value = "/userinfo/queryuserlinkuser",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto queryuserlinkuser(@RequestBody ItripUserLinkUser itripUserLinkUser){
        List<ItripUserLinkUser> list=dao.getQueryuserlinkuser(itripUserLinkUser);
        return DtoUtil.returnDataSuccess(list);
    }
    //添加联系人
    @RequestMapping(value = "/userinfo/adduserlinkuser",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto adduserlinkuser(@RequestBody ItripUserLinkUser itripUserLinkUser) throws Exception {
//        itripUserLinkUser.setLinkIdCard(MD5.getMd5(itripUserLinkUser.getLinkIdCard(),32));
        int i=dao.insertItripUserLinkUser(itripUserLinkUser);
        if(i==1){
            return DtoUtil.returnDataSuccess("新增常用联系人成功");
        }else{
            return  DtoUtil.returnFail("新增联系人失败","100411" );
        }
    }
    //删除联系人
    @RequestMapping(value = "/userinfo/deluserlinkuser",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto deluserlinkuser(Integer ids) throws Exception {
        int i=dao.deleteItripUserLinkUserById(new Long(ids));
        if(i==1) {
            return DtoUtil.returnDataSuccess("删除常用联系人成功");
        }else{
            return  DtoUtil.returnFail("删除常用联系人失败","100432");
        }
    }
    //修改常用联系人
    @RequestMapping(value = "/userinfo/modifyuserlinkuser",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto modifyuserlinkuser(@RequestBody ItripUserLinkUser itripUserLinkUser) throws Exception {
        int i=dao.updateItripUserLinkUser(itripUserLinkUser);
        if(i==1) {
            return DtoUtil.returnDataSuccess("修改修改常用联系人成功");
        }else{
            return DtoUtil.returnFail("修改常用联系人失败","100421 ");
        }
    }
    //热门城市
    @Resource
    ItripAreaDicMapper areaDicMapper;
    @RequestMapping(value = "/hotel/queryhotcity/{type}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto queryhotcity(@PathVariable("type")String type) throws Exception {
        List<ItripAreaDic> list=areaDicMapper.hotcity(type);
        return DtoUtil.returnDataSuccess(list);
    }
    //特色酒店
    @Resource
    ItripLabelDicMapper labelDicMapper;
    @RequestMapping(value = "/hotel/queryhotelfeature",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto labelDicMapper() throws Exception {
        List<ItripLabelDic> list=labelDicMapper.queryhotelfeature();
        return DtoUtil.returnDataSuccess(list);
    }

    //分页查询地址
    @Resource
    ItripAreaDicMapper itripAreaDicMapper;
    @RequestMapping(value = "/hotel/querytradearea/{cityId}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto querytradearea(@PathVariable("cityId")Integer cityId) throws Exception {
        List<ItripAreaDicVO> list= itripAreaDicMapper.querytradearea(cityId);
        return DtoUtil.returnDataSuccess(list);
    }


    //查询酒店房间列表
    @Resource
    ItripHotelRoomMapper itripHotelRoomMapper;
    @RequestMapping(value = "/hotelroom/queryhotelroombyhotel",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto<List<ItripHotelRoomVO>> querytradearea(@RequestBody SearchHotelRoomVO vo) throws Exception {
        //酒店id
        Long hotelId=vo.getHotelId();
        //在此时间段内查询未预定的房间数据
        List<Date> dateList= DateUtil.getBetweenDates(vo.getStartDate(),vo.getEndDate());
        Map map=new HashMap();
        map.put("hotelId",hotelId);
        map.put("timesList",dateList);

        List<ItripHotelRoomVO> list=itripHotelRoomMapper.getItripHotelRoomListByMap(map);

        List<List<ItripHotelRoomVO>> hotelRoomVOList = null;
        hotelRoomVOList = new ArrayList();
        for (ItripHotelRoomVO roomVO : list) {
            List<ItripHotelRoomVO> tempList = new ArrayList<ItripHotelRoomVO>();
            tempList.add(roomVO);
            hotelRoomVOList.add(tempList);
        }
        //方便前台查询
        return DtoUtil.returnSuccess("获取成功", hotelRoomVOList);
    }
    //酒店图片
    @Resource
    ItripImageMapper itripImageMapper;
    @RequestMapping(value = "/hotelroom/getimg/{targetId}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto getimg(@PathVariable String targetId) throws Exception {

        List list =  itripImageMapper.getItripImageById(new Long(targetId));
        return DtoUtil.returnDataSuccess(list);
    }
    //根据酒店id查询酒店特色、商圈、酒店名称
    @Resource
    ItripHotelMapper itripHotelMapper;
    @RequestMapping(value = "/hotel/getvideodesc/{hotelId}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto getvideodesc(@PathVariable String hotelId) throws Exception {
        List<ItripAreaDic> list= itripHotelMapper.getHotelAreaByHotelId(new Long(hotelId));
        return DtoUtil.returnDataSuccess(list);
    }
    //根据酒店id查询酒店设施
    @RequestMapping(value = "/hotel/queryhotelfacilities/{id}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto<ItripSearchFacilitiesHotelVO> queryhotelfacilities(@PathVariable Integer id) throws Exception {
        ItripSearchFacilitiesHotelVO itripSearchFacilitiesHotelVO= itripHotelMapper.getItripHotelFacilitiesById(new Long(id));
        return DtoUtil.returnDataSuccess(itripSearchFacilitiesHotelVO.getFacilities());
    }
    //根据酒店id查询政策
    @RequestMapping(value = "/hotel/queryhotelpolicy/{id}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto<ItripSearchPolicyHotelVO> queryhotelpolicy(@PathVariable Integer id) throws Exception {
        ItripSearchPolicyHotelVO itripSearchPolicyHotelVO=itripHotelMapper.queryHotelPolicy(new Long(id));
        return DtoUtil.returnDataSuccess(itripSearchPolicyHotelVO.getHotelPolicy());
    }
    //根据酒店id查询平均分
   /* @RequestMapping(value = "/comment/gethotelscore/{hotelId}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto gethotelscore(@PathVariable Integer id) throws Exception {
        ItripSearchPolicyHotelVO itripSearchPolicyHotelVO=itripHotelMapper.queryHotelPolicy(new Long(id));
        return DtoUtil.returnDataSuccess(itripSearchPolicyHotelVO.getHotelPolicy());
    }*/

}
