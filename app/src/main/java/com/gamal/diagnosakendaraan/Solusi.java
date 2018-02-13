package com.gamal.diagnosakendaraan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Solusi extends AppCompatActivity {
    Intent intent;
    SimpleDateFormat sdft, sdfw;
    Locale inLocale;
    TextView textSolusi, textTanggal, textWaktu;
    String jenis = "", kendala = "", diagnosa = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solusi);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        inLocale = new Locale("id", "ID");
        sdft = new SimpleDateFormat("EEEE, dd MMMM yyyy", inLocale);
        sdfw = new SimpleDateFormat("HH:mm:ss", inLocale);

        textSolusi = (TextView) findViewById(R.id.textViewSolusi);
        textTanggal = (TextView) findViewById(R.id.textViewTanggal);
        textWaktu = (TextView) findViewById(R.id.textViewWaktu);

        Bundle extras = getIntent().getExtras();
        jenis = extras.getString("JENIS");
        kendala = extras.getString("KENDALA");
        diagnosa = extras.getString("DIAGNOSA");
        setTitle(jenis);

        getSolusi(jenis, kendala, diagnosa);
    }

    private void getSolusi(String jenis, String kendala, String diagnosa) {
        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT solusi FROM kendaraan " +
                "WHERE jenis='" + jenis + "' AND kendala = '"+kendala+"' AND diagnosa='" + diagnosa +"'", null);
        c.moveToFirst();
        textSolusi.setText(c.getString(0));
        textTanggal.setText(sdft.format(new Date()));
        textWaktu.setText(sdfw.format(new Date()));
        c.close();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        intent = new Intent(Solusi.this, MenuUtama.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            intent = new Intent(Solusi.this, MenuUtama.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
