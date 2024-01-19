package com.nativeap;
import com.nativeap.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import com.facebook.react.bridge.ReactContext;
public class BackGroundService extends Service implements SensorEventListener {
   // code from chat gpt for updated value
    private ReactContext reactContext;


    boolean running = true;
    float nowSteps = 0f;
    boolean run = false;

    private int seconds = 0;
    String time="00:00";
    SensorManager sensorManager;
    float totalSteps = 0f;
    float previousTotalSteps = 0f;
    float checkSteps = 0f;
    int currentSteps;
    int notiSetps;
    float distance, calories;

    // @ReactMethod
    // public void startForegroundService(String title, String message) {
    //     Intent serviceIntent = new Intent(reactContext, YourForegroundService.class);
    //     serviceIntent.putExtra("title", title);
    //     serviceIntent.putExtra("message", message);

    //     if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
    //         String CHANNEL_ID = "your_channel_id";
    //         NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_HIGH);
    //         NotificationManager notificationManager = (NotificationManager) reactContext.getSystemService(Context.NOTIFICATION_SERVICE);
    //         notificationManager.createNotificationChannel(channel);

    //         Notification notification = new NotificationCompat.Builder(reactContext, CHANNEL_ID)
    //             .setContentTitle(title)
    //             .setContentText(message)
    //             .setSmallIcon(R.drawable.ic_notification)
    //             .build();

    //         serviceIntent.putExtra("notification", notification);
    //     }

    //     reactContext.startForegroundService(serviceIntent);
    // }
     // Add a method to check the 'running' status
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        running = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepSensor == null) {
            Toast.makeText(this, "No Sensor Detected on this Device", Toast.LENGTH_SHORT).show();
            Log.d("here", "Program is here: no detendion");
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
            
            Log.d("here", "Program is here: detection");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("steps", Context.MODE_PRIVATE);
        running = sharedPreferences.getBoolean("running", true);

        checkSteps = intent.getFloatExtra("sensorValue", 0f);
        seconds = intent.getIntExtra("sec",0);
        time = intent.getStringExtra("time");
        if (checkSteps == 0) {
            float current;
            try{
                String value = intent.getStringExtra("current");
                current = Float.parseFloat(value);
                  Log.e("****steps: in try", "Program is here: " + current  );
            }catch(Exception e){
                e.printStackTrace();
                current = 1f;
               
                  Log.e("****steps: in exeption", "Program is here: ");
            }
           //this line is crashing app
            previousTotalSteps = current;
            Log.e("****steps:", "Program is here: " + previousTotalSteps + "1");
//  Toast.makeText(this, "Toast in checksteps", Toast.LENGTH_SHORT).show();
        } else {
            previousTotalSteps = checkSteps;
            run = false;            
        }
   Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                running = sharedPreferences.getBoolean("running", true);
                 
        if (running){
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;
                    time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                    // Toast.makeText(this, "code in running", Toast.LENGTH_SHORT).show();
                    if (running) {
                        seconds++;
                        saveTime();
                        showNotification();
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
         
                h.postDelayed(this, 1000);
            }
        });



        showNotification();




        return super.onStartCommand(intent, flags, startId);
        // return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running) {

            totalSteps = sensorEvent.values[0];
            Log.e("***Service","total steps "+totalSteps);
            SharedPreferences pref = getSharedPreferences("steps", Context.MODE_PRIVATE);
            pref.edit().putFloat("sensorValue", sensorEvent.values[0]).apply();
            if (checkSteps == 0) {
                Log.e("total steps:", "Program is here: " + totalSteps + "2");
                previousTotalSteps++;
                float st = previousTotalSteps + totalSteps;
                Log.e("total steps:", "Program is here: " + st + "3");
                totalSteps = st - sensorEvent.values[0];
                totalSteps++;
                Log.e("total steps:", "Program is here with updated value: " + totalSteps );
            } else {
                if (run) {
                    nowSteps++;
                    float st = nowSteps + totalSteps;
                    Log.e("total steps:", "Program is here: " + st + "3");
                    totalSteps = st - sensorEvent.values[0];
                    totalSteps++;
                    Log.e("total steps:", "Program is here in run code: " + totalSteps + "4");
                } else {
                    totalSteps = totalSteps - previousTotalSteps;
                    totalSteps++;
                    nowSteps = totalSteps;
                    run = true;
                    Log.e("***Service","total steps in else part"+totalSteps);
                }
            }
            // sendEvent("onTotalStepsUpdate",totalSteps);
            distance = (75 * totalSteps) / 10000;
            calories = (float) (0.04 * totalSteps);





            saveData();
            showNotification();
            // sendEvent("onTotalStepsUpdate",totalSteps);
        }
    }

