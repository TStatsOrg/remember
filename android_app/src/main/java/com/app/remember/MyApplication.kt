package com.app.remember

import android.app.Application
import com.app.shared.coroutines.AppScope
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.utils.MLogger
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    private val provider by lazy {
        DependencyProvider(appContext = this)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(provider.module)
        }

//        AppScope.launch(context = MainDispatcher) {
//            getData(url = "https://www.hotnews.ro/stiri-international-23838481-administratia-trump-calcul-data-1-mai-pentru-relaxarea-restrictiilor-impuse-pentru-limitarea-raspandirii-coronavirusului-vedem-lumina-capatul-tunelului.htm")
//        }
    }
}

suspend fun getData(url: String) {
    withContext(context = DefaultDispatcher) {

        val con2 = Jsoup.connect(url)
        val doc = con2.get()

        val e1: Element? = doc.head().select("link[href~=.*\\.(ico|png)]").first() // example type 1 & 2
        val imageUrl1 = e1?.attr("href")

        val e2: Element? = doc.head().select("meta[itemprop=image]").first() //example type 3
        val imageUrl2 = e2?.attr("itemprop")

        val title = doc.title()
        val exc: Element? = doc.head().select("meta[name=description]").first()
        val descr = exc?.attr("content")

        MLogger.log("Url $url title $title $imageUrl1 $imageUrl2 $descr")
    }
}