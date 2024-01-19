// package com.nativeap; // replace your-app-name with your app’s name
// import com.facebook.react.ReactPackage;
// // import com.nativeap.MyNativeModule;
// import com.facebook.react.bridge.NativeModule;
// import com.facebook.react.bridge.ReactApplicationContext;
// import com.facebook.react.uimanager.ViewManager;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;

// public class MyAppPackage implements ReactPackage {
    
   

//    @Override
//    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
//        return Collections.emptyList();
//    }

//    @Override
//    public List<NativeModule> createNativeModules(
//            ReactApplicationContext reactContext) {
//        List<NativeModule> modules = new ArrayList<>();

//        modules.add(new MyAppPackage(reactContext));

//        return modules;
//    }

// }
package com.nativeap; // replace with your app’s package name
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAppPackage implements ReactPackage {

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new MyNativeModule(reactContext));

        return modules;
    }

}