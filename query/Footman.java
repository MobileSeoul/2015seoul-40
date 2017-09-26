package com.seoul.hanokmania.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-20.
 */
public class Footman {

    private List<I_Query> queryList = new ArrayList<I_Query>();

    public void takeQuery(I_Query query){
        queryList.add(query);
    }

    public List placeQueries(){
        List list;

        for (I_Query query : queryList) {
            return list = query.execute();
        }
        queryList.clear();

        return null;

    }
}


