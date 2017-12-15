package com.lille1.bermont.gestionparclille;

import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author Benjamin Bermont
 *         Classe qui s'occupe de l'ajout d'un nouveau problème
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

        // Envoie un problème pour le sauvegarder
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_probleme, android.R.layout.simple_spinner_item);
        // Indique le layout à utiliser quand la liste des choix apparaît
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applique l'adapter sur le spinner
        spinner.setAdapter(adapter);

        // Gestion et affichage du GPS
        m_text = (TextView) findViewById(R.id.affichage_coordonnees);
        m_gps = new GPS(this);

        // On met à jour la localisation
        updateLocation();
    }

    /**
     * Méthode pour mettre à jour la localisation
     */
    private void updateLocation() {
        location = m_gps.getLocation();
        String coordonnees = "(" + location.getLatitude() + ", " + location.getLongitude() + ")";

        m_text.setText(coordonnees);
        displayCompleteAddress();
    }

    /**
     * Methde pour envoyer un nouveau problème en base de données
     */
    private void sendProblem() {
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

        if (TextUtils.isEmpty(problemType) || TextUtils.isEmpty(problemDescription) || TextUtils.isEmpty(problemLatitude) || TextUtils.isEmpty(problemLongitude) || TextUtils.isEmpty(problemAddress)) {
            Toast toast = Toast.makeText(ProblemAdd.this, "Merci de bien renseigner tous les champs.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Problem problem = new Problem(problemType, problemLatitude, problemLongitude, problemDescription, problemAddress);
            problem.save();
            finish();
        }
    }

    /**
     * Méthode pour afficher une adresse réelle à partir de coordonnées
     */
    public void displayCompleteAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(ProblemAdd.this, Locale.getDefault());

        double problemLat = location.getLatitude();
        double problemLong = location.getLongitude();
        TextView address = (TextView) findViewById(R.id.adresse_value);

        try {
            addresses = geocoder.getFromLocation(problemLat, problemLong, 1); // 1 correspond au nombre d'adresses retournées. On peut retourner plus d'adresses à proximité
            if (addresses != null && addresses.size() > 0) {
                String completeAddress = addresses.get(0).getAddressLine(0);
                address.setText(completeAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
