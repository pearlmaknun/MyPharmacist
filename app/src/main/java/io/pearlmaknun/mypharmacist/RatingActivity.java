package io.pearlmaknun.mypharmacist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist.R;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.model.LoginResponse;
import io.pearlmaknun.mypharmacist.model.RegisterResponse;
import io.pearlmaknun.mypharmacist.util.CommonUtil;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.RATE_CHAT;
import static io.pearlmaknun.mypharmacist.data.Constan.REGISTER;

public class RatingActivity extends AppCompatActivity {

    @BindView(R.id.rating_bar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.et_ulasan)
    TextInputEditText inputUlasan;
    @BindView(R.id.submit)
    Button btnSubmit;

    Session session;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);

        session = new Session(this);

        id = getIntent().getStringExtra("chatid");
    }

    private void checkValidasi() {
        if (ratingBar.getRating() == 0) {
            ratingBar.requestFocus();
            return;
        }
        rate();
    }

    private void rate(){
        DialogUtils.openDialog(this);
        Log.e("A", id);
        Log.e("B", inputUlasan.getText().toString());
        Log.e("C", String.valueOf(ratingBar.getRating()));
        ANRequest.PostRequestBuilder postRequestBuilder = new ANRequest.PostRequestBuilder(RATE_CHAT + id);
        postRequestBuilder.addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .addQueryParameter("star", String.valueOf(ratingBar.getRating()));
        if (!inputUlasan.getText().toString().equals("")){
            postRequestBuilder.addQueryParameter("comment", inputUlasan.getText().toString());
        } else {
            postRequestBuilder.addQueryParameter("comment", "");
        }
        postRequestBuilder.build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof LoginResponse) {
                            LoginResponse response1 = (LoginResponse) response;
                            Log.e("RESPONSE SUCCESS", "" + new Gson().toJson(response1));
                            if (response1.getStatus()) {
                                DialogUtils.closeDialog();
                                Toast.makeText(RatingActivity.this, response1.getMessage(), Toast.LENGTH_LONG).show();
                                DialogUtils.dialogYesNo(RatingActivity.this, "Apakah anda tertarik ingin menemui apoteker ?", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RatingActivity.this, DetailApotekerActivity.class);
                                        intent.putExtra("id", id);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(RatingActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                DialogUtils.closeDialog();
                                Toast.makeText(RatingActivity.this, response1.getMessage(), Toast.LENGTH_LONG).show();
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

    @OnClick({R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                checkValidasi();
                break;
        }
    }
}
