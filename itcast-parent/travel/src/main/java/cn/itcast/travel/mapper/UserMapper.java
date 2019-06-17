package cn.itcast.travel.mapper;

import cn.itcast.travel.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserByUserName(@Param("username") String username);

    void addUser(User user);

    int active(@Param("code") String code);
}
