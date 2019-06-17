package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.mapper.FavoriteMapper;
import cn.itcast.travel.service.FavoriteService;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Override
    public boolean isFavoriteByRidAndUserId(Integer rid, int uid) {
        //根据rid和uid查找收藏对象
        Favorite favorite = favoriteMapper.findFavoriteByRidAndUserId(rid,uid);
        //判断收藏对象为空,返回false,否则返回true
        return favorite!=null;
    }

    @Override
    public void addFavorite(Integer rid, User user) {
        //向数据库插入一条收藏记录
        int row = favoriteMapper.addFavorite(rid,user.getUid());
        //更新当前线路的收藏数量+1
        row = favoriteMapper.updateRouteFavoriteNum(rid);
    }


    @Override
    public PageBean<Favorite> getPageBean(Integer curPage, int uid) {
//        PageHelper.startPage(curPage,pageSize);
//        //当前页数据列表
//        List<Favorite> favoriteList = favoriteMapper.findFavoriteListByPage(uid);
//        PageInfo<Favorite> pageInfo = new PageInfo<>(favoriteList);
//        pageBean.setData(pageInfo.getList());
//        pageBean.setCount((int)pageInfo.getTotal());
        //实例PageBean
        PageBean<Favorite> pageBean = new PageBean<Favorite>();
        //封装当前页
        pageBean.setCurPage(curPage);
        //封装每页大小
        int pageSize = 4;
        int start = (curPage-1) * pageSize;
        pageBean.setPageSize(pageSize);
        //总记录数
        int count = favoriteMapper.getCountByUid(uid);
        pageBean.setCount(count);
        //当前页数据列表
        List<Favorite> favoriteList=favoriteMapper.findFavoriteListByPage(uid,start,pageSize);
        pageBean.setData(favoriteList);
        return  pageBean;
    }
}
