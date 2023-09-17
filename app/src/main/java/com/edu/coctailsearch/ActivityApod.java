package com.edu.coctailsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edu.coctailsearch.modelo.ApiService;
import com.edu.coctailsearch.modelo.PeticionesApod;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ActivityApod extends AppCompatActivity {

    private String URL_API="a";
    private static final String BASE_URL = "https://api.nasa.gov/";
    private EditText txtFecha;
    private TextView txtTitulo;
    private TextView txtDesc;
    private ImageView img;
    private FloatingActionButton btnBuscar;
    private FloatingActionButton btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        txtFecha= findViewById(R.id.etxtFecha);
        txtTitulo = findViewById(R.id.txtTitulo);
        img = findViewById(R.id.imgFoto);
        btnBuscar = findViewById(R.id.buttonBuscar);
        txtDesc = findViewById(R.id.txtDescripción);
        btnLimpiar = findViewById(R.id.buttonLimpiar);

        //CARGAR LA INFORMACIÓN
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = txtFecha.getText().toString();
                //Variable con la url de la api, unida con la fecha
                URL_API= "https://api.nasa.gov/planetary/apod?api_key=fKOwxfUmLSy5qSjKzoZ6JVWWzTijEfvNFNXhRrLb&date="+fecha;
                //Toast.makeText(getApplicationContext(), "URL:"+URL_API, Toast.LENGTH_LONG).show();
                //buscar(fecha);
                obtenerRecursosDesdeAPI();
            }
        });
        //LIMPIAR
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitulo.setText(null);
                txtFecha.setText(null);
                txtDesc.setText(null);
                Glide.with(getApplication()).load(R.drawable.papod).into(img);
            }
        });

    }



    //BUSCAR LA URL
    private void obtenerRecursosDesdeAPI() {
        //Se crea la instancia Retrofit
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        //CRear objeto del servicio de la API
        ApiService servicioAPI = retro.create(ApiService.class);

        //Hacer la llamada a la API con la URL creada
        Call<String> call = servicioAPI.obtenerJsonString(URL_API);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    if(response.isSuccessful()){
                        String jsonString = response.body();
                        //Esta es la cadena JSON convertida a String para usarse
                        obtenerUrlImagenDesdeJSON(jsonString);
                        obtenerTituloDesdeJSON(jsonString);
                        obtenerDescDesdeJSON(jsonString);
                    }
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falla de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //OBTENER LA IMAGEN
    private void obtenerUrlImagenDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String urlImagen = jsonObject.getString("url");
            // Aquí está la URL de la imagen
            Glide.with(getApplication()).load(urlImagen).into(img);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //OBTENER EL TITULO
    private void obtenerTituloDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String titulo = jsonObject.getString("title");
            // Aquí está la URL de la imagen
            txtTitulo.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //OBTENER LA DESCRIPCION
    private void obtenerDescDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String titulo = jsonObject.getString("explanation");
            // Aquí está la URL de la imagen
            txtDesc.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}