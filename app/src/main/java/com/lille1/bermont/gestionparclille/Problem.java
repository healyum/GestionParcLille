package com.lille1.bermont.gestionparclille;

import android.content.Context;

import com.orm.SugarRecord;

import java.util.List;

import static com.orm.util.ReflectionUtil.getDomainClasses;

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

    public static void deleteAllrecords(Context applicationContext) {
        List<Class> domainClasses = getDomainClasses(applicationContext);
        for (Class domain : domainClasses) {
            SugarRecord.deleteAll(domain);
        }
    }
}
