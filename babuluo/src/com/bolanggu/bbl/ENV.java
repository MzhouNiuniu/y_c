package com.bolanggu.bbl;

/**
 * Created by pabula on 2016/11/13.
 */
public class ENV {



    //库存阀值
    public static Double STOCK_OFF = 0.75;

    //阿里云KEY
    //public static String ALIYUN_KEY = "LTAImVQlWKQXIQcD";   //jiaorder测试的
    public static String ALIYUN_KEY = "LTAIEHpVQat6f83C";   //BBL客户的

    //阿里云授权
    //public static String ALIYUN_SERCT = "kYSyEoB00SZje6GCaS8yqhZzNt6Mkd";     //jiaorder测试的
    public static String ALIYUN_SERCT = "uZy8ypZNyiGBYT0RqcWbmb6L1bOGNn";       //BBL客户的

    //阿里云节点
//public static String ALIYUN_POINT = "http://oss-cn-hangzhou.aliyuncs.com";        //jiaorder测试的
    public static String ALIYUN_POINT = "http://oss-cn-shanghai.aliyuncs.com";      //BBL客户的

    //OSS BUCKET
    //public static String ALIYUN_BUCKET = "babuluo-file";        //jiaorder测试的
    public static String ALIYUN_BUCKET = "bbl-upload";        //BBL客户的

    //内容编辑器中，上传文件的存放目录
    public static String ALIYUN_UPLOAD_MAIN_DIR = "content";

    //阿里云上传后URL
    //public  static String ALIYUN_UPLOAD_URL = "http://babuluo-file.oss-cn-hangzhou.aliyuncs.com/" + ALIYUN_UPLOAD_MAIN_DIR + "/"; //jiaorder测试的
    public  static String ALIYUN_UPLOAD_URL = "http://bbl-upload.oss-cn-shanghai.aliyuncs.com/" + ALIYUN_UPLOAD_MAIN_DIR + "/";     //BBL客户的

    //阿里云上传在内容编辑器中，缩略图尺寸
    public static String ALIYUN_UPLOAD_IMG_SIZE = "?x-oss-process=image/resize,w_750";

    //门店接口
    public static String API_MENDIAN = "http://58.240.110.186:98/BBL/";

    //门店接口KEY
    public static String API_MENDIAN_CERT = "F4EBA1DE727A41A91B5D10754BFBF657";


    /**
     * 微信公众号配置信息
     */

    public static String WX_APPID = "wx7c4d78e05a44115e";
    public static String WX_APPSECRET = "4e204b8f9cf5a5c11457ffa40d891ffc";


    /**
     * 微信支付配置信息
     */
    public static String WX_MCHID = "1298356201";
    public static String WX_KEY = "fdbnhlsh6gs79ro4lhr6vutmgnx0flfc";

    public static String WX_PAY_RESULT_CALLBACK_URL = "http://h5.bblycyz.com/h5/pages/main/wxPayCallback.jsp";


    public static String WX_TOKEN_GRANT_TYPE = "client_credential";
}