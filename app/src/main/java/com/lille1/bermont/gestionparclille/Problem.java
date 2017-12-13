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

    public String getTypeProbleme() {
        return typeProbleme;
    }

    public void setTypeProbleme(String typeProbleme) {
        this.typeProbleme = typeProbleme;
    }

    public String getPosLatitute() {
        return posLatitute;
    }

    public void setPosLatitute(String posLatitute) {
        this.posLatitute = posLatitute;
    }

    public String getPosLongitude() {
        return posLongitude;
    }

    public void setPosLongitude(String posLongitude) {
        this.posLongitude = posLongitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
