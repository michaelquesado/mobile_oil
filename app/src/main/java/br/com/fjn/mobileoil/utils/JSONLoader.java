package br.com.fjn.mobileoil.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by unobre on 28/08/2015.
 */
public class JSONLoader {
    public static String getJSON(String url) {
        InputStream inputStream = null;
        String result = "";

        try {

            HttpClient httpclient = new DefaultHttpClient(); // create HttpClient

            HttpGet o = new HttpGet(url); // preparando objeto httpget
            o.setHeader("Accept", "application/json"); // Cabecalhos
            o.setHeader("Content-Type", "application/json");

            HttpResponse httpResponse = httpclient.execute(o); // Executando a requisicao
            inputStream = httpResponse.getEntity().getContent(); // Recebe a resposta

            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Impossivel obter arquivo do servidor.";
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();
        return result;
    }
}