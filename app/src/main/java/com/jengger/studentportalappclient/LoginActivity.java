package com.jengger.studentportalappclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.LoginRequest;
import com.jengger.studentportalappclient.share_pref.Preference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = LoginActivity.class.getSimpleName();
    private CardView cardReset;
    private EditText edtNPM, edtPassword;
    private String password;
    private Integer npm;
    private Button btnLogin, btnAktivasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, String.valueOf(Preference.getLoggedStatus(getBaseContext())));
        if (Preference.getLoggedStatus(getBaseContext())) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if (Preference.getLoggedStatusAkun(getBaseContext()).equals("Tidak Aktif")) {
            Toast.makeText(this, "Akun belum teraktivasi", Toast.LENGTH_SHORT).show();
        }

        cardReset = findViewById(R.id.card_reset);
        cardReset.setOnClickListener(this);

        edtPassword = findViewById(R.id.edt_password);
        edtNPM = findViewById(R.id.edt_npm);
        btnLogin = findViewById(R.id.btn_login);
        btnAktivasi = findViewById(R.id.btn_aktivasi);

        btnLogin.setOnClickListener(this);
        btnAktivasi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login : {
                if (edtNPM.getText().toString().length() > 0) {
                    npm = Integer.parseInt(edtNPM.getText().toString());
                }
                if (edtNPM.getText().toString().length() == 0) {
                    npm = 0;
                }
                if (edtPassword.getText().toString().length() > 0) {
                    password = edtPassword.getText().toString();
                }
                if (edtPassword.getText().toString().length() == 0) {
                    password = "";
                }

                if (npm == 0 || TextUtils.isEmpty(password)) {
                    Snackbar.make(v, "Masukkan NPM atau Password", Snackbar.LENGTH_LONG)
                            .show();
//                    Toast.makeText(this, "NPM or password required", Toast.LENGTH_LONG).show();
                } else {
//                    login(v, npm, password);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            }
            case R.id.btn_aktivasi: {
                Intent intent = new Intent(LoginActivity.this, AktivasiActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.card_reset: {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                break;
            }
        }
    }

    private void login(final View view,final Integer npm, String password) {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNpm(npm);
        loginRequest.setPassword(password);

        Call<ResponseBody> loginResponseCall = APIClient.getMahasiswaService().mahasiswaLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            JSONObject jsonObjectResult = new JSONObject(response.body().string());
                            if (jsonObjectResult.getString("code").equals("404")) {
                                Snackbar.make(view , "NPM atau Password salah", Snackbar.LENGTH_LONG)
                                        .show();
                                return;
                            }

                            if (jsonObjectResult.getString("code").equals("200") && jsonObjectResult.getString("values").length() > 0) {
                                JSONObject dataObject = jsonObjectResult.getJSONObject("values");

                                if (dataObject.getString("status").equals("Tidak Aktif")) {
                                    Snackbar.make(view , "Akun anda belum teraktivasi", Snackbar.LENGTH_LONG)
                                            .show();
                                    return;
                                }
                                String tokenAuth = dataObject.getString("token");
                                Log.d(TAG, tokenAuth);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                Preference.setLoggedNPM(getBaseContext(), npm);
                                Preference.setLoggedToken(getBaseContext(), tokenAuth);
                                Preference.setLoggedStatus(getBaseContext(), true);

                                getDataAkun(npm, tokenAuth);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Snackbar.make(view , "NPM atau Password salah", Snackbar.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getLocalizedMessage());
                Snackbar.make(view , "Internal Server Error", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void getDataAkun(Integer npm, String token) {
        Call<ResponseBody> akunResult = APIClient.getMahasiswaService().getAkunMahasiswa(npm, token);
        akunResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if(jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");
                            JSONObject dataObject = dataArray.getJSONObject(0);

                            Preference.setLoggedStatusAkun(getBaseContext(), dataObject.getString("STATUS_AKUN"));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}