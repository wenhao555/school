package com.example.school.net;

public class Urls
{
    //本地local
//    public static String defaultUrl = "http://192.168.1.104:8000";
    public static String defaultUrl = "http://192.168.1.167:8000";
    //    app端登录用
    public static String GET_USER = defaultUrl + "/login";
    //    app添加用户用
    public static String ADD_USER = defaultUrl + "/addUsers";
    //更新用户
    public static String UPDATE_USERS = defaultUrl + "/updateUsers";
    public static String UPDATETEACHER = defaultUrl + "/updateTeacher";
    //查找广告
    public static String FIND_DISCOVER = defaultUrl + "/findDiscovery";
    //添加班级圈
    public static String ADDCLASSCIRCLE = defaultUrl + "/addClassCircle";
    public static String ADDCOMCIRCLE = defaultUrl + "/addComCircle";
    public static String PARENTREADCIRCLE = defaultUrl + "/parentReadCircle";
    public static String PARENTREADCOMCIRCLE = defaultUrl + "/parentReadComCircle";
    public static String ADDCLASSCIRCLEZAN = defaultUrl + "/addClassCircleZan";
    public static String ADDCOMCIRCLEZAN = defaultUrl + "/addComCircleZan";

    public static String DELCLASSCIRCLEZAN = defaultUrl + "/delClassCircleZan";
    public static String DELCOMCIRCLEZAN = defaultUrl + "/delComCircleZan";
    public static String ADDCLASSCIRCLEREPLY = defaultUrl + "/addClassCircleReply";
    public static String ADDCOMCIRCLEREPLY = defaultUrl + "/addComCircleReply";

    public static String ADDCOMCIRCLECOMMENT = defaultUrl + "/addComCircleComment";
    public static String ADDCLASSCIRCLECOMMENT = defaultUrl + "/addClassCircleComment";
}
