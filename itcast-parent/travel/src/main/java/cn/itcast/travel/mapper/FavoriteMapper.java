package cn.itcast.travel.mapper;

import cn.itcast.travel.domain.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavoriteMapper {
    Favorite findFavoriteByRidAndUserId(@Param("rid")Integer rid, @Param("uid")int uid);

    int addFavorite(@Param("rid")Integer rid,@Param("uid") int uid);

    int updateRouteFavoriteNum(Integer rid);

    List<Favorite> findFavoriteListByPage(@Param("uid")int uid, @Param("start")int start, @Param("pageSize")int pageSize);

    int getCountByUid(int uid);
}
