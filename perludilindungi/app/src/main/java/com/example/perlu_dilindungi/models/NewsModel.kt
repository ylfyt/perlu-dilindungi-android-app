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
    var pubdate: String? = null
    var cdata: String? = null
    var enclURL: String? = null
    var enclLength: String? = null
    var enclType: String? = null
}