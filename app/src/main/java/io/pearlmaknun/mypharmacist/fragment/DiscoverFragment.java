package io.pearlmaknun.mypharmacist.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.pearlmaknun.mypharmacist.R;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.helper.GpsTrackers;
import io.pearlmaknun.mypharmacist.model.Apotek;
import io.pearlmaknun.mypharmacist.model.ApotekResponse;
import io.pearlmaknun.mypharmacist.model.NearestApoteker;
import io.pearlmaknun.mypharmacist.model.NearestApotekerResponse;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.NEAREST_APOTEK;
import static io.pearlmaknun.mypharmacist.data.Constan.NEAREST_APOTEKER;

public class DiscoverFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    Circle circle;

    Session session;

    private LatLng lastPosition;
    private GpsTrackers gps;

    List<Apotek> list = new ArrayList<>();

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        session = new Session(getContext());

        gps = new GpsTrackers(getContext());
        lastPosition = new LatLng(gps.getLatitude(), gps.getLongitude());

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    public float getZoomLevel(Circle circle) {
        float zoomLevel = 0;
        if (circle != null) {
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel + .5f;
    }

    private void discover() {
        Log.e("location: ", "" + lastPosition.latitude + ", " + lastPosition.longitude);
        DialogUtils.openDialog(getContext());
        AndroidNetworking.get(NEAREST_APOTEK)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addQueryParameter("latitude", String.valueOf(lastPosition.latitude))
                .addQueryParameter("longitude", String.valueOf(lastPosition.longitude))
                .addQueryParameter("radius", String.valueOf(600))
                .build()
                .getAsObject(ApotekResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ApotekResponse) {
                            ApotekResponse response1 = (ApotekResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                DialogUtils.closeDialog();
                                list = (((ApotekResponse) response).getData());
                            } else {
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtils.closeDialog();
                        Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                    }

                });
    }

    private Marker createMarker(double latitude, double longitude, String title, String
            snippet) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.drugstore_icon)));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        AndroidNetworking.get(NEAREST_APOTEK)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addQueryParameter("latitude", String.valueOf(lastPosition.latitude))
                .addQueryParameter("longitude", String.valueOf(lastPosition.longitude))
                .addQueryParameter("radius", String.valueOf(1))
                .build()
                .getAsObject(ApotekResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ApotekResponse) {
                            ApotekResponse response1 = (ApotekResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                list = (((ApotekResponse) response).getData());
                                for (int i = 0; i < list.size(); i++) {
                                    //createMarker(Double.parseDouble(list.get(i).getApotikLatitude()), Double.parseDouble(list.get(i).getApotikLongitude()), list.get(i).getApotikName(), list.get(i).getApotikAddress());
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(list.get(i).getApotikLatitude()), Double.parseDouble(list.get(i).getApotikLongitude())))
                                            .title(list.get(i).getApotikName())
                                            .snippet(list.get(i).getApotikAddress())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.drugstore_icon)));
                                }
                            } else {
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                    }

                });

        Log.e("list size", ""+list.size());

        //mMap.addMarker(new MarkerOptions().position(sydney1).title("Masjid Assalam PTA Bandung").snippet("Jl. Soekarno Hatta No.714, Babakan Penghulu, Bojongloa Kidul, Kota Bandung, Jawa Barat 40235").icon(BitmapDescriptorFactory.fromResource(R.drawable.drugstore_icon)));
        double iKilo = 1;
        double iMeter = iKilo * 1000;
        //circle.remove();
        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(lastPosition.latitude, lastPosition.longitude))
                .radius(iMeter) // Converting Miles into Meters...
                .strokeColor(Color.RED)
                .strokeWidth(5));
        circle.isVisible();
        float currentZoomLevel = getZoomLevel(circle);
        float animateZomm = currentZoomLevel + 5;

        Log.e("Zoom Level:", currentZoomLevel + "");
        Log.e("Zoom Level Animate:", animateZomm + "");

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastPosition.latitude, lastPosition.longitude), animateZomm));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel), 2000, null);
    }
}
