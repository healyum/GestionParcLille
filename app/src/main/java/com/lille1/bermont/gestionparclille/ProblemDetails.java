package com.lille1.bermont.gestionparclille;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by USER on 10/12/2017.
 */

public class ProblemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_details);

        Button btn_itineraire = (Button) findViewById(R.id.btn_showmap);
        btn_itineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPositionOnMap();
            }
        });

        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProblem();
            }
        });

        //String pb_description = getIntent().getExtras().getString("pb_desc","Pas de description");
        Problem object_problem = (Problem) getIntent().getExtras().getSerializable(BundleKey.PROBLEM_ITEM);
        TextView problemType = (TextView) findViewById(R.id.problemTypeValue);
        TextView problemDescription = (TextView) findViewById(R.id.problemDescValue);
        TextView problemLatitude = (TextView) findViewById(R.id.problemLatValue);
        TextView problemLongitude = (TextView) findViewById(R.id.problemLongValue);

        problemType.setText(object_problem.typeProbleme);
        problemDescription.setText(object_problem.description);
        problemLatitude.setText(object_problem.posLatitute);
        problemLongitude.setText(object_problem.posLongitude);

    }

    private void showPositionOnMap() {
        // Affichage d'une position
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

    public void deleteProblem() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous vraiment supprimer ce problème ?").setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
    }
}