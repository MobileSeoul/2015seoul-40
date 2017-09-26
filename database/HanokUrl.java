package com.seoul.hanokmania.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoul.hanokmania.fragments.apiurl.Api;
import com.seoul.hanokmania.models.Hanok;
import com.seoul.hanokmania.models.HanokBukchon;
import com.seoul.hanokmania.models.HanokRepair;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.provider.HanokUrlHelper;
import com.seoul.hanokmania.query.QueryContract;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by namudak on 2015-09-20.
 */
public class HanokUrl {

    Context mContext= null;
    HanokUrlHelper mDbHelper= null;

    public HanokUrl(Context context, HanokUrlHelper helper){
        this.mContext= context;
        mDbHelper= helper;
    }

    /**
     * Make db from seoul city database
     *
     */
    public void UpdateHanokData() {

        try {
            ContentValues values = new ContentValues();
            int nCase;

            // *** Hanok *** HTTP 에서 내용을 String 으로 받아 온다
            String jsonString = getResponse(
                    String.format(Api.URL_HANOK, Api.Key_Hanok));

            JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokStatus");
            JSONArray jsonArray = jsonObject.getJSONArray("row");

            ObjectMapper objectMapper = new ObjectMapper();

            List<Hanok> hanoklist = objectMapper.readValue(jsonArray.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, Hanok.class
                    ));

            int leng = 0;

            // All of 'hanoknum' field delimited by ','
            String numAll= getHanokAllQuery(QueryContract.QUERYHANOKALL);

            for (int i = 0; i < hanoklist.size(); i++) {
                String tempStr = hanoklist.get(i).toString();
                Hanok hanok = hanoklist.get(i);

                if (numAll.contains(hanok.HANOKNUM)) {
                    if(tempStr.equals(getQueryById(
                        QueryContract.QUERYHANOKALL, hanok.HANOKNUM)
                    )) {// ok next
                        continue;
                    } else {// rec for update
                        nCase= 0;
                    }
                } else {// new rec for insertion
                    nCase = 1;
                }

                values.clear();

                values.put(HanokContract.HanokCol.HANOKNUM, hanok.HANOKNUM);
                values.put(HanokContract.HanokCol.ADDR, hanok.ADDR);
                values.put(HanokContract.HanokCol.PLOTTAGE, hanok.PLOTTAGE);
                values.put(HanokContract.HanokCol.TOTAR, hanok.TOTAR);
                values.put(HanokContract.HanokCol.BUILDAREA, hanok.BUILDAREA);
                values.put(HanokContract.HanokCol.FLOOR, hanok.FLOOR);
                values.put(HanokContract.HanokCol.FLOOR2, hanok.FLOOR2);
                values.put(HanokContract.HanokCol.USE, hanok.USE);
                values.put(HanokContract.HanokCol.STRUCTURE, hanok.STRUCTURE);
                values.put(HanokContract.HanokCol.PLANTYPE, hanok.PLANTYPE);
                values.put(HanokContract.HanokCol.BUILDDATE, hanok.BUILDDATE);
                values.put(HanokContract.HanokCol.NOTE, hanok.NOTE);

                leng = hanok.HANOKNUM.length();
                if (leng > 1) {
                    values.put(HanokContract.HanokCol.HANOKNUM2,
                            hanok.HANOKNUM.substring(5, leng));
                } else {
                    values.put(HanokContract.HanokCol.HANOKNUM2, "");
                }

                HanokContract.setHanokContract("hanok");
                if(nCase== 0) {
                    mContext.getContentResolver().update(HanokContract.CONTENT_URI, values,
                                                    "hanoknum=?", new String[]{hanok.HANOKNUM});
                } else {
                    mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
                }
            }

            // *** HanokBukchon *** HTTP 에서 내용을 String 으로 받아 온다
            // '01' '02' '03' '04' '05'(주거, 전통, 교육/문화, 자연, 예술)
            // '11' '12' '13'(고궁, 건축물, 공원)
            String[] house_type = {"01", "02", "03", "04", "05", "11", "12", "13"};
            for (int j = 0; j < house_type.length; j++) {
                jsonString = getResponse(
                        String.format(Api.URL_HANOK_N, Api.Key_Hanok_N, house_type[j]));

                if (!jsonString.contains("INFO-000"))
                    continue;

                jsonObject = new JSONObject(jsonString).getJSONObject("BukchonHanokVillageInfo");

                jsonArray = jsonObject.getJSONArray("row");

                objectMapper = new ObjectMapper();

                List<HanokBukchon> hanokBukchonList = objectMapper.readValue(jsonArray.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, HanokBukchon.class
                        ));

                // All of 'house_id' field delimited by ','
                String idAll = getHanokAllQuery(QueryContract.QUERYBUKCHONHANOKALL);

                for (int jj = 0; jj < hanokBukchonList.size(); jj++) {
                    String tempStr = hanoklist.get(jj).toString();
                    HanokBukchon hanokBukchon = hanokBukchonList.get(jj);

                    if (idAll.contains(hanokBukchon.HOUSE_ID)) {
                        if(tempStr.equals(getQueryById(
                                QueryContract.QUERYBUKCHONHANOKALL, hanokBukchon.HOUSE_ID
                        ))) {// ok next
                            continue;
                        } else {// rec for update
                            nCase= 0;
                        }
                    } else {// new rec for insertion
                        nCase = 1;
                    }

                    values.clear();

                    values.put(HanokContract.HanokBukchonCol.HOUSE_TYPE, hanokBukchon.HOUSE_TYPE);
                    values.put(HanokContract.HanokBukchonCol.TYPE_NAME, hanokBukchon.TYPE_NAME);
                    values.put(HanokContract.HanokBukchonCol.LANGUAGE_TYPE, hanokBukchon.LANGUAGE_TYPE);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ID, hanokBukchon.HOUSE_ID);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_NAME, hanokBukchon.HOUSE_NAME);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ADDR, hanokBukchon.HOUSE_ADDR);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_OWNER, hanokBukchon.HOUSE_OWNER);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ADMIN, hanokBukchon.HOUSE_ADMIN);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_TELL, hanokBukchon.HOUSE_TELL);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_HP, hanokBukchon.HOUSE_HP);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_OPEN_TIME, hanokBukchon.HOUSE_OPEN_TIME);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_REG_DATE, hanokBukchon.HOUSE_REG_DATE);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_YEAR, hanokBukchon.HOUSE_YEAR);
                    values.put(HanokContract.HanokBukchonCol.BOOL_CULTURE, hanokBukchon.BOOL_CULTURE);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_CONTENT, hanokBukchon.HOUSE_CONTENT);
                    values.put(HanokContract.HanokBukchonCol.SERVICE_OK, hanokBukchon.SERVICE_OK);
                    values.put(HanokContract.HanokBukchonCol.PRIORITY, hanokBukchon.PRIORITY);

                    HanokContract.setHanokContract("bukchon_hanok");
                    if(nCase== 0) {
                        mContext.getContentResolver().update(HanokContract.CONTENT_URI, values,
                                "house_id=?", new String[]{hanokBukchon.HOUSE_ID});
                    } else {
                        mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
                    }
                }
            }

            // *** HanokRepair *** HTTP 에서 내용을 String 으로 받아 온다
            String[] fromTo = {"1", "999", "1997"};
            for (int k = 0; k < fromTo.length - 1; k++) {
                jsonString = getResponse(
                        String.format(Api.URL_HANOK_R, Api.Key_Hanok_R, fromTo[k], fromTo[k + 1]));

                jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokRepairAdvice");
                jsonArray = jsonObject.getJSONArray("row");

                objectMapper = new ObjectMapper();

                List<HanokRepair> hanokRepairList = objectMapper.readValue(jsonArray.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, HanokRepair.class
                        ));

                // All of 'hanoknum' field delimited by ','
                numAll= getHanokAllQuery(QueryContract.QUERYHANOKREPAIRALL);

                for (int kk = 0; kk < hanokRepairList.size(); kk++) {
                    String str = hanokRepairList.get(kk).toString();
                    HanokRepair hanokRepair = hanokRepairList.get(kk);

                    if (numAll.contains(hanokRepair.HANOKNUM)) {
                        if(str.equals(getQueryById(
                                QueryContract.QUERYHANOKREPAIRALL, hanokRepair.HANOKNUM
                        ))) {// ok next
                            continue;
                        } else {// rec for update
                            nCase= 0;
                        }
                    } else {// new rec for insertion
                        nCase = 1;
                    }

                    values.clear();

                    values.put(HanokContract.HanokRepairCol.HANOKNUM, hanokRepair.HANOKNUM);
                    values.put(HanokContract.HanokRepairCol.SN, hanokRepair.SN);
                    values.put(HanokContract.HanokRepairCol.ADDR, hanokRepair.ADDR);
                    values.put(HanokContract.HanokRepairCol.ITEM, hanokRepair.ITEM);
                    values.put(HanokContract.HanokRepairCol.CONSTRUCTION, hanokRepair.CONSTRUCTION);
                    values.put(HanokContract.HanokRepairCol.REQUEST, hanokRepair.REQUEST);
                    values.put(HanokContract.HanokRepairCol.REVIEW, hanokRepair.REVIEW);
                    values.put(HanokContract.HanokRepairCol.RESULT, hanokRepair.RESULT);
                    values.put(HanokContract.HanokRepairCol.LOANDEC, hanokRepair.LOANDEC);
                    values.put(HanokContract.HanokRepairCol.NOTE, hanokRepair.NOTE);

                    HanokContract.setHanokContract("repair_hanok");
                    if(nCase== 0) {
                        mContext.getContentResolver().update(HanokContract.CONTENT_URI, values,
                                "hanoknum=?", new String[]{hanokRepair.HANOKNUM});
                    } else {
                        mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Make db from seoul city database
     *
     */
    public void MakeHanokData() {

        try {
            Uri uri;
            String[] valueArray= null;
            ContentValues values = new ContentValues();

            // *** Hanok *** HTTP 에서 내용을 String 으로 받아 온다
            String jsonString = getResponse(
                    String.format(Api.URL_HANOK, Api.Key_Hanok));

            JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokStatus");
            JSONArray jsonArray = jsonObject.getJSONArray("row");

            ObjectMapper objectMapper = new ObjectMapper();

            List<Hanok> hanoklist = objectMapper.readValue(jsonArray.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, Hanok.class
                    ));

            int leng= 0;
            for(Hanok hanok : hanoklist) {
                values.clear();

                values.put(HanokContract.HanokCol.HANOKNUM, hanok.HANOKNUM);
                values.put(HanokContract.HanokCol.ADDR, hanok.ADDR);
                values.put(HanokContract.HanokCol.PLOTTAGE, hanok.PLOTTAGE);
                values.put(HanokContract.HanokCol.TOTAR, hanok.TOTAR);
                values.put(HanokContract.HanokCol.BUILDAREA, hanok.BUILDAREA);
                values.put(HanokContract.HanokCol.FLOOR, hanok.FLOOR);
                values.put(HanokContract.HanokCol.FLOOR2, hanok.FLOOR2);
                values.put(HanokContract.HanokCol.USE, hanok.USE);
                values.put(HanokContract.HanokCol.STRUCTURE, hanok.STRUCTURE);
                values.put(HanokContract.HanokCol.PLANTYPE, hanok.PLANTYPE);
                values.put(HanokContract.HanokCol.BUILDDATE, hanok.BUILDDATE);
                values.put(HanokContract.HanokCol.NOTE, hanok.NOTE);
                leng= hanok.HANOKNUM.length();
                if(leng> 1) {
                    values.put(HanokContract.HanokCol.HANOKNUM2,
                            hanok.HANOKNUM.substring(5, leng));
                } else {
                    values.put(HanokContract.HanokCol.HANOKNUM2, "");
                }
                HanokContract.setHanokContract("hanok");
                mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
            }


            // *** HanokBukchon *** HTTP 에서 내용을 String 으로 받아 온다
            // '01' '02' '03' '04' '05'(주거, 전통, 교육/문화, 자연, 예술)
            // '11' '12' '13'(고궁, 건축물, 공원)
            String[] house_type= {"01", "02", "03", "04", "05", "11", "12", "13"};
            for(int j= 0; j< house_type.length; j++ ) {
                jsonString = getResponse(
                        String.format(Api.URL_HANOK_N, Api.Key_Hanok_N, house_type[j]));

                if(!jsonString.contains("INFO-000"))
                    continue;

                jsonObject = new JSONObject(jsonString).getJSONObject("BukchonHanokVillageInfo");

                jsonArray = jsonObject.getJSONArray("row");

                objectMapper = new ObjectMapper();

                List<HanokBukchon> hanokBukchonList= objectMapper.readValue(jsonArray.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, HanokBukchon.class
                        ));

                for(int jj= 0; jj< hanokBukchonList.size(); jj++) {
                    HanokBukchon hanokBukchon = hanokBukchonList.get(jj);
                    values.clear();

                    values.put(HanokContract.HanokBukchonCol.HOUSE_TYPE, hanokBukchon.HOUSE_TYPE);
                    values.put(HanokContract.HanokBukchonCol.TYPE_NAME, hanokBukchon.TYPE_NAME);
                    values.put(HanokContract.HanokBukchonCol.LANGUAGE_TYPE, hanokBukchon.LANGUAGE_TYPE);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ID, hanokBukchon.HOUSE_ID);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_NAME, hanokBukchon.HOUSE_NAME);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ADDR, hanokBukchon.HOUSE_ADDR);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_OWNER, hanokBukchon.HOUSE_OWNER);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ADMIN, hanokBukchon.HOUSE_ADMIN);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_TELL, hanokBukchon.HOUSE_TELL);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_HP, hanokBukchon.HOUSE_HP);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_OPEN_TIME, hanokBukchon.HOUSE_OPEN_TIME);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_REG_DATE, hanokBukchon.HOUSE_REG_DATE);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_YEAR, hanokBukchon.HOUSE_YEAR);
                    values.put(HanokContract.HanokBukchonCol.BOOL_CULTURE, hanokBukchon.BOOL_CULTURE);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_CONTENT, hanokBukchon.HOUSE_CONTENT);
                    values.put(HanokContract.HanokBukchonCol.SERVICE_OK, hanokBukchon.SERVICE_OK);
                    values.put(HanokContract.HanokBukchonCol.PRIORITY, hanokBukchon.PRIORITY);

                    HanokContract.setHanokContract("bukchon_hanok");
                    mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
                }
            }

            // *** HanokRepair *** HTTP 에서 내용을 String 으로 받아 온다
            String[] fromTo= {"1", "999", "1997"};
            for(int k= 0; k< fromTo.length- 1; k++) {
                jsonString = getResponse(
                        String.format(Api.URL_HANOK_R, Api.Key_Hanok_R, fromTo[k], fromTo[k+ 1]));

                jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokRepairAdvice");
                jsonArray = jsonObject.getJSONArray("row");

                objectMapper = new ObjectMapper();

                List<HanokRepair> hanokRepairList = objectMapper.readValue(jsonArray.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, HanokRepair.class
                        ));

                for (int kk = 0; kk < hanokRepairList.size(); kk++) {
                    HanokRepair hanokRepair = hanokRepairList.get(kk);

                    values.clear();

                    values.put(HanokContract.HanokRepairCol.HANOKNUM, hanokRepair.HANOKNUM);
                    values.put(HanokContract.HanokRepairCol.SN, hanokRepair.SN);
                    values.put(HanokContract.HanokRepairCol.ADDR, hanokRepair.ADDR);
                    values.put(HanokContract.HanokRepairCol.ITEM, hanokRepair.ITEM);
                    values.put(HanokContract.HanokRepairCol.CONSTRUCTION, hanokRepair.CONSTRUCTION);
                    values.put(HanokContract.HanokRepairCol.REQUEST, hanokRepair.REQUEST);
                    values.put(HanokContract.HanokRepairCol.REVIEW, hanokRepair.REVIEW);
                    values.put(HanokContract.HanokRepairCol.RESULT, hanokRepair.RESULT);
                    values.put(HanokContract.HanokRepairCol.LOANDEC, hanokRepair.LOANDEC);
                    values.put(HanokContract.HanokRepairCol.NOTE, hanokRepair.NOTE);

                    HanokContract.setHanokContract("repair_hanok");
                    mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * get hanoknum, house_id, hanoknum for 'hanok', 'bukchon_hanok', 'hanok_repair'
     * on querymode
     */
    private String getHanokAllQuery(int querymode) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                QueryContract.mQuery[querymode],
                null
        );

        String val = "";
        String colName= "";
        switch (querymode){
            case QueryContract.QUERYHANOKALL:
                colName= HanokContract.HanokCol.HANOKNUM;
                break;
            case QueryContract.QUERYBUKCHONHANOKALL:
                colName= HanokContract.HanokBukchonCol.HOUSE_ID;
                break;
            case QueryContract.QUERYHANOKREPAIRALL:
                colName= HanokContract.HanokRepairCol.HANOKNUM;
                break;
        }
        while (cursor.moveToNext()) {
            val= cursor.getString(cursor.getColumnIndexOrThrow(
                    colName)
            );
            val+= ",";
        }

        cursor.close();

        return val;
    }
    /**
     * get record for 'hanok', 'bukchon_hanok', 'hanok_repair'
     * on querymode
     */
    private String getQueryById(int querymode, String id) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String val = "";
        String query= "";
        switch (querymode){
            case QueryContract.QUERYHANOKALL:
                query= String.format(QueryContract.mQueryById,
                        "hanok", "hanoknum", id );
                break;
            case QueryContract.QUERYBUKCHONHANOKALL:
                query= String.format(QueryContract.mQueryById,
                        "bukchon_hanok", "house_id", id );
                break;
            case QueryContract.QUERYHANOKREPAIRALL:
                query= String.format(QueryContract.mQueryById,
                        "hanok_repair", "hanoknum", id );
                break;
        }

        Cursor cursor = db.rawQuery(
                query,
                null
        );

        while (cursor.moveToNext()) {
            for(int i= 0; i< cursor.getColumnCount(); i++) {
                val += cursor.getString(i)+ ",";
            }
        }

        cursor.close();

        return val;
    }
    /**
     * url 로 부터 스트림을 읽어 String 으로 반환한다
     * @param url
     * @return
     * @throws IOException
     */
    private String getResponse(String url) throws IOException {

        // 클라이언트 오브젝트
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }


}
