package com.lille1.bermont.gestionparclille;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by USER on 14/12/2017.
 */

public class ProblemMockData extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajouter un problème en BDD
        // new Problem(type, latitude, longitude, description, adresse)
        Problem problemMock = new Problem("Détritus", "50", "3", "Poubelle renversée", "");
        Problem problemMock2 = new Problem("Détritus", "50.609667", "3.143796", "Poubelle renversée", "Bâtiment C9");
        Problem problemMock3 = new Problem("Détritus", "50.608318", "3.145545", "Poubelle renversée", "Bâtiment SN4");
        Problem problemMock4 = new Problem("Détritus", "50.611171", "3.144343", "Poubelle renversée", "Bâtiment C4");
        Problem problemMock5 = new Problem("Détritus", "50.605364", "3.133668", "Poubelle renversée", "Bâtiment B5");
        Problem problemMock6 = new Problem("Détritus", "50.608965", "3.139054", "Poubelle renversée", "Bâtiment M1");
        Problem problemMock7 = new Problem("Détritus", "50.609413", "3.136318", "Poubelle renversée", "Bâtiment M5");
        Problem problemMock8 = new Problem("Détritus", "50.610925", "3.140373", "Poubelle renversée", "Bâtiment P5");
        Problem problemMock9 = new Problem("Détritus", "50.609013", "3.136457", "Poubelle renversée", "SEMM");
        Problem problemMock10 = new Problem("Détritus", "50.608188", "3.140395", "Poubelle renversée", "SEMM");

        problemMock.save();
        problemMock2.save();
        problemMock3.save();
        problemMock4.save();
        problemMock5.save();
        problemMock6.save();
        problemMock7.save();
        problemMock8.save();
        problemMock9.save();
        problemMock10.save();
    }

    // Supprimer tous les problèmes
    // List<Problem> problems = Problem.listAll(Problem.class);
    //Problem.deleteAll(Problem.class);
}
