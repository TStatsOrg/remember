//
//  RSSItemsView.swift
//  ios_feature_rss_items
//
//  Created by Gabriel Coman on 21/06/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import SwiftUI

public struct RSSItemsView: View {
    
    private let rssId: Int32
    
    public init(rssId: Int32) {
        self.rssId = rssId
    }
    
    public var body: some View {
        Text("Items")
    }
}
