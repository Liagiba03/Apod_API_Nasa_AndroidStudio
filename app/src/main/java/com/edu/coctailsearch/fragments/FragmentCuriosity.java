package com.edu.coctailsearch.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.edu.coctailsearch.R;
import com.edu.coctailsearch.modelo.ApiService;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCuriosity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCuriosity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //OBJETOS QUE SE USAN ESTANDO EN EL XML
    TextView txtNombre;
    TextView txtEstatus;
    TextView txtLan;
    TextView txtAte;
    TextView txtSol;
    TextView txtFecha;
    TextView txtFotos;
    Button boton;
    private String URL_API="https://api.nasa.gov/mars-photos/api/v1/manifests/curiosity?&api_key=fKOwxfUmLSy5qSjKzoZ6JVWWzTijEfvNFNXhRrLb";
    private static final String BASE_URL = "https://api.nasa.gov/";

    public FragmentCuriosity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCuriosity.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCuriosity newInstance(String param1, String param2) {
        FragmentCuriosity fragment = new FragmentCuriosity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //CONEXIÓN A COMPONENTES
        View view = inflater.inflate(R.layout.fragment_curiosity,container,false);
        txtNombre = (TextView)view.findViewById(R.id.txtNombre);
        txtEstatus= (TextView)view.findViewById(R.id.txtEstado);
        txtLan = (TextView)view.findViewById(R.id.txtLanzamiento);
        txtAte = (TextView)view.findViewById(R.id.txtAterrizaje);
        txtSol = (TextView)view.findViewById(R.id.txtMaxSol);
        txtFecha = (TextView)view.findViewById(R.id.txtMaxFecha);
        txtFotos = (TextView)view.findViewById(R.id.txtFotos);
        obtenerRecursosDesdeAPI();
        //Toast.makeText(getContext(), "EVENTO", Toast.LENGTH_SHORT).show();



        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_curiosity, container, false);
        return  view;
    }

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
                        obtenerNombreDesdeJSON(jsonString);
                        obtenerEstatusDesdeJSON(jsonString);
                        obtenerLanzDesdeJSON(jsonString);
                        obtenerAteDesdeJSON(jsonString);
                        obtenerSolDesdeJSON(jsonString);
                        obtenerFechaDesdeJSON(jsonString);
                        obtenerFotosDesdeJSON(jsonString);

                    }
                }catch (Exception ex){
                    Toast.makeText(getView().getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getView().getContext(), "Falla de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //OBTENER EL NOMBRE
    private void obtenerNombreDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photoManifest = jsonObject.getJSONObject("photo_manifest");
            String titulo = photoManifest.getString("name");
            // Aquí está la URL de la imagen
            txtNombre.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //OBTENER EL ESTATUS
    private void obtenerEstatusDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photoManifest = jsonObject.getJSONObject("photo_manifest");
            String titulo = photoManifest.getString("status");
            // Aquí está la URL de la imagen
            txtEstatus.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //OBTENER FECHA LANZAMIENNTO
    private void obtenerLanzDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photoManifest = jsonObject.getJSONObject("photo_manifest");
            String titulo = photoManifest.getString("launch_date");
            // Aquí está la URL de la imagen
            txtLan.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //OBTENER FECHA ATERRIZAJE
    private void obtenerAteDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photoManifest = jsonObject.getJSONObject("photo_manifest");
            String titulo = photoManifest.getString("landing_date");
            // Aquí está la URL de la imagen
            txtAte.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //OBTENER SOL
    private void obtenerSolDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photoManifest = jsonObject.getJSONObject("photo_manifest");
            String titulo = photoManifest.getString("max_sol");
            // Aquí está la URL de la imagen
            txtSol.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //OBTENER SOL
    private void obtenerFechaDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photoManifest = jsonObject.getJSONObject("photo_manifest");
            String titulo = photoManifest.getString("max_date");
            // Aquí está la URL de la imagen
            txtFecha.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //OBTENER FOTOS
    private void obtenerFotosDesdeJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photoManifest = jsonObject.getJSONObject("photo_manifest");
            String titulo = photoManifest.getString("total_photos");
            // Aquí está la URL de la imagen
            txtFotos.setText(titulo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}