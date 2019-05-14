
import { NativeModules } from 'react-native';

const { RNImageFilter } = NativeModules;

const DEFAULT_OPTIONS = {
    imageSource: null,
    dataType: "Path",
    filterType: 0
}

module.exports = {
    ...RNImageFilter,
    getSourceImage: function getSourceImage(options, callback) {
        if (typeof options === 'function') {
            callback = options;
            options = {};
        }
        return RNImageFilter.getSourceImage({ ...DEFAULT_OPTIONS, ...options }, callback)
    }
}
