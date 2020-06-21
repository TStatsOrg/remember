//
//  Dateutils.swift
//  ios_views
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation

public struct DateUtils {
    
    static func format(fromLong date: Int64) -> String {
        let formatter = DateFormatter()
        let iosTimestamp = date / 1000
        formatter.dateStyle = .medium
        formatter.timeStyle = .none
        formatter.locale = Locale(identifier: "en_US")
        let date = Date(timeIntervalSince1970: TimeInterval(iosTimestamp))
        return formatter.string(from: date)
    }
}
