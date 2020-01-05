package io.pearlmaknun.mypharmacist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist.adapter.NearestApotekerAdapter;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.helper.GpsTrackers;
import io.pearlmaknun.mypharmacist.model.CheckActivityResponse;
import io.pearlmaknun.mypharmacist.model.Konsultasi;
import io.pearlmaknun.mypharmacist.model.LoginResponse;
import io.pearlmaknun.mypharmacist.model.NearestApoteker;
import io.pearlmaknun.mypharmacist.model.NearestApotekerResponse;
import io.pearlmaknun.mypharmacist.model.RequestConsultation;
import io.pearlmaknun.mypharmacist.util.CommonUtil;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.BERLANGSUNG;
import static io.pearlmaknun.mypharmacist.data.Constan.CHECK_HAS_CONSULTATION;
import static io.pearlmaknun.mypharmacist.data.Constan.DIPROSES;
import static io.pearlmaknun.mypharmacist.data.Constan.END_CHAT;
import static io.pearlmaknun.mypharmacist.data.Constan.NEAREST_APOTEKER;
import static io.pearlmaknun.mypharmacist.data.Constan.REQUEST_CONSULTATION;

public class KonsultasiActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_list)
    RelativeLayout layoutList;
    @BindView(R.id.layout_aktif)
    RelativeLayout layoutAktif;
    @BindView(R.id.layout_waiting)
    RelativeLayout layoutWaiting;
    @BindView(R.id.layout_diterima)
    RelativeLayout layoutDiterima;
    @BindView(R.id.ic_history)
    ImageView btnHistory;
    @BindView(R.id.ic_history_janji)
    ImageView btnJanji;

    Session session;

    private LatLng lastPosition;
    private GpsTrackers gps;

    NearestApotekerAdapter adapter;
    Konsultasi konsultasi;

    long diff = 0;
    boolean statusList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsultasi);
        ButterKnife.bind(this);
        btnJanji.setVisibility(View.GONE);

        session = new Session(getApplicationContext());

        gps = new GpsTrackers(getApplicationContext());
        lastPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
        requestLocationUpdates();
        initView();
    }

    private void initView() {
        check();

        adapter = new NearestApotekerAdapter(getApplicationContext());

        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.hasFixedSize();

        adapter.setOnItemClickListener(position -> {
            Log.e("clicked", adapter.getItem(position).getApotekerId());
            requestKonsul(adapter.getItem(position).getApotekerId());
        });
    }

    private void requestKonsul(String apoteker_id) {
        Log.e("location: ", "" + lastPosition.latitude + ", " + lastPosition.longitude);
        //DialogUtils.openDialog(getApplication().getBaseContext());
        AndroidNetworking.post(REQUEST_CONSULTATION)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addQueryParameter("apoteker_id", apoteker_id)
                .build()
                .getAsObject(RequestConsultation.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof RequestConsultation) {
                            //DialogUtils.closeDialog();
                            RequestConsultation response1 = (RequestConsultation) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                Toast.makeText(getApplicationContext(), "Pengajuan Konsultasi berhasil.", Toast.LENGTH_LONG).show();
                                layout("0");
                            } else {
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                                Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //DialogUtils.closeDialog();
                        Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                    }

                });
    }

    private void check() {
        Log.e("location: ", "" + lastPosition.latitude + ", " + lastPosition.longitude);
        //DialogUtils.openDialog(getActivity());
        if (lastPosition != null){
            AndroidNetworking.get(CHECK_HAS_CONSULTATION)
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("device_id", session.getDeviceId())
                    .addHeaders("Authorization", "Bearer " + session.getToken())
                    .build()
                    .getAsObject(CheckActivityResponse.class, new ParsedRequestListener() {
                        @Override
                        public void onResponse(Object response) {
                            //DialogUtils.closeDialog();
                            if (response instanceof CheckActivityResponse) {
                                CheckActivityResponse response1 = (CheckActivityResponse) response;
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                                if (response1.getStatus()) {
                                    konsultasi = response1.getData();
                                    String currentTime = CommonUtil.getCurrentDate();
                                    Log.d("current_time", currentTime);
                                    String startDate = konsultasi.getStartChat();
                                    Log.d("start_con", "" + startDate);

                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                    try {
                                        Date d2 = format.parse(currentTime);
                                        Date d1 = format.parse(startDate);

                                        diff = d2.getTime() - d1.getTime();
                                        Log.d("diff", "" + diff);

                                        long timeLimit = Long.valueOf(konsultasi.getChatDuration()) * 60 * 1000;
                                        diff = timeLimit - diff;
                                        Log.d("cerecall time", konsultasi.getChatDuration());

                                        if (diff < timeLimit) {
                                            Log.d("CLICKED CHAT", "" + diff);
                                            layout(response1.getData().getStatusChat());
                                        } else {
                                            endChat();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    layout("-1");
                                    Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            //DialogUtils.closeDialog();
                            Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                        }

                    });
        }else{
            Toast.makeText(getApplicationContext(), "Failed get current location", Toast.LENGTH_LONG).show();
        }

    }

    private void layout(String status){
        switch (status){
            case DIPROSES:
                layoutAktif.setVisibility(View.GONE);
                layoutList.setVisibility(View.GONE);
                statusList = false;
                layoutDiterima.setVisibility(View.GONE);
                layoutWaiting.setVisibility(View.VISIBLE);
                break;
            case BERLANGSUNG:
                layoutAktif.setVisibility(View.VISIBLE);
                layoutList.setVisibility(View.GONE);
                statusList = false;
                layoutDiterima.setVisibility(View.GONE);
                layoutWaiting.setVisibility(View.GONE);
                break;
            default:
                layoutAktif.setVisibility(View.GONE);
                layoutList.setVisibility(View.VISIBLE);
                statusList = true;
                layoutDiterima.setVisibility(View.GONE);
                layoutWaiting.setVisibility(View.GONE);
                break;
        }
    }

    private void endChat(){
        DialogUtils.openDialog(getApplicationContext());
        AndroidNetworking.post(END_CHAT + konsultasi.getChatId())
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof LoginResponse) {
                            LoginResponse response1 = (LoginResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                DialogUtils.closeDialog();
                                Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), RatingActivity.class);
                                i.putExtra("chatid", konsultasi.getChatId());
                                startActivity(i);
                            } else {
                                DialogUtils.closeDialog();
                                Toast.makeText(getApplicationContext(), response1.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("RESPONSE SUCCESS", response1.getMessage() + new Gson().toJson(response1));
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

    @OnClick({R.id.search, R.id.lanjutkan, R.id.ic_history, R.id.ic_history_janji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                discover();
                break;
            case R.id.lanjutkan:
                Intent intent = new Intent(getApplicationContext(), ConsultationActivity.class);
                intent.putExtra("konsultasi", konsultasi);
                intent.putExtra("diff", diff);
                startActivity(intent);
                break;
            case R.id.ic_history:
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
                break;
            case R.id.ic_history_janji:
                Intent a = new Intent(getApplicationContext(), HistoryPertemuan.class);
                startActivity(a);
                break;
        }
    }

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();
        //Specify how often your app should request the device’s location
        request.setInterval(5000);
        //Get the most accurate location data available//
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        int permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        //If the app currently has access to the location permission...//
        if (permission == PackageManager.PERMISSION_GRANTED) {
            //...then request location updates//
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    gps = new GpsTrackers(getApplicationContext());
                    lastPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
                    Log.e("location realtime", ""+lastPosition.latitude +", "+lastPosition.longitude);
                    if (lastPosition.latitude != 0 && lastPosition.longitude != 0 && statusList){
                        discover();
                    }

                }
            }, null);
        }
    }

    private void discover() {
        Log.e("location: ", "" + lastPosition.latitude + ", " + lastPosition.longitude);
        //DialogUtils.openDialog(getActivity());
        AndroidNetworking.get(NEAREST_APOTEKER)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addQueryParameter("latitude", String.valueOf(lastPosition.latitude))
                .addQueryParameter("longitude", String.valueOf(lastPosition.longitude))
                .addQueryParameter("radius", String.valueOf(600))
                .build()
                .getAsObject(NearestApotekerResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        //DialogUtils.closeDialog();
                        if (response instanceof NearestApotekerResponse) {
                            NearestApotekerResponse response1 = (NearestApotekerResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                layoutAktif.setVisibility(View.GONE);
                                layoutList.setVisibility(View.VISIBLE);
                                statusList = true;
                                layoutDiterima.setVisibility(View.GONE);
                                layoutWaiting.setVisibility(View.GONE);
                                List<NearestApoteker> list = (((NearestApotekerResponse) response).getData());
                                if (list.size() > 0) {
                                    //noData.setVisibility(View.GONE);
                                    adapter.swap(list);
                                } else {
                                    //noData.setVisibility(View.VISIBLE);
                                }

                            } else {
                                Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //DialogUtils.closeDialog();
                        Log.e("RESPONSE GAGAL", "" + new Gson().toJson(anError.getErrorBody() + anError.getMessage()));
                    }

                });
    }

    @Override
    public void onResume(){
        super.onResume();
        check();
    }
}
