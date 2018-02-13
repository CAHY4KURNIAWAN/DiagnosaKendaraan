package com.gamal.diagnosakendaraan;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MenuUtama extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "MainActivity";

    Button btn01, btn02, btn03;
    Intent intent;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        DataBaseHelper dbHelper = new DataBaseHelper(MenuUtama.this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            Log.e(TAG, "Unable to create database");
        }
        try {
            dbHelper.openDataBase();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to open database");
        }

        btn01 = (Button) findViewById(R.id.btn4tak);
        btn02 = (Button) findViewById(R.id.btn2tak);
        btn03 = (Button) findViewById(R.id.btnTentang);

        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn4tak:
                this.finish();
                intent = new Intent(MenuUtama.this, Kendala.class);
                value = "4 TAK";
                intent.putExtra("JENIS", value);
                startActivity(intent);
                break;
            case R.id.btn2tak:
                intent = new Intent(MenuUtama.this, Kendala.class);
                value = "2 TAK";
                intent.putExtra("JENIS", value);
                startActivity(intent);
                break;
            default:
                intent = new Intent(MenuUtama.this, Tentang.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Apakah anda ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MenuUtama.this.finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
