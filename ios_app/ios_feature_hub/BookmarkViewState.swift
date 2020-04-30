//
//  BookmarkState.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 18/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

struct BookmarkViewState: Identifiable {
    
    private let state: BookmarkState
    
    init(state: BookmarkState) {
        self.state = state
    }
    
    var id: Int32 {
        return state.id
    }
    
    var viewState: BookmarkViewStateType? {
        switch state {
        case let text as BookmarkStateText:
            return BookmarkTextViewState(state: text)
        case let image as BookmarkStateImage:
            return BookmarkImageViewState(state: image)
        case let link as BookmarkStateLink:
            return BookmarkLinkViewState(state: link)
        default:
            return nil
        }
    }
}

protocol BookmarkViewStateType {
    var state: BookmarkState { get }
    var source: String { get }
}

extension BookmarkViewStateType {
    
    var id: Int32 {
        return state.id
    }
    
    var date: String {
        let formatter = DateFormatter()
        let iosTimestamp = state.date / 1000
        formatter.dateFormat = "MM/dd/yyyy"
        let date = Date(timeIntervalSince1970: TimeInterval(iosTimestamp))
        return formatter.string(from: date)
    }
    
    var topic: String {
        return state.topic?.name ?? ""
    }
}

struct BookmarkTextViewState: BookmarkViewStateType {
    internal let state: BookmarkState
    
    let text: String
    
    let source: String = "Clipped text"
    
    init(state: BookmarkStateText) {
        self.state = state
        text = state.text
    }
}

struct BookmarkImageViewState: BookmarkViewStateType {
    internal let state: BookmarkState
    
    let url: URL?
    
    let source: String = "Image"
    
    init(state: BookmarkStateImage) {
        self.state = state
        url = URL(string: state.url)
    }
}

struct BookmarkLinkViewState: BookmarkViewStateType {
    internal let state: BookmarkState
    
    let title: String?
    let caption: String?
    let icon: URL?
    let source: String
    
    init(state: BookmarkStateLink) {
        self.state = state
        
        title = state.title
        caption = state.caption
        
        if let iconUrl = state.icon {
            icon = URL(string: iconUrl)
            source = icon?.host ?? "N/A"
        } else {
            icon = nil
            source = "N/A"
        }
    }
}
