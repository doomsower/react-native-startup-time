import React from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';
import { getTimeSinceStartup } from 'react-native-startup-time';

const styles = StyleSheet.create({
  container: {
    ...StyleSheet.absoluteFillObject,
    alignItems: 'center',
    justifyContent: 'center',
  },
  label: {
    marginTop: 16,
  },
});

const App = () => {
  const [label, setLabel] = React.useState('');
  const refreshLabel = React.useCallback(() => {
    getTimeSinceStartup().then((time) => {
      console.log(`Time since startup: ${time} ms`);
      setLabel(`Time since startup: ${time} ms`);
    });
  }, []);
  React.useEffect(() => {
    refreshLabel();
  }, [refreshLabel]);
  return (
    <View style={styles.container}>
      <Button title="Refresh" onPress={refreshLabel} />
      <Text style={styles.label}>{label}</Text>
    </View>
  );
};

App.displayName = 'App';

export default App;
