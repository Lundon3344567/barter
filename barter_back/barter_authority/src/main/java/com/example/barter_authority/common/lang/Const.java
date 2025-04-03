package com.example.barter_authority.common.lang;

/**
 * 常量定义
 * @author walnut
 * @date 2021/11/7 2:36 下午
 * @version 1.0
 */
public class Const {
    public final static String CAPTCHA_KEY = "captcha";
    /** 用户已存在*/
    public final static int USER_EXIST = 400;
    /** 正常返回*/
    public final static int OK = 200;
    /** 发送注册邮件代码 */
    public final static String SEND_REGISTER_MAIL = "4001";
    /** 发送忘记密码邮件代码 */
    public final static String SEND_FORGET_MAIL = "4002";
    /** 不被允许 */
    public static final int NOT_ALLOWED = 4003;
    /** 系统错误 */
    public static final int FILED = 500;

    //数据库相关

    /** 普通账户在用 */
    public static final int USER_ON_USE = 1;
    /** 普通账户被锁 */
    public static final int USER_LOCKED = 2;
    /** 普通账户已注销 */
    public static final int USER_LOG_OFF = 3;

    /** 管理员账户在用 */
    public static final int ADMIN_ON_USE = 101;
    /** 管理员账户被锁 */
    public static final int ADMIN_LOCKED = 102;
    /** 管理员账户已注销 */
    public static final int ADMIN_LOG_OFF = 103;

    /** 删除失败返回码 */
    public static final int DELETE_FAILED = -2;
    /** 删除成功返回码 */
    public static final int DELETE_SUCCESS = 2;
    /** 插入失败返回码 */
    public static final int INSERT_FAILED = -1;
    /** 插入成功返回码 */
    public static final int INSERT_SUCCESS = 1;
    /**
     * 修改成功返回码
     */
    public static final int UPDATE_SUCCESS = 1;
    /** 默认过期时间 1小时 */
    public static final int DEFAULT_EXPIRATION_TIME = 60 * 60;
/** 固定式验证码*/
    public static final String CODE = "52d7218e-9a91-4379-aafe-36d9564c9d89";
}
