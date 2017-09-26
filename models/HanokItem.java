package com.seoul.hanokmania.models;

/**
 * Created by namudak on 2015-10-17.
 */
public class HanokItem {

    // 12 fields for 'hanok'
    private String HANOKNUM;        // 등록번호
    private String ADDR;            // 주소
    private String PLOTTAGE;        // 대지면적
    private String TOTAR;           // 연면적
    private String BUILDAREA;       // 건축면적
    private String FLOOR;           // 층수(지상)
    private String FLOOR2;          // 층수(지하)
    private String USE;             // 용도
    private String STRUCTURE;       // 구조
    private String PLANTYPE;        // 평면형식
    private String BUILDDATE;       // 건립일
    private String NOTE;            // 비고

    // 17 fields for 'bukchon_hanok'
    private String HOUSE_TYPE;      //건물 종류코드
    private String TYPE_NAME;       //건물 종류명
    private String LANGUAGE_TYPE;   //언어별 구분코드
    private String HOUSE_ID;        //건물 고유아이디
    private String HOUSE_NAME;      //건물 이름
    private String HOUSE_ADDR;      //건물 주소
    private String HOUSE_OWNER;     //건물 소유주
    private String HOUSE_ADMIN;     //건물 관리자
    private String HOUSE_TELL;      //건물 연락처
    private String HOUSE_HP;        //건물 홈페이지
    private String HOUSE_OPEN_TIME; //건물 개방 시간
    private String HOUSE_REG_DATE;  //건물 지정일
    private String HOUSE_YEAR;      //건물 연대
    private String BOOL_CULTURE;    //문화재지정내용
    private String HOUSE_CONTENT;   //건물 설명
    private String SERVICE_OK;      //SERVICE
    private String PRIORITY;        //중요도

    // 10 fields for 'repair_hanok'
    private String SN;              //차수별
    private String ADDR_R;          //주소
    private String ITEM;            //안건
    private String CONSTRUCTION;    //공사종별
    private String REQUEST;         //신청내용
    private String REVIEW;          //심의내용
    private String RESULT;          //심의결과
    private String LOANDEC;         //비용지원결정
    private String NOTE_R;          //비고


    public HanokItem(String[] val) {
        HANOKNUM= val[0];
        ADDR= val[1];
        PLOTTAGE= val[2];
        BUILDAREA= val[3];
        USE= val[4];
        STRUCTURE= val[5];
    }

    public String getHANOKNUM() {
        return HANOKNUM;
    }

    public void setHANOKNUM(String HANOKNUM) {
        this.HANOKNUM = HANOKNUM;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String ADDR) {
        this.ADDR = ADDR;
    }

    public String getPLOTTAGE() {
        return PLOTTAGE;
    }

    public void setPLOTTAGE(String PLOTTAGE) {
        this.PLOTTAGE = PLOTTAGE;
    }

    public String getTOTAR() {
        return TOTAR;
    }

    public void setTOTAR(String TOTAR) {
        this.TOTAR = TOTAR;
    }

    public String getBUILDAREA() {
        return BUILDAREA;
    }

    public void setBUILDAREA(String BUILDAREA) {
        this.BUILDAREA = BUILDAREA;
    }

    public String getFLOOR() {
        return FLOOR;
    }

    public void setFLOOR(String FLOOR) {
        this.FLOOR = FLOOR;
    }

    public String getFLOOR2() {
        return FLOOR2;
    }

    public void setFLOOR2(String FLOOR2) {
        this.FLOOR2 = FLOOR2;
    }

    public String getUSE() {
        return USE;
    }

    public void setUSE(String USE) {
        this.USE = USE;
    }

    public String getSTRUCTURE() {
        return STRUCTURE;
    }

    public void setSTRUCTURE(String STRUCTURE) {
        this.STRUCTURE = STRUCTURE;
    }

    public String getPLANTYPE() {
        return PLANTYPE;
    }

    public void setPLANTYPE(String PLANTYPE) {
        this.PLANTYPE = PLANTYPE;
    }

    public String getBUILDDATE() {
        return BUILDDATE;
    }

    public void setBUILDDATE(String BUILDDATE) {
        this.BUILDDATE = BUILDDATE;
    }

    public String getNOTE() {
        return NOTE;
    }

    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    public String getHOUSE_TYPE() {
        return HOUSE_TYPE;
    }

