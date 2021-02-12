package com.example.tresenraya;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private MediaPlayer media, mediaMusica;
    private SQLiteHelper helper;
    private SQLiteDatabase db;
    private int partidasJugadas;
    private String dificultad;
    private RadioGroup rGroup;
    private RadioButton r1, r2, r3;
    private int tiempoMusica;
    private EditText jugador, jugador1, jugador2;
    //para guardar la casilla pulsada
    private int[] CASILLAS;
    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new SQLiteHelper(this);
        db = helper.getWritableDatabase();

        //Inicializamos el array con cada casilla del tablero
        CASILLAS = new int[9];
        CASILLAS[0] = R.id.a1;
        CASILLAS[1] = R.id.a2;
        CASILLAS[2] = R.id.a3;
        CASILLAS[3] = R.id.b1;
        CASILLAS[4] = R.id.b2;
        CASILLAS[5] = R.id.b3;
        CASILLAS[6] = R.id.c1;
        CASILLAS[7] = R.id.c2;
        CASILLAS[8] = R.id.c3;

        rGroup = findViewById(R.id.grupoDificultad);
        r1 = findViewById(R.id.rFacil);
        r2 = findViewById(R.id.rDificil);
        r3 = findViewById(R.id.rExtremo);
        jugador = findViewById(R.id.txtJugador);
        jugador1 = findViewById(R.id.txtJugador1);
        jugador2 = findViewById(R.id.txtJugador2);
        media = MediaPlayer.create(this, R.raw.sonido_click);
        mediaMusica = MediaPlayer.create(this, R.raw.retro_linkin);


        if (!mediaMusica.isPlaying()) {
            mediaMusica.start();
            mediaMusica.seekTo(0);
        }

    }

    // Boton mute musica
    public void musicaAmbiente(View view) {
        if (mediaMusica.isPlaying()) {
            tiempoMusica = mediaMusica.getCurrentPosition();
            mediaMusica.pause();
        } else {
            mediaMusica.start();
            mediaMusica.seekTo(tiempoMusica);
        }
    }

    /**
     * Boton jugar
     *
     * @param v
     */
    public void Jugar(View v) throws Exception {
            // Insertar nuevo nombre
            ContentValues values = new ContentValues();
            values.put(EstructuraBBDD.EstructuraRayasRanking.COLUMN_NAME_USUARIO, jugador.getText().toString());
            db.insert("Ranking", null, values);




        if (jugador.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debes escribir un nombre antes de jugar.", Toast.LENGTH_SHORT).show();
        } else {
            //reseteamos el tablero
            ImageView imagen;

            for (int casilla : CASILLAS) {
                imagen = (ImageView) findViewById(casilla);

                imagen.setImageResource(R.drawable.casilla);
            }

            //establecemos los jugadores que van a jugar (1 o 2 jugadores)
            int jugadores = 1;
            //el metodo Jugar será llamado tanto en el botón de un jugador como en el de dos
            //por eso comprobamos la vista que entra como parámetro
            if (v.getId() == R.id.dosjugadores) {
                jugadores = 2;
            }

            //evaluamos la dificultad
            RadioGroup configDificultad = (RadioGroup) findViewById(R.id.grupoDificultad);

            int id = configDificultad.getCheckedRadioButtonId();

            int dificultad = 0;

            if (id == R.id.rDificil) {
                dificultad = 1;
            } else if (id == R.id.rExtremo) {
                dificultad = 2;
            }

            int juadores = 1;
            partida = new Partida(dificultad, juadores);

            //deshabilitamos los botones del tablero
            ((Button) findViewById(R.id.unjugador)).setEnabled(false);
            ((Button) findViewById(R.id.dosjugadores)).setEnabled(false);
            ((RadioGroup) findViewById(R.id.grupoDificultad)).setAlpha(0);
        }
    }

    //creamos el método que se lanza al pulsar cada casilla
    public void toqueCasilla(View v) {

        //hacemos que sólo se ejecute cuando la variable partida no sea null
        if (partida == null) {
            return;
        } else {
            int casilla = 0;
            //recorremos el array donde tenemos almacenada cada casilla
            for (int i = 0; i < 9; i++) {
                if (CASILLAS[i] == v.getId()) {
                    casilla = i;
                    break;
                }
            }

            //creamos un mensaje toast
           /* Toast mensaje= Toast.makeText(this,"has pulsado la casilla "+ casilla,Toast.LENGTH_LONG);
            mensaje.setGravity(Gravity.CENTER,0,0);
            mensaje.show();*/

            //si la casilla pulsada ya está ocupada salimos del método
            if (partida.casilla_libre(casilla) == false) {
                return;
            }
            //llamamos al método para marcar la casilla que se ha tocado
            marcaCasilla(casilla);
            media.start();
            media.seekTo(0);

            int resultado = partida.turno();

            if (resultado > 0) {
                terminar_partida(resultado);
                return;
            }

            //realizamos el marcado de la casilla que elige el programa
            casilla = partida.ia();

            while (partida.casilla_libre(casilla) != true) {
                casilla = partida.ia();
            }

            marcaCasilla(casilla);

            resultado = partida.turno();

            if (resultado > 0) {
                terminar_partida(resultado);
            }

        }
    }

    //metodo para marcar las casillas
    private void marcaCasilla(int casilla) {
        ImageView imagen;
        imagen = (ImageView) findViewById(CASILLAS[casilla]);

        if (partida.jugador == 1) {
            imagen.setImageResource(R.drawable.circulo);
        } else {
            imagen.setImageResource(R.drawable.aspa);
        }

    }

    /**
     * Ir a la vista Partidas
     *
     * @param view
     */
    public void irPartidas(View view) {
        Intent i = new Intent(this, Partidas.class);
        startActivity(i);
    }

    /**
     * Ir a la vista Ranking
     *
     * @param view
     */
    public void irRanking(View view) {
        Intent i = new Intent(this, Ranking.class);
        startActivity(i);
    }

    /**
     * Comprueba la dificultad
     *
     * @return
     */
    public String compruebaDificultad() {
        //evaluamos la dificultad
        RadioGroup configDificultad = (RadioGroup) findViewById(R.id.grupoDificultad);

        int id = configDificultad.getCheckedRadioButtonId();

        String dificultad = "Fácil";

        if (id == R.id.rDificil) {
            dificultad = "Difícil";
        } else if (id == R.id.rExtremo) {
            dificultad = "Extremo";
        }
        return dificultad;
    }

    /**
     * Al terminar la partida guardamos datos
     *
     * @param res
     */
    private void terminar_partida(int res) {

        String mensaje;
        dificultad = compruebaDificultad();

        if (res == 1) {
            mensaje = "Ganan círculos";
            insertaRanking(1);
            insertaPartidas(mensaje);
        } else if (res == 2) {
            mensaje = "Ganan aspas";
            insertaRanking(0);
            insertaPartidas(mensaje);
        } else {
            mensaje = "Empate";
            insertaRanking(0);
            insertaPartidas(mensaje);
        }


        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        //terminamos el juego
        partida = null;

        //habilitamos los botones del tablero
        ((Button) findViewById(R.id.unjugador)).setEnabled(true);
        ((Button) findViewById(R.id.dosjugadores)).setEnabled(true);
        ((RadioGroup) findViewById(R.id.grupoDificultad)).setAlpha(1);

    }

    /**
     * Insertamos en la bbdd la partida
     *
     * @param mensaje
     */
    public void insertaPartidas(String mensaje) {
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.EstructuraRayasPartidas.COLUMN_NAME_JUGADOR1, jugador.getText().toString());
        values.put(EstructuraBBDD.EstructuraRayasPartidas.COLUMN_NAME_JUGADOR2, "Máquina");
        values.put(EstructuraBBDD.EstructuraRayasPartidas.COLUMN_NAME_DIFICULTAD, dificultad);
        values.put(EstructuraBBDD.EstructuraRayasPartidas.COLUMN_NAME_RESULTADO, mensaje);
        db.insert("Partidas", null, values);
    }

    /**
     * Inserta los datos al ranking
     */
    public void insertaRanking(int puntos) {
        ContentValues values = new ContentValues();
        String nombre = jugador.getText().toString();
        values.put(EstructuraBBDD.EstructuraRayasRanking.COLUMN_NAME_PARTIDAS, 1);
        values.put(EstructuraBBDD.EstructuraRayasRanking.COLUMN_NAME_PUNTOS, puntos);
        String[] args = new String []{nombre};
        db.update("Ranking", values, "usuario_ranking = ?", args);
    }

    /**
     * Consulta las partidas jugadas por un jugador
     *
     * @return
     */
    /*
    private String compruebaNombre() throws Exception{
        db = helper.getReadableDatabase();
        String nombre="";
        String usuario = jugador.getText().toString();
        String[] columns = {"usuario_ranking"};

        String selection = "usuario_ranking = ?";
        String[] selectionArgs = {usuario};
        try{
            Cursor c = db.query("Ranking", columns, selection, selectionArgs, null,
                    null, null);
            nombre = c.getString(0);
        }catch (Exception e){
            nombre = "";
            throw e;
        }

        return nombre;
    }*/


}