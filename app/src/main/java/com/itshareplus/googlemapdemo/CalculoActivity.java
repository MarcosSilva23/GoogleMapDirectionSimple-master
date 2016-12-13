package com.itshareplus.googlemapdemo;

/**
 * Created by marcos on 11/12/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CalculoActivity extends AppCompatActivity {

    EditText txtDist, txtConsumo, txtLitroGas, txtLucro;
    Button btnConsultaFrete, btnAjustaTela,btnBateria,btnHistorico;
    private FreteBD base;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);

        btnAjustaTela = (Button) findViewById(R.id.btnAjustaTela);
        btnBateria = (Button) findViewById(R.id.btnBateria);
        final LinearLayout fundoTela = (LinearLayout) findViewById(R.id.fundoTela);
        btnConsultaFrete = (Button) findViewById(R.id.btnConsultaFrete);
        txtDist  = (EditText)findViewById(R.id.txtDist);
        txtConsumo  = (EditText)findViewById(R.id.txtConsumo);
        txtLitroGas  = (EditText)findViewById(R.id.txtLitroGas);
        txtLucro  = (EditText)findViewById(R.id.txtLucro);
        btnHistorico = (Button) findViewById(R.id.btnHistorico);



        base = new FreteBD(this);

        btnAjustaTela.setOnClickListener(new Button.OnClickListener() {
                                             public void onClick(View v) {
                                                 fundoTela.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                             }
                                         }
        );

        btnBateria.setOnClickListener(new Button.OnClickListener() {
                                             public void onClick(View v) {
                                                 fundoTela.setBackgroundColor(Color.parseColor("#363636"));
                                             }
                                         }
        );



        btnConsultaFrete.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    double v1 = Double.parseDouble(txtDist.getText().toString());
                                                    double v2 = Double.parseDouble(txtConsumo.getText().toString());
                                                    double v3 = Double.parseDouble(txtLitroGas.getText().toString());
                                                    double v4 = Double.parseDouble(txtLucro.getText().toString());
                                                    double res = ((v1 + v1 )* v2 *  v3) + v4;


                                                    AlertDialog.Builder dialogo = new AlertDialog.Builder(CalculoActivity.this);
                                                    dialogo.setTitle("FRETE");
                                                    dialogo.setMessage("Total: R$ "+ res);
                                                    dialogo.setNeutralButton("OK", null);
                                                    dialogo.show();

                                                }

                                            }

        );

        btnHistorico.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SQLiteDatabase db = base.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT SUM (valor) FROM frete",null);

                if (cursor.moveToFirst()) {
                    double total = cursor.getDouble(0);

                    AlertDialog.Builder dialogo = new AlertDialog.Builder(CalculoActivity.this);
                    dialogo.setTitle("LUCRO");
                    dialogo.setMessage("Total: R$ "+ total);
                    dialogo.setNeutralButton("OK", null);
                    dialogo.show();



                }


            }

        });

    }

    public void salvarHistorico(View view) {
        SQLiteDatabase db = base.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("consumo", Integer.parseInt(txtConsumo.getText().toString()));
        values.put("km", Integer.parseInt(txtDist.getText().toString()));
        values.put("valor", Double.parseDouble(txtLucro.getText().toString()));

        long resultado = db.insert("frete", null, values);
        if (resultado != -1) {
            Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();
            limpar();
        } else {
            Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
        }
    }

    public void excluirFrete(View view) {
        SQLiteDatabase db = base.getWritableDatabase();
        long resultado = db.delete("frete", null, null);
        if (resultado != -1) {
            Toast.makeText(this, "Registro excluídos com sucesso!", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Erro ao excluir!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        base.close();
        super.onDestroy();
    }

    private void limpar() {

        txtConsumo.setText("");
        txtDist.setText("");
        txtLucro.setText("");
        txtLitroGas.setText("");
    }
}

