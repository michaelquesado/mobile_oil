package br.com.fjn.mobileoil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class CombustivelAdicionarValor extends Activity implements View.OnClickListener {

    private String TAG = "ACT_ADD_VALOR";

    private EditText mTextValorCombustivel;
    private Button buttonSalvarValorCombustivel;

    private String postoID;
    private String combustivelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combustivel_adicionar_valor);

        //Inicializa os componentes
        initComponents();

        Intent it = getIntent();
        if (it != null) {
            postoID = it.getStringExtra("postoID");
            combustivelID = it.getStringExtra("postoCombustivelId");
            Log.e(TAG, "idposto: " + postoID);
        }
    }

    private void initComponents() {
        mTextValorCombustivel = (EditText) findViewById(R.id.editTextValorCombustivel);
        buttonSalvarValorCombustivel = (Button) findViewById(R.id.buttonSalvarValorCombustivel);
        buttonSalvarValorCombustivel.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_combustivel_adicionar_valor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (mTextValorCombustivel.getText().toString().length() < 4) {
            Toast.makeText(this, "Informe o numero corretamente.", Toast.LENGTH_SHORT).show();
        } else {
            sendPostRequest(postoID, mTextValorCombustivel.getText().toString(), combustivelID, "1");
        }
    }

    private void sendPostRequest(String postoId, String valor, String combustivelID, String usuarioId) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String paramPostoId = params[0];
                String paramValor = params[1];
                String paramCombustivelId = params[2];
                String paramUsuarioId = params[3];

                HttpClient httpClient = new DefaultHttpClient();

                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpPost httpPost = new HttpPost("http://93.188.167.153/ws_mobile_oil/Precos/cadastrar");

                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
                BasicNameValuePair postoId = new BasicNameValuePair("posto_id", paramPostoId);
                BasicNameValuePair postoValor = new BasicNameValuePair("valor", paramValor);
                BasicNameValuePair postoCombustivelId = new BasicNameValuePair("combustivel_id", paramCombustivelId);
                BasicNameValuePair postoUsuarioId = new BasicNameValuePair("usuario_id", paramUsuarioId);

                // We add the content that we want to pass with the POST request to as name-value pairs
                //Now we put those sending details to an ArrayList with type safe of NameValuePair
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(postoId);
                nameValuePairList.add(postoValor);
                nameValuePairList.add(postoCombustivelId);
                nameValuePairList.add(postoUsuarioId);

                try {
                    // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
                    //This is typically useful while sending an HTTP POST request.
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                    // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                    httpPost.setEntity(urlEncodedFormEntity);

                    try {
                        // HttpResponse is an interface just like HttpPost.
                        //Therefore we can't initialize them
                        HttpResponse httpResponse = httpClient.execute(httpPost);

                        // According to the JAVA API, InputStream constructor do nothing.
                        //So we can't initialize InputStream although it is not an interface
                        InputStream inputStream = httpResponse.getEntity().getContent();

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuilder stringBuilder = new StringBuilder();

                        String bufferedStrChunk = null;

                        while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                            stringBuilder.append(bufferedStrChunk);
                        }

                        return stringBuilder.toString();

                    } catch (ClientProtocolException cpe) {
                        System.out.println("First Exception caz of HttpResponese :" + cpe);
                        cpe.printStackTrace();
                    } catch (IOException ioe) {
                        System.out.println("Second Exception caz of HttpResponse :" + ioe);
                        ioe.printStackTrace();
                    }

                } catch (UnsupportedEncodingException uee) {
                    System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
                    uee.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(getApplicationContext(), "Cadastrado com sucesso! " + result, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    String msg = jsonObject.getString("msg").trim();

                    if (msg.equals("ok")) {
                        Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid POST req...", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(postoId, valor, combustivelID, usuarioId);
    }
}