// private void sendEvent(String eventName, float data) {
//     // Get the ReactContext from the ReactApplication class
//     ReactContext reactContext = MainApplication.getReactContext(); // replace YourApplicationClassName with your actual class name

//     // Check if the context is available and has the event listener
//     if (reactContext != null && reactContext.hasActiveCatalystInstance()) {
//         // Send the event to React Native
//         reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                 .emit(eventName, data);
//     }
// }
    // private void sendEvent(String eventName, Object data) {
    //     // DeviceEventManagerModule.RCTDeviceEventEmitter listener =
    //     //     getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);

    //     // eventListeners.put(eventName, listener);

    //     // listener.emit(eventName, data);
    //     // MyNativeModule.sendEvent(getReactApplicationContext(), eventName, data);
    // }
    void saveTime(){
        SharedPreferences sharedPreferences = getSharedPreferences("steps", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("time", seconds).apply();
        sharedPreferences.edit().putString("totalTime", time).apply();
        try {
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("onTimeUpdate", time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        //             .emit("onTimeUpdate", time);
    }

    void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("steps", Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat("key1", totalSteps).apply();
        sharedPreferences.edit().putFloat("distance", distance).apply();
        sharedPreferences.edit().putFloat("calories", calories).apply();
        sharedPreferences.edit().putInt("time", seconds).apply();
        sharedPreferences.edit().putString("totalTime", time).apply();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    // void saveTime() {
    //     SharedPreferences sharedPreferences = getSharedPreferences("steps", Context.MODE_PRIVATE);
    //     sharedPreferences.edit().putInt("time", seconds).apply();
    //     sharedPreferences.edit().putString("totalTime", time).apply();

    //     // Emit an event with the updated time value
    //     reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
    //             .emit("onTimeUpdate", time);
    // }
    void showNotification() {


        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.noti_layout);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_layout);
        notificationLayoutExpanded.setTextViewText(R.id.steps_notification, totalSteps + "");
        notificationLayout.setTextViewText(R.id.steps_small, totalSteps + "");
        notificationLayoutExpanded.setTextViewText(R.id.calories_large, new DecimalFormat("#.###").format(calories) + "");
        notificationLayout.setTextViewText(R.id.calories_small, new DecimalFormat("#.###").format(calories) + "");
        notificationLayout.setTextViewText(R.id.distance_small, new DecimalFormat("#.###").format(distance) + "");
        notificationLayoutExpanded.setTextViewText(R.id.distance_large, new DecimalFormat("#.###").format(distance) + "");

            notificationLayoutExpanded.setTextViewText(R.id.time_large,time);
            notificationLayout.setTextViewText(R.id.time_small,time);


        final String CHANNELID = "Foreground Service ID";
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_LOW
            );
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                    .setSmallIcon(R.drawable.ic_play)
                    .setCustomContentView(notificationLayout)
                    .setCustomBigContentView(notificationLayoutExpanded)
                    .setSmallIcon(R.drawable.ic_launcher_background);
            startForeground(1001, notification.build());
        }
    }
public boolean isRunning() {
    return running;
}

// Add a method to get the 'totalSteps'
public float getTotalSteps() {
    return totalSteps;
}
public String getTime(){
    return time;
}
}