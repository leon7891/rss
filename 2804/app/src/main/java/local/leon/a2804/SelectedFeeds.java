package local.leon.a2804;

import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectedFeeds extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private List<String> liste;
    private String login;
    private String passwd;
    private Bitmap[] parent_bitmaps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            login = extras.getString("login");
            passwd = extras.getString("passwd");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_feeds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EPI RSS AG");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewsListView();
            }
        });

        final Button switchButton = (Button) findViewById(R.id.switchButton);
        final Button addRemoveButton = (Button) findViewById(R.id.addRemoveButton);

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchButton.getText() == "Get enabled\nitems") {
                    switchButton.setText("Get disabled\nitems");
                    getActivatedListView();
                }
                else {
                    switchButton.setText("Get enabled\nitems");
                    getUnactivatedListView();
                }
            }
        });
    }

    private void getActivatedListView() {
        getEnabledRSSlist my_set_list_task = new getEnabledRSSlist(this);
        my_set_list_task.execute();
    }

    private void getUnactivatedListView() {
        getDisabledRSSlist my_set_list_task = new getDisabledRSSlist(this);
        my_set_list_task.execute();
    }

    private void removeItemNb(Button btn) {
        deleteRemoveRSS my_set_list_task = new deleteRemoveRSS(this, String.valueOf(btn.getId()));
        my_set_list_task.execute();
    }

    private void addItemNb(Button btn) {
        postAddRSS my_set_list_task = new postAddRSS(this, String.valueOf(btn.getId()));
        my_set_list_task.execute();
    }

    private void getNewsListView() {
        getNEWSlist my_set_list_task = new getNEWSlist(this);
        my_set_list_task.execute();
    }

    private void openNewInInternetBrowser(String url) {
        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(url) );
        startActivity(intent);
    }

    public void populate_pictures(Bitmap[] array)
    {
        parent_bitmaps = array;
    }

    public void populate(String list, int what_action) {
        final LinearLayout lm = (LinearLayout) findViewById(R.id.myLinearLayout);
        lm.removeAllViewsInLayout();
        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //final ListView lv = (ListView) findViewById(R.id.activatedRSSListView);
        ObjectMapper mapper = new ObjectMapper();
        ActivatedRSSModel[] test = null;
        NewFromNewsModel[] test2 = null;

        if (what_action < 2) {
            try {
                test = mapper.readValue(list, ActivatedRSSModel[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < test.length; i++)
            {
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                // une textbox
                TextView un_flux = new TextView(this);
                un_flux.setWidth(600);
                un_flux.setText(test[i].title + "\n" + test[i].url);
                ll.addView(un_flux);

                if (what_action == 0) {
                    // un bouton
                    final Button btn2 = new Button(this);
                    btn2.setWidth(100);
                    // Give button an ID
                    btn2.setId(test[i].id);
                    btn2.setText("-");
                    // set the layoutParams on the button
                    btn2.setLayoutParams(params);

                    // evenement click button
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            removeItemNb(btn2);
                            getActivatedListView();
                        }
                    });
                    //
                    ll.addView(btn2);
                }
                else if (what_action == 1)
                {
                    // un bouton
                    final Button btn2 = new Button(this);
                    btn2.setWidth(100);
                    // Give button an ID
                    btn2.setId(test[i].id);
                    btn2.setText("+");
                    // set the layoutParams on the button
                    btn2.setLayoutParams(params);

                    // evenement click button
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            addItemNb(btn2);
                            getUnactivatedListView();
                        }
                    });
                    //
                    ll.addView(btn2);
                }
                lm.addView(ll);
            }
        }
        else if (what_action == 2)
        {
            try {
                test2 = mapper.readValue(list, NewFromNewsModel[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < test2.length; i++) {
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);

                // une textbox
                TextView un_flux = new TextView(this);
                un_flux.setWidth(700);
                un_flux.setText(test2[i].title);
                ll.addView(un_flux);

                // une image
                ImageView img = new ImageView(this);
                img.setLayoutParams(params);
                img.setMaxWidth(300);
                img.setImageBitmap(parent_bitmaps[i]);
                ll.addView(img);

                // un bouton
                final Button btn2 = new Button(this);
                btn2.setWidth(700);
                // Give button an ID
                btn2.setId(test2[i].id);
                btn2.setText(test2[i].url);
                // set the layoutParams on the button
                btn2.setLayoutParams(params);

                // evenement click button
                btn2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        openNewInInternetBrowser(String.valueOf(btn2.getText()));
                    }
                });
                //
                ll.addView(btn2);
                //
                lm.addView(ll);
            }
        }
    }

    class getEnabledRSSlist extends AsyncTask<Void, Void, String> {

        private String responseStr = "";
        private SelectedFeeds parent;

        getEnabledRSSlist(SelectedFeeds received){
            parent = received;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            URL dataUrl = null;
            try {
                dataUrl = new URL("http://shendai.rip/rss/rest/activated");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) dataUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("login", login);
                conn.setRequestProperty("password", passwd);

                try {
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    responseStr = br.readLine();

                    conn.disconnect();
                } catch (Exception e) {

                    e.printStackTrace();
                } finally {

                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                return responseStr;
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseStr;
        }

        protected void onPostExecute(String result) {
            parent.populate(result, 0);
        }
    }

    class getDisabledRSSlist extends AsyncTask<Void, Void, String> {

        private String responseStr = "";
        private SelectedFeeds parent;

        getDisabledRSSlist(SelectedFeeds received){
            parent = received;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            URL dataUrl = null;
            try {
                dataUrl = new URL("http://shendai.rip/rss/rest/inactivated");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) dataUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("login", login);
                conn.setRequestProperty("password", passwd);

                try {
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    responseStr = br.readLine();

                    conn.disconnect();
                } catch (Exception e) {

                    e.printStackTrace();
                } finally {

                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                return responseStr;
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseStr;
        }

        protected void onPostExecute(String result) {
            parent.populate(result, 1);
        }
    }

    class postAddRSS extends AsyncTask<Void, Void, String> {

        private String responseStr = "";
        private SelectedFeeds parent;
        private String the_id = "";

        postAddRSS(SelectedFeeds received, String received_id){
            parent = received;
            the_id = received_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            URL dataUrl = null;
            try {
                dataUrl = new URL("http://shendai.rip/rss/rest/add/" + the_id);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) dataUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("login", login);
                conn.setRequestProperty("password", passwd);

                try {
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    responseStr = br.readLine();

                    conn.disconnect();
                } catch (Exception e) {

                    e.printStackTrace();
                } finally {

                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                return responseStr;
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseStr;
        }

        protected void onPostExecute(Void param) {
        }
    }

    class deleteRemoveRSS extends AsyncTask<Void, Void, String> {

        private String responseStr = "";
        private SelectedFeeds parent;
        private String the_id = "";

        deleteRemoveRSS(SelectedFeeds received, String received_id){
            parent = received;
            the_id = received_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            URL dataUrl = null;
            try {
                dataUrl = new URL("http://shendai.rip/rss/rest/remove/" + the_id);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) dataUrl.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("login", login);
                conn.setRequestProperty("password", passwd);

                try {
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    responseStr = br.readLine();

                    conn.disconnect();
                } catch (Exception e) {

                    e.printStackTrace();
                } finally {

                    if (connection != null) {
                        connection.disconnect();
                    }
                    return responseStr;
                }
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseStr;
        }

        protected void onPostExecute(Void param) {
        }
    }

    class getNEWSlist extends AsyncTask<Void, Void, String> {

        private String responseStr = "";
        private SelectedFeeds parent;
        private Bitmap[] child_bitmaps = null;

        getNEWSlist(SelectedFeeds received){
            parent = received;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            URL dataUrl = null;
            try {
                dataUrl = new URL("http://shendai.rip/rss/rest/news");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) dataUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("login", login);
                conn.setRequestProperty("password", passwd);

                try {
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    responseStr = br.readLine();

                    child_bitmaps = getBitmapsArray(responseStr);

                    conn.disconnect();
                } catch (Exception e) {

                    e.printStackTrace();
                } finally {

                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                return responseStr;
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseStr;
        }

        protected void onPostExecute(String result) {
            parent.populate_pictures(child_bitmaps);
            parent.populate(result, 2);
        }

        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return null;
            }
        }

        public Bitmap[] getBitmapsArray(String requete) {
            NewFromNewsModel[] test = null;
            ObjectMapper mapper = new ObjectMapper();

            try {
                test = mapper.readValue(requete, NewFromNewsModel[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap[] new_array = new Bitmap[test.length];
            for (int i = 0; i < new_array.length; i++)
            {
                new_array[i] = getBitmapFromURL(test[i].image);
            }

            return new_array;
        }
    }
}
