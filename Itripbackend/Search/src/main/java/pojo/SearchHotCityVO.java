package pojo;


import java.io.Serializable;

/**
 * 根据热门城市搜酒店的传入参数 VO
 * Created by XX on 17-5-15.
 */
public class SearchHotCityVO implements Serializable{

    private Integer cityId;

    private Integer count;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
