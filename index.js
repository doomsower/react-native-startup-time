import { NativeModules } from 'react-native';

const { RNStartupTime } = NativeModules;

export const getTimeSinceStartup = () => RNStartupTime.getTimeSinceStartup();
