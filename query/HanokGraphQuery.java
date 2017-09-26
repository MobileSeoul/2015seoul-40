package com.seoul.hanokmania.query;

import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
public class HanokGraphQuery implements I_Query{
    private Sequel hanok;

    public HanokGraphQuery(Sequel aQuery){
        this.hanok = aQuery;
    }

    public List execute() {return hanok.selectGraph();}

}
