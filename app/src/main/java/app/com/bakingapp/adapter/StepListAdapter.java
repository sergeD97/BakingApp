package app.com.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.bakingapp.R;
import app.com.bakingapp.model.Step;

/**
 * Created by Serge Pessokho on 31/10/2018.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private List<Step> list;
    private StepClickListener stepClickListener;
    int selectId;


    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
        notifyDataSetChanged();
    }

    public StepListAdapter(){
        this.list = new ArrayList<>();
        selectId=-1;
    }

    public List<Step> getList() {
        return list;
    }

    public void setList(List<Step> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public StepClickListener getStepClickListener() {
        return stepClickListener;
    }

    public void setStepClickListener(StepClickListener stepClickListener) {
        this.stepClickListener = stepClickListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(list.get(position));
    }


    @Override
    public int getItemCount() {
       return list.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView description;
        View itemV;

        public StepViewHolder(View itemView) {
            super(itemView);
            description = (TextView)itemView.findViewById(R.id.description_tv);
            itemV = itemView.findViewById(R.id.item);

            itemV.setOnClickListener(this);

        }

        public void bind(Step step){
            description.setText(step.getShortDescription());
            itemV.setSelected(step.isSelect());
        }

        @Override
        public void onClick(View v) {
            if(stepClickListener != null){
                stepClickListener.onClick(list.get(getAdapterPosition()));
            }
                list.get(getAdapterPosition()).setSelect(true);
                for(Step s : list){
                    if(s.isSelect() && s.getId() != list.get(getAdapterPosition()).getId() ){
                        s.setSelect(false);
                        break;
                    }
                }
                notifyDataSetChanged();
        }
    }

    public interface StepClickListener{
        public void onClick(Step step);
    }
}
