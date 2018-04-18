package com.ynov.aca.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.ynov.aca.R;
import com.ynov.aca.model.Entree;
import com.ynov.aca.service.ImageService;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intentData = getIntent();
        Entree entry = (Entree)intentData.getSerializableExtra("data");

        ImageView imageView = findViewById(R.id.imageView);
        TextView textName = findViewById(R.id.textName);
        TextView textEspece = findViewById(R.id.textEspece);
        TextView textSexe = findViewById(R.id.textSexe);
        TextView textDescription = findViewById(R.id.textDescription);

        new ImageService(imageView).execute("http://thibault01.com:8081/images/" + entry.getId() + ".png");

        setTitle("Détail de " + entry.getNom());
        textName.setText(entry.getNom());
        textEspece.setText(entry.getEspece());

        String sexe = entry.getSexe();
        if(sexe.contentEquals("M"))
            textSexe.setText("Mâle");
        else
            textSexe.setText("Femelle");

        textDescription.setText(entry.getDescription());
        textDescription.setMovementMethod(new ScrollingMovementMethod());

    }
}
