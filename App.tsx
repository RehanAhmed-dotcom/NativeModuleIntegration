// import React, {useState, useEffect} from 'react';
// import {
//   View,
//   NativeModules,
//   PermissionsAndroid,
//   NativeEventEmitter,
//   Alert,
//   DeviceEventEmitter,
//   Button,
//   Text,
// } from 'react-native';
// // import {add} from './BackgroundService';
// import {check, PERMISSIONS, request, RESULTS} from 'react-native-permissions';
// const App = () => {
//   // const back = NativeModules.BackGroundService;
//   const [counter, setCounter] = useState(0);
//   const {MyNativeModule} = NativeModules;
//   console.log('native variables', MyNativeModule);
//   const eventEmitter = new NativeEventEmitter(MyNativeModule);

//   useEffect(() => {
//     // console.log('Adding event listeners');

//     // const timeSubscription = eventEmitter.addListener('onTimeUpdate', time => {
//     //   console.log('Updated time:', time);
//     //   // Update your component state or perform any other actions
//     // });

//     const totalStepsSubscription = DeviceEventEmitter.addListener(
//       'onTotalStepsUpdate',
//       totalSteps => {
//         console.log('Received total steps update:', totalSteps);
//         // Do something with the updated totalSteps value
//       },
//     );

//     console.log(
//       'is Time',
//       MyNativeModule.getTime(time => {
//         console.log('Time from native module:', time);
//       }),
//       // MyNativeModule.getTime(res => {
//       //   console.log('res of callback of time', res);
//       // }),
//     );

//     // Clean up the subscriptions when the component is unmounted
//     return () => {
//       console.log('Removing event listeners');
//       // timeSubscription.remove();
//       totalStepsSubscription.remove();
//     };
//   }, []);

//   const startService = () => {
//     // back.onStartCommand();
//   };
//   useEffect(() => {
//     // Subscribe to the event
//     const eventListener = DeviceEventEmitter.addListener(
//       'onTimeUpdate',
//       newTime => {
//         console.log('Received updated time:', newTime);
//         // Handle the updated time value here
//         // setUpdatedTime(newTime);
//       },
//     );

//     // Cleanup the event listener when the component is unmounted
//     return () => {
//       eventListener.remove();
//     };
//   }, []);
//   useEffect(() => {
//     requestCameraPermission();
//   }, []);

//   // console.log(NativeModules.BackGroundService.getNativeVariable());
//   // const add = async () => {
//   //   let addition = 0;
//   //   [1, 2, 3, 4, 5, 6, 7, 8].map(element => (addition = addition + element));
//   //   return addition;
//   // };
//   const requestCameraPermission = async () => {
//     checkAndroidPermission();
//     // console.log('first', await add());
//     // console.log('my first object', await add());
//   };
//   const checkAndroidPermission = async () => {
//     try {
//       const permission = PermissionsAndroid.PERMISSIONS.ACTIVITY_RECOGNITION;
//       await PermissionsAndroid.request(permission);
//       Promise.resolve();
//     } catch (error) {
//       Promise.reject(error);
//     }
//   };

//   return (
//     <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
//       <Text>{counter}</Text>
//       <Button title="Start Service" onPress={startService} />
//       {/* <Button title="Stop Service" onPress={stopService} /> */}
//     </View>
//   );
// };
// export default App;
import React, {useState, useEffect} from 'react';
import {
  View,
  NativeModules,
  PermissionsAndroid,
  NativeEventEmitter,
  DeviceEventEmitter,
  Button,
  Text,
} from 'react-native';
import {check, PERMISSIONS, request} from 'react-native-permissions';

const App = () => {
  const [counter, setCounter] = useState(0);
  const {MyNativeModule} = NativeModules;
  const eventEmitter = new NativeEventEmitter(MyNativeModule);

  eventEmitter.addListener('onUpdateValue', event => {
    console.log('updated value in eventEmitter', event);
  });
  useEffect(() => {
    const totalStepsSubscription = DeviceEventEmitter.addListener(
      'onTotalStepsUpdate',
      updatedTotalSteps => {
        console.log('Received updated total steps:', updatedTotalSteps);
        // setTotalSteps(updatedTotalSteps);
      },
    );

    // Clean up the event listener when the component unmounts
    return () => {
      totalStepsSubscription.remove();
    };
  }, []);

  useEffect(() => {
    // Fetch initial total steps when the component mounts
    MyNativeModule.getTotalSteps(initialTotalSteps => {
      console.log('Initial total steps:', initialTotalSteps);
      // setTotalSteps(initialTotalSteps);
    });
  }, []);
  useEffect(() => {
    const totalStepsSubscription = DeviceEventEmitter.addListener(
      'onTotalStepsUpdate',
      totalSteps => {
        console.log('Received total steps update:', totalSteps);
        // Do something with the updated totalSteps value
      },
    );

    console.log(
      MyNativeModule.getTime(time => {
        console.log('Time from native module:', time);
      }),
    );

    return () => {
      // console.log('Removing event listeners');
      totalStepsSubscription.remove();
    };
  }, []);

  useEffect(() => {
    console.log(
      MyNativeModule.getTotalSteps(steps => {
        console.log('get total steps inside', steps);
      }),
    );
  }, [MyNativeModule]);

  useEffect(() => {
    requestCameraPermission();
  }, []);

  const requestCameraPermission = async () => {
    try {
      await checkAndroidPermission();
    } catch (error) {
      console.error('Error requesting permission:', error);
    }
  };

  const checkAndroidPermission = async () => {
    try {
      const permission = PermissionsAndroid.PERMISSIONS.ACTIVITY_RECOGNITION;
      await PermissionsAndroid.request(permission);
      // console.log('Permission granted successfully');
    } catch (error) {
      // console.error('Error requesting permission:', error);
      throw error;
    }
  };

  // console.log('native variables', MyNativeModule);

  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Text>{counter}</Text>
      {/* <Button title="Start Service" onPress={startService} /> */}
    </View>
  );
};

export default App;
