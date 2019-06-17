package cn.itcast.travel.controller;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("route")
public class RouteController {
    //获取黑马精选数据
    @Autowired
    private RouteService routeService;

    @RequestMapping("routeCareChoose")
    @ResponseBody
    public ResultInfo routeCareChoose() {
        ResultInfo resultInfo = null;
        try {
            //调用业务逻辑类获取黑马精选数据
            Map<String, List<Route>> map = routeService.routeCareChoose();
            //正常实例返回对象
            resultInfo = new ResultInfo(true, map, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙。。。");
        }
        return resultInfo;
    }

    //查询分页获取国内游数据
    @RequestMapping("findRouteListByCid")
    @ResponseBody
    public ResultInfo findRouteListByCid(@RequestParam("cid") Integer cid,
                                         @RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
                                         @RequestParam(value = "rname", required = false) String rname) {
        ResultInfo resultInfo = null;
        try {
            //调用业务逻辑层获取国内游分页数据PageBean
            PageBean pageBean = routeService.getPageBean(cid, curPage, rname);
            //返回正常数据
            resultInfo = new ResultInfo(true, pageBean, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙。。。");
        }
        return resultInfo;
    }
    /**
     * 根据路线id查询路线详情
     *
     * @return
     */
    @RequestMapping("findRouteByRid")
    @ResponseBody
    public ResultInfo findRouteByRid(@RequestParam("rid") Integer rid) {
        ResultInfo resultInfo = null;

        try {
            // 调用业务逻辑根据rid获取旅游线路对象
            Route route = routeService.findRouteByRid(rid);
            // 实例结果
            resultInfo = new ResultInfo(true, route, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙..");
        }

        return resultInfo;
    }
}
