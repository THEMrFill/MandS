package com.marksspencers.philip.arnold.utils

import junit.framework.TestCase.assertEquals
import org.joda.time.DateTime
import org.junit.Test

class DateUtilsTests {
    @Test
    fun `check the DateTime`() {
        val dt = DateTime(2020, 12, 20, 0, 0)
        val str = "2020-12-20"
        assertEquals(dt, str.toJodaTime())
    }

    @Test
    fun `check the formatting of the date`() {
        val str = "2020-12-20"
        assertEquals("20 Dec 2020", str.formatDate())
    }
}