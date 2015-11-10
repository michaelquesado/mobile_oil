package br.com.fjn.mobileoil.dao;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by ch4p0 on 10/11/2015.
 */
public class LoginWebService {

    public static final String URL_LOGIN_CADASTRO = "http://93.188.167.153/ws_mobile_oil/login/cadastro";

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

            } catch (Exception e) {}
    }
}
