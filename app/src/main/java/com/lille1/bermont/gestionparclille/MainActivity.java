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
        //Problem problemMock = new Problem("Détritus", "50", "3", "Poubelle renversée");
        //problemMock.save();

        //problem.findById(Problem.class, 1);
        //TextView probleme1 = (TextView) findViewById(R.id.problem1);
        //probleme1.setText(problem.description);


        // Tableau de données
        List<Problem> allProblems = Problem.listAll(Problem.class);

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
                intent.putExtra("pb_desc", listeProblem.get(position));
                intent.putExtra("pb_type", listeProblem);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        /* Méthode très sale pour raffrichir liste après ajout*/
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

        mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProblemDetails.class);
                intent.putExtra("pb_desc", listeProblem.get(position));
                intent.putExtra("pb_type", listeProblem);
                startActivity(intent);
            }
        });
    }

    /* Appel de la vue pour ajouter un problème */
    public void addProblem(View view) {
        Intent intent = new Intent(this, ProblemAdd.class);
        startActivity(intent);
    }
}