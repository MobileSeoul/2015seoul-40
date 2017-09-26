package com.seoul.hanokmania.query;

import org.achartengine.GraphicalView;

/**
 * Created by namudak on 2015-10-22.
 */
public class QueryContract {

    public static String mHanokQuery=
            "select count(*) from hanok;";

    public static String mBukchonHanokQuery=
            "select count(*) from bukchon_hanok;";

    public static String mPlottageQuery=
            "select hanoknum, addr, plottage, totar, buildarea, use, structure "+
                    "from hanok " +
                    "order by cast(plottage as integer) asc; ";

    public static String mBuilddateQuery=
            "select substr(builddate,1,4), count(*) from hanok " +
                    "group by substr(builddate, 1, 4) " +
                    "order by substr(builddate, 1, 4) desc; ";

    public static final String mHousetypeQuery=
            "select count(*) from hanok_hanok_bukchon " +
            "where house_type<> 'NULL';";

    public static String mBoolculture=
            "select count(*) from hanok_hanok_bukchon " +
            "where bool_culture<> 'NULL' and bool_culture<> '';";

    public static String mSn=
            "select substr(sn, 1, 4), count(*) from repair_hanok " +
            "group by substr(sn, 1, 4);";

    public static String mRealPlottageQuery=
            "select hanoknum, addr, plottage, totar, buildarea, use, structure " +
            "from hanok " +
            "where plottage > 0.0 " +
            "order by cast(plottage as integer) asc;";

    public static String mRealBuilddateQuery=
            "select  count(*) from hanok " +
            "where cast(substr(builddate, 1, 4) as integer)%s ? " +
            "and length(builddate)> 0";

    public static String mUseQuery=
            "select use, count(*) from hanok " +
            "group by use;";

    public static String mStructureQuery=
            "select structure, count(*) from hanok " +
                    "group by structure;";

    public static String mDeleteQuery1=
            "delete from hanok;";

    public static String mDeleteQuery2=
            "delete from bukchon_hanok;";

    public static String mDeleteQuery3=
            "delete from repair_hanok;";

    public static String mHanokAllQuery=
            "select * from hanok;";

    public static String mBukchonHanokAllQuery=
            "select * from bukchon_hanok;";

    public static String mHanokRepairAllQuery=
            "select * from repair_hanok;";

    public static String mQueryById=
            "select * from %s " +
                    "where %s= '%s'";

    public static String[] mQuery= {
            mHanokQuery,
            mBukchonHanokQuery,
            mPlottageQuery,
            mBuilddateQuery,
            mHousetypeQuery,
            mBoolculture,
            mSn,
            mRealPlottageQuery,
            mRealBuilddateQuery,
            mUseQuery,
            mStructureQuery,
            mDeleteQuery1,
            mDeleteQuery2,
            mDeleteQuery3,
            mHanokAllQuery,
            mBukchonHanokAllQuery,
            mHanokRepairAllQuery,
            mQueryById
    };

    public static final int QUERYHANOK= 0;
    public static final int QUERYBUKCHONHANOK= 1;
    public static final int QUERYPLOTTAGE= 2;
    public static final int QUERYBUILDDATE= 3;
    public static final int QUERYHOUSETYPE= 4;
    public static final int QUERYBOOLCULTURE= 5;
    public static final int QUERYSN= 6;
    public static final int QUERYREALPLOTTAGE= 7;
    public static final int QUERYREALBUILDDATE= 8;
    public static final int QUERYUSE= 9;
    public static final int QUERYSTRUCTURE= 10;
    public static final int QUERYDELETE1= 11;
    public static final int QUERYDELETE2= 12;
    public static final int QUERYDELETE3= 13;
    public static final int QUERYHANOKALL= 14;
    public static final int QUERYBUKCHONHANOKALL= 15;
    public static final int QUERYHANOKREPAIRALL= 16;
    public static final int QUERYBYID= 17;


    public static GraphicalView mChartView;

}
