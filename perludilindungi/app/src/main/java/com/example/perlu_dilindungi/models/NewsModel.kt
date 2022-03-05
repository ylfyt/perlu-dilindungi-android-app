package com.example.perlu_dilindungi.models

class NewsResponseModel {
    var success = false
    var message: String? = null
    var count_total = 0
    var results: ArrayList<NewsModel>? = null
}

class NewsModel {
    var title: String? = null
    var link: ArrayList<String>? = null
    var guid: String? = null
    var pubDate: String? = null
    var description: NewsDescription? = null
    var enclosure: NewsEnclosure? = null
}

class NewsDescription {
    var __cdata: String? = null
}

class NewsEnclosure {
    var _url: String? = null
    var _length: String? = null
    var _type: String? = null
}