package app.com.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.com.bakingapp.R;
import app.com.bakingapp.model.Ingredient;
import app.com.bakingapp.model.Recipe;
import app.com.bakingapp.utils.JsonUtils;
import app.com.bakingapp.utils.NetWorkUtils;
import app.com.bakingapp.utils.PreferenceUtils;

/**
 * Created by Serge Pessokho on 10/11/2018.
 */

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private List<Ingredient> list = new ArrayList<>();
    private int id;
    public static final String ID_EXTRA = "recipe.id.extra";

    public WidgetFactory(Context context, Intent intent) {
        this.context = context;
        this.id = intent.getIntExtra(ID_EXTRA, 1);
    }

    @Override
    public void onCreate() {

        load();
    }

    @Override
    public void onDataSetChanged() {
        load();
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(list == null){
            return 0;
        }else{
            return list.size();
        }

    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingre_item);
        Ingredient ingre = list.get(position);
        views.setTextViewText(R.id.ingre_tv, ingre.getIngredient());
       //Toast.makeText(context, "viewat", Toast.LENGTH_SHORT).show();
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        //Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show();
        return new RemoteViews(context.getPackageName(), R.layout.load_list);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void load(){
        try{
            /*InputStream inputStream = context.getResources().openRawResource(R.raw.baking);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String l, ligne= "";


            while ((l = bufferedReader.readLine()) != null) {
                ligne += l;
            }*/
            String ligne = NetWorkUtils.getResponseFromHttpUrl(NetWorkUtils.buildUrl());
            List<Recipe> liste = JsonUtils.buildRecipeList(new JSONArray(ligne));
            this.list = new ArrayList<>(liste.get(this.id-1).getIngredients());
            //Toast.makeText(context, "changed", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            // Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
