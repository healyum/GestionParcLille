package com.lille1.bermont.gestionparclille;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView m_text;
    GPS m_gps;

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

        // Itinialisation des boutons
        // Affiche un itinéraire sur une carte (Google Maps)
        Button btn_itineraire = (Button) findViewById(R.id.btn_position);
        btn_itineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocation();
            }
        });

        // Gestion du Spinner
        Spinner spinner = (Spinner) findViewById(R.id.type_probleme);
        // Crée un ArrayAdapter utilisant le tableau de string et le layout par defaut du spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_probleme, android.R.layout.simple_spinner_item);
        // Indique le layout à utiliser quand la liste des choix apparaît
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applique l'adapter sur le spinner
        spinner.setAdapter(adapter);

        // Gestion du GPS
        m_text = (TextView) findViewById(R.id.affichage_coordonnees);
        m_gps = new GPS(this);

        // BDD
        Problem problem = new Problem("Arbre à tailler", "50", "3", "Gros arbre à tailler");
        problem.save();

        problem.findById(Problem.class, 1);
        TextView probleme1 = (TextView) findViewById(R.id.probleme1);
        probleme1.setText(problem.description);

    }

    private void updateLocation()
    {
        Location location = m_gps.getLocation();
        String format = "(" + location.getLatitude() + ", " + location.getLongitude() + ", " + location.getAltitude() + ")";

        m_text.setText(format);
    }

}