package com.lille1.bermont.gestionparclille;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benjamin Bermont
 *         Classe principale, point d'entrée de l'application. S'occupe de gérer les permissions, d'afficher la listView avec les problèmes.
 *         Crée une liste de problèmes fictifs au premier démarrage de l'applicatio.
 */

public class MainActivity extends AppCompatActivity {

    final ArrayList<String> listeProblem = new ArrayList<>();
    ListView mListView;
    ArrayAdapter<String> adapter;
    List<Problem> allProblems;
    private Boolean firstTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Autoriser les permissions à partir d'Android 6
        // Les permissions Fine et coarse servent à récupérer la position du GPS
        // tandis que la permission internet est utilisée pour changer cette position en une vraie adresse
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 3);
            }
        }

        FloatingActionButton btn_add = (FloatingActionButton) findViewById(R.id.problemAdd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProblem(view);
            }
        });

        // Fixture : Créer des données fictives uniquement au premier lancement de l'application
        isFirstTime();

        // Récupère tous les données de la table Problem
        allProblems = Problem.listAll(Problem.class);

        // Pour chaque problème on ajoute la description
        for (Problem problem : allProblems) {
            listeProblem.add(problem.description);
        }

        mListView = (ListView) findViewById(R.id.listView);

        // On passe notre liste à un adapter
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listeProblem);
        mListView.setAdapter(adapter);

        // Evènement lors du clique sur un problème. On transmet l'objet problème sélectionné à l'activité ProblemDetails pour afficher plus d'informations
        mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProblemDetails.class);
                Problem tProblem = allProblems.get(position);
                intent.putExtra(BundleKey.PROBLEM_ITEM, tProblem);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        // De retour sur l'activité, on récupère les problèmes stockés en base de données, on efface les anciennes données et on met à jour l'adapter
        allProblems = Problem.listAll(Problem.class);
        listeProblem.clear();
        for (Problem problem : allProblems) {
            listeProblem.add(problem.description);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Vérifie si l'utilisateur ouvre l'application pour la première fois
     * La méthode peut être appelée plusieurs depuis une même activité sans executer le code de nouveau, on se souvient qu'il a déjà été executé
     *
     * @return boolean
     */
    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();

                // Fixture
                // Ajouter un problème en BDD
                // new Problem(type, latitude, longitude, description, adresse)

                Problem problemMock1 = new Problem("Haie à tailler", "50.606868", "3.133743", "Haie bloquant le passage", "Halle Grémeaux, Avenue Paul Langevin");
                Problem problemMock2 = new Problem("Mauvaise herbe", "50.609667", "3.143796", "Herbe peu entretenue", "Bâtiment C9, Avenue Paul Langevin");
                Problem problemMock3 = new Problem("Détritus", "50.608318", "3.145545", "Poubelle non vidée", "Bâtiment SN4, Avenue Paul Langevin");
                Problem problemMock4 = new Problem("Arbre à abattre", "50.611171", "3.144343", "Arbre dangereux", "Bâtiment C4, Avenue Paul Langevin");
                Problem problemMock5 = new Problem("Autre", "50.605364", "3.133668", "Travaux", "Bâtiment B5, Avenue Paul Langevin");
                Problem problemMock6 = new Problem("Haie à tailler", "50.608965", "3.139054", "Haie emcombrante", "Bâtiment M1, Avenue Paul Langevin");
                Problem problemMock7 = new Problem("Détritus", "50.609413", "3.136318", "Poubelles non vidées", "Bâtiment M5, Avenue Paul Langevin");
                Problem problemMock8 = new Problem("Mauvaise herbe", "50.610925", "3.140373", "Herbe abîmée", "Bâtiment P5, Avenue Paul Langevin");
                Problem problemMock9 = new Problem("Autre", "50.609013", "3.136457", "Barrière sur le passage", "SEMM, Avenue Paul Langevin");
                Problem problemMock10 = new Problem("Mauvaise herbe", "50.608188", "3.140395", "Herbe malade", "A3, Avenue Paul Langevin");

                problemMock1.save();
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
        }
        return firstTime;
    }

    /* Appel de la vue pour ajouter un problème */
    public void addProblem(View view) {
        Intent intent = new Intent(this, ProblemAdd.class);
        startActivity(intent);
    }
}