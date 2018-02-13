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

public class Kendala extends AppCompatActivity implements View.OnClickListener {

    Button btnYa, btnTidak;
    Intent intent;
    TextView textPertanyaan;
    String jenis = "", kondisi = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kendala);
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
        setTitle(jenis);

        getKendala(jenis);
    }

    private int getRow(String jenis) {
        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(DISTINCT kendala) FROM kendaraan " +
                "WHERE jenis='" + jenis + "'" + kondisi, null);
        c.moveToFirst();
        int tmp = c.getInt(0);
        c.close();
        return tmp;
    }

    private void getKendala(String jenis) {
        int row = getRow(jenis);
        if (row == 0) {
            kondisi = "";
            getKendala(jenis);
        } else {
            SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
            Cursor c = db.rawQuery("SELECT DISTINCT kendala FROM kendaraan " +
                    "WHERE jenis='" + jenis + "'" + kondisi, null);
            c.moveToFirst();
            textPertanyaan.setText(c.getString(0));
            kondisi = kondisi + " AND kendala != '" + c.getString(0) + "'";
            c.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYa:
                this.finish();
                intent = new Intent(Kendala.this, Diagnosa.class);
                intent.putExtra("JENIS", jenis);
                intent.putExtra("KENDALA", textPertanyaan.getText().toString());
                startActivity(intent);
                break;
            default:
                getKendala(jenis);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        intent = new Intent(Kendala.this, MenuUtama.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            intent = new Intent(Kendala.this, MenuUtama.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
