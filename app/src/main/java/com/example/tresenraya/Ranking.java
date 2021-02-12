package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Ranking extends AppCompatActivity {
    SQLiteHelper helper;
    SQLiteDatabase db;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        lv = findViewById(R.id.itemListRanking);
        mostrarTabla();
    }

    /**
     * Muestra la tabla
     */
    public void mostrarTabla() {
        helper = new SQLiteHelper(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(EstructuraBBDD.EstructuraRayasRanking.TABLE_NAME_RANKING, null, null, null, null, null, "puntos_ranking");
        int[] to = {R.id.txtUsuario, R.id.txtPartidas, R.id.txtPuntos};
        String[] from = {EstructuraBBDD.EstructuraRayasRanking.COLUMN_NAME_USUARIO, EstructuraBBDD.EstructuraRayasRanking.COLUMN_NAME_PARTIDAS, EstructuraBBDD.EstructuraRayasRanking.COLUMN_NAME_PUNTOS};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista_ranking, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        lv.setAdapter(adaptador);
        db.close();
    }
}