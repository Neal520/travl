package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.User;

public interface FavoriteService {
    boolean isFavoriteByRidAndUserId(Integer rid, int uid);

    void addFavorite(Integer rid, User user);

    PageBean<Favorite> getPageBean(Integer curPage, int uid);
}
