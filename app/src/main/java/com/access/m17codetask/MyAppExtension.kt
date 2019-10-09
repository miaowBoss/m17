package com.access.m17codetask

import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.request.BaseRequestOptions


@GlideExtension
class MyAppExtension private constructor() {
    companion object {
        @GlideOption
        @JvmStatic
        fun miniThumb(options: BaseRequestOptions<*>, size: Int): BaseRequestOptions<*> {
            return options
                .fitCenter()
                .override(size)
        }
    }
}