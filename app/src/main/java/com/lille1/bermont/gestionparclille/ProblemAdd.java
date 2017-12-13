package com.lille1.bermont.gestionparclille;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by USER on 10/12/2017.
 */

public class ProblemAdd extends AppCompatActivity {

    TextView m_text;
    GPS m_gps;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_add);

        // Affiche un itinéraire sur une carte (Google Maps)
        Button btn_itineraire = (Button) findViewById(R.id.btn_position);
        btn_itineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocation();
            }
        });

        Button btn_send_problem = (Button) findViewById(R.id.btn_send_problem);
        btn_send_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendProblem();
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

        updateLocation();
    }

    private void updateLocation()
    {
        location = m_gps.getLocation();
        String coordonnees = "(" + location.getLatitude() + ", " + location.getLongitude() + ")";

        m_text.setText(coordonnees);
    }

    private void sendProblem(){
        TextView description = (TextView) findViewById(R.id.description_pb_value);
        String problemDescription = description.getText().toString();

        Spinner selectedProblemType = (Spinner) findViewById(R.id.type_probleme);
        String problemType = selectedProblemType.getSelectedItem().toString();

        double problemLat = location.getLatitude();
        String problemLatitude = String.valueOf(problemLat);

        double problemLong = location.getLongitude();
        String problemLongitude = String.valueOf(problemLong);

        TextView address = (TextView) findViewById(R.id.adresse_value);
        String problemAddress = address.getText().toString();

        if(TextUtils.isEmpty(problemType) || TextUtils.isEmpty(problemDescription) || TextUtils.isEmpty(problemLatitude) || TextUtils.isEmpty(problemLongitude) || TextUtils.isEmpty(problemAddress)){
            Toast toast = Toast.makeText(ProblemAdd.this, "Merci de bien renseigner tous les champs.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Problem problem = new Problem(problemType, problemLatitude, problemLongitude, problemDescription, problemAddress);
            problem.save();
            finish();
        }
    }
}
