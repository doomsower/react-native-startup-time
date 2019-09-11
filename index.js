import React from 'react';
import { NativeModules, StyleSheet, Text, View } from 'react-native';

const { RNStartupTime } = NativeModules;

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    backgroundColor: '#FFF',
    top: 8,
    right: 8,
    padding: 8,
  },
});

export const getTimeSinceStartup = () => RNStartupTime.getTimeSinceStartup();

export const StartupTime = ({ style, ready = true }) => {
  const [time, setTime] = React.useState('');
  const [layoutComplete, setLayoutComplete] = React.useState('');
  const onLayout = React.useCallback(() => setLayoutComplete(true), [
    setLayoutComplete,
  ]);
  React.useEffect(() => {
    if (ready && layoutComplete) {
      getTimeSinceStartup().then((t) => setTime(t));
    }
  }, [ready, layoutComplete, setTime]);
  return (
    <View style={[styles.container, style]} onLayout={onLayout}>
      <Text>{`startup time: ${time} ms`}</Text>
    </View>
  );
};
