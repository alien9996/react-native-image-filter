using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Image.Filter.RNImageFilter
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNImageFilterModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNImageFilterModule"/>.
        /// </summary>
        internal RNImageFilterModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNImageFilter";
            }
        }
    }
}
