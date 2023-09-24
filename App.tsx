import React, { Component } from 'react';
import { View, Button } from 'react-native';
import BackgroundService from './BackgroundService'; // Import the background service module

const App= ()=> {
 const startService=()=> {
    BackgroundService.startService();
  }

 const stopService=()=> {
    BackgroundService.stopService();
  }

  
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Button title="Start Service" onPress={startService} />
        <Button title="Stop Service" onPress={stopService} />
      </View>
    );
  }
export default App;