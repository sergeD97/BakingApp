package app.com.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

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

/**
 * Created by Serge Pessokho on 10/11/2018.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        return new WidgetFactory(getApplicationContext(), intent);
        //return null;

    }


}
