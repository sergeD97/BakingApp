package app.com.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import app.com.bakingapp.MainActivity;
import app.com.bakingapp.R;
import app.com.bakingapp.StepsActivity;
import app.com.bakingapp.model.Recipe;
import app.com.bakingapp.utils.JsonUtils;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {
   public static List<Recipe> list;
    public static int stepid = 1;
    private static final String ACTION_SWITC_RECIPE = "ACTION_WIDGETSWITCHRECIPE";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


        loadList(context);
        if(intent.getAction().equals(ACTION_SWITC_RECIPE)){
            if(stepid >= list.size()){
                stepid = 1;
            }else{
                ++stepid;
            }
        }
        RemoteViews views = setView(context);

        ComponentName appWidget = new ComponentName(context, RecipeAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidget, views);

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        loadList(context);
        RemoteViews views = setView(context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        loadList(context);
        RemoteViews views = setView(context);

        ComponentName appWidget = new ComponentName(context, RecipeAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidget, views);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static Recipe getRecipe(){
        if(list != null){
            return list.get(stepid-1);
        }
        return null;
    }

    public static void loadList(Context context){
        if(getRecipe()  == null){
            try{
                InputStream inputStream = context.getResources().openRawResource(R.raw.baking);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String l, ligne= "";

                while ((l = bufferedReader.readLine()) != null) {
                    ligne += l;
                }
                list = JsonUtils.buildRecipeList(new JSONArray(ligne));
            }catch (Exception e){

            }
        }
    }

    public static RemoteViews setView( Context context ){
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
        Intent switchs = new Intent(context, RecipeAppWidget.class);
        switchs.setAction(ACTION_SWITC_RECIPE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, switchs,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button, pendingIntent);

        if(getRecipe()!=null){
            views.setTextViewText(R.id.appwidget_text, getRecipe().getName());
            Intent detail = new Intent(context, StepsActivity.class);
            detail.putExtra(MainActivity.RECIPE_EXTRA, getRecipe());
            PendingIntent penDetail = PendingIntent.getActivity(context, 0, detail, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget_text, penDetail);
        }

        return views;
    }
}

