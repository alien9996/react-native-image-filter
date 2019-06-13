
# react-native-image-filter

**Various image filters** for **iOS** and **Android**

## Demo

![gif](https://github.com/alien9996/ReactNativeImageFilter/blob/master/filter.gif?raw=true)

## Getting started

`$ npm install react-native-image-filter --save`

### Mostly automatic installation

`$ react-native link react-native-image-filter`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-image-filter` and add `RNImageFilter.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNImageFilter.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNImageFilterPackage;` to the imports at the top of the file
  - Add `new RNImageFilterPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-image-filter'
  	project(':react-native-image-filter').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-image-filter/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-image-filter')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNImageFilter.sln` in `node_modules/react-native-image-filter/windows/RNImageFilter.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Image.Filter.RNImageFilter;` to the usings at the top of the file
  - Add `new RNImageFilterPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Example
```javascript
import RNImageFilter from 'react-native-image-filter';
 
RNImageFilter.getSourceImage({
          imageSource: "/storage/emulated/0/Download/img2-0.jpg",
          dataType: "Path",
          filterType: 1
        }, (source) => {
                    this.setState( imgBase64 : source.base64);
                    console.log("SOURCE", source);
                    // source returns the height, width and the Base64 string of the image.
        });
```

## Options

Props | Default | Options/Info
------ | --- | ------
imageSource (String)|null|The path to the image in the device or a Base64 string.
dataType (String)|Path|If you send a path, enter the string "Path"<br>If you send a Base64 string, enter the string "Base64".
filterType (int)|0|Select the type you want to filter images, the values from 0 to 21. Other values around 0 to 21 will not take effect.<br> **Note**: Valid only when dataType = "Path".

## Filter types

![filterType](https://github.com/alien9996/ReactNativeImageFilter/blob/master/filter_type.png?raw=true)

## Note
- The image path you send into ***imageSource:''** must be the absolute path. If you have problems with the absolute path, you can find the solution [here](https://stackoverflow.com/questions/52423067/how-to-get-absolute-path-of-a-file-in-react-native).

### Thank you for your interest!
