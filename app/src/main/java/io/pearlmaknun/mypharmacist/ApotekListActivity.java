package io.pearlmaknun.mypharmacist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist.adapter.ApotekAdapter;
import io.pearlmaknun.mypharmacist.adapter.NearestApotekerAdapter;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.helper.GpsTrackers;
import io.pearlmaknun.mypharmacist.model.Apotek;
import io.pearlmaknun.mypharmacist.model.ApotekResponse;
import io.pearlmaknun.mypharmacist.model.NearestApoteker;
import io.pearlmaknun.mypharmacist.model.NearestApotekerResponse;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.NEAREST_APOTEK;
import static io.pearlmaknun.mypharmacist.data.Constan.NEAREST_APOTEKER;

public class ApotekListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    Session session;

    private LatLng lastPosition;
    private GpsTrackers gps;

    ApotekAdapter adapter;

    List<Apotek> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apotek_list);
        session = new Session(getApplicationContext());
        ButterKnife.bind(this);

        gps = new GpsTrackers(getApplicationContext());
        lastPosition = new LatLng(gps.getLatitude(), gps.getLongitude());

        initView();
    }

    private void initView() {

        adapter = new ApotekAdapter(getApplicationContext());

        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.hasFixedSize();

        discover();
    }

    public void discover(){
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
                                if (list.size() > 0) {
                                    adapter.swap(list);
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
    }
}
