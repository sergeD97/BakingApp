package app.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
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
import app.com.bakingapp.utils.NetWorkUtils;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.ClickListener, LoaderManager.LoaderCallbacks<List<Recipe>> {

    public static final String RECIPE_EXTRA = "app.com.bakingapp.recipe.extra";

    public static final int LOADER_TASK_ID = 111;

    RecyclerView recipeList;
    RecipeListAdapter adapter;
    List<Recipe> list;
    GridLayoutManager gridLayoutManager;
    TextView errorTv;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView large_tv = (TextView) findViewById(R.id.large_tv);
        errorTv = (TextView) findViewById(R.id.error_tv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

        getSupportLoaderManager().initLoader(LOADER_TASK_ID,null,this);


    }


    @Override
    public void onClick(Recipe recipe) {
        Toast.makeText(this, recipe.getName(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, StepsActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable final Bundle args) {

        return new AsyncTaskLoader<List<Recipe>>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                start();
                forceLoad();
            }

            @Nullable
            @Override
            public List<Recipe> loadInBackground() {

                try{
                    /*InputStream inputStream = getResources().openRawResource(R.raw.baking);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String l, ligne= "";

                    while ((l = bufferedReader.readLine()) != null) {
                        ligne += l;
                    }*/

                   String ligne = NetWorkUtils.getResponseFromHttpUrl(NetWorkUtils.buildUrl());

                    return JsonUtils.buildRecipeList(new JSONArray(ligne));

                }catch (Exception e){

                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        Toast.makeText(this, "yygygygygyg", Toast.LENGTH_SHORT).show();
        if(data != null){

            adapter.setList(data);
            success();
        }else{
            error();
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {

    }

    public void success(){
        recipeList.setVisibility(View.VISIBLE);
        errorTv.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

    }

    public void error(){
        recipeList.setVisibility(View.INVISIBLE);
        errorTv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void start(){
        recipeList.setVisibility(View.INVISIBLE);
        errorTv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

    }
}
