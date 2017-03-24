package com.example.irving.programauno;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by irving on 14/03/17.
 */
public class PagoEstacionamiento {

    private Date inicio = null;
    private Date fin = null;

    public PagoEstacionamiento()
    {

    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public String obtenerDateTime()
    {
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String regreso = s.format(new Date());

        //  Si es la primera vez que se pulsa obten inicio
        if (inicio == null)
        {
            try
            {
                inicio = s.parse(regreso);
            } catch(ParseException e)
            {
                e.printStackTrace();
            }

        }
        //  Si no, obten el fin
        else
        {
            try
            {
                fin = s.parse(regreso);
            } catch(ParseException e)
            {
                e.printStackTrace();
            }
        }
        return regreso;
    }

    public int calculaPago(String costoHora, String costoFraccion)
    {
        int iCostoHora = Integer.parseInt(costoHora);
        int iCostoFraccion = Integer.parseInt(costoFraccion);
        int pago;

        ArrayList<Integer> array = getDiferencia();
        int dias = array.get(0);
        int hora = array.get(1);
        int minutos = array.get(2);

        //  Cobra la primer hora
        pago = iCostoHora;
        //  Si excede de la primer hora
        if (hora > 1)
        {
            int fracc = minutos;

            if (fracc >= 0 && fracc < 15)
                fracc = 1;
            else if (fracc >= 15 && fracc < 30 )
                fracc = 2;
            else if (fracc >= 30 && fracc < 45)
                fracc = 3;
            else
                fracc = 4;
            //  Cobra fraccion
            pago += fracc * iCostoFraccion;
            //  Cobra dias, si es el caso
            pago += dias * (24*iCostoHora);
            //  Cobra horas extras, menos la primera
            pago += (hora - 1) * iCostoHora;
        }

        return pago;
    }

    private ArrayList<Integer> getDiferencia(){

        ArrayList<Integer> array = new ArrayList<>();

        long diferencia = fin.getTime() - inicio.getTime();

        int segsMilli = 1000;
        int minsMilli = segsMilli * 60;
        int horasMilli = minsMilli * 60;
        int diasMilli = horasMilli * 24;

        int diasTranscurridos = (int) diferencia / diasMilli;
        diferencia = diferencia % diasMilli;

        int horasTranscurridos = (int)diferencia / horasMilli;
        diferencia = diferencia % horasMilli;

        int minutosTranscurridos = (int) diferencia / minsMilli;


        array.add(diasTranscurridos);
        array.add(horasTranscurridos);
        array.add(minutosTranscurridos);

        return array;
    }
}
