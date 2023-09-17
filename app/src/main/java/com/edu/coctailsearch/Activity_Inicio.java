package com.edu.coctailsearch;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class Activity_Inicio extends AppCompatActivity {
    Button btnApod;
    Button btnRover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btnApod = findViewById(R.id.buttonApod);
        btnRover = findViewById(R.id.buttonRover);
        btnApod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(Activity_Inicio.this,ActivityApod.class);
                startActivity(inte);
            }
        });
        btnRover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(Activity_Inicio.this, ActivityRoverManifest.class);
                startActivity(inte);
            }
        });
    }
}
