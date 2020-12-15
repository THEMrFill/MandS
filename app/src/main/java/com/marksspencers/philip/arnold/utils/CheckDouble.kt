package com.marksspencers.philip.arnold.utils

fun Pair<Any?, Any?>.let(action: (pair: Pair<Any?, Any?>) -> Unit) {
    if (this.first != null && this.second != null)
        action(this)
}