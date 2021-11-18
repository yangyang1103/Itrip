package cn.itrip.controller;

import cn.itrip.dao.itripHotelRoom.ItripHotelRoomMapper;
import cn.itrip.pojo.ItripHotelRoom;
import cn.itrip.pojo.ItripSearchFacilitiesHotelVO;
import cn.itrip.pojo.StoreVo;
import cn.itrip.pojo.ValidateRoomStoreVO;
import common.Dto;
import common.DtoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class BizOrderController {
    @Resource
    ItripHotelRoomMapper dao;
    @RequestMapping(value = "/hotelorder/getpreorderinfo",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto<RoomStoreVO> getpreorderinfo(@RequestBody ValidateRoomStoreVO vo) throws Exception {
        //下订单存入临时数据库,如果库存表中没有该订单则插入一条数据
        Map map=new HashMap();
        map.put("hotelid",vo.getHotelId());
        map.put("roomid",vo.getRoomId());
        map.put("sdata",vo.getCheckInDate());
        map.put("edata",vo.getCheckOutDate());
        dao.flushDate(map);
        //查询该房间库存数量
        Map store=new HashMap();
        store.put("roomId",vo.getRoomId());
        store.put("checkInDate",vo.getCheckInDate());
        store.put("checkOutDate",vo.getCheckOutDate());
        List<StoreVo> list=dao.getStore(store);
        //根据房间id
        RoomStoreVO room=new RoomStoreVO();
        room.setHotelName("写死的酒店名");
        room.setStore(list.get(0).getStore());
        room.setCheckInDate(vo.getCheckInDate());
        room.setCheckOutDate(vo.getCheckOutDate());
        room.setPrice(new BigDecimal(50));


        return DtoUtil.returnDataSuccess(room);
    }
}
