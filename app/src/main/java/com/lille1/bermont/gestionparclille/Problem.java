package com.lille1.bermont.gestionparclille;

import android.content.Context;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

import static com.orm.util.ReflectionUtil.getDomainClasses;

/**
 * Created by USER on 10/12/2017.
 */

public class Problem extends SugarRecord implements Serializable {
    String typeProbleme;
    String posLatitute;
    String posLongitude;
    String description;
    String address;

    public Problem(){
    }

    public Problem(String typeProbleme, String posLatitute, String posLongitude, String description, String address) {
        this.typeProbleme = typeProbleme;
        this.posLatitute = posLatitute;
        this.posLongitude = posLongitude;
        this.description = description;
        this.address = address;
    }

    public static void deleteAllrecords(Context applicationContext) {
        List<Class> domainClasses = getDomainClasses(applicationContext);
        for (Class domain : domainClasses) {
            SugarRecord.deleteAll(domain);
        }
    }
}
