package io.pearlmaknun.mypharmacist.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.pearlmaknun.mypharmacist.ApotekListActivity;
import io.pearlmaknun.mypharmacist.R;
import io.pearlmaknun.mypharmacist.SearchApotekerActivity;
import io.pearlmaknun.mypharmacist.data.Session;

public class HomeFragment extends Fragment {

    Session session;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        session = new Session(getContext());

        return view;
    }

    /*@OnClick({R.id.btn_ask, R.id.btn_consultation, R.id.btn_apotek, R.id.btn_education})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ask:
                Toast.makeText(getContext(), "Fitur Ask ini belum berfungsi!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_consultation:
                Intent intent = new Intent(getContext(), SearchApotekerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_apotek:
                Intent i = new Intent(getContext(), ApotekListActivity.class);
                startActivity(i);
                break;
            case R.id.btn_education:
                Toast.makeText(getContext(), "Fitur Edu ini belum berfungsi!", Toast.LENGTH_LONG).show();
                break;
        }
    }*/
}
