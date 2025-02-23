package com.firestormsw.tinylist

import android.content.Context

class ResourceProvider(private val context: Context) {
    fun getString(resId: Int, vararg formatArgs: Any?): String {
        return context.getString(resId, *formatArgs)
    }
}