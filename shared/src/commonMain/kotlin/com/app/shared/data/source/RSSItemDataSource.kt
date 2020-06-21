package com.app.shared.data.source

import com.app.shared.business.Either
import com.app.shared.data.dto.RSSItemDTO

interface RSSItemDataSource {
    fun getRSSItems(fromLink: String): Either<List<RSSItemDTO>>
}