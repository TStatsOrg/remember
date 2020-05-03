//
//  PreviewViewState.swift
//  ios_feature_preview
//
//  Created by Gabriel Coman on 03/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

struct PreviewViewState: Identifiable {
    
    private let state: BookmarkState
    
    init(state: BookmarkState) {
        self.state = state
    }
    
    var id: Int32 {
        return state.id
    }
    
    var viewState: PreviewViewStateType? {
        switch state {
        case let text as BookmarkStateText:
            return PreviewTextViewState(state: text)
        case let image as BookmarkStateImage:
            return PreviewImageViewState(state: image)
        case let link as BookmarkStateLink:
            return PreviewLinkViewState(state: link)
        default:
            return nil
        }
    }
}

protocol PreviewViewStateType {
    var state: BookmarkState { get }
    var source: String { get }
}

extension PreviewViewStateType {
    
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
}

struct PreviewTextViewState: PreviewViewStateType {
    internal let state: BookmarkState
    
    let text: String
    
    let source: String = "Clipped text"
    
    init(state: BookmarkStateText) {
        self.state = state
        text = state.text
    }
}

struct PreviewImageViewState: PreviewViewStateType {
    internal let state: BookmarkState
    
    let url: URL?
    
    let source: String = "Image"
    
    init(state: BookmarkStateImage) {
        self.state = state
        url = URL(string: state.url)
    }
}

struct PreviewLinkViewState: PreviewViewStateType {
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
