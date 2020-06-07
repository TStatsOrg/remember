//
//  iOSDeviceUtils.swift
//  ios_dependencies
//
//  Created by Gabriel Coman on 07/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import UIKit
import RememberShared

public class iOSDeviceUtils: DeviceUtils {
    
    public func isLandscape() -> Bool {
        return false
    }
    
    public func isTablet() -> Bool {
        return UIDevice.current.userInterfaceIdiom == .pad
    }
}
