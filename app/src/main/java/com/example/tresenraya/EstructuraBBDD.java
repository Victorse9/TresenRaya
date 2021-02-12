package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.BaseColumns;

public class EstructuraBBDD extends AppCompatActivity {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS "+EstructuraRayasPartidas.TABLE_NAME_PARTIDAS
                    + "(" + EstructuraRayasPartidas._ID + " integer PRIMARY KEY, "
                    + EstructuraRayasPartidas.COLUMN_NAME_JUGADOR1 + " text, "
                    + EstructuraRayasPartidas.COLUMN_NAME_JUGADOR2 + " text, "
                    + EstructuraRayasPartidas.COLUMN_NAME_DIFICULTAD + " text, "
                    + EstructuraRayasPartidas.COLUMN_NAME_RESULTADO + " text);";

    public static final String SQL_CREATE_ENTRIES1 =
            "CREATE TABLE IF NOT EXISTS "+EstructuraRayasRanking.TABLE_NAME_RANKING
                    + "(" + EstructuraRayasRanking._ID + " integer PRIMARY KEY, "
                    + EstructuraRayasRanking.COLUMN_NAME_USUARIO + " text, "
                    + EstructuraRayasRanking.COLUMN_NAME_PARTIDAS + " integer, "
                    + EstructuraRayasRanking.COLUMN_NAME_PUNTOS + " integer);";


    public static final String SQL_DELETE_ENTRIES= "DROP TABLE IF EXISTS "+EstructuraRayasPartidas.TABLE_NAME_PARTIDAS;
    public static final String SQL_DELETE_ENTRIES1= "DROP TABLE IF EXISTS "+EstructuraRayasRanking.TABLE_NAME_RANKING;

    /* Clase interna que define la estructura de la tabla de tres en raya */
    public static class EstructuraRayasPartidas implements BaseColumns {
        public static final String TABLE_NAME_PARTIDAS= "Partidas";
        public static final String COLUMN_NAME_JUGADOR1= "Jugador_1";
        public static final String COLUMN_NAME_JUGADOR2= "Jugador_2";
        public static final String COLUMN_NAME_DIFICULTAD= "Dificultad";
        public static final String COLUMN_NAME_RESULTADO= "Resultado";
    }

    public static class EstructuraRayasRanking implements BaseColumns {
        public static final String TABLE_NAME_RANKING= "Ranking";
        public static final String COLUMN_NAME_USUARIO= "usuario_ranking";
        public static final String COLUMN_NAME_PARTIDAS= "partidas_ranking";
        public static final String COLUMN_NAME_PUNTOS= "puntos_ranking";
    }


}