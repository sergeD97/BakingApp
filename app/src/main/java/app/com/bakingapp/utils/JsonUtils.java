package app.com.bakingapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.bakingapp.model.Ingredient;
import app.com.bakingapp.model.Recipe;
import app.com.bakingapp.model.Step;

/**
 * Created by Serge Pessokho on 01/11/2018.
 */

public class JsonUtils {
    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INGRES = "ingredients";
    public static final String RECIPE_STEPS = "steps";
    public static final String RECIPE_SERVING = "servings";
    public static final String RECIPE_IMG = "image";

    public static final String STEP_ID = "id";
    public static final String STEP_SHORT_DESC = "shortDescription";
    public static final String STEP_DESC = "description";
    public static final String STEP_VIDEO = "videoURL";
    public static final String STEP_THUMB = "thumbnailURL";

    public static final String INGRE_QTY = "quantity";
    public static final String INGRE_MEASURE = "measure";
    public static final String INGRE_INGRE = "ingredient";


    public static Recipe buildRecipe(JSONObject jso) throws JSONException{
        Recipe obj = new Recipe();

        obj.setId(jso.getInt(RECIPE_ID));
        obj.setName(jso.getString(RECIPE_NAME));
        obj.setIngredients(buildIngredientList(jso.getJSONArray(RECIPE_INGRES)));
        obj.setSteps(buildStepList(jso.getJSONArray(RECIPE_STEPS)));
        obj.setServings(jso.getInt(RECIPE_SERVING));
        obj.setImage(jso.getString(RECIPE_IMG));

        return obj;
    }

    public static Step buildStep(JSONObject jso) throws JSONException{
        Step obj = new Step();
        obj.setId(jso.getInt(STEP_ID));
        obj.setShortDescription(jso.getString(STEP_SHORT_DESC));
        obj.setDescription(jso.getString(STEP_DESC));
        obj.setVideoURL(jso.getString(STEP_VIDEO));
        obj.setThumbnailURL(jso.getString(STEP_THUMB));

        return obj;
    }

    public static Ingredient buildIngredient(JSONObject jso) throws JSONException{
        Ingredient obj = new Ingredient();
        obj.setIngredient(jso.getString(INGRE_INGRE));
        obj.setMeasure(jso.getString(INGRE_MEASURE));
        obj.setQuantity(jso.getInt(INGRE_QTY));

        return obj;
    }

    public static List<Recipe> buildRecipeList(JSONArray jsa) throws JSONException{
        List<Recipe> list = new ArrayList<>();
        int l = jsa.length();
        for(int i = 0; i < l; i++){
            list.add(buildRecipe(jsa.getJSONObject(i)));
        }
        return list;
    }

    public static List<Step> buildStepList(JSONArray jsa) throws JSONException{
        List<Step> list = new ArrayList<>();
        int l = jsa.length();
        for(int i = 0; i < l; i++){
            list.add(buildStep(jsa.getJSONObject(i)));
        }
        return list;
    }

    public static List<Ingredient> buildIngredientList(JSONArray jsa) throws JSONException{
        List<Ingredient> list = new ArrayList<>();
        int l = jsa.length();
        for(int i = 0; i < l; i++){
            list.add(buildIngredient(jsa.getJSONObject(i)));
        }
        return list;
    }

}
