package app.com.bakingapp.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import app.com.bakingapp.R;
import app.com.bakingapp.widget.RecipeAppWidget;
import app.com.bakingapp.widget.WidgetFactory;
import app.com.bakingapp.widget.WidgetService;

/**
 * Created by Serge Pessokho on 17/11/2018.
 */

public class PreferenceUtils {

    public static void setLastRecipe(Context context, int recipeId, String name){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putInt(context.getString(R.string.recipe_id_pref), recipeId);
        edit.putString(context.getString(R.string.recipe_pref), name);

        edit.commit();

        ComponentName appWidget = new ComponentName(context,RecipeAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
        views.setTextViewText(R.id.appwidget_text, name);

        Intent i = new Intent(context, WidgetService.class);
        i.putExtra(WidgetFactory.ID_EXTRA, recipeId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.list, i);

        appWidgetManager.updateAppWidget(appWidget, views);

    }

    public static int getLastRecipeId(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(context.getString(R.string.recipe_id_pref), 1);
    }

    public static String getLastRecipeName(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.recipe_pref), "Nutella Pie");
    }
}
