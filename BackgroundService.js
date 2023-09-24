import { NativeModules } from 'react-native';

const { BackgroundService } = NativeModules;

export default class BackgroundServiceModule {
  static startService() {
    BackgroundService.startService();
  }

  static stopService() {
    BackgroundService.stopService();
  }

  static sendEvent(eventName, params) {
    BackgroundService.sendEvent(eventName, params);
  }
}