package cn.itcast.travel.exception;

/**
 * 用户名不能为空异常
 */
public class UserNoActiveException extends Exception {
    public UserNoActiveException(){

    }
    public UserNoActiveException(String errorMsg){
        super(errorMsg);
    }
}
