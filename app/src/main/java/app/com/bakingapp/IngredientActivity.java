package app.com.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import app.com.bakingapp.adapter.IngreAdapter;
import app.com.bakingapp.model.Recipe;

public class IngredientActivity extends AppCompatActivity {
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        Recipe re = getIntent().getParcelableExtra(MainActivity.RECIPE_EXTRA);
        rv = (RecyclerView) findViewById(R.id.list_rv);
        setTitle(re.getName());

        IngreAdapter adpt= new IngreAdapter();
        adpt.setList(re.getIngredients());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adpt);
    }
}
