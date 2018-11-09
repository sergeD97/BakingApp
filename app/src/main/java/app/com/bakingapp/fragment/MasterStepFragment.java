package app.com.bakingapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import app.com.bakingapp.R;
import app.com.bakingapp.adapter.StepListAdapter;
import app.com.bakingapp.model.Ingredient;
import app.com.bakingapp.model.Recipe;
import app.com.bakingapp.model.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterStepFragment extends Fragment implements  StepListAdapter.StepClickListener{

    public static final String LIST_EXTRA = "list.step";
    public static final String SELECT_EXTRA = "select.extra";

    public static MasterStepFragment factory(Recipe recipe, int selectId){
        MasterStepFragment fragment = new MasterStepFragment();
        Bundle b = new Bundle();
        b.putParcelable(LIST_EXTRA, recipe);
        b.putInt(SELECT_EXTRA, selectId);
        fragment.setArguments(b);
        return fragment;

    }

    private StepListAdapter.StepClickListener stepClickListener;
    private List<Step> stepList;
    StepListAdapter stepListAdapter;
    Recipe recipe;

    public StepListAdapter getStepListAdapter() {
        return stepListAdapter;
    }

    public void setStepListAdapter(StepListAdapter stepListAdapter) {
        this.stepListAdapter = stepListAdapter;
    }

    public MasterStepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*if(savedInstanceState != null){
            stepList = savedInstanceState.getParcelableArrayList(LIST_EXTRA);

        }*/
        View rootView = inflater.inflate(R.layout.fragment_master_step, container, false);

            recipe = getArguments().getParcelable(LIST_EXTRA);
            stepList = recipe.getSteps();
        TextView servingTv, ingredientTv;
        servingTv = (TextView)rootView.findViewById(R.id.serving_tv);
        ingredientTv = (TextView)rootView.findViewById(R.id.ingredient_tv);

        servingTv.append(recipe.getServings()+"");

        for(Ingredient ingr : recipe.getIngredients()){
            ingredientTv.append(ingr.getIngredient() +" : "+ingr.getQuantity()+" "+ingr.getMeasure()+", ");
        }



            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.step_list);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            stepListAdapter = new StepListAdapter();
            stepListAdapter.setStepClickListener(this);
            stepListAdapter.setList(stepList);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(stepListAdapter);


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof StepListAdapter.StepClickListener){
            stepClickListener = (StepListAdapter.StepClickListener)context;
        }
    }


    @Override
    public void onClick(Step step) {
        if(stepClickListener != null){
            stepClickListener.onClick(step);
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
