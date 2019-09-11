import * as React from 'react';
import { StyleProp, ViewStyle } from 'react-native';

export declare const getTimeSinceStartup: () => Promise<number>;
export declare const StartupTime: React.FC<{
  style?: StyleProp<ViewStyle>;
  ready?: boolean;
}>;
