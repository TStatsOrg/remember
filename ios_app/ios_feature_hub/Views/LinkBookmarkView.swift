//
//  LinkBookmarkView.swift
//  ios_feature_hub
//
//  Created by Gabriel Coman on 30/04/2020.
//  Copyright © 2020 Gabriel Coman. All rights reserved.
//

import Foundation
import SwiftUI
import ios_dependencies
import RememberShared
import SDWebImageSwiftUI
import ios_views

struct LinkBookmarkView: View {
    
    @Injected var navigation: Navigation
    let viewState: BookmarkLinkViewState
    @SwiftUI.State private var showingSheet = false
    @SwiftUI.State var isPresented: Bool = false
    
    var body: some View {
        VStack(alignment: .leading) {
            BookmarkTopicView(viewState: viewState)
            HStack(alignment: .top) {
                Text(viewState.title ?? "")
                    .fontWeight(.bold)
                    .lineLimit(nil)
                if !self.viewState.isIconHidden {
                    WebImage(url: self.viewState.icon)
                        .resizable()
                        .frame(width: 60, height: 60)
                        .scaledToFit()
                        .clipped()
                }
            }
            Text(viewState.date)
                .fontWeight(.light)
        }
        .onTapGesture {
            self.navigation.seeUrlDestination(url: self.viewState.destinationUrl)
        }
        .onLongPressGesture {
            self.showingSheet = true
        }
        .actionSheet(isPresented: $showingSheet) {
            ActionSheet(title: Text("Editing"),
                        message: Text("options"),
                        buttons: [
                            .default(Text("Edit"), action: {
                                self.isPresented = true
                            }),
                            .destructive(Text("Delete"), action: {
                                
                            }),
                            .cancel()
                        ])
        }
        .sheet(isPresented: $isPresented, content: {
            self.navigation.seeEditBookmark(forBookmarkId: self.viewState.id)
            
        })
    }
}
