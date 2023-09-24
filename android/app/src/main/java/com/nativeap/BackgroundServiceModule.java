package com.nativeap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class BackgroundServiceModule extends ReactContextBaseJavaModule {

    public BackgroundServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "BackgroundService";
    }

    @ReactMethod
    public void startService() {
        // Start your background service logic here
        // For this example, we will log a message every 5 seconds
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        String message = "Background service is running";
                        System.out.println(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    @ReactMethod
    public void stopService() {
        // Stop your background service logic here
    }

    @ReactMethod
    public void sendEvent(String eventName, String params) {
        // Send events from the background service to the JavaScript side if needed
    }
}
