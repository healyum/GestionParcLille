package com.lille1.bermont.gestionparclille;

import com.orm.SugarRecord;

/**
 * Created by USER on 10/12/2017.
 */

public class Problem extends SugarRecord {
    String typeProbleme;
    String posLatitute;
    String posLongitude;
    String description;

    public Problem(){

    }

    public Problem(String typeProbleme, String posLatitute, String posLongitude, String description) {
        this.typeProbleme = typeProbleme;
        this.posLatitute = posLatitute;
        this.posLongitude = posLongitude;
        this.description = description;
    }
}
