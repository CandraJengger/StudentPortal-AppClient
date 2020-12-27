package com.jengger.studentportalappclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.StudentAccount;
import com.jengger.studentportalappclient.api.Model.StudentAccountRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AktivasiActivity extends AppCompatActivity {
    Button btnKembali, btnAktivasi;
    EditText txtNPM, txtTanggal, txtPassword, txtUlangiPassword;
    StudentAccount studentAccount = new StudentAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi);

        txtNPM = findViewById(R.id.edt_npm);
        txtTanggal = findViewById(R.id.edt_tgl);
        txtPassword = findViewById(R.id.edt_password);
        txtUlangiPassword = findViewById(R.id.edt_confirm_password);

        btnKembali = findViewById(R.id.btn_kembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AktivasiActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAktivasi = findViewById(R.id.btn_aktivasi);
        btnAktivasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtPassword.getText().toString().equals(txtUlangiPassword.getText().toString())) {
                    Snackbar.make(v, "Password tidak sama", Snackbar.LENGTH_LONG).show();
                    return;
                }
                Integer npmMhs = Integer.parseInt(txtNPM.getText().toString());
                studentAccount.setNpm(npmMhs);
                aktivasi(v);
//                Snackbar.make(v, "Aktivasi Berhasil", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void aktivasi(final View view) {
        StudentAccountRequest studentAccountRequest = new StudentAccountRequest();
        studentAccountRequest.setStudentAccount(studentAccount);

        Call<ResponseBody> aktivasiResult = APIClient.getMahasiswaService().activation(studentAccountRequest);
        aktivasiResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("code").equals("404")) {
                            Snackbar.make(view , "NPM tidak ditemukan", Snackbar.LENGTH_LONG)
                                    .show();
                            return;
                        }

                        if (jsonObjectResult.getString("code").equals("200")) {

                            Snackbar.make(view, "Aktivasi Berhasil", Snackbar.LENGTH_LONG).show();
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