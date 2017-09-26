package com.seoul.hanokmania.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by namudak on 2015-10-16.
 */
// 매칭되는 필드가 없을 때 무시
@JsonIgnoreProperties(ignoreUnknown = true)
public class HanokBukchon implements Serializable{

    // 17 fields
    public String HOUSE_TYPE;	//건물 종류코드
    public String TYPE_NAME;	//건물 종류명
    public String LANGUAGE_TYPE;//언어별 구분코드
    public String HOUSE_ID;	    //건물 고유아이디
    public String HOUSE_NAME;	//건물 이름
    public String HOUSE_ADDR;	//건물 주소
    public String HOUSE_OWNER;	//건물 소유주
    public String HOUSE_ADMIN;	//건물 관리자
    public String HOUSE_TELL;	//건물 연락처
    public String HOUSE_HP;	    //건물 홈페이지
    public String HOUSE_OPEN_TIME;	//건물 개방 시간
    public String HOUSE_REG_DATE;	//건물 지정일
    public String HOUSE_YEAR;	//건물 연대
    public String BOOL_CULTURE;	//문화재지정내용
    public String HOUSE_CONTENT;//건물 설명
    public String SERVICE_OK;	//SERVICE
    public String PRIORITY;	    //중요도

    @Override
    public String toString() {

        return HOUSE_TYPE+ ","+ TYPE_NAME+ ","+ LANGUAGE_TYPE+ ","+
                HOUSE_ID+ ","+ HOUSE_NAME+ ","+ HOUSE_ADDR+ ","+
                HOUSE_OWNER+ ","+ HOUSE_ADMIN+ ","+ HOUSE_TELL+ ","+
                HOUSE_HP+ ","+ HOUSE_OPEN_TIME+ ","+ HOUSE_REG_DATE+ ","+
                HOUSE_YEAR+ ","+ BOOL_CULTURE+ ","+ HOUSE_CONTENT+ ","+
                SERVICE_OK+ ","+ PRIORITY+ ",";
    }
}

