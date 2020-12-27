package com.jengger.studentportalappclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.StatusMahasiswa;
import com.jengger.studentportalappclient.share_pref.Preference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TOKEN = "extra_token";
    public static final String EXTRA_NPM = "extra_npm";

    public static Integer npmMhs;
    public static String tokenMhs;
    public static String semesterMhs;

    private String TAG = MainActivity.class.getSimpleName();

    private String tokenAuth;
    private Integer npm;

    private AppBarConfiguration mAppBarConfiguration;

    private final StatusMahasiswa statusMahasiswa = new StatusMahasiswa();

    private TextView tvNPM, tvNama;

    CircleImageView profileCircleImageViewDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        if (!Preference.getLoggedStatusAkun(getBaseContext()).equals("Tidak Aktif")) {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // Get data from login
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            tokenAuth = intent.getStringExtra(EXTRA_TOKEN);
            npm = intent.getIntExtra(EXTRA_NPM, 0);
        }

        // get Header
        tvNama = navigationView.getHeaderView(0).findViewById(R.id.txt_nama);
        tvNPM = navigationView.getHeaderView(0).findViewById(R.id.txt_npm);

        getDataPribadi(Preference.getLoggedNPM(getBaseContext()), "Bearer " + Preference.getLoggedToken(getBaseContext()));

        /* Glide */
        profileCircleImageViewDrawer = navigationView.getHeaderView(0).findViewById(R.id.imageView);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menuKuliahFragment, R.id.nav_pengumumanFragment,
                R.id.nav_menuProfileFragment, R.id.nav_daftarUlangFragment, R.id.nav_pengaturanFragment,
                R.id.nav_wisudaFragment, R.id.nav_searchFragment, R.id.nav_menuKuesionerFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.nav_pengaturanFragment:
                return NavigationUI.onNavDestinationSelected(item, navController);
            case R.id.action_logout:
                Preference.clearLoggedIn(getBaseContext());
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

                        Glide.with(MainActivity.this)
                                .load("http://"+dataObject.getString("url"))
                                .placeholder(R.drawable.plalceholder)
                                .into(profileCircleImageViewDrawer);
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

}