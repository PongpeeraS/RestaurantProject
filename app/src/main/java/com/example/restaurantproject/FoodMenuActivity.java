package com.example.restaurantproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.restaurantproject.Adapter.SearchAdapter;
import com.example.restaurantproject.database.database;
import com.google.firebase.database.DatabaseReference;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;


public class FoodMenuActivity extends AppCompatActivity {
    private EditText mSearchField;
    private Button mSearchBtn;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mResultList;
    private DatabaseReference mUserDatabase;
    private com.example.restaurantproject.database.database database;
    SearchAdapter adapter;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        mResultList = (RecyclerView) findViewById(R.id.recycler_search);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mResultList.setLayoutManager(linearLayoutManager);
        mResultList.setHasFixedSize(true);
        mResultList.setAdapter(adapter);

        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar);

        database = new database(this);

        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);
        loadSuggestList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                List<String> suggest = new ArrayList<>();
                for(String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    mResultList.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }
            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        adapter = new SearchAdapter(this,database.getFood());
    }

    private void startSearch(String text){
        adapter = new SearchAdapter(this,database.getFoodbyName(text));
        mResultList.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList=database.getName();
        materialSearchBar.setLastSuggestions(suggestList);
    }

}