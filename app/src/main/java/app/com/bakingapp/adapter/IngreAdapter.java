package app.com.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import app.com.bakingapp.R;
import app.com.bakingapp.model.Ingredient;

/**
 * Created by Serge Pessokho on 10/11/2018.
 */

public class IngreAdapter extends RecyclerView.Adapter<IngreAdapter.IViewHolder> {

    private List<Ingredient> list;

    public List<Ingredient> getList() {
        return list;
    }

    public void setList(List<Ingredient> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingre_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new IViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class IViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public IViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.ingre_tv);
        }

        public void bind(Ingredient ingre){
            tv.setText(ingre.getIngredient());
        }
    }
}
