package net.alpacaplayground.nowtime

import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Time {

    private val calendar = Calendar.getInstance()
    private val format = SimpleDateFormat("HH", Locale.CHINA)

    var hour by CalendarDelegate(Calendar.HOUR_OF_DAY)

    var minute by CalendarDelegate(Calendar.MINUTE)

    var second by CalendarDelegate(Calendar.SECOND)

    var timeInMillis: Long
        get() = calendar.timeInMillis
        set(value) {
            calendar.timeInMillis = value
        }

    val nextHour: Int
    get() = if(hour==23) 0 else hour

    fun updateToNow() {
        timeInMillis = System.currentTimeMillis()
    }

    fun format(pattern: String): String {
        format.applyPattern(pattern)
        return format.format(timeInMillis)
    }

    private class CalendarDelegate(val field: Int) : ReadWriteProperty<Time,Int>{
        override fun getValue(thisRef: Time, property: KProperty<*>): Int {
            return thisRef.calendar[field]
        }

        override fun setValue(thisRef: Time, property: KProperty<*>, value: Int) {
            thisRef.calendar.set(field,value)
        }

    }

    operator fun plus(time: Int): Time{
        timeInMillis += time
        return this
    }
}