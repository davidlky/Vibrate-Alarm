package com.sevenhourdev.vibratealarm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevenhourdev.vibratealarm.Models.Alarm;
import com.sevenhourdev.vibratealarm.helper.AlarmDataProvider;
import com.sevenhourdev.vibratealarm.service.AlarmBackgroundService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlarmDataProvider.AlarmDataCallback {

    private AlarmBackgroundService mBoundService;
    private AlarmDataProvider mAlertProvider;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((AlarmBackgroundService.AlarmBinder)service).getService(getApplicationContext());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddAlarmActivity.class);
                startActivity(intent);
            }
        });

        bindService(new Intent(this,
                AlarmBackgroundService.class), mConnection, Context.BIND_AUTO_CREATE);
        mAlertProvider= AlarmDataProvider.getInstance(this,this);
        mAlertProvider.getAllAlarms();
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
        Intent intent = new Intent(MainActivity.this,AddAlarmActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAlertProvider.addCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAlertProvider.removeCallback(this);
    }

    @Override
    public void newDataSet(ArrayList<Alarm> alarms) {
        if(alarms.size()>0){
        }else{
            Alarm alarm = new Alarm();
            alarm.name = "No Alarms yet!";
            alarms.add(alarm);
        }
        RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.listview));
        recyclerView.setAdapter(new CustomAdapter(this, alarms));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        private List<Alarm> alarms;
        private Context c;

        CustomAdapter(Context c, List<Alarm> alarms) {
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
            holder.time.setText(alarms.get(position).time.toString());
            holder.name.setText(alarms.get(position).name);
            holder.background.setBackgroundColor(getResources().getColor(R.color.highlight));

            holder.wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Ordinary Intent for launching a new activity
                    Intent intent = new Intent(c, AlarmActivity.class);
//
//                    // Get the transition name from the string
//                    String transitionName = "alarm";
//
//                    // Define the view that the animation will start from
//                    View viewStart = findViewById(R.id.list_item);
//
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation((Activity) c, viewStart, transitionName);
//                    //Start the Intent
//                    ActivityCompat.startActivity((Activity) c, intent, options.toBundle());

                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return alarms.size();
        }
    }
}
