package io.pearlmaknun.mypharmacist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.helper.GpsTrackers;
import io.pearlmaknun.mypharmacist.model.LoginResponse;
import io.pearlmaknun.mypharmacist.util.CommonUtil;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.LOGIN;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.password)
    TextInputEditText password;

    Session session;

    private LatLng lastPosition;
    private GpsTrackers gps;
    double lat, lng;
    private FusedLocationProviderClient fusedLocationClient;

    /*FirebaseAuth auth;
    DatabaseReference reference;*/

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new Session(this);

        gps = new GpsTrackers(this);
        lastPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                        Log.e("get last loc", ""+lat+lng);
                    }
                });

        /*auth = FirebaseAuth.getInstance();*/

        @SuppressLint("HardwareIds")
        String device_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        session.setDeviceId(device_id);

        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = pinfo.versionName;
            session.setVersionApp(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkValidasi() {
        ArrayList<View> list = new ArrayList<>();
        list.add(email);
        list.add(password);
        if (CommonUtil.validateEmptyEntries(list)) {
            login();
        }
    }

    private void login() {
        DialogUtils.openDialog(this);
        AndroidNetworking.post(LOGIN)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter("email", email.getText().toString())
                .addBodyParameter("password", password.getText().toString())
                .addBodyParameter("app_version", session.getVersionApp())
                .addBodyParameter("device_id", session.getDeviceId())
                .addBodyParameter("latitude", String.valueOf(lastPosition.latitude))
                .addBodyParameter("longitude", String.valueOf(lastPosition.longitude))
                .addBodyParameter("package_name", getApplicationContext().getPackageName())
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof LoginResponse) {
                            LoginResponse response1 = (LoginResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                session.createLoginSession(response1.getData());
                                session.setToken(response1.getToken());
                                //loginFirebase(email.getText().toString(), password.getText().toString());
                                DialogUtils.closeDialog();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
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

    /*private void loginFirebase(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            DialogUtils.closeDialog();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            DialogUtils.closeDialog();
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }*/

    @OnClick({R.id.login, R.id.daftar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                checkValidasi();
                break;
            case R.id.daftar:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

}
