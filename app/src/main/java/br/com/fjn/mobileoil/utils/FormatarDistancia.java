package br.com.fjn.mobileoil.utils;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by unobre on 28/08/2015.
 *
 * @param d Distância como String do JSON
 * @author unobre
 *         <p>
 *         formata a distancia baseado no dado informado.
 *         Se for abaixo de mil metros, então retorna como metros.
 *         Se for acima de mil metros, então retorna como kilometros.
 *         </p>
 */
public class FormatarDistancia {

    /**
     * @param d Distancia em como String
     * @return Aqui, 2 Mts ou 1.2Km
     */
    public static String getDistanciaFormatada(String d) {
        double distancia = Double.parseDouble(d);
        String distanciaString = "Aqui";

        if (distancia <= 1) {
            return distanciaString;
        }

        if (distancia < 1000) {
            distanciaString = (int) distancia + " Mts";
        } else {
            double numero = distancia / 1000;
            DecimalFormat df = new DecimalFormat("#,###.0");
            distanciaString = df.format(numero) + " Km";
        }
        return distanciaString;
    }
}
