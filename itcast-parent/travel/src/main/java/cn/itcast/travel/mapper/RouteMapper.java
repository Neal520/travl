package cn.itcast.travel.mapper;

import cn.itcast.travel.domain.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteMapper {
    List<Route> getPopularityRouteList();

    List<Route> getNewestRouteList();

    List<Route> getThemeRouteList();

    List<Route> findRouteListByPage(@Param("cid")Integer cid, @Param("rname")String rname);

  //  int getCountByCid(@Param("cid")Integer cid, @Param("rname")String rname);

    Route findRouteByRid(@Param("rid")Integer rid);
}
