package com.app.shared.feature.capture

import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.capture.DataCapture
import com.app.shared.data.capture.DataProcess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedDataCaptureViewModel(
    private val dataCapture: DataCapture,
    private val dataProcess: DataProcess
): DataCaptureViewModel {

    private val scope: CoroutineScope = provideViewModelScope()

    override fun capture(callback: (DataProcess.Item) -> Unit) {
        scope.launch(context = MainDispatcher) {
            val result = withContext(context = DefaultDispatcher) {
                // get whatever unboxed data is in there
                val unboxed = dataCapture.unbox()
                // return a processed version
                return@withContext dataProcess.process(capture = unboxed)
            }

            callback(result)
        }
    }
}