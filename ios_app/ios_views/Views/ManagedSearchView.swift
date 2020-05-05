//
//  ManagedSearchView.swift
//  ios_views
//
//  Created by Gabriel Coman on 05/05/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public struct ManagedSearchView: UIViewRepresentable {
    
    @Binding private var text: String
    private let delegate = Delegate()
    
    public init(text: Binding<String>) {
        self._text = text
    }
    
    public class Delegate: NSObject, UISearchBarDelegate {
        
        public func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
            print("GABBOX => Search button clicked")
        }
        
        public func searchBarShouldBeginEditing(_ searchBar: UISearchBar) -> Bool {
            print("GABBOX => Search bar up")
            return true
        }
        
        public func searchBarShouldEndEditing(_ searchBar: UISearchBar) -> Bool {
            print("GABBOX => Search bar down")
            return true
        }
    }
    
    public func makeCoordinator() -> Delegate {
        return Delegate()
    }
    
    public func makeUIView(context: UIViewRepresentableContext<ManagedSearchView>) -> UISearchBar {
        let searchBar = UISearchBar(frame: .zero)
        searchBar.searchBarStyle = .minimal
        searchBar.delegate = context.coordinator
        return searchBar
    }
    
    public func updateUIView(_ uiView: UISearchBar, context: UIViewRepresentableContext<ManagedSearchView>) {
        uiView.text = text
    }
}
