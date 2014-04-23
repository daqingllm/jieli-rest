package com.jieli.common.entity;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-19
 * Time: 下午8:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class ProfessionTag {

    public static String IT_INTERNET = "互联网/计算机/信息技术";
    public static String FINANCIAL_LAW = "金融/会计/法律";
    public static String TRADE = "贸易/加工";
    public static String MEDICAL = "卫生/医疗/保健";
    public static String BUILDING = "地产/建筑/建材";
    public static String FOOD_TRIP = "餐饮/酒店/旅游";
    public static String TRAVEL = "交通/运输/物流/仓储";
    public static String ENERGY = "能源/矿产/环保/水利";
    public static String MACHINE = "机械/制造";
    public static String INFRASTRUCTURE = "基础设施";
    public static String AGRICULTURE = "农林牧渔";
    public static String GOVERNMENT = "政府机构";
    public static String SCIENCE = "科研/技术服务";
    public static String HR = "人力资源/人力服务";
    public static String EDUCATION = "教育";
    public static String CULTURE_SPORT = "文化/体育/娱乐业";
    public static String MEDIA = "传媒/广告/公关";
    public static String OTHER = "其他";

    public static String[] ALL = {
            IT_INTERNET,FINANCIAL_LAW,TRADE,MEDICAL,BUILDING,FOOD_TRIP,TRAVEL,ENERGY,MACHINE,INFRASTRUCTURE
            ,AGRICULTURE,GOVERNMENT,SCIENCE,HR,EDUCATION,CULTURE_SPORT,MEDIA,OTHER
    };
}
