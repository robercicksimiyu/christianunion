package com.example.robercick.christianunion;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by robercick on 5/11/15.
 */
public class CuFragment extends android.support.v4.app.Fragment {

    public CuFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String[] menuArray={
                "Events",
                "News",
                "Ministries",
                "Media",
                "Get Involved",
                "About"
        };

        List<String> cuMenu=new ArrayList<>(Arrays.asList(menuArray));

        ArrayAdapter<String> mCuAdapter = new ArrayAdapter<String>(
                //get the current context(This fragment parent activity)
                getActivity(),
                //id of the list item layout
                R.layout.list_item_cu,
                //Id of the text view to populate
                R.id.list_item_cu_textview,
                cuMenu);

        ListView listView=(ListView) rootView.findViewById(R.id.listview_cu);

        listView.setAdapter(mCuAdapter);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //This line is added for the fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {                ;
        inflater.inflate(R.menu.cu_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handleaction bar item clicks here. The action bar will
        // automatically handle clicks on the home/up button, so long as
        //you specify the parent activity in AndroidManifest.xml
       int id=item.getItemId();
       if(id==R.id.action_refresh){
           FetchCuData cuTask=new FetchCuData();
           cuTask.execute("1john3:16-18");
           return true;
       }
        return super.onOptionsItemSelected(item);
    }



    public class FetchCuData extends AsyncTask<String,Void,String[]>{
        private final String LOG_TAG=FetchCuData.class.getSimpleName();
        @Override
        protected String[] doInBackground(String... params) {
            //if there's no zip code, there's nothing to lookup. Verify size of params
            if(params.length==0){
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String format="verse";
            String biblePsg="1john1:1";

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String CU_BASE_URL="http://libbible.com/api/query.php?";
                final String QUERY_PARAM="type";
                final String QUERY_PASS="br";

                Uri builtUri = Uri.parse(CU_BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM,format).appendQueryParameter(QUERY_PASS,params[0]).build();
                //URL url = new URL("https://getbible.net/json?passage=1Jn3:16-18");
                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG,"Built in url"+builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr=buffer.toString();
                Log.v(LOG_TAG,"Here come a verse "+forecastJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                forecastJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            try{
                Log.v(LOG_TAG,getCuDataFromJson(forecastJsonStr).toString());
                //return getCuDataFromJson(forecastJsonStr);

                String[] jam={"asd","affdf"};
                return jam;
            }catch (Exception e){

            }
            return null;
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =
                getMenuInflater();
        inflater.inflate(R.menu.cu_menu, menu);
        return true;
    }*/



    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy: constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private JSONArray getCuDataFromJson(String forecastJsonStr)
            throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String VERSE_LIST = "list";
        final String BIBLE_VERSES = "verses";
        final String BIBLE_VERSE = "br";
        final String VERSE_TEXT = "text";
        final String VERSE_DESCRIPTION = "verses";
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        //JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(VERSE_LIST);
        String[] resultStrs = new String[];
        /*for(int i = 0; i < weatherArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            String day;
            String description;
            String highAndLow;
            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);
            // The date/time is returned as a long. We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".
            long dateTime = dayForecast.getLong(OWM_DATETIME);
            day = getReadableDateString(dateTime);
            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);
            // Temperatures are in a child object called "temp". Try not to name variables
            // "temp" when working with temperature. It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);
            highAndLow = formatHighLows(high, low);
            resultStrs[i] = day + " - " + description + " - " + highAndLow;
        }*/
        //return resultStrs;
        return weatherArray;
    }

}
