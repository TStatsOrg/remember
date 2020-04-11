package com.app.shared.features.savecontent

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.SavedContent
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

open class SharedSaveContentViewModel(private val store: Store<AppState>): SaveContentViewModel {

    private val scope: CoroutineScope = provideViewModelScope()

    override fun clear() = store.dispatch(action = Actions.PreviewContent.Reset)

    override fun handle(sharedData: SharedData) {
        val unboxed = sharedData.unbox() ?: return

        scope.launch(context = MainDispatcher) {
            when (unboxed) {
                is SharedDataType.Text -> store.dispatch(action = Actions.PreviewContent.Text(content = unboxed.content))
                is SharedDataType.Link -> store.dispatch(action = Actions.PreviewContent.Link(url = unboxed.url))
                is SharedDataType.Unsupported -> Unit
            }
        }
    }

    override fun observePreviewState(callback: (SavedContent) -> Unit) {
        scope.launch(context = MainDispatcher) {
            store.asFlow()
                .flowOn(context = DefaultDispatcher)
                .mapNotNull { it.previewContent }
                .collect {
                    callback(it)
                }
        }
    }
}