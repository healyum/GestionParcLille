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
import android.widget.Toast;

import java.util.List;

/**
 * Created by USER on 10/12/2017.
 */

public class ProblemDetails extends AppCompatActivity {

    Problem object_problem;

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


        /* On récupère l'objet problème sérialisé envoyé depuis la listView*/
        object_problem = (Problem) getIntent().getExtras().getSerializable(BundleKey.PROBLEM_ITEM);
        TextView problemType = (TextView) findViewById(R.id.problemTypeValue);
        TextView problemDescription = (TextView) findViewById(R.id.problemDescValue);
        TextView problemLatitude = (TextView) findViewById(R.id.problemLatValue);
        TextView problemLongitude = (TextView) findViewById(R.id.problemLongValue);
        TextView problemAddress = (TextView) findViewById(R.id.problemAddressValue);

        problemType.setText(object_problem.typeProbleme);
        problemDescription.setText(object_problem.description);
        problemLatitude.setText(object_problem.posLatitute);
        problemLongitude.setText(object_problem.posLongitude);
        problemAddress.setText(object_problem.address);

        //List<Problem> pt = Problem.find(Problem.class, "description = ?", object_problem.description);


        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProblem();
            }
        });
    }

    private void showPositionOnMap() {
        // Affichage d'une position
        Uri location = Uri.parse("https://www.google.com/maps/search/?api=1&query="+ object_problem.posLatitute +", "+ object_problem.posLongitude+"");
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
                    // Réponse positive à la suppresion
                    case DialogInterface.BUTTON_POSITIVE:
                        Problem.executeQuery("DELETE FROM PROBLEM WHERE DESCRIPTION = '" + object_problem.description + "'");
                        Toast toast = Toast.makeText(ProblemDetails.this, "Le problème a bien été supprimé", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                        break;

                    // Annulation de la suppresion
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous vraiment supprimer ce problème ?").setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
    }
}