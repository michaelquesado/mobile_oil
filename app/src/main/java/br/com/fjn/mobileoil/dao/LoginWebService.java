package br.com.fjn.mobileoil.dao;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Junior Siqueira on 10/11/2015.
 */
public class LoginWebService {

    public static final String URL_LOGIN_CADASTRO = "http://93.188.167.153/ws_mobile_oil/login/cadastro";
    public static final String URL_LOGIN_VALIDAR = "http://93.188.167.153/ws_mobile_oil/login/logar";

    //funcao que salva os dados de login do usuario no banco de dados atraves do WebService
    public static void cadastrarDadosDoLogin(String username, String pass, String email) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_LOGIN_CADASTRO);

        try {
            ArrayList<NameValuePair> valoresLogin = new ArrayList<NameValuePair>();
            valoresLogin.add(new BasicNameValuePair("username", username));
            valoresLogin.add(new BasicNameValuePair("pass", pass));
            valoresLogin.add(new BasicNameValuePair("email", email));

            httpPost.setEntity(new UrlEncodedFormEntity(valoresLogin));
            final HttpResponse resposta = httpClient.execute(httpPost);

        } catch (Exception e) {
        }
    }


    //função que checa na hora do login se o usuário já está cadastrado no banco
    //se retornar true ele loga e não salva nem altera nada no banco, se der false ele
    //se loga salvando todos os seus dados no banco.
    public static String validarUserBanco(String pass, String email) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_LOGIN_VALIDAR);
        InputStream inputStream = null;
        String result = null;

        try {
            ArrayList<NameValuePair> valoresLogin = new ArrayList<NameValuePair>();
            valoresLogin.add(new BasicNameValuePair("pass", pass));
            valoresLogin.add(new BasicNameValuePair("email", email));
            httpPost.setEntity(new UrlEncodedFormEntity(valoresLogin));
            final HttpResponse resposta = httpClient.execute(httpPost);

            inputStream = resposta.getEntity().getContent();

            if (inputStream != null) {
                String dados = getStringFromInputStream(inputStream);
                JSONObject jsonObject = new JSONObject(dados);
                result = jsonObject.getString("retorno");
            } else {
                result = "Não conseguimos obter nenhuma resposta do servidor";
            }
        } catch (Exception e) {
        }
        System.out.println(result);
        return result;
    }


    //função responsável por converter um InputStrem em String para
    //capturar o valor da resposta do json retornado na função de validar o usuário
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}