package com.lille1.bermont.gestionparclille;

import android.content.Context;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

import static com.orm.util.ReflectionUtil.getDomainClasses;

/**
 * @author Benjamin Bermont
 *         Classe Problem qui mappe un objet Problem avec une bdd SQLITE via l'orm SugarORM
 *         SugarORM crée la bdd en se basant sur la structure de l'objet Problem
 */

public class Problem extends SugarRecord implements Serializable {
    String typeProbleme;
    String posLatitute;
    String posLongitude;
    String description;
    String address;

    public Problem() {
    }

    /**
     * @param typeProbleme
     * @param posLatitute
     * @param posLongitude
     * @param description
     * @param address
     */
    public Problem(String typeProbleme, String posLatitute, String posLongitude, String description, String address) {
        this.typeProbleme = typeProbleme;
        this.posLatitute = posLatitute;
        this.posLongitude = posLongitude;
        this.description = description;
        this.address = address;
    }

    /**
     * Méthode pour supprimer tous les enregistrements de la base de données. Inutilisée
     * Utilisation : Problem.deleteAllrecords(this);
     *
     * @param applicationContext
     */
    public static void deleteAllrecords(Context applicationContext) {
        List<Class> domainClasses = getDomainClasses(applicationContext);
        for (Class domain : domainClasses) {
            SugarRecord.deleteAll(domain);
        }
    }
}
