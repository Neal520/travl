package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    Boolean register(User user) throws Exception;

    boolean active(String code);

    User login(String username, String password) throws Exception;
}
