package io.pearlmaknun.mypharmacist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist.adapter.ApotekAdapter;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.helper.GpsTrackers;
import io.pearlmaknun.mypharmacist.model.Apotek;
import io.pearlmaknun.mypharmacist.model.Apoteker;
import io.pearlmaknun.mypharmacist.model.ApotekerResponse;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.INFO_APOTEKER;

public class DetailApotekerActivity extends AppCompatActivity {

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    Session session;

    private LatLng lastPosition;
    private GpsTrackers gps;

    ApotekAdapter adapter;
    Apoteker apoteker;

    List<Apotek> list = new ArrayList<>();

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_apoteker);
        ButterKnife.bind(this);

        session = new Session(this);

        gps = new GpsTrackers(getApplicationContext());
        lastPosition = new LatLng(gps.getLatitude(), gps.getLongitude());

        id = getIntent().getStringExtra("id");
        id = "34";

        initView();
    }

    private void initView() {

        adapter = new ApotekAdapter(getApplicationContext());

        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.hasFixedSize();

        getInfo();
    }

    private void getInfo() {
        DialogUtils.openDialog(this);
        AndroidNetworking.get(INFO_APOTEKER + "/" + id)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .addQueryParameter("latitude", String.valueOf(lastPosition.latitude))
                .addQueryParameter("longitude", String.valueOf(lastPosition.longitude))
                .build()
                .getAsObject(ApotekerResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ApotekerResponse) {
                            if (((ApotekerResponse) response).getStatus()) {
                                DialogUtils.closeDialog();
                                apoteker = (((ApotekerResponse) response).getData());
                                username.setText(apoteker.getApotekerName());
                                email.setText(apoteker.getApotekerEmail());
                                phone.setText(apoteker.getApotekerNumber());
                                list = (((ApotekerResponse) response).getApotik());
                                if (list.size() > 0) {
                                    adapter.swap(list);
                                }
                            } else {
                                DialogUtils.closeDialog();
                                Toast.makeText(DetailApotekerActivity.this, ((ApotekerResponse) response).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(DetailApotekerActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                        Log.d("anError", anError.getErrorBody() + " AND " + anError.getErrorDetail());
                    }
                });
    }
}
