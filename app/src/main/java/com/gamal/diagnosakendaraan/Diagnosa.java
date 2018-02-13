package com.gamal.diagnosakendaraan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Diagnosa extends AppCompatActivity implements View.OnClickListener{

    Button btnYa, btnTidak;
    Intent intent;
    TextView textPertanyaan;
    String jenis = "", kendala = "", kondisi = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosa);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        textPertanyaan = (TextView) findViewById(R.id.textPertanyaan);

        btnYa = (Button) findViewById(R.id.btnYa);
        btnYa.setOnClickListener(this);
        btnTidak = (Button) findViewById(R.id.btnTidak);
        btnTidak.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        jenis = extras.getString("JENIS");
        kendala = extras.getString("KENDALA");
        setTitle(jenis);

        getDiagnosa(jenis, kendala);
    }

    private int getRow(String jenis, String kendala) {
        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(DISTINCT diagnosa) FROM kendaraan " +
                "WHERE jenis='" + jenis + "' AND kendala = '"+kendala+"'" + kondisi, null);
        c.moveToFirst();
        int tmp = c.getInt(0);
        c.close();
        return tmp;
    }

    private void getDiagnosa(String jenis, String kendala) {
        int row = getRow(jenis, kendala);
        if (row == 0) {
            kondisi = "";
            getDiagnosa(jenis, kendala);
        } else {
            SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
            Cursor c = db.rawQuery("SELECT DISTINCT diagnosa FROM kendaraan " +
                    "WHERE jenis='" + jenis + "' AND kendala = '"+kendala+"'" + kondisi, null);
            c.moveToFirst();
            textPertanyaan.setText(c.getString(0));
            kondisi = kondisi + " AND diagnosa != '" + c.getString(0) + "'";
            c.close();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYa:
                this.finish();
                intent = new Intent(Diagnosa.this, Solusi.class);
                intent.putExtra("JENIS", jenis);
                intent.putExtra("KENDALA", kendala);
                intent.putExtra("DIAGNOSA", textPertanyaan.getText());
                startActivity(intent);
                break;
            default:
                getDiagnosa(jenis, kendala);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        intent = new Intent(Diagnosa.this, Kendala.class);
        intent.putExtra("JENIS", jenis);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            intent = new Intent(Diagnosa.this, Kendala.class);
            intent.putExtra("JENIS", jenis);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
