package com.example.irving.parquimetro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //  Variables
    private EditText costoHora, costoFraccion, horaInicio, horaFin;
    private Button boton;
    private TextView texto;
    private PagoEstacionamiento pago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Conectar variables con controles
        costoHora = (EditText) findViewById(R.id.costoHora);
        costoFraccion = (EditText) findViewById(R.id.costoFraccion);
        horaInicio = (EditText) findViewById(R.id.horaInicio);
        horaFin = (EditText) findViewById(R.id.horaFin);
        boton = (Button) findViewById(R.id.button);
        texto = (TextView) findViewById(R.id.textView);
        pago = new PagoEstacionamiento();
    }

    public void onClick(View v)
    {
        if (!hayCamposVacios())
        {
            //  Si ya se presionó el boton una vez
            if (boton.getText().toString().equals("Detener"))
            {
                //  Finaliza conteo y cambia etiqueta del boton
                horaFin.setText(pago.obtenerDateTime());
                boton.setText("Iniciar");

                //  Habilitar controles de precio para volver a usarlos
                costoHora.setEnabled(true);
                costoFraccion.setEnabled(true);

                //  Calcula total a pagar
                Integer pagoTotal = pago.calculaPago(costoHora.getText().toString(),
                        costoFraccion.getText().toString());
                texto.setText("Total a pagar: $" + pagoTotal.toString());

                //  Reiniciar date
                pago.setFin(null);
                pago.setInicio(null);

                costoHora.requestFocus();
            }
            //  Si es la primera vez que se presiona
            else
            {
                //  Vaciar campos
                horaInicio.setText("");
                horaFin.setText("");
                texto.setText("Total a pagar: $0");

                //  Inicia conteo y cambia etiqueta del boton
                horaInicio.setText(pago.obtenerDateTime());
                boton.setText("Detener");

                //  Deshabilitar controles de precio
                costoHora.setEnabled(false);
                costoFraccion.setEnabled(false);
            }
        }
    }

    /*  Metodo que comprueba que el costo
    **  esté asignado para hora y fracción
    */
    private boolean hayCamposVacios()
    {
        Boolean r = true;
        if (costoHora.getText().length() == 0)
        {
            costoHora.requestFocus();
            costoHora.setError("Costo por hora es requerido");
        }
        else if (costoFraccion.getText().length() == 0)
        {
            costoFraccion.requestFocus();
            costoFraccion.setError("Costo por fraccion es requerido");
        }
        else
        {
            r = false;
        }
        return r;
    }

    public void onClickHora (View v)
    {
        int idView = v.getId();
        switch (idView)
        {
            case R.id.horaInicio:
                Toast.makeText(this,"Se establecerá la hora de inicio al presionar Iniciar",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.horaFin:
                Toast.makeText(this,"Se establecerá la hora de fin al presionar Finalizar",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }




}
