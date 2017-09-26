package com.seoul.hanokmania.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Arrays;

/**
 * Created by namudak on 2015-09-14.
 */
public final class HanokContract {
    public static final String DB_NAME = "hanok-20151017-1300.db";
    public static final int DB_VERSION = 1;
    public static final String AUTHORITY = "com.seoul.hanokmania.HanokProvider";
    public static final String[] TABLES= {"hanok", "bukchon_hanok", "repair_hanok",
                                            "user_hanok", "housetypecode",
                "hanok_hanok_bukchon", "hanok_hanok_repair", "hanok_bukchon_repair"};
    public static String TABLE= "";
    public static Uri CONTENT_URI= Uri.parse("");
    public static final int TASKS_LIST=1;
    public static final int TASKS_ITEM=2;
    public static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/seoul.hanokdb/";
    public static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/seoul/hanokdb";

    public HanokContract() {}

    /**
     * For manipulating multiple tables
     *
     * @param strDb
     */
    public static void setHanokContract(String strDb){

        TABLE= strDb;

        int index= Arrays.asList(TABLES).indexOf(strDb);

        CONTENT_URI= Uri.parse("content://" + AUTHORITY + "/"+ TABLES[index]);

//        TASKS_LIST= index* 10+ 1;
//        TASKS_ITEM= index* 10+ 2;

        CONTENT_TYPE+= TABLES[index];
        CONTENT_ITEM_TYPE+= TABLES[index];
    }


    public static class HanokCol implements BaseColumns {
        // 13 columns(fields) for table
        //public static final String _ID= BaseColumns._ID;
        public static final String HANOKNUM= "hanoknum";    //등록번호
        public static final String ADDR= "addr";	        //주소
        public static final String PLOTTAGE= "plottage";	//대지면적
        public static final String TOTAR= "totar";	        //연면적(㎡)
        public static final String BUILDAREA= "buildarea";	//건축면적(㎡)
        public static final String FLOOR= "floor";	        // 층수(지상)
        public static final String FLOOR2= "floor2";	    //층수(지하)
        public static final String USE= "use";	            //용도
        public static final String STRUCTURE= "structure";	//구조
        public static final String PLANTYPE= "plantype";	//평면형식
        public static final String BUILDDATE= "builddate";	//건립일
        public static final String NOTE= "note";	        //비고
        public static final String HANOKNUM2= "hanoknum2";	//북촌용 ID

    }

    public static class HanokBukchonCol implements BaseColumns {
        // 17 columns(fields) for table
        public static final String HOUSE_TYPE= "house_type";	    //건물 종류코드
        public static final String TYPE_NAME= "type_name";	        //건물 종류명
        public static final String LANGUAGE_TYPE= "language_type";	//언어별 구분코드
        public static final String HOUSE_ID= "house_id";	        //건물 고유아이디
        public static final String HOUSE_NAME= "house_name";	    //건물 이름
        public static final String HOUSE_ADDR= "house_addr";	    //건물 주소
        public static final String HOUSE_OWNER= "house_owner";	    //건물 소유주
        public static final String HOUSE_ADMIN= "house_admin";	    //건물 관리자
        public static final String HOUSE_TELL= "house_tell";	    //건물 연락처
        public static final String HOUSE_HP= "house_hp";	        //건물 홈페이지
        public static final String HOUSE_OPEN_TIME= "house_open_time";	//건물 개방 시간
        public static final String HOUSE_REG_DATE= "house_reg_date";	//건물 지정일
        public static final String HOUSE_YEAR= "house_year";	    //건물 연대
        public static final String BOOL_CULTURE= "bool_culture";	//문화재지정내용
        public static final String HOUSE_CONTENT= "house_content";	//건물 설명
        public static final String SERVICE_OK= "service_ok";	    //SERVICE
        public static final String PRIORITY= "priority";	        //중요도

    }

    public static class HanokRepairCol implements BaseColumns {
        // 10 columns(fields) for table
        public static final String HANOKNUM= "hanoknum";	//등록번호
        public static final String SN= "sn";	            //차수별
        public static final String ADDR= "addr";	        //주소
        public static final String ITEM= "item";	        //안건
        public static final String CONSTRUCTION= "construction";	//공사종별
        public static final String REQUEST= "request";	    //신청내용
        public static final String REVIEW= "review";	    //심의내용
        public static final String RESULT= "result";	    //심의결과
        public static final String LOANDEC= "loandec";	    //비용지원결정
        public static final String NOTE= "note";	        //비고

    }

    public static class HanokUserCol implements BaseColumns {
        // 4 columns(fields) for table
        public static final String TIME= "time";            //YYYY-MM-DD
        public static final String PHOTO_URI= "photo_uri";
        public static final String PHOTO_ID= "photo_id";
        public static final String PRICE= "price";

    }

    public static class HanokHouseTypeCodeCol implements BaseColumns {
        // 4 columns(fields) for table
        public static final String HOUSETYPE= "housetype";
        public static final String NAMEKIND= "namekind";

    }


}