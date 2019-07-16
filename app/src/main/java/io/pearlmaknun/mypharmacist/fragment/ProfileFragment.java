package io.pearlmaknun.mypharmacist.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.pearlmaknun.mypharmacist.LoginActivity;
import io.pearlmaknun.mypharmacist.R;
import io.pearlmaknun.mypharmacist.data.Session;
import io.pearlmaknun.mypharmacist.model.Profile;
import io.pearlmaknun.mypharmacist.model.ProfileResponse;
import io.pearlmaknun.mypharmacist.util.CommonUtil;
import io.pearlmaknun.mypharmacist.util.DialogUtils;

import static io.pearlmaknun.mypharmacist.data.Constan.LOGOUT;
import static io.pearlmaknun.mypharmacist.data.Constan.PROFIL;

public class ProfileFragment extends Fragment {

    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phone)
    TextView phone;

    Session session;

    Profile profile;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        session = new Session(getContext());
        getProfile();

        return view;
    }

    private void getProfile() {
        AndroidNetworking.get(PROFIL)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .build()
                .getAsObject(ProfileResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ProfileResponse) {
                            if (((ProfileResponse) response).getStatus()) {
                                profile = (((ProfileResponse) response).getData());
                                username.setText(profile.getUserName());
                                email.setText(profile.getUserEmail());
                                phone.setText(profile.getUserNumber());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("anError", anError.getErrorBody() + " AND " + anError.getErrorDetail());
                    }
                });

    }

    private void logout() {
        AndroidNetworking.get(LOGOUT)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Bearer " + session.getToken())
                .addHeaders("device_id", session.getDeviceId())
                .build()
                .getAsObject(ProfileResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof ProfileResponse) {
                            if (((ProfileResponse) response).getStatus()) {
                                FirebaseAuth.getInstance().signOut();
                                session.logoutUser();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                Toast.makeText(getContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("anError", anError.getMessage());
                    }
                });

    }

    @OnClick(R.id.logout)
    public void onViewClicked() {
        DialogUtils.dialogYesNo(getActivity(), "Anda yakin ingin keluar ?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
