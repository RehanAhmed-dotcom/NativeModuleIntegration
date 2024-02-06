// package com.nativeap;
// import com.facebook.react.bridge.ReactApplicationContext;
// import com.facebook.react.bridge.ReactContextBaseJavaModule;
// import com.facebook.react.bridge.ReactMethod;
// import com.facebook.react.bridge.Callback;
// import com.nativeap.BackGroundService;
// public class MyNativeModule extends ReactContextBaseJavaModule {
//     private final BackGroundService backGroundService;
//     public MyNativeModule(ReactApplicationContext reactContext) {
//         super(reactContext);
//         backGroundService = new BackGroundService();
//     }

//     @Override
//     public String getName() {
//         return "MyNativeModule";
//     }

//     @ReactMethod
//     public void startService() {
//         // Start your service here
//     }

//     @ReactMethod
//     public void stopService() {
//         // Stop your service here
//     }
//     @ReactMethod
//     public boolean isRunning() {
//         return backGroundService.isRunning();
//     }

//     @ReactMethod
//     public float getTotalSteps() {
//         return backGroundService.getTotalSteps();
//     }
//     @ReactMethod
//     public void getDataFromService(Callback callback) {
//         // Retrieve data from your service and pass it to the callback
//         // String data = BackGroundService.getData(); // Replace with your service logic
//         // callback.invoke(data);
//     }
//     @ReactMethod(isBlockingSynchronousMethod = true)
// public Double added(Double a, Double b){
//     Double sum = a + b;
//     return sum;
// }
// }


//Second example


// package com.nativeap;
// import android.content.ComponentName;
// import android.content.ServiceConnection;
// import android.os.IBinder;
// import com.facebook.react.bridge.Arguments;
// import com.facebook.react.bridge.WritableMap;
// import android.media.MediaPlayer;
// import android.media.PlaybackParams;
// import android.os.Build;
// import com.facebook.react.bridge.ReactApplicationContext;
// import com.facebook.react.bridge.ReactContextBaseJavaModule;
// import com.facebook.react.bridge.ReactMethod;
// import com.facebook.react.modules.core.DeviceEventManagerModule;
// public class MyNativeModule extends ReactContextBaseJavaModule {
//     private final ReactApplicationContext reactContext;
//     // private BackGroundService backgroundService;
//     private BackGroundService backgroundService;
//     private boolean isServiceBound = false;
//     public MyNativeModule(ReactApplicationContext reactContext) {
//         super(reactContext);
//         this.reactContext = reactContext;
//         // this.backgroundService = new BackGroundService();
//     }

//     @Override
//     public String getName() {
//         return "MyNativeModule";
//     }
//     // @ReactMethod
//     // public void bindToService() {
//     //     if (!isServiceBound) {
//     //         backgroundService = new BackGroundService();
//     //         bindService(backgroundService, serviceConnection, Context.BIND_AUTO_CREATE);
//     //         isServiceBound = true;
//     //     }
//     // }
//     // @ReactMethod
//     // public void unbindFromService() {
//     //     if (isServiceBound) {
//     //         unbindService(serviceConnection);
//     //         isServiceBound = false;
//     //     }
//     // }
//     @ReactMethod
//     public void getRunningStatus() {
//         if (isServiceBound) {
//             boolean runningStatus = backgroundService.isRunning(); // Replace with your logic
//             // Emit the running status to your React Native code
//         }
//     }
//     @ReactMethod
//     public void getTotalSteps() {
//         if (isServiceBound) {
//             float totalSteps = backgroundService.getTotalSteps(); // Replace with your logic
//             // Emit the totalSteps to your React Native code
//         }
//     }
//     // private ServiceConnection serviceConnection = new ServiceConnection() {
//     //     @Override
//     //     public void onServiceConnected(ComponentName className, IBinder service) {
//     //         BackGroundService.LocalBinder binder = (BackGroundService.LocalBinder) service;
//     //         backgroundService = binder.getService();
//     //     }

//     //     @Override
//     //     public void onServiceDisconnected(ComponentName arg0) {
//     //         backgroundService = null;
//     //     }
//     // };
//     public class BackGroundService extends Service implements SensorEventListener {

//     // ...

//     public class LocalBinder extends Binder {
//         BackGroundService getService() {
//             return BackGroundService.this;
//         }
//     }

//     @Nullable
//     @Override
//     public IBinder onBind(Intent intent) {
//         return new LocalBinder();
//     }

//     // ...
//     // @ReactMethod
//     // public String getNativeVariable() {
//     //     return "This is a native variable from Java!";
//     // }
//     // @ReactMethod
//     // public String getNativeVariables() {
//     //     return "This is my second native variable from Java!";
//     // }
// //     @ReactMethod
// // public void addition(Double a, Double b, Promise promise) {
// //     Double sum = a + b;
// //     promise.resolve(sum);
// // }
// // @ReactMethod
// // public void add(Double a, Double b, Callback cb) {            
// //         Double sum = a + b;
// //         cb.invoke(sum);
// // } 
// // @ReactMethod(isBlockingSynchronousMethod = true)
// // public Double added(Double a, Double b){
// //     Double sum = a + b;
// //     return sum;
// // }
// // @ReactMethod
// //  public void getRunningStatus() {
// //         // Implement the logic to get the 'running' status and send it to your React Native code.
// //         boolean runningStatus = BackGroundService.running; // Replace with your logic
// //         WritableMap params = Arguments.createMap();
// //         params.putBoolean("running", runningStatus);
// //         reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
// //             .emit("runningStatus", params);
// //     }

