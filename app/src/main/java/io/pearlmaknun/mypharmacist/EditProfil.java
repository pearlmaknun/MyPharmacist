package io.pearlmaknun.mypharmacist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.model.Profile;
import io.pearlmaknun.mypharmacist.model.RegisterResponse;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.PROFIL;

public class EditProfil extends AppCompatActivity {

    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.domisili)
    TextInputEditText domisili;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.con_password)
    TextInputEditText conPassword;

    Session session;

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        ButterKnife.bind(this);

        session = new Session(getApplicationContext());
        profile = (Profile) getIntent().getSerializableExtra("profil");

        initView();
    }

    void initView(){
        username.setText(profile.getUserName());
        domisili.setText(profile.getUserAddress());
    }

    void cekValidasi(){
        if(username.getText().toString().equals("")){
            Toast.makeText(EditProfil.this, "Field Nama Jangan Kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if(domisili.getText().toString().equals("")){
            Toast.makeText(EditProfil.this, "Field Alamat Jangan Kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.getText().toString().equals("")){
            if(!password.getText().toString().equals(conPassword.getText().toString())){
                Toast.makeText(EditProfil.this, "Password konfirmasi tidak sama", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        editProfil();
    }

    private void editProfil() {
        DialogUtils.openDialog(this);
        ANRequest.PutRequestBuilder putRequestBuilder = new ANRequest.PutRequestBuilder(PROFIL);
        putRequestBuilder
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .addBodyParameter("user_name", username.getText().toString())
                .addBodyParameter("user_address", domisili.getText().toString());

        if(password.getText().toString() != ""){
            putRequestBuilder.addBodyParameter("user_password", password.getText().toString());
        }
        putRequestBuilder.build()
                .getAsObject(RegisterResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof RegisterResponse) {
                            RegisterResponse response1 = (RegisterResponse) response;
                            DialogUtils.closeDialog();
                            if (response1.getStatus()) {
                                Toast.makeText(EditProfil.this, "Profil berhasil disimpan..", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditProfil.this, "Profil gagal disimpan !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        DialogUtils.closeDialog();
                        Toast.makeText(EditProfil.this, "Mohon maaf, kesalahan teknis !", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    @OnClick(R.id.edit)
    public void onViewClicked(View view) {
        cekValidasi();
    }
}
