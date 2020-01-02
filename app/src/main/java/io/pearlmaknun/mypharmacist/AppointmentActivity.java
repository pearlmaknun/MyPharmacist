package io.pearlmaknun.mypharmacist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist.data.Session;

public class AppointmentActivity extends AppCompatActivity {

    @BindView(R.id.apoteker_name)
    TextView apotekerName;
    @BindView(R.id.konseli_name)
    TextView konseliName;
    @BindView(R.id.datetime)
    TextView tanggalJanji;
    @BindView(R.id.et_alamat)
    TextView alamat;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.c_apoteker)
    TextView cApoteker;
    @BindView(R.id.c_konseli)
    TextView cKonseli;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);

        session = new Session(getApplicationContext());
    }
}
