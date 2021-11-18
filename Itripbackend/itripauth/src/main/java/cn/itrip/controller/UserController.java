package cn.itrip.controller;

import cn.itrip.biz.PhoneBiz;
import cn.itrip.biz.TokenBiz;
import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.ItripUserVO;
import com.alibaba.fastjson.JSONArray;
import common.Dto;
import common.DtoUtil;
import common.ItripTokenVO;
import common.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.RedisHelp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("/api")
public class UserController {
    @Resource
    ItripUserMapper dao;
    @Resource
    RedisHelp redis;
    @Resource
    TokenBiz biz;
    //登录
    @RequestMapping(value="/dologin",produces="application/json; charset=utf-8")
    @ResponseBody
    public Dto getLogin(String name, String password, HttpServletRequest request) throws Exception {
        System.out.println("----------"+name);
        ItripUser user=dao.login(name, MD5.getMd5(password,32));
        System.out.println("密码:"+user.getUserCode());
        //token:包含用户信息 过期时间信息 客户端信息6
        String token=biz.generateToken(request.getHeader("User-Agent"),user);
        //把用户信息存储到redis key=token,value=用户对象
        redis.SetData(token, JSONArray.toJSONString(user));
        ItripTokenVO tokenVO=new ItripTokenVO(token, Calendar.getInstance().getTimeInMillis()*7200,Calendar.getInstance().getTimeInMillis());
        //token
        return DtoUtil.returnDataSuccess(tokenVO);
    }

    //手机验证
    @Resource
    PhoneBiz phoneBiz;
    @RequestMapping(value = "/registerbyphone",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto registerbyphone(@RequestBody  ItripUserVO userVo) throws Exception {
        try {
            //查询是否有重复手机号
            ItripUser user=dao.getPhone(userVo.getUserCode());
            if(user!=null){
                return DtoUtil.returnFail("已有此账号","1000");
            }
            //添加用户到数据库
            userVo.setUserPassword(MD5.getMd5(userVo.getUserPassword(),32));
            Integer dto=dao.insertItripUserVo(userVo);
            //发送验证码
            String Suiji=(int)(Math.random()*9000+1000)+"";
            System.out.println(Suiji);
            phoneBiz.sentSMS(userVo.getUserCode(),Suiji+"");
            //将验证码存储到redis
            redis.SetData(userVo.getUserCode(),Suiji+"");
            ItripTokenVO tokenVO=new ItripTokenVO(userVo.getUserCode(), Calendar.getInstance().getTimeInMillis()*7200,Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnDataSuccess("注册成功");
        }
        catch (Exception e){
            return DtoUtil.returnDataSuccess("注册失败");
        }
    }

    //手机验证激活
   @RequestMapping(value = "/activate",produces = "application/json;charset=utf-8")
   @ResponseBody
   public Dto validatephone(String user,String code) throws Exception {
        //redeis是否匹配
      String result=redis.Getdata(user);
      if(!result.equals(null)&&result.equals(code)){//有这个验证码
          dao.jihuo(user);
          return DtoUtil.returnDataSuccess("激活成功");
      }else{
          return DtoUtil.returnFail("激活失败","1000");
      }
   }

    @RequestMapping(value="/json",method= RequestMethod.GET,produces="application/json; charset=utf-8")
    @ResponseBody
    public String getlist(int pid) throws Exception {
        return JSONArray.toJSONString(dao.getItripUserById(new Long(pid)));
    }
}
