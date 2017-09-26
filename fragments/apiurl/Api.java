package com.seoul.hanokmania.fragments.apiurl;

/**
 * Created by namudak on 2015-10-17.
 */
public final class Api {
    public static final String Key_Hanok= "7a4a4d56787362643234584b516641";
    public static final String Key_Hanok_N="435173514473626437334f74657478";
    public static final String Key_Hanok_R="45547a6c6d73626434306151646a4d";

    // 한옥등록 현황
    public static final String URL_HANOK= "http://openAPI.seoul.go.kr:8088/%s/"+
            "json/SeoulHanokStatus/1/999/";//600 cases
    // 북촌 한옥마을 분류별 한옥정보
    public static final String URL_HANOK_N= "http://openAPI.seoul.go.kr:8088/%s/"+
            "json/BukchonHanokVillageInfo/1/999/%s/";//94 cases
    // 한옥 수선자원 현황
    public static final String URL_HANOK_R= "http://openAPI.seoul.go.kr:8088/%s/"+
            "json/SeoulHanokRepairAdvice/%s/%s";//1929 cases

}
