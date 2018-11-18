package app.com.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Permission;
import java.security.Permissions;
import java.util.List;

import app.com.bakingapp.MainActivity;
import app.com.bakingapp.R;
import app.com.bakingapp.StepsActivity;
import app.com.bakingapp.model.Recipe;
import app.com.bakingapp.utils.JsonUtils;
import app.com.bakingapp.utils.PreferenceUtils;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = setView(context, appWidgetId);
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

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static String getRecipe(Context context){
        return PreferenceUtils.getLastRecipeName(context);
    }


    public static RemoteViews setView( Context context, int id){
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
        Intent i = new Intent(context, WidgetService.class);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        i.putExtra(WidgetFactory.ID_EXTRA, PreferenceUtils.getLastRecipeId(context));
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.list, i);
        views.setTextViewText(R.id.appwidget_text, getRecipe(context));

        return views;
    }
}

