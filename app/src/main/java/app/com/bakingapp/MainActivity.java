package app.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import app.com.bakingapp.adapter.RecipeListAdapter;
import app.com.bakingapp.model.Recipe;
import app.com.bakingapp.utils.JsonUtils;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.ClickListener {

    public static final String RECIPE_EXTRA = "app.com.bakingapp.recipe.extra";

    RecyclerView recipeList;
    RecipeListAdapter adapter;
    List<Recipe> list;
    GridLayoutManager gridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView large_tv = (TextView) findViewById(R.id.large_tv);
        gridLayoutManager = new GridLayoutManager(this, 1);
        if(large_tv != null){
            gridLayoutManager.setSpanCount(3);
        }
        adapter = new RecipeListAdapter();
        adapter.setRecipeClickListener(this);

        recipeList = findViewById(R.id.list_rv);
        recipeList.setHasFixedSize(true);
        recipeList.setLayoutManager(gridLayoutManager);
        recipeList.setAdapter(adapter);

        try{
            InputStream inputStream = getResources().openRawResource(R.raw.baking);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String l, ligne= "";

            while ((l = bufferedReader.readLine()) != null) {
                ligne += l;
            }

            list = JsonUtils.buildRecipeList(new JSONArray(ligne));
            adapter.setList(list);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(Recipe recipe) {
        Toast.makeText(this, recipe.getName(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, StepsActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        startActivity(intent);
    }
}
