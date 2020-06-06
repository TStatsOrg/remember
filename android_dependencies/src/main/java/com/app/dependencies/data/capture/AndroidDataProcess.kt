package com.app.dependencies.data.capture

import com.app.shared.data.capture.RawDataProcess

class AndroidDataProcess(
    private val htmlParser: HTMLParser,
    private val imageParser: ImageParser
): RawDataProcess {

    override fun process(capture: String?, result: (RawDataProcess.Item) -> Unit) {
        // if null, it's unknown
        if (capture == null) {
            result(RawDataProcess.Item.Unknown)
            return
        }

        // if not url, must be text
        if (!capture.isUrl()) {
            result(RawDataProcess.Item.Text(text = capture))
            return
        }

        // if it's an image
        if (capture.isImage()) {
            val image = imageParser.parse(url = capture)
            val output = when {
                image != null -> RawDataProcess.Item.Image(url = image)
                else -> RawDataProcess.Item.Unknown
            }
            result(output)
            return
        }

        // then it's a link
        htmlParser.parse(url = capture) {
            it.fold(
                onSuccess = {
                    result(RawDataProcess.Item.Link(
                        url = capture,
                        title = it.title,
                        description = it.description,
                        icon = it.icon
                    ))
                },
                onFailure = {
                    result(RawDataProcess.Item.Unknown)
                }
            )
        }
    }

    private fun String.isUrl(): Boolean {
        return this.startsWith("http://") || this.startsWith("https://") || this.startsWith("content://")
    }

    private fun String.isImage(): Boolean {
        return this.endsWith(".png") || this.endsWith(".jpeg") || this.endsWith(".jpg")
    }
}