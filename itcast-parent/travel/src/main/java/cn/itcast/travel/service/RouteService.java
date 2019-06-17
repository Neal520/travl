package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;
import java.util.Map;

public interface RouteService {
    Map<String,List<Route>> routeCareChoose();

    PageBean getPageBean(Integer cid, Integer curPage, String rname);

    Route findRouteByRid(Integer rid);
}
