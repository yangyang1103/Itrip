package cn.itrip.dao.itripAreaDic;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.pojo.ItripAreaDicVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripAreaDicMapper {

	public List<ItripAreaDic> hotcity(@Param(value = "type") String type)throws Exception;

	public ItripAreaDic getItripAreaDicById(@Param(value = "id") Long id)throws Exception;

	public List<ItripAreaDic>	getItripAreaDicListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripAreaDicCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripAreaDic(ItripAreaDic itripAreaDic)throws Exception;

	public Integer updateItripAreaDic(ItripAreaDic itripAreaDic)throws Exception;

	public Integer deleteItripAreaDicById(@Param(value = "id") Long id)throws Exception;

	//查询热门商圈
	public  List<ItripAreaDicVO> querytradearea(@Param(value = "cityId")Integer cityId);
}
