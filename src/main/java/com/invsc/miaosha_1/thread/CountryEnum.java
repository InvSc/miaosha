package com.invsc.miaosha_1.thread;

import lombok.Getter;

/**
 * 枚举 即类似于一张数据库表 这是一个实用小技巧
 * 优势
 *  降低代码耦合度 在多线程时候不用添加if判断 也不用进行数据库连接操作
 * 案例
 *  搭配CountDownLatchDemo
 */
@Getter
public enum CountryEnum {
    ONE(1, "齐"), TWO(2, "楚"), THR(3, "燕"), FOU(4, "韩"), FIV(5, "赵"), SIX(6, "魏");
    private Integer retCode;
    private String retMsg;
    CountryEnum(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
    public static String getCountryName(int index) {
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum countryEnum : myArray) {
            if (countryEnum.getRetCode() == index) {
                return countryEnum.getRetMsg();
            }
        }
        return null;
    }
}
