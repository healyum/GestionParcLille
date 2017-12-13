package com.lille1.bermont.gestionparclille;

import android.Manifest;
import android.content.Intent;
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
        }

        // BDD
        // Ajouter un problème
        //Problem problemMock = new Problem("Détritus", "50", "3", "Poubelle renversée");
        //problemMock.save();

        // Supprimer tous les problèmes
//        List<Problem> problems = Problem.listAll(Problem.class);
        //Problem.deleteAll(Problem.class);

        // Tableau de données
        final List<Problem> allProblems = Problem.listAll(Problem.class);

        final ArrayList<String> listeProblem = new ArrayList<>();
        for(Problem problem:allProblems){
            listeProblem.add(problem.description);
//            listeProblem.add(problem.posLongitude);
//            listeProblem.add(problem.posLatitute);
//            listeProblem.add(problem.typeProbleme);
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

    /* Appel de la vue pour ajouter un problème */
    public void addProblem(View view) {
        Intent intent = new Intent(this, ProblemAdd.class);
        startActivity(intent);
    }
}