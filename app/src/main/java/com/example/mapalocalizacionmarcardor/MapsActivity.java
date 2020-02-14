package com.example.mapalocalizacionmarcardor;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    /*Este oyente le notificará cuando el mapa esté listo invocando onMapReady junto
con un objeto GoogleMap.*/


    //Objeto location paara almacenar la informacion del lugar.
    private Location currentLocation;
    //Elemento fusedLocation para ser inicializado
    private FusedLocationProviderClient fusedLocationProviderClient;
    //
    private static final int LOCATION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Se inicializa el elemento fusedLocation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Se checa que tengan los permisos necesarios
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }

        fetchLastLocation();

    }

    //Buscar ubicación actual
    private void fetchLastLocation() {
        //Objeto tipo task que obtiene la ubicación actual y representa una operación azincrona
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) { //listener del callback exitosa 1ue se invoca una vez que se establezca la conexión y se recupere la ubicación
                if (location != null) {
                    //Almacena ubicación en el objeto
                    currentLocation = location;
                    //Toast msg rapido para el usuario que muestra las cordenadas de su ubicación
                    Toast.makeText(MapsActivity.this, currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    // Obtener SupportMapFragment
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

                    //Petición de la notificación cuando el mapa esté listo para usar
                    supportMapFragment.getMapAsync((OnMapReadyCallback) MapsActivity.this);

                } else {
                    Toast.makeText(MapsActivity.this, "No Location recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Metodo que verifica el codigo de resultado que obtuvo al checar los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();//no pasa nada
                    Toast.makeText(MapsActivity.this, "Hola", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MapsActivity.this, "Location permission missing", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /*GoogleMap es la clase principal de la API de Android de Google Maps y es el punto
        de entrada para todos los métodos relacionados con el mapa*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(250, 250, conf);
        Canvas canvas1 = new Canvas(bmp);

        // paint defines the text color, stroke width and size
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.BLACK);

        // modify canvas
        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.any04), 0, 0, color);
       // canvas1.drawText("User Name!", 30, 40, color);


        //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Any").icon(BitmapDescriptorFactory.fromBitmap(bmp));

        //Adding the created the marker on the map
        googleMap.addMarker(markerOptions).showInfoWindow();

        //googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));

        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
