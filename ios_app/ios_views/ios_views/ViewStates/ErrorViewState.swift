//
//  ErrorViewState.swift
//  ios_views
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared
import SwiftUI

public struct ErrorViewState {
    
    private let error: KotlinThrowable?
    
    public init(error: KotlinThrowable? = nil) {
        self.error = error
    }
    
    public var isVisible: Bool {
        return error != nil
    }
    
    public var message: LocalizedStringKey {
        switch error {
        case let network where network is Errors.Network:
            return Translations.Errors.Network
        case let rss where rss is Errors.InvalidRSSFormat:
            return Translations.Errors.InvalidRSSFormat
        case let db where db is Errors.Database:
            return Translations.Errors.Database
        case let url where url is Errors.InvalidURL:
            return Translations.Errors.InvalidURL
        default:
            return Translations.Errors.Other
        }
    }
}