    public void setHOUSE_TYPE(String HOUSE_TYPE) {
        this.HOUSE_TYPE = HOUSE_TYPE;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    public String getLANGUAGE_TYPE() {
        return LANGUAGE_TYPE;
    }

    public void setLANGUAGE_TYPE(String LANGUAGE_TYPE) {
        this.LANGUAGE_TYPE = LANGUAGE_TYPE;
    }

    public String getHOUSE_ID() {
        return HOUSE_ID;
    }

    public void setHOUSE_ID(String HOUSE_ID) {
        this.HOUSE_ID = HOUSE_ID;
    }

    public String getHOUSE_NAME() {
        return HOUSE_NAME;
    }

    public void setHOUSE_NAME(String HOUSE_NAME) {
        this.HOUSE_NAME = HOUSE_NAME;
    }

    public String getHOUSE_ADDR() {
        return HOUSE_ADDR;
    }

    public void setHOUSE_ADDR(String HOUSE_ADDR) {
        this.HOUSE_ADDR = HOUSE_ADDR;
    }

    public String getHOUSE_OWNER() {
        return HOUSE_OWNER;
    }

    public void setHOUSE_OWNER(String HOUSE_OWNER) {
        this.HOUSE_OWNER = HOUSE_OWNER;
    }

    public String getHOUSE_ADMIN() {
        return HOUSE_ADMIN;
    }

    public void setHOUSE_ADMIN(String HOUSE_ADMIN) {
        this.HOUSE_ADMIN = HOUSE_ADMIN;
    }

    public String getHOUSE_TELL() {
        return HOUSE_TELL;
    }

    public void setHOUSE_TELL(String HOUSE_TELL) {
        this.HOUSE_TELL = HOUSE_TELL;
    }

    public String getHOUSE_HP() {
        return HOUSE_HP;
    }

    public void setHOUSE_HP(String HOUSE_HP) {
        this.HOUSE_HP = HOUSE_HP;
    }

    public String getHOUSE_OPEN_TIME() {
        return HOUSE_OPEN_TIME;
    }

    public void setHOUSE_OPEN_TIME(String HOUSE_OPEN_TIME) {
        this.HOUSE_OPEN_TIME = HOUSE_OPEN_TIME;
    }

    public String getHOUSE_REG_DATE() {
        return HOUSE_REG_DATE;
    }

    public void setHOUSE_REG_DATE(String HOUSE_REG_DATE) {
        this.HOUSE_REG_DATE = HOUSE_REG_DATE;
    }

    public String getHOUSE_YEAR() {
        return HOUSE_YEAR;
    }

    public void setHOUSE_YEAR(String HOUSE_YEAR) {
        this.HOUSE_YEAR = HOUSE_YEAR;
    }

    public String getBOOL_CULTURE() {
        return BOOL_CULTURE;
    }

    public void setBOOL_CULTURE(String BOOL_CULTURE) {
        this.BOOL_CULTURE = BOOL_CULTURE;
    }

    public String getHOUSE_CONTENT() {
        return HOUSE_CONTENT;
    }

    public void setHOUSE_CONTENT(String HOUSE_CONTENT) {
        this.HOUSE_CONTENT = HOUSE_CONTENT;
    }

    public String getSERVICE_OK() {
        return SERVICE_OK;
    }

    public void setSERVICE_OK(String SERVICE_OK) {
        this.SERVICE_OK = SERVICE_OK;
    }

    public String getPRIORITY() {
        return PRIORITY;
    }

    public void setPRIORITY(String PRIORITY) {
        this.PRIORITY = PRIORITY;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getADDR_R() {
        return ADDR_R;
    }

    public void setADDR_R(String ADDR_R) {
        this.ADDR_R = ADDR_R;
    }

    public String getITEM() {
        return ITEM;
    }

    public void setITEM(String ITEM) {
        this.ITEM = ITEM;
    }

    public String getCONSTRUCTION() {
        return CONSTRUCTION;
    }

    public void setCONSTRUCTION(String CONSTRUCTION) {
        this.CONSTRUCTION = CONSTRUCTION;
    }

    public String getREQUEST() {
        return REQUEST;
    }

    public void setREQUEST(String REQUEST) {
        this.REQUEST = REQUEST;
    }

    public String getREVIEW() {
        return REVIEW;
    }

    public void setREVIEW(String REVIEW) {
        this.REVIEW = REVIEW;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getLOANDEC() {
        return LOANDEC;
    }

    public void setLOANDEC(String LOANDEC) {
        this.LOANDEC = LOANDEC;
    }

    public String getNOTE_R() {
        return NOTE_R;
    }

    public void setNOTE_R(String NOTE_R) {
        this.NOTE_R = NOTE_R;
    }
}
