package com.seoul.hanokmania.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by namudak on 2015-10-16.
 */
// 매칭되는 필드가 없을 때 무시
@JsonIgnoreProperties(ignoreUnknown = true)
public class HanokRepair implements Serializable{

    // 10 fields
    public String HANOKNUM;	//등록번호
    public String SN;	    //차수별
    public String ADDR;	    //주소
    public String ITEM;	    //안건
    public String CONSTRUCTION;	//공사종별
    public String REQUEST;	//신청내용
    public String REVIEW;	//심의내용
    public String RESULT;	//심의결과
    public String LOANDEC;	//비용지원결정
    public String NOTE;	    //비고

    @Override
    public String toString() {

        return HANOKNUM+ ","+ SN+ ","+ ADDR+ ","+
                ITEM+ ","+ CONSTRUCTION+ ","+ REQUEST+ ","+
                REVIEW+ ","+ RESULT+ ","+ LOANDEC+ ","+
                NOTE+ ",";
    }
}
