//
//  ManagedSearchView.swift
//  ios_views
//
//  Created by Gabriel Coman on 05/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI
import RememberShared

public struct ManagedSearchView: UIViewRepresentable {
    
    @Binding var searchText: String
    
    private let openSearchEmitter = ObservableEmitter()
    private let searchChangedEmitter = ObservableEmitter()
    private let closeSearchEmitter = ObservableEmitter()
    private let cancelSearchEmitter = ObservableEmitter()
    
    public init(binding: Binding<String>) {
        _searchText = binding
    }
    
    public class Delegate: NSObject, UISearchBarDelegate {
        
        private let openSearchEmitter: ObservableEmitter
        private let searchChangedEmitter: ObservableEmitter
        private let closeSearchEmitter: ObservableEmitter
        private let cancelSearchEmitter: ObservableEmitter
        
        init(openSearchEmitter: ObservableEmitter,
             searchChangedEmitter: ObservableEmitter,
             closeSearchEmitter: ObservableEmitter,
             cancelSearchEmitter: ObservableEmitter) {
            self.openSearchEmitter = openSearchEmitter
            self.searchChangedEmitter = searchChangedEmitter
            self.closeSearchEmitter = closeSearchEmitter
            self.cancelSearchEmitter = cancelSearchEmitter
        }
        
        public func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
            cancelSearchEmitter.emit(value: nil)
        }
        
        public func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
            
        }
        
        public func searchBarShouldBeginEditing(_ searchBar: UISearchBar) -> Bool {
            openSearchEmitter.emit(value: nil)
            return true
        }
        
        public func searchBarShouldEndEditing(_ searchBar: UISearchBar) -> Bool {
            closeSearchEmitter.emit(value: nil)
            return true
        }
        
        public func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
//            self.searchText = searchText
            if !searchText.isEmpty {
                searchChangedEmitter.emit(value: searchText)
            }
        }
    }
    
    public func makeCoordinator() -> Delegate {
        return Delegate(openSearchEmitter: openSearchEmitter,
                        searchChangedEmitter: searchChangedEmitter,
                        closeSearchEmitter: closeSearchEmitter,
                        cancelSearchEmitter: cancelSearchEmitter)
    }
    
    public func makeUIView(context: UIViewRepresentableContext<ManagedSearchView>) -> UISearchBar {
        let searchBar = UISearchBar(frame: .zero)
        searchBar.searchBarStyle = .minimal
        searchBar.delegate = context.coordinator
        searchBar.showsCancelButton = true
//        searchBar.placeholder = "in: News"
        return searchBar
    }
    
    public func updateUIView(_ uiView: UISearchBar, context: UIViewRepresentableContext<ManagedSearchView>) {
//        uiView.delegate = nil
//        uiView.text = searchText
//        uiView.delegate = context.coordinator
        uiView.placeholder = searchText
    }
    
    public func updateText(text: String) {
        
    }
}

extension ManagedSearchView {
    
    @discardableResult
    public func observeSearchOpened(callback: @escaping () -> Void) -> ManagedSearchView {
        openSearchEmitter.observer().collect { _ in
            callback()
        }

        return self
    }
    
    @discardableResult
    public func observeSearchChanged(callback: @escaping (String) -> Void) -> ManagedSearchView {
        searchChangedEmitter.observer().collect { searchTerm in
            
            if let term = searchTerm as? String {
                callback(term)
            }
        }
        
        return self
    }
    
    @discardableResult
    public func observeSearchClosed(callback: @escaping () -> Void) -> ManagedSearchView {
        closeSearchEmitter.observer().collect { _ in
            callback()
        }
        
        return self
    }
    
    @discardableResult
    public func observeCancelSearch(callback: @escaping () -> Void) -> ManagedSearchView {
        cancelSearchEmitter.observer().collect { _ in
            callback()
        }
        
        return self
    }
}
