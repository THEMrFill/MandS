package com.marksspencers.philip.arnold.utils

import org.joda.time.DateTime

const val DATE_FORMAT = "d MMM yyyy"

fun String.formatDate(): String = this.toJodaTime().toString(DATE_FORMAT)

fun String.toJodaTime(): DateTime = DateTime.parse(this)
