package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Partidas extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas);
        lv = findViewById(R.id.itemList);

        mostrarTabla();
    }

    public void mostrarTabla(){
        helper = new SQLiteHelper(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(EstructuraBBDD.EstructuraRayasPartidas.TABLE_NAME_PARTIDAS, null, null,null,null,null,null);
        int [] to = {R.id.listaJugador1, R.id.listaJugador2, R.id.listaDificultad, R.id.listaResultado};
        String [] from = {EstructuraBBDD.EstructuraRayasPartidas.COLUMN_NAME_JUGADOR1, EstructuraBBDD.EstructuraRayasPartidas.COLUMN_NAME_JUGADOR2, EstructuraBBDD.EstructuraRayasPartidas.COLUMN_NAME_DIFICULTAD, EstructuraBBDD.EstructuraRayasPartidas.COLUMN_NAME_RESULTADO};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        lv.setAdapter(adaptador);
        db.close();
    }
}