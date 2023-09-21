/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import type {PropsWithChildren} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  TouchableOpacity,
  Text,
  useColorScheme,
  View,
} from 'react-native';
import Example from './Example';

function App(): JSX.Element {
  return (
    <View>
      <View>
        <TouchableOpacity
          style={{
            width: '100%',
            marginTop: 100,
            backgroundColor: 'red',
            height: 50,
            alignItems: 'center',
            justifyContent: 'center',
          }}
          onPress={() => Example.startService()}>
          <Text>Start</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={{
            width: '100%',
            marginTop: 100,
            backgroundColor: 'red',
            height: 50,
            alignItems: 'center',
            justifyContent: 'center',
          }}
          onPress={() => Example.stopService()}>
          <Text>Stop</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

export default App;
