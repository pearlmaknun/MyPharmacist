package io.pearlmaknun.mypharmacist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.pearlmaknun.mypharmacist.adapter.ChatAdapter;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.model.Chat;
import io.pearlmaknun.mypharmacist.model.Konsultasi;
import io.pearlmaknun.mypharmacist.model.LoginResponse;
import io.pearlmaknun.mypharmacist.model.UserApoteker;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.END_CHAT;
import static io.pearlmaknun.mypharmacist.data.Constan.REPORT;

public class ConsultationActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.txt_toolbar)
    TextView txtToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.txt_message)
    EditText txtMessage;
    @BindView(R.id.txt_countdown)
    TextView txtCountdown;

    Session session;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    ChatAdapter chatAdapter;
    List<Chat> chats;

    Konsultasi konsultasi;

    ValueEventListener seenListener;

    Long duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        ButterKnife.bind(this);

        session = new Session(this);

        konsultasi = (Konsultasi) getIntent().getSerializableExtra("konsultasi");
        duration = getIntent().getLongExtra("diff", 0);

        recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        //linearLayoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(linearLayoutManager);

        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance()
                .getReference("Apoteker")
                .orderByChild("apoteker_id")
                .equalTo(konsultasi.getApotekerId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("user yg konsultasi", dataSnapshot.toString());
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    UserApoteker user = childSnapshot.getValue(UserApoteker.class);
                    txtToolbar.setText(user.getUsername());
                    readMessage(konsultasi.getChatId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessage(final String konsultasiId) {
        chats = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Chats")
                .orderByChild("id_konsultasi")
                .equalTo(konsultasiId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("user yg konsultasi", dataSnapshot.toString());
                chats.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = childSnapshot.getValue(Chat.class);
                    chats.add(chat);
                }
                Log.e("list chat", chats.toString());
                chatAdapter = new ChatAdapter(getApplicationContext(), chats, "");
                recyclerview.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(konsultasiId);
        countDown();
    }

    private void seenMessage(final String konsultasiId){
        seenListener = FirebaseDatabase.getInstance().getReference("Chats").orderByChild("id_konsultasi")
                .equalTo(konsultasiId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    assert chat != null;
                    if (chat.getPenerima().equals(konsultasi.getUserId()) && chat.getPengirim().equals(konsultasi.getApotekerId())){
                        Log.e("which chat", chat.getPenerima() + "=" + konsultasi.getUserId() + "pesan: " + chat.getPesan());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String pesan) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id_konsultasi", konsultasi.getChatId());
        hashMap.put("pengirim", konsultasi.getUserId());
        hashMap.put("penerima", konsultasi.getApotekerId());
        hashMap.put("pesan", pesan);
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);
    }

    @OnClick({R.id.btn_back, R.id.btn_send, R.id.txt_end, R.id.report, R.id.teruskan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_send:
                String msg = txtMessage.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(msg);
                } else {
                    Toast.makeText(ConsultationActivity.this, "You can't send empty message", Toast.LENGTH_LONG).show();
                }
                txtMessage.setText("");
                break;
            case R.id.txt_end:
                endChat();
                break;
            case R.id.report:
                reportChat();
                break;
            case R.id.teruskan:
                Intent i = new Intent(ConsultationActivity.this, MainPertemuanActivity.class);
                i.putExtra("konsultasi", konsultasi);
                startActivity(i);
                break;
        }
    }

    private void endChat(){
        DialogUtils.openDialog(this);
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
                                Toast.makeText(ConsultationActivity.this, response1.getMessage(), Toast.LENGTH_LONG).show();
                                sendMessage("#end#");
                                Intent i = new Intent(ConsultationActivity.this, RatingActivity.class);
                                i.putExtra("chatid", konsultasi.getChatId());
                                startActivity(i);
                                finish();
                            } else {
                                DialogUtils.closeDialog();
                                Toast.makeText(ConsultationActivity.this, response1.getMessage(), Toast.LENGTH_LONG).show();
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

    public void countDown() {

        Log.e("duration on c", duration.toString());

        new CountDownTimer(duration, 1000) {

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {
                txtCountdown.setText("" + String.format("%d : %d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {

            }

        }.start();
    }

    private void reportChat(){
        DialogUtils.openDialog(this);
        AndroidNetworking.post(REPORT + konsultasi.getChatId())
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
                                Toast.makeText(ConsultationActivity.this, response1.getMessage(), Toast.LENGTH_LONG).show();
                                /*Intent i = new Intent(ConsultationActivity.this, RatingActivity.class);
                                i.putExtra("chatid", konsultasi.getChatId());
                                startActivity(i);*/
                                finish();
                            } else {
                                DialogUtils.closeDialog();
                                Toast.makeText(ConsultationActivity.this, response1.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //reference.removeEventListener(seenListener);
    }
}
