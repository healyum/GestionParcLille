package com.lille1.bermont.gestionparclille;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    ArrayAdapter<String> adapter;
    private Boolean firstTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Autoriser les permissions à partir d'Android 6
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 2);
            }
        }

        isFirstTime();

        // Supprimer tous les problèmes
        // List<Problem> problems = Problem.listAll(Problem.class);
        //Problem.deleteAll(Problem.class);

        // Tableau de données
        final List<Problem> allProblems = Problem.listAll(Problem.class);

        final ArrayList<String> listeProblem = new ArrayList<>();
        for(Problem problem:allProblems){
            listeProblem.add(problem.description);
        }

        // Listview
        mListView = (ListView) findViewById(R.id.listView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listeProblem);
        mListView.setAdapter(adapter);

        // Afficher détail d'un problème
        mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProblemDetails.class);
                Problem tProblem = allProblems.get(position);
                intent.putExtra(BundleKey.PROBLEM_ITEM,tProblem);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        /* Méthode très sale pour raffrichir liste après ajout qui recrée la liste car notifyDataSetChanged rencontre un bug */
        List<Problem> allProblems = Problem.listAll(Problem.class);

        final ArrayList<String> listeProblem = new ArrayList<>();
        for(Problem problem:allProblems){
            listeProblem.add(problem.description);
        }

        // Récupération de la ListView
        ListView mListView = (ListView)findViewById(R.id.listView);

        // Création d'un adapter à l'aide d'un tableau de String (myList)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeProblem);

        // Affectation de l'adapter à la liste view
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    /**
     * Vérifie si l'utilisateur ouvre l'application pour la première fois
     * La méthode peut être appelée plusieurs depuis une même activité sans ré-exectuer le code
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