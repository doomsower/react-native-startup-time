# react-native-startup-time

This module helps you to measure your app launch time. It is measured from the earliest point in time available to the native module, that is the module's initialization. When `getTimeSinceStartup` is called on JS side, you'll get a promise which resolves with difference between these two moments in ms. This is not very accurate, but should give you good enough base for further optimizations.

On iOS time measurement is based on [this article](https://medium.com/@michael.eisel/measuring-your-ios-apps-pre-main-time-in-the-wild-98197f3d95b4). On Android [SystemClock.uptimeMills](https://developer.android.com/reference/android/os/SystemClock.html) is used.

As far as I know, there's no way to programmatically obtain time passed since the moment when user taps on app icon. For this you have to use native dev tools. On Android this module will call `reportFullyDrawn` so you can inspect adb logs.

If you know a better way to measure startup time (in a module), let me know or better shoot a PR.

## Getting started

`$ yarn add react-native-startup-time`

### Mostly automatic installation

This module supports autolinking so if you use RN 0.60+ then no additional action is required.

Otherwise, run

`$ react-native link react-native-startup-time`

### Manual linking installation

1. Open `./android/settings.gradle`, add this:

```gradle
include ':react-native-startup-time'
project(':react-native-startup-time').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-startup-time/android')
```

2. Open `./android/app/build.gradle`, add this:

```gradle
implementation project(':react-native-startup-time')
```

3. In `MainApplication.java`, add this:

```java
// Import
import com.github.doomsower.RNStartupTimePackage;

// Define package
new RNStartupTimePackage()
```

## Usage

Render startup time badge somewhere on your first screen:

```jsx
import { StartupTime } from 'react-native-startup-time';
...

<StartupTime
    ready={true /* optional, defaults to true */}
    style={styles.startupTime /* optional*/}
/>

```

Or use imperative call:

```jsx
import { getTimeSinceStartup } from 'react-native-startup-time';

// when you app is ready:
getTimeSinceStartup().then((time) => {
  console.log(`Time since startup: ${time} ms`);
});
```
