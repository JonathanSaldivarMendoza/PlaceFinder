package com.mobilestudio.placefinder;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mobilestudio.placefinder.Model.apiFourSquareResponse;

public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        callFourSquareAPI("gasolinera");
    }

    /*
      El metodo callFourSquareAPI recibe el parametro queryString que puede ser cualquier tipo de busqueda tal como bar, restaruante, gasolinera etc.
   */
    public void callFourSquareAPI(String queryString){

        RequestQueue myQue = Volley.newRequestQueue(this);
        String locationString = "19.395209" +","+"-99.1544203";
        String URL = Uri.parse("https://api.foursquare.com/v2/venues/search").buildUpon()
                .appendQueryParameter("client_id","L4UK14EMS0MCEZOVVUYX2UO5ULFHJN3EHOFVQFSW0Z1MSFSR")
                .appendQueryParameter("client_secret","YKJB0JRFDPPSGTHALFOEP5O1NDDATHKQ2IZ5RO2GOX452SFA")
                .appendQueryParameter("v","20130815")
                .appendQueryParameter("ll",locationString)
                .appendQueryParameter("query",queryString).build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL,this,this);

        // aqui ejecutamos el request

        myQue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(Object response) {
        Log.i("Response:  ", "onResponse:" + response.toString());

        // interpretamos el response en formato JSON  pr meio de la libreria Gson
        Gson gson = new Gson();
        try {
            apiFourSquareResponse fourSquareResponse = gson.fromJson((String) response, apiFourSquareResponse.class);
        }catch (JsonParseException e){

        }
    }
}
