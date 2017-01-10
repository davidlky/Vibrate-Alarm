package com.sevenhourdev.vibratealarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
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
import android.widget.Toast;

import com.sevenhourdev.vibratealarm.Models.Alarm;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddAlarm.class);
                startActivity(intent);
            }
        });

        DataReader dr = new DataReader(this);
        ArrayList<Alarm> alarms = dr.findAllFromDatabase();
        if(alarms.size()>0){
        }else{
            Alarm alarm = new Alarm();
            alarm.name = "No Alarms yet!";
            alarm.description  = "Please add an alarm";
            alarms.add(alarm);
        }
        RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.listview));
        recyclerView.setAdapter(new CustomAdapter(this, alarms));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    public void addAlarm(View view) {
        Intent intent = new Intent(MainActivity.this,AddAlarm.class);
        startActivity(intent);
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout background;
            TextView name;
            TextView time;
            CardView wrapper;

            public ViewHolder(View view){
                super(view);
                this.name = (TextView) view.findViewById(R.id.name);
                this.background = (LinearLayout) view.findViewById(R.id.background);
                this.time = (TextView) view.findViewById(R.id.times);
                this.wrapper = (CardView) view.findViewById(R.id.list_item);

            }
        }

        private ArrayList<Alarm> alarms;
        private Context c;

        CustomAdapter(Context c, ArrayList<Alarm> alarms) {
            this.alarms = alarms;
            this.c = c;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.listitem_alarmlist,
                            parent,
                            false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.time.setText(alarms.get(position).time);
            holder.name.setText(alarms.get(position).name);
            holder.background.setBackgroundColor(getResources().getColor(R.color.highlight));

            holder.wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Ordinary Intent for launching a new activity
                    Intent intent = new Intent(c, AlarmActivity.class);

                    // Get the transition name from the string
                    String transitionName = "alarm";

                    // Define the view that the animation will start from
                    View viewStart = findViewById(R.id.list_item);

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) c, viewStart, transitionName);
                    //Start the Intent
                    ActivityCompat.startActivity((Activity) c, intent, options.toBundle());
                }
            });
        }

        @Override
        public int getItemCount() {
            return alarms.size();
        }
    }
}
