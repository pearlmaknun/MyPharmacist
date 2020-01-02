package io.pearlmaknun.mypharmacist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.model.Konsultasi;
import io.pearlmaknun.mypharmacist.model.Pertemuan;
import io.pearlmaknun.mypharmacist.model.PertemuanResponse;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.CHECK_HAS_APPOINMENT;

public class MainPertemuanActivity extends AppCompatActivity {

    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.apoteker_name)
    TextView apotekerName;
    @BindView(R.id.konseli_name)
    TextView konseliName;
    @BindView(R.id.alamat)
    TextView alamat;
    @BindView(R.id.map)
    TextView lokasi;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.status)
    TextView status;

    Session session;

    String id;

    Konsultasi konsultasi;
    Pertemuan pertemuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pertemuan);
        ButterKnife.bind(this);

        session = new Session(getApplicationContext());

        konsultasi = (Konsultasi) getIntent().getSerializableExtra("konsultasi");

        check();
    }

    private void check() {
        DialogUtils.openDialog(this);
        AndroidNetworking.get(CHECK_HAS_APPOINMENT + konsultasi.getChatId())
                .addHeaders("Content-Type", "application/json")
                .addHeaders("device_id", session.getDeviceId())
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .build()
                .getAsObject(PertemuanResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        DialogUtils.closeDialog();
                        if (response instanceof PertemuanResponse) {
                            PertemuanResponse response1 = (PertemuanResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                pertemuan = response1.getData();
                                card.setVisibility(View.VISIBLE);
                                apotekerName.setText("Apoteker: " + pertemuan.getApotekerName());
                                konseliName.setText("Konseli: " + pertemuan.getUserName());
                                detail.setText("Keterangan: " + pertemuan.getPertemuanDetail());
                                if (pertemuan.getPertemuanAlamat() != null) {
                                    alamat.setText(pertemuan.getPertemuanAlamat());
                                }
                                if (pertemuan.getPertemuanLokasi() != null) {
                                    lokasi.setVisibility(View.VISIBLE);
                                }
                                if (pertemuan.getPertemuanStatus().equals("0")) {
                                    status.setText("Status: Di Proses ke Konseli");
                                } else if (pertemuan.getPertemuanStatus().equals("1")) {
                                    status.setText("Status: Disetujui kedua belah pihak");
                                }
                            } else {
                                card.setVisibility(View.GONE);
                                Toast.makeText(MainPertemuanActivity.this, response1.getMessage(), Toast.LENGTH_LONG).show();
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

    @OnClick(R.id.submit)
    public void onViewClicked(View view) {

    }

    @OnClick(R.id.map)
    public void onViewClickedMap(View view) {
        int iend = pertemuan.getPertemuanLokasi().indexOf(",");
        String lat = "", lng = "";
        if (iend != -1) {
            lat = pertemuan.getPertemuanLokasi().substring(0, iend);
            lng = pertemuan.getPertemuanLokasi().substring(iend+1);
        }
        Log.e("INI YANG LAIN POKONYA", lat + "," + lng);
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Float.valueOf(lat), Float.valueOf(lng));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
