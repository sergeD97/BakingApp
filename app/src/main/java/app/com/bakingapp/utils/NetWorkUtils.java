package app.com.bakingapp.utils;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Serge Pessokho on 10/11/2018.
 */

public class NetWorkUtils {

    private static final String MOVIEDB_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    public static URL buildUrl() {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        BufferedReader reader ;


        HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
        try {
            reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
            String l, ligne= "";

            while ((l = reader.readLine()) != null) {
                ligne += l;
            }

            return ligne;

        }finally {
            connexion.disconnect();
        }
    }
}
