package cn.itcast.travel.controller;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private RouteService routeService;

    @RequestMapping("isFavoriteByRid")
    @ResponseBody
    public ResultInfo isFavoriteByRid(HttpSession session,@RequestParam("rid")Integer rid){
        ResultInfo resultInfo = null;

        try {
            //判断用户是否登录
            User user =(User) session.getAttribute("loginUser");
            if(user==null) {
                //用户没有登录,返回false
                //第一个参数,代表正常处理结果
                //第二个参数,代表没有收藏
                resultInfo = new ResultInfo(true,false,null);
            }else {
                //用户登录,根据rid和用户去数据判断是否有收藏记录
                boolean flag = favoriteService.isFavoriteByRidAndUserId(rid,user.getUid());
                if(flag) {
                    //收藏了,返回true
                    resultInfo = new ResultInfo(true,true,null);
                }else {
                    //没有收藏,返回false
                    resultInfo = new ResultInfo(true,false,null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器正忙..");
        }
        return resultInfo;

    }

    /**
     * 添加收藏
     */
    @RequestMapping("addFavorite")
    @ResponseBody
    public ResultInfo addFavorite(HttpSession session,@RequestParam("rid")Integer rid){
        ResultInfo resultInfo = null;
        try {
            //判断用户是否登录
            User user =(User) session.getAttribute("loginUser");
            if(user==null) {
                //没有登录返回0
                resultInfo = new ResultInfo(true,0,null);
            }else {
                favoriteService.addFavorite(rid,user);

                //根据rid获取线路对象
                Route route = routeService.findRouteByRid(rid);

                //根据线路对象获取最新收藏数量
                int count = route.getCount();
                //返回
                resultInfo = new ResultInfo(true,count,null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器正忙..");
        }
        return resultInfo;
    }

    /**
     * 分页查询收藏列表
     */
    @RequestMapping("findFavoriteByPage")
    @ResponseBody
    public ResultInfo findFavoriteByPage(@RequestParam(value="curPage",defaultValue = "1")Integer curPage,HttpSession session){
        ResultInfo resultInfo = null;
        try {
            //获取当前登录的用户
            User user = (User)session.getAttribute("loginUser");

            //根据curPage和user调用业务获取收藏数据的PageBean
            PageBean<Favorite> pageBean =  favoriteService.getPageBean(curPage,user.getUid());

            //封装结果
            resultInfo = new ResultInfo(true,pageBean,null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false,null,"服务器正忙..");
        }
        return resultInfo;
    }
}
