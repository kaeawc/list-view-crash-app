package io.kaeawc.listviewcrashapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    List<String> mStrings;
    ListView mList;
    Button mSyncButton;
    Button mAsyncButton;
    Button mCrashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStrings = new ArrayList<>();

        mList = (ListView) findViewById(R.id.list);

        if (mList == null) {
            Log.w(TAG, "mList is null");
        }

        mList.setAdapter(new ArrayAdapter<>
                (getBaseContext(),
                        R.layout.list_item,
                        mStrings));

        mSyncButton = (Button) findViewById(R.id.sync_button);
        mSyncButton.setOnClickListener(syncClicked);

        mAsyncButton = (Button) findViewById(R.id.async_button);
        mAsyncButton.setOnClickListener(asyncClicked);

        mCrashButton = (Button) findViewById(R.id.crash_button);
        mCrashButton.setOnClickListener(crashClicked);

        // Enable filtering when the user types in the virtual keyboard
        mList.setTextFilterEnabled(true);

        // Set an setOnItemClickListener on the ListView
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Display a Toast message indicting the selected item
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener syncClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) mList.getAdapter();
            mStrings.clear();
            adapter.notifyDataSetChanged();

            for (String color : Colors.get(getResources())) {
                mStrings.add(color);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private View.OnClickListener asyncClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SafeBackground task = new SafeBackground();
            task.execute();
        }
    };

    private View.OnClickListener crashClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UnsafeBackground task = new UnsafeBackground();
            task.execute();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SafeBackground extends AsyncTask<Void, String, String> {

        ListView mListReference;
        List<String> mStringsReference;

        @Override
        protected void onPreExecute() {
            mListReference = (ListView) findViewById(R.id.list);
            mStringsReference = new ArrayList<>();

            for (String color : mStrings) {
                mStringsReference.add(color);
            }

            mListReference.setAdapter(new ArrayAdapter<>
                    (getBaseContext(),
                            R.layout.list_item,
                            mStringsReference));

            mStringsReference.clear();
        }


        @Override
        protected String doInBackground(Void... voids) {
            List<String> colors = Colors.get(getResources());

            for (String color : colors) {
                onProgressUpdate(color);
            }

            Log.i(TAG, "Finished pushing list items");
            return "";
        }

        @Override
        protected void onProgressUpdate(String... colors) {

            Log.i(TAG, String.format("Updating list with %s", colors));
            Collections.addAll(mStringsReference, colors);
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) mListReference.getAdapter();
            adapter.notifyDataSetChanged();
        }
    }

    private class UnsafeBackground extends AsyncTask<Void, String, String> {

        ListView mListReference;
        List<String> mStringsReference;

        @Override
        protected void onPreExecute() {
            mListReference = (ListView) findViewById(R.id.list);
            mStringsReference = new ArrayList<>();

            for (String color : mStrings) {
                mStringsReference.add(color);
            }

            mListReference.setAdapter(new StringAdapter
                    (getBaseContext(),
                            R.layout.list_item,
                            mStringsReference));

            mStringsReference.clear();
        }


        @Override
        protected String doInBackground(Void... voids) {
            List<String> colors = Colors.get(getResources());

            for (String color : colors) {
                onProgressUpdate(color);
            }

            Log.i(TAG, "Finished pushing list items");
            return "";
        }

        @Override
        protected void onProgressUpdate(String... colors) {

            Log.i(TAG, String.format("Updating list with %s", colors));
            Collections.addAll(mStringsReference, colors);
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) mListReference.getAdapter();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}
