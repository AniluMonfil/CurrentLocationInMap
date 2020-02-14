package com.example.mapalocalizacionmarcardor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
/*Este oyente le notificará cuando el mapa esté listo invocando onMapReady junto
con un objeto GoogleMap.*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    /*GoogleMap es la clase principal de la API de Android de Google Maps y es el punto
     de entrada para todos los métodos relacionados con el mapa*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
    }
}
