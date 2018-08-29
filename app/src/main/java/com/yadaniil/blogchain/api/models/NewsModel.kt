package com.yadaniil.blogchain.api.models

import java.util.*

class NewsModel(var title: String,
                var publishDate: Date,
                var newsLink: String,
                var imageLink: String = "",
                var sourceName: String = "",
                var sourceImageLink: String = "")