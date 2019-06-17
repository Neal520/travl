package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.exception.*;
import cn.itcast.travel.mapper.NewUserMapper;
import cn.itcast.travel.mapper.UserMapper;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.utils.MailUtil;
import cn.itcast.travel.utils.Md5Util;
import cn.itcast.travel.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
   // private UserMapper userMapper;

     private NewUserMapper userMapper;
    @Override
    public Boolean register(User user) throws Exception {
        //数据验证，用户名不能为空
        if(user.getUsername()==null|| "".equals(user.getUsername())){
            //抛出自定义异常
            throw new UserNameNotNullException("用户名不能为空");
        }
        //判断用户名是否已被注册
        User u=new User();
        u.setUsername(user.getUsername());
        User dbUser = userMapper.selectOne(u);
       // User dbUser = userMapper.getUserByUserName(user.getUsername());
        if(dbUser!=null){
            //抛出自定义异常
            throw new UserExistsException("用户名已存在");
        }
        //封装业务字段-激活状态为未激活
        user.setStatus("N");
        //封装业务字段-激活码（唯一，uuid）
        user.setCode(UuidUtil.getUuid());
        //密码加密，使用md5加密，md5号称不可逆的加密算法
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));

        // 发送邮件
        MailUtil.sendMail(user.getEmail(),
                "<a href='http://localhost:8080/user/active?code=" + user.getCode() + "'>用户激活</a>");
        // 注册用户添加用户
      //  userMapper.addUser(user);
        userMapper.insert(user);
        return true;
    }

    @Override
    public boolean active(String code) {
        User user=new User();
        user.setCode(code);
        user= userMapper.selectOne(user);
        user.setStatus("Y");
        int row = userMapper.updateByPrimaryKey(user);
     //   int row = userMapper.active(code);
        return row>0;
    }

    @Override
    public User login(String username, String password) throws Exception {
        //数据校验
        if(username==null || username.equalsIgnoreCase("")){
            throw new UserNameNotNullException("用户名不能为空");
        }
        //判断用户名是否存在
        User user=new User();
        user.setUsername(username);
        User dbUser = userMapper.selectOne(user);
      //  User dbUser = userMapper.getUserByUserName(username);
        if(dbUser==null){
            throw new UserNotExistsException("用户名不存在");
        }
        //判断密码是否正确
        if(!dbUser.getPassword().equals(password)){
            throw new PasswordErrorException("密码错误");
        }
        //用户是否已激活
        if(dbUser.getStatus().equals("N")){
            throw new UserNoActiveException("用户未激活");
        }
        return dbUser;
    }
}
