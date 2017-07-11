package com.example.james.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 7/11/2017.
 */

public class MovesActivity extends AppCompatActivity {
    private ListView items;
    private List<String> list;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        items = (ListView)findViewById(R.id.lvItems);
        title = (TextView)findViewById(R.id.textTitle);
        title.setText("Moves");
        fetch();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.Pokemons:
                Intent a = new Intent(MovesActivity.this, MainActivity.class);
                startActivity(a);
                break;
            case R.id.Berries:
                Intent b = new Intent(MovesActivity.this, BerriesActivity.class);
                startActivity(b);
                break;
            case R.id.Items:
                Intent c = new Intent(MovesActivity.this, ItemsActivity.class);
                startActivity(c);
                break;
            case R.id.Location:
                Intent d = new Intent(MovesActivity.this, LocationActivity.class);
                startActivity(d);
                break;
            case R.id.Moves:
                return true;

        }
        return false;
    }
    public void fetch() {
        list = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://pokeapi.co/api/v2/move/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {

                                JSONArray res = response.getJSONArray("results");
                                for (int i = 0; i < res.length(); i++) {
                                    JSONObject object = res.getJSONObject(i);
                                    String url = object.getString("name");
                                    list.add(url);
                                }
                                ListAdapter Adapter = new ArrayAdapter<String>(MovesActivity.this, android.R.layout.simple_list_item_1, list);
                                items.setAdapter(Adapter);
                            } catch (JSONException e) {
                                Log.e("Error", e.getMessage());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "Parsing");
                        Toast.makeText(MovesActivity.this, "super", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


}
