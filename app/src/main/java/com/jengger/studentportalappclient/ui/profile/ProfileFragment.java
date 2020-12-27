package com.jengger.studentportalappclient.ui.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.tabs.TabLayout;
import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.share_pref.Preference;
import com.jengger.studentportalappclient.ui.profile.page.DataOrangtuaFragment;
import com.jengger.studentportalappclient.ui.profile.page.DataPribadiFragment;
import com.jengger.studentportalappclient.ui.profile.page.StatusFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    CircleImageView profileCircleImageProfile;
    private String TAG = ProfileFragment.class.getSimpleName();
    private TextView tvNPM, tvNama;
    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, view, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileCircleImageProfile = view.findViewById(R.id.imageView);

        /* Tab and View pager */
        tabs = view.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(R.string.tab_status));
        tabs.addTab(tabs.newTab().setText(R.string.tab_data_pribadi));
        tabs.addTab(tabs.newTab().setText(R.string.tab_data_orangtua));

        tvNama = view.findViewById(R.id.txt_nama);
        tvNPM = view.findViewById(R.id.txt_npm);

        getDataPribadi(Preference.getLoggedNPM(getContext()), "Bearer " + Preference.getLoggedToken(getContext()));

        if (tabs.getTabAt(0).getText().equals("Status")) {
            StatusFragment statusFragment = new StatusFragment();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.frame, statusFragment);
            ft.commit();
        }

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0: {
                        StatusFragment statusFragment = new StatusFragment();
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, statusFragment);
                        ft.commit();
                        break;
                    }
                    case 1: {
                        DataPribadiFragment dataPribadiFragment = new DataPribadiFragment();
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, dataPribadiFragment);
                        ft.commit();
                        break;
                    }
                    case 2: {
                        DataOrangtuaFragment dataOrangtuaFragment = new DataOrangtuaFragment();
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, dataOrangtuaFragment);
                        ft.commit();
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getDataPribadi(final int npm, final String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getMahasiswaService().getProfilMahasiswa(npm, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        tvNPM.setText(String.valueOf(npm));
                        tvNama.setText(dataObject.getString("NAMA_MHS"));


                        Glide.with(ProfileFragment.this)
                                .load("http://"+dataObject.getString("url"))
                                .placeholder(R.drawable.plalceholder)
                                .into(profileCircleImageProfile);

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Error " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Destach");
    }
}