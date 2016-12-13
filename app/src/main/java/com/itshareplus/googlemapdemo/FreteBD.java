package com.itshareplus.googlemapdemo;

/**
 * Created by marcos on 11/12/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FreteBD extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "motofrete";
    private static int VERSAO = 1;


    public FreteBD(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE frete (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "consumo INT, " +
                "km INT, " +
                "valor DOUBLE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS frete");
    }

}
