package com.seoul.hanokmania.query;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namudak on 2015-10-20.
 */
public class Sequel {

    private Context mContext;

    public Sequel(Context context) {
        mContext= context;
    }

    public List selectText(){

        List list= new ArrayList<>();
        try {
            list =new HanokTextTask(mContext).execute().get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        return list;
    }

    public List selectGraph(){

        List list= new ArrayList<>();
        try {
            list =new HanokGraphTask(mContext).execute().get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        return list;
    }


}
