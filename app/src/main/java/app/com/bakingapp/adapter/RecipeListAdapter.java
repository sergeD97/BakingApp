package app.com.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.com.bakingapp.R;
import app.com.bakingapp.model.Recipe;

/**
 * Created by Serge Pessokho on 31/10/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>  {

    private List<Recipe> list;
    private ClickListener recipeClickListener;

    public RecipeListAdapter() {
        this.list = new ArrayList<>();
    }

    public List<Recipe> getList() {
        return list;
    }

    public void setList(List<Recipe> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public ClickListener getRecipeClickListener() {
        return recipeClickListener;
    }

    public void setRecipeClickListener(ClickListener recipeClickListener) {
        this.recipeClickListener = recipeClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new RecipeViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        ImageView image;
        View itemV;
        Context context;

        public RecipeViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.name_tv);
            image = (ImageView) itemView.findViewById(R.id.recipe_iv);
            itemV = itemView.findViewById(R.id.item);
            itemV.setOnClickListener(this);

        }

        public void bind(Recipe recipe){
            name.setText(recipe.getName());
            if(!recipe.getImage().equals("")){
                Picasso.with(context)
                        .load(recipe.getImage())
                        .centerCrop()
                        .fit()
                        .error(R.drawable.def)
                        .placeholder(R.drawable.def)
                        .into(image);
            }else {
                    Picasso.with(context)
                            .load(R.drawable.def)
                            .centerCrop()
                            .fit()
                            .into(image);

            }
        }

        @Override
        public void onClick(View v) {
            if(recipeClickListener!= null){
                recipeClickListener.onClick(list.get(getAdapterPosition()));
            }

        }
    }

    public interface ClickListener{
        public void onClick(Recipe recipe);
    }
}
