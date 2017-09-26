package com.seoul.hanokmania.models;

/**
 * Created by student on 2015-10-16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Ray Choe on 2015-10-16.
 */
// 매칭되는 필드가 없을 때 무시
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hanok implements Serializable{

    // 12 fileds
    // 등록번호
    public String HANOKNUM;
    // 주소
    public String ADDR;
    // 대지면적
    public String PLOTTAGE;
    // 연면적
    public String TOTAR;
    // 건축면적
    public String BUILDAREA;
    // 층수(지상)
    public String FLOOR;
    // 층수(지하)
    public String FLOOR2;
    // 용도
    public String USE;
    // 구조
    public String STRUCTURE;
    // 평면형식
    public String PLANTYPE;
    // 건립일
    public String BUILDDATE;
    // 비고
    public String NOTE;
    // 비고
    public String HANOKNUM2;

    @Override
    public String toString() {

        return HANOKNUM+ ","+ ADDR+ ","+ PLOTTAGE+ ","+
                TOTAR+ ","+ BUILDAREA+ ","+ FLOOR+ ","+
                FLOOR2+ ","+ USE+ ","+ STRUCTURE+ ","+
                PLANTYPE+ ","+ BUILDDATE+ ","+ NOTE+ ","+
                HANOKNUM2+ ",";
    }
}
