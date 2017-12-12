package com.lille1.bermont.gestionparclille;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by USER on 10/12/2017.
 */

public class ProblemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_details);

        // Affiche un itinéraire sur une carte (Google Maps)
        Button btn_itineraire = (Button) findViewById(R.id.btn_showmap);
        btn_itineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPositionOnMap();
            }
        });
    }

    private void ShowPositionOnMap() {
        // Affichage d'un itinéraire de Paris à Lille
        Uri location = Uri.parse("https://www.google.com/maps/search/?api=1&query=50.6160803, 3.1558468");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        // Vérifie qu'Android connait ce type d'activité
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Démarre une activité qui est sûre de fonctionner
        if (isIntentSafe) {
            startActivity(mapIntent);
        }
    }
}
