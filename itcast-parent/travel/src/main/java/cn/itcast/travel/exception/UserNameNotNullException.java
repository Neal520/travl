package cn.itcast.travel.exception;

/**
 * 用户名不能为空异常
 */
public class UserNameNotNullException extends RuntimeException {
    public  UserNameNotNullException(){

    }
    public  UserNameNotNullException(String errorMsg){
        super(errorMsg);
    }
}
