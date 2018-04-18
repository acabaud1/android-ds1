package com.ynov.aca.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.ynov.aca.IHttpResult;
import com.ynov.aca.R;
import com.ynov.aca.adapter.EntryAdapter;
import com.ynov.aca.model.Entree;
import com.ynov.aca.service.HttpService;
import com.ynov.aca.service.ImageService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ListEntryActivity extends AppCompatActivity {

    IHttpResult mResultCallback = null;
    HttpService mHttpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entry);
        setTitle("Visualiser les entrées");

        initHttp();
        mHttpService = new HttpService(mResultCallback,this);
        mHttpService.getData("http://thibault01.com:8081/getEntree");

        ListView listEntries = findViewById(R.id.listEntries);

        listEntries.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListEntryActivity.this, DetailActivity.class);
                intent.putExtra("data", (Serializable) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }

    public void initHttp() {
        mResultCallback = new IHttpResult() {
            @Override
            public void httpSuccess(String requestType, String response) {
                Log.d("HttpLog", "HTTP String response: " + response);

                try {
                    ArrayList<Entree> entryList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Entree entry = new Entree();

                        entry.setId(jsonObject.getInt("id"));
                        entry.setNom(jsonObject.getString("nom"));
                        entry.setEspece(jsonObject.getString("espece"));
                        entry.setSexe(jsonObject.getString("sexe"));
                        entry.setDescription(jsonObject.getString("description"));

                        //new ImageService(entry).execute("http://thibault01.com:8081/images/" + jsonObject.getInt("id") + ".png");
                        entryList.add(entry);
                    }

                    ListView listViewEntries = findViewById(R.id.listEntries);
                    EntryAdapter adapter = new EntryAdapter(ListEntryActivity.this, R.layout.activity_list_entry, entryList);
                    listViewEntries.setAdapter(adapter);
                } catch(Exception e) {
                    Log.e("JSONError", e.getMessage());
                }

            }

            @Override
            public void httpSuccess(String requestType, JSONObject response) {
                Log.d("HttpLog", "HTTP JSON response: " + response);

            }

            @Override
            public void httpError(String requestType, VolleyError error) {
                Log.d("HttpLog", "HTTP error: " + error.getMessage());
            }
        };
    }
}
