package app.com.bakingapp;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;

import app.com.bakingapp.fragment.DetailStepFragment;
import app.com.bakingapp.model.Step;

public class DetailStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        getSupportActionBar().hide();


        Step step = getIntent().getParcelableExtra(StepsActivity.STEP_EXTRA);
        setTitle(step.getShortDescription());


        if(savedInstanceState == null){

            DetailStepFragment frag = DetailStepFragment.factory(step);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.details_frame, frag)
                    .commit();
        }



    }


}
