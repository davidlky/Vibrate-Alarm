package com.sevenhourdev.vibratealarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sevenhourdev.vibratealarm.Models.Alarm;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<Alarm> alarms = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Alarm alarm = new Alarm();
            alarm.date = new Date(2012, 12, 19);
            alarm.name = "Whoot";
            alarm.time = new Time(12, 12, 00);
            alarms.add(alarm);
        }

        ((ListView) findViewById(R.id.listview)).setAdapter(new CustomAdapter(this, alarms));
    }

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

    class CustomAdapter extends BaseAdapter {

        private class ViewHolder {
            LinearLayout background;
            TextView name;
            TextView time;
        }

        private ArrayList<Alarm> alarms;
        private Context c;

        public CustomAdapter(Context c, ArrayList<Alarm> alarms) {
            this.alarms = alarms;
            this.c = c;
        }

        @Override
        public int getCount() {
            return alarms.size();
        }

        @Override
        public Object getItem(int position) {
            return alarms.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            LayoutInflater mInflater = (LayoutInflater)
                    c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listitem_alarmlist, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.background = (LinearLayout) convertView.findViewById(R.id.background);
                holder.time = (TextView) convertView.findViewById(R.id.times);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.time.setText(alarms.get(position).time.getHours() + ":" + alarms.get(position).time.getMinutes() + ":" + alarms.get(position).time.getSeconds());
            holder.name.setText(alarms.get(position).name);
            holder.background.setBackgroundColor(getResources().getColor(R.color.highlight));

            return convertView;
        }
    }


    public void animateIntent(View view) {

        // Ordinary Intent for launching a new activity
        Intent intent = new Intent(this, AlarmActivity.class);

        // Get the transition name from the string
        String transitionName = "alarm";

        // Define the view that the animation will start from
        View viewStart = findViewById(R.id.list_item);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, viewStart, transitionName);
        //Start the Intent
        ActivityCompat.startActivity(this, intent, options.toBundle());

    }
}
