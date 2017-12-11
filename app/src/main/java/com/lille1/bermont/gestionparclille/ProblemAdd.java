package com.lille1.bermont.gestionparclille;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by USER on 10/12/2017.
 */

public class ProblemAdd extends AppCompatActivity {

    TextView m_text;
    GPS m_gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_add);

        // Itinialisation des boutons
        // Affiche un itinéraire sur une carte (Google Maps)
        Button btn_itineraire = (Button) findViewById(R.id.btn_showmap);
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

    }

    private void updateLocation()
    {
        Location location = m_gps.getLocation();
        String format = "(" + location.getLatitude() + ", " + location.getLongitude() + ", " + location.getAltitude() + ")";

        m_text.setText(format);
    }
}
