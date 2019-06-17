package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.mapper.RouteMapper;
import cn.itcast.travel.service.RouteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteMapper routeMapper;
    //查询黑马精选数据
    @Override
    public Map<String, List<Route>> routeCareChoose() {
        //定义
        Map<String,List<Route>> map = new HashMap<String,List<Route>>();
        //获取人气线路列表
        List<Route> routeList = routeMapper.getPopularityRouteList();
        //获取最新线路列表
        List<Route> newsList = routeMapper.getNewestRouteList();
        //获取主题线路列表
        List<Route> themesList = routeMapper.getThemeRouteList();
        //将3个列表写入map集合中
        map.put("popularity",routeList);
        map.put("news",newsList);
        map.put("themes",themesList);
        //返回map集合
        return map;
    }
    //查询分页获取国内游数据
    @Override
    public PageBean getPageBean(Integer cid, Integer curPage, String rname) {
        //封装分页类数据
        PageBean<Route> pageBean = new PageBean<Route>();
        //封装当前页
        pageBean.setCurPage(curPage);
        //每页大小
        int pageSize = 3;
        pageBean.setPageSize(pageSize);
        //在调用查询方法之前，调用分页插件的静态方法，中间最好不要间隔任何代码
        PageHelper.startPage(curPage,pageSize);
        //动态获取当前页数据列表
        List<Route> routeList = routeMapper.findRouteListByPage(cid,rname);
        PageInfo<Route> pageInfo=new PageInfo<>(routeList);
        pageBean.setData(pageInfo.getList());
     //   int count = routeMapper.getCountByCid(cid,rname);
        pageBean.setCount((int) pageInfo.getTotal());

        //返回分页类数据
        return pageBean;
    }

    @Override
    public Route findRouteByRid(Integer rid) {
        //调用数据库获取线路相关数据(线路\所属分类\所属商家)
        Route route = routeMapper.findRouteByRid(rid);
        return route;
    }
}