//     // @ReactMethod
//     // public void getTotalSteps() {
//     //     // Implement the logic to get the 'totalSteps' and send it to your React Native code.
//     //     float totalSteps = BackGroundService.totalSteps; // Replace with your logic
//     //     WritableMap params = Arguments.createMap();
//     //     params.putDouble("totalSteps", totalSteps);
//     //     reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//     //         .emit("totalSteps", params);
//     // }
// }

// }
package com.nativeap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Callback;
import androidx.annotation.Nullable;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
public class MyNativeModule extends ReactContextBaseJavaModule {

    private final BackGroundService backGroundService;
    private DeviceEventManagerModule.RCTDeviceEventEmitter timeUpdateListener;
    private final Map<String, DeviceEventManagerModule.RCTDeviceEventEmitter> eventListeners = new HashMap<>();
    public MyNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        // Create an instance of BackGroundService
        backGroundService = new BackGroundService();
    }

    @Override
    public String getName() {
        return "MyNativeModule";
    }
//  @ReactMethod
//     public void getTotalSteps(Callback callback) {
//         // Call the instance method on the created BackGroundService instance
//         float totalSteps = backGroundService.getTotalSteps();
//         callback.invoke(totalSteps);
//     }
@ReactMethod
public void getTotalSteps(Callback callback) {
    float totalSteps = backGroundService.getTotalSteps();
    // Toast.makeText(this, totalSteps, Toast.LENGTH_SHORT).show();
    // Send the totalSteps to the React Native side
    callback.invoke(totalSteps);
}
    @ReactMethod
    public void isRunning(Callback callback) {
        // Call the instance method on the created BackGroundService instance
        float result = backGroundService.getTotalSteps();
        callback.invoke(result);
    }
    @ReactMethod
    public void getTime(Callback callback) {
        // Call the getTime method in BackGroundService and pass the result to the callback
        BackGroundService backGroundService = new BackGroundService();
        String time = backGroundService.getTime();
        callback.invoke(time);
    }
    public void removeListener(String eventName) {
        DeviceEventManagerModule.RCTDeviceEventEmitter listener = eventListeners.get(eventName);
        if (listener != null) {
            // listener.removeAllListeners(); // This removes all listeners for the emitter
            eventListeners.remove(eventName);
        }
    }
    private void sendEvent(String eventName, Object data) {
        DeviceEventManagerModule.RCTDeviceEventEmitter listener =
            eventListeners.get(eventName);

        if (listener != null) {
            listener.emit(eventName, data);
        }
    }
    // @ReactMethod
    // public void isTime(Callback callback) {
    //     // Call the instance method on the created BackGroundService instance
    //     float result = backGroundService.getTime();
    //     callback.invoke(result);
    // }
    //  @ReactMethod
    // public void isTime() {
    //     // Call the instance method on the created BackGroundService instance
    //     String time = backGroundService.getTime();
    //     // callback.invoke(result);
    //     sendEvent("onTimeUpdate", time);
    // }
    // public void removeListeners() {
    //     // Unregister all listeners
    //     for (DeviceEventManagerModule.RCTDeviceEventEmitter listener : eventListeners.values()) {
    //         listener.removeListener("onTimeUpdate");  // Use the specific event name
    //     }
    //     // Clear the map
    //     eventListeners.clear();
    // }

    // public void removeListener(String eventName) {
    //     DeviceEventManagerModule.RCTDeviceEventEmitter listener = eventListeners.get(eventName);
    //     if (listener != null) {
    //         listener.removeAllListeners(eventName);
    //         eventListeners.remove(eventName);
    //     }
    // }
   
    // private void sendEvent(String eventName, Object data) {
    //     DeviceEventManagerModule.RCTDeviceEventEmitter listener =
    //         getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);

    //     eventListeners.put(eventName, listener);

    //     listener.emit(eventName, data);
    // }
    // private void sendEvent(String eventName, Object data) {
    //     getReactApplicationContext()
    //         .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
    //         .emit(eventName, data);
    // }
    // private void sendEvent(String eventName, @Nullable String data) {
    //     DeviceEventManagerModule.RCTDeviceEventEmitter listener =
    //             getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);

    //     // Keep track of the listener in the map
    //     eventListeners.put(eventName, listener);

    //     listener.emit(eventName, data);
    // }
    // public void removeListeners() {
    //     // Unregister the specific listener
    //     if (timeUpdateListener != null) {
    //         timeUpdateListener.removeAllListeners();
    //     }
    // }
    // private void sendEvent(String eventName, @Nullable String data) {
    //     getReactApplicationContext()
    //         .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
    //         .emit(eventName, data);
    // }
    // private void sendEvent(String eventName, @Nullable String data) {
    //     timeUpdateListener = getReactApplicationContext()
    //         .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    //     timeUpdateListener.emit(eventName, data);
    // }
    
   
}



