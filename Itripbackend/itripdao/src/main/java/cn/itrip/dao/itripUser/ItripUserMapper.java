package cn.itrip.dao.itripUser;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.ItripUserVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripUserMapper {

	public ItripUser getItripUserById(@Param(value = "id") Long id)throws Exception;

	public ItripUser login(@Param(value = "name")String name,@Param("password")String password)throws Exception;

	public List<ItripUser>	getItripUserListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripUserCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripUser(ItripUser itripUser)throws Exception;

	public Integer updateItripUser(ItripUser itripUser)throws Exception;

	public Integer deleteItripUserById(@Param(value = "id") Long id)throws Exception;

	public Integer insertItripUserVo(ItripUserVO itripUserVo)throws Exception;

	public Integer jihuo(@Param("code")String code)throws Exception;
	//重复手机号
	public ItripUser getPhone(@Param("phone")String phone)throws Exception;
}
