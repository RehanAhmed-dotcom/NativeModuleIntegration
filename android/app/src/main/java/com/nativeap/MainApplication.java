package com.nativeap;
import com.nativeap.BackGroundService;
// import com.nativeap.MyNativeModule;
// import com.nativeap.MyNativeModule;
import android.app.Application;
import com.facebook.react.PackageList;
import android.content.Intent;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.ReactPackage;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactNativeHost;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.soloader.SoLoader;
import java.util.List;
import android.widget.Toast;
public class MainApplication extends Application implements ReactApplication {
  // private ReactNativeHost mReactNativeHost;
  private final ReactNativeHost mReactNativeHost =
      new DefaultReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
          // packages.add(new MyReactNativePackage());
          // packages.add(new MyAppPackage());
          // packages.add(new BackGroundServiceM());
          
          packages.add(new MyAppPackage());
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }
        @Override
        public ReactInstanceManager getReactInstanceManager() {
            return super.getReactInstanceManager();
        }
        @Override
        protected boolean isNewArchEnabled() {
          return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
        }

        @Override
        protected Boolean isHermesEnabled() {
          return BuildConfig.IS_HERMES_ENABLED;
        }
    
      };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }
//       public static ReactContext getReactContext() {
//     return mReactNativeHost.getReactInstanceManager().getCurrentReactContext();
// }
  @Override
  public void onCreate() {
    super.onCreate();
    //code from chatgpt for updated value
    // reactContext = new ReactContext(this); 
    SoLoader.init(this, /* native exopackage */ false);
    registerService();
    if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
      // If you opted-in for the New Architecture, we load the native entry point for this app.
      DefaultNewArchitectureEntryPoint.load();
    }
    ReactNativeFlipper.initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
  }
  private void registerService(){
    Intent serviceIntent = new Intent(this, BackGroundService.class);
                        serviceIntent.putExtra("sensorValue", 0f);
                        serviceIntent.putExtra("sec", 0f);
                        serviceIntent.putExtra("time", "00:00");
    startForegroundService(serviceIntent);
   }
}
