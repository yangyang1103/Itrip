package cn.itrip.dao.itripHotel;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.pojo.ItripHotel;
import cn.itrip.pojo.ItripSearchFacilitiesHotelVO;
import cn.itrip.pojo.ItripSearchPolicyHotelVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripHotelMapper {

	public ItripHotel getItripHotelById(@Param(value = "id") Long id)throws Exception;

	public List<ItripHotel>	getItripHotelListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripHotelCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripHotel(ItripHotel itripHotel)throws Exception;

	public Integer updateItripHotel(ItripHotel itripHotel)throws Exception;

	public Integer deleteItripHotelById(@Param(value = "id") Long id)throws Exception;
	//根据酒店id查询酒店设施
	public ItripSearchFacilitiesHotelVO getItripHotelFacilitiesById(@Param(value="id") Long id) throws Exception;
	//根据酒店的id查询酒店的政策
	public ItripSearchPolicyHotelVO queryHotelPolicy(@Param(value="id") Long id)throws Exception;

	/**
	 *  根据酒店ID获取商圈
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<ItripAreaDic> getHotelAreaByHotelId(@Param(value = "id") Long id)throws Exception;
}
