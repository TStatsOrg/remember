//
//  BookmarkViewState.swift
//  ios_views
//
//  Created by Gabriel Coman on 05/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import RememberShared

public struct BookmarkViewState: Identifiable {
    
    private let state: BookmarkState
    
    public init(state: BookmarkState) {
        self.state = state
    }
    
    public var id: Int32 {
        return state.id
    }
    
    public var viewState: BookmarkViewStateType? {
        switch state {
        case let text as BookmarkStateText:
            return BookmarkTextViewState(state: text)
        case let image as BookmarkStateImage:
            return BookmarkImageViewState(state: image)
        case let link as BookmarkStateLink:
            return BookmarkLinkViewState(state: link)
        case let rssFeed as BookmarkStateFeed:
            return BookmarkFeedViewState(state: rssFeed)
        default:
            return nil
        }
    }
}

public protocol BookmarkViewStateType {
    var state: BookmarkState { get }
    var source: String { get }
}

public extension BookmarkViewStateType {
    
    var id: Int32 {
        return state.id
    }
    
    var date: String {
        return DateUtils.format(fromLong: state.date)
    }
    
    var topic: String {
        return state.topic?.name.uppercased() ?? ""
    }
    
    var info: String {
        return "\(source) - \(date)"
    }
}

public struct BookmarkTextViewState: BookmarkViewStateType {
    public let state: BookmarkState
    
    public let text: String
    public let source: String = "Clipped text"
    
    public init(state: BookmarkStateText) {
        self.state = state
        text = state.text
    }
}

public struct BookmarkImageViewState: BookmarkViewStateType {
    public let state: BookmarkState
    
    public let url: URL?
    public let source: String = "Image"
    
    public init(state: BookmarkStateImage) {
        self.state = state
        url = URL(string: state.url)
    }
}

public struct BookmarkLinkViewState: BookmarkViewStateType {
    public let state: BookmarkState
    
    public let title: String
    public let caption: String?
    public let icon: URL?
    public let source: String
    public let isIconHidden: Bool
    public let destinationUrl: URL?
    
    public init(state: BookmarkStateLink) {
        self.state = state
        
        title = state.title ?? "N/A"
        caption = state.caption
        
        if let url = URL(string: state.url) {
            source = url.host ?? "N/A"
            destinationUrl = url
        } else {
            source = "N/A"
            destinationUrl = nil
        }
        
        if let iconUrl = state.icon, iconUrl != "" {
            icon = URL(string: iconUrl)
            isIconHidden = false
        } else {
            icon = nil
            isIconHidden = true
        }
    }
}

public struct BookmarkFeedViewState: BookmarkViewStateType {
    public var state: BookmarkState
    public var source: String
    
    public let title: String
    public let caption: String?
    
    public let icon: URL?
    public let isIconHidden: Bool
    public let destinationUrl: URL?
    
    public let isSubscribed: Bool
    
    public init(state: BookmarkStateFeed) {
        self.state = state
        
        title = state.title ?? "N/A"
        caption = state.caption
        source = state.url
        isSubscribed = state.isFavourite
        
        if let url = URL(string: state.url) {
            destinationUrl = url
        } else {
            destinationUrl = nil
        }
        
        if let iconUrl = state.icon, iconUrl != "" {
            icon = URL(string: iconUrl)
            isIconHidden = false
        } else {
            icon = nil
            isIconHidden = true
        }
    }
}
