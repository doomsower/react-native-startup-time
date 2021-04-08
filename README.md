# react-native-startup-time

This module helps you to measure your app launch time. It is measured from the earliest point in time available to the native module, that is the module's initialization. When `getTimeSinceStartup` is called on JS side, you'll get a promise which resolves with difference between these two moments in ms. This is not very accurate, but should give you good enough base for further optimizations.

On iOS time measurement is based on [this article](https://medium.com/@michael.eisel/measuring-your-ios-apps-pre-main-time-in-the-wild-98197f3d95b4). On Android [SystemClock.uptimeMills](https://developer.android.com/reference/android/os/SystemClock.html) is used.

As far as I know, there's no way to programmatically obtain time passed since the moment when user taps on app icon. For this you have to use native dev tools. On Android this module will call `reportFullyDrawn` so you can inspect adb logs.

If you know a better way to measure startup time (in a module), let me know or better shoot a PR.

## Getting started

`$ yarn add react-native-startup-time`

### Mostly automatic installation

This module supports autolinking so **if you use RN 0.60+ then no additional action is required**.

Otherwise, run

`$ react-native link react-native-startup-time`

### Manual linking installation

<details>
    <summary>Show manual installation steps</summary>
    
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
</details>


## Usage

Render startup time badge somewhere on your first screen:

```jsx
import { StartupTime } from 'react-native-startup-time';

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

_The following sections are applicable to Android only_

### Single-Sampling

This makes sure Android doesn't resolve the getTimeSinceStartup promise more than once per app execution. More information in [PR #10](https://github.com/doomsower/react-native-startup-time/pull/10).

Since v1.4.0 this strategy is enabled by default, if you're migrating from a previous version and you just want things to keep working as they are, follow the steps below.

#### Disabling Single-Sampling:

Be aware, depending on which lifecycle hook you've attached your call to `getTimeSinceStartup()` you might receive redundant invocations, e.g. when the app is brought from bg to fg. Because the app isn't really starting up, the measured time can be unrealistic; such unrealistic samples adulterate your data.

To disable single-sampling strategy, create your package using constructor with parameter `false`:
```java
// Define package
new RNStartupTimePackage(false)
```
