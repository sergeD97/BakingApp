package app.com.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import app.com.bakingapp.adapter.StepListAdapter;
import app.com.bakingapp.fragment.DetailStepFragment;
import app.com.bakingapp.fragment.MasterStepFragment;
import app.com.bakingapp.model.Recipe;
import app.com.bakingapp.model.Step;
import app.com.bakingapp.utils.PreferenceUtils;

public class StepsActivity extends AppCompatActivity implements StepListAdapter.StepClickListener{
    public static final String STEP_EXTRA = "app.com.step.extra";
    public static final String PANE = "pane";
    private boolean twoPane;
    FragmentManager fragmentManager;
    Step selectStep;
    MasterStepFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
        Recipe recipe = getIntent().getParcelableExtra(MainActivity.RECIPE_EXTRA);
        setTitle(recipe.getName());

        if(linear == null){
            twoPane=false;
        }else{
            twoPane=true;
        }
        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){

            frag = MasterStepFragment.factory(recipe, -1);
            fragmentManager.beginTransaction()
                    .add(R.id.list_frame, frag)
                    .commit();

            //set the last recipe and update the app widget
            PreferenceUtils.setLastRecipe(this, recipe.getId(), recipe.getName());

        }else{
            selectStep = savedInstanceState.getParcelable(STEP_EXTRA);
            if(frag != null){
                if(frag.getStepListAdapter() != null){
                    frag.getStepListAdapter().notifyDataSetChanged();
                }
            }

        }
        if(twoPane && selectStep !=null){
            DetailStepFragment frage = DetailStepFragment.factory(selectStep);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_frame, frage)
                    .commit();
        }
    }

    @Override
    public void onClick(Step step) {
        selectStep = step;
        if(twoPane){
            DetailStepFragment frag = DetailStepFragment.factory(selectStep);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.details_frame, frag)
                    .commit();
        }else{
            Intent details= new Intent(StepsActivity.this, DetailStepActivity.class);
            details.putExtra(STEP_EXTRA, step);
            startActivity(details);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_EXTRA, selectStep);
    }
}
