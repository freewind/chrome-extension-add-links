package example

import org.w3c.dom.*
import kotlin.browser.document

private val links: List<Pair<List<String>, String>> = listOf(
        listOf("FCoin", "Fcoin", "fcoin") to "https://y0.cn/fcoin", //""https://www.fcoin.com/i/Nybzz",
        listOf("Binance", "binance", "币安") to "https://y0.cn/binance", //"https://www.binance.top/?ref=13925629",
        listOf("Huobi", "huobi", "火币") to "https://y0.cn/huobi" //"https://www.huobi.br.com/topic/invited/?invite_code=76dk3"
)

fun main(args: Array<String>) {
    document.body?.let { body ->
        links.forEach { (keywords, url) ->
            keywords.forEach { keyword ->
                println("Looking for keyword: $keyword")
                walkAll(body) { text ->
                    if (text.wholeText.contains(keyword)) {
                        val html = text.wholeText.replace(keyword, """<a href="$url" target="_blank">$keyword</a>""")
                        document.fromHtml(html)
                    } else {
                        null
                    }
                }
            }
        }
    }
}

fun walkAll(element: Element, fn: (Text) -> Node?) {
    val children = element.childNodes
    for (i in 0 until children.length) {
        children[i]?.let { child ->
            when (child) {
                is HTMLAnchorElement -> Unit // ignore links
                is Text -> {
                    val newNode = fn(child)
                    if (newNode != null) {
                        element.insertBefore(newNode, child)
                        element.removeChild(child)
                    }
                }
                is Element -> walkAll(child, fn)
            }
        }
    }
}


private fun Document.fromHtml(html: String): Node {
    val span = this.createElement("span") as HTMLSpanElement
    span.innerHTML = html
    return span
}