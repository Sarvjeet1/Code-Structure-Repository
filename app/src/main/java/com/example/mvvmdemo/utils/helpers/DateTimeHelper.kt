package com.example.mvvmdemo.utils.helpers

import android.content.Context
import android.os.SystemClock
import android.text.format.DateFormat
import com.example.mvvmdemo.R
import com.example.mvvmdemo.utils.constants.AppConst
import com.example.mvvmdemo.utils.constants.DateTimeConst
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * This is the helper class which contains all the methods or functions regarding Date and Time.
 */
object DateTimeHelper {
    private var TAG = javaClass.simpleName

    private val simpleDateFormat: SimpleDateFormat =
        SimpleDateFormat(DateTimeConst.PATTERN.DAY_DATE_TIME, Locale.US)

    internal fun getCurrentTimestampInMilliseconds(): Long = System.currentTimeMillis()

    internal fun getCurrentTimeZone(): String =
        TimeZone.getDefault().id

    internal fun formatDayDateTime(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.DAY_DATE_TIME)
        }.format(Date(milliseconds))
    }

    internal fun formatDateTime(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.DATE_TIME)
        }.format(Date(milliseconds))
    }

    internal fun formatDate(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.API_ENCODED_DATE)
        }.format(Date(milliseconds))
    }

    internal fun formatDateMonthYear(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.DATE_CALENDAR_SELECTION)
        }.format(Date(milliseconds))
    }

    internal fun formatTime(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.TIME)
        }.format(Date(milliseconds))
    }

    internal fun formatCalendarDate(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.CALENDAR_DATE)
        }.format(Date(milliseconds))
    }

    internal fun formatDayOfWeek(date: Date): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.DAY_OF_WEEK)
        }.format(date)
    }

    internal fun formatDateForCalendarSelection(date: Date): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.DATE_CALENDAR_SELECTION)
        }.format(date)
    }

    internal fun formatDateForToday(date: Date): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.DATE_TODAY)
        }.format(date)
    }

    internal fun formatDay(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.DAY)
        }.format(Date(milliseconds))
    }

    internal fun formatMonth(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.MONTH)
        }.format(Date(milliseconds))
    }

    internal fun formatYear(milliseconds: Long): String {
        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.YEAR)
        }.format(Date(milliseconds))
    }

    internal fun convertSecondsToMilliseconds(seconds: Long): Long =
        seconds * DateTimeConst.DURATION_IN_MILLISECONDS.ONE_SECOND

    internal fun convertMillisecondsToSeconds(milliseconds: Long): Long =
        milliseconds / DateTimeConst.DURATION_IN_MILLISECONDS.ONE_SECOND

    internal fun formatTimeDuration(milliseconds: Long): String {
        val hr = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val min = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        val sec = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
        val ms = TimeUnit.MILLISECONDS.toMillis(milliseconds) % 1000

        return String.format("%02d:%02d:%02d", hr, min, sec)
    }

    internal fun formatDateTimeLeft(context: Context, milliseconds: Long): String =
        when {
            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_DAY -> formatDaysLeft(
                context = context,
                milliseconds = milliseconds
            )

            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_HOUR -> formatHoursLeft(
                context = context,
                milliseconds = milliseconds
            )

            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_MINUTE -> formatMinutesLeft(
                context = context,
                milliseconds = milliseconds
            )

            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_SECOND -> formatSecondsLeft(
                context = context,
                milliseconds = milliseconds
            )

            else -> formatSecondsLeft(context = context, milliseconds = milliseconds)
        }

    internal fun formatTodayYesterdayAndDate(context: Context, milliseconds: Long): String {
        val days: Long = TimeUnit.MILLISECONDS.toDays(milliseconds)

        return when {
            getCalendarInstance().get(Calendar.DATE) == days.toInt() -> context.getString(R.string.today)
            (getCalendarInstance().get(Calendar.DATE) - days.toInt()) == 1 -> context.getString(R.string.yesterday)

            else -> formatDateMonthYear(milliseconds = milliseconds)
        }
    }

    private fun formatDaysLeft(context: Context, milliseconds: Long): String {
        val days: Long = TimeUnit.MILLISECONDS.toDays(milliseconds)

        return if (days > AppConst.NUMBER.ONE)
            String.format(context.getString(R.string.format_days_left, days.toString()))
        else
            String.format(context.getString(R.string.format_day_left, days.toString()))
    }

    private fun formatHoursLeft(context: Context, milliseconds: Long): String {
        val hours: Long = TimeUnit.MILLISECONDS.toHours(milliseconds)

        return if (hours > AppConst.NUMBER.ONE)
            String.format(context.getString(R.string.format_hours_left, hours.toString()))
        else
            String.format(context.getString(R.string.format_hour_left, hours.toString()))
    }

    private fun formatMinutesLeft(context: Context, milliseconds: Long): String {
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(milliseconds)

        return if (minutes > AppConst.NUMBER.ONE)
            String.format(context.getString(R.string.format_minutes_left, minutes.toString()))
        else
            String.format(context.getString(R.string.format_minute_left, minutes.toString()))
    }

    private fun formatSecondsLeft(context: Context, milliseconds: Long): String {
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        return if (seconds > AppConst.NUMBER.ONE)
            String.format(context.getString(R.string.format_seconds_left, seconds.toString()))
        else
            String.format(context.getString(R.string.format_second_left, seconds.toString()))
    }

    internal fun formatDateOfBirth(context: Context, day: Int, month: Int, year: Int): String {
        val dayString: String =
            if (day >= AppConst.NUMBER.TEN) "$day"
            else "${AppConst.NUMBER.ZERO}$day"

        val monthString: String =
            if (month >= AppConst.NUMBER.TEN) "$month"
            else "${AppConst.NUMBER.ZERO}$month"

        return String.format(context.getString(R.string.format_dob, monthString, dayString, year))
    }

    internal fun formatDateTimeAgo(context: Context, milliseconds: Long): String =
        when {
            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_WEEK -> formatWeeksAgo(
                context = context,
                milliseconds = milliseconds
            )

            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_DAY -> formatDaysAgo(
                context = context,
                milliseconds = milliseconds
            )

            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_HOUR -> formatHoursAgo(
                context = context,
                milliseconds = milliseconds
            )

            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_MINUTE -> formatMinutesAgo(
                context = context,
                milliseconds = milliseconds
            )

            milliseconds > DateTimeConst.DURATION_IN_MILLISECONDS.ONE_SECOND -> formatSecondsAgo(
                context = context,
                milliseconds = milliseconds
            )

            else -> formatSecondsAgo(context = context, milliseconds = milliseconds)
        }

    private fun formatWeeksAgo(context: Context, milliseconds: Long): String {
        val weeks: Long =
            TimeUnit.MILLISECONDS.toDays(milliseconds) / DateTimeConst.DURATION_IN_DAYS.ONE_WEEK

        return String.format(context.getString(R.string.format_w, weeks.toString()))
//        return if (days > AppConst.NUMBER.ONE)
//            String.format(context.getString(R.string.format_weeks_ago, days.toString()))
//        else
//            String.format(context.getString(R.string.format_week_ago, days.toString()))
    }

    private fun formatDaysAgo(context: Context, milliseconds: Long): String {
        val days: Long = TimeUnit.MILLISECONDS.toDays(milliseconds)

        return String.format(context.getString(R.string.format_d, days.toString()))
//        return if (days > AppConst.NUMBER.ONE)
//            String.format(context.getString(R.string.format_days_ago, days.toString()))
//        else
//            String.format(context.getString(R.string.format_day_ago, days.toString()))
    }

    private fun formatHoursAgo(context: Context, milliseconds: Long): String {
        val hours: Long = TimeUnit.MILLISECONDS.toHours(milliseconds)

        return String.format(context.getString(R.string.format_h, hours.toString()))
//        return if (hours > AppConst.NUMBER.ONE)
//            String.format(context.getString(R.string.format_hours_ago, hours.toString()))
//        else
//            String.format(context.getString(R.string.format_hour_ago, hours.toString()))
    }

    private fun formatMinutesAgo(context: Context, milliseconds: Long): String {
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(milliseconds)

        return String.format(context.getString(R.string.format_m, minutes.toString()))
//        return if (minutes > AppConst.NUMBER.ONE)
//            String.format(context.getString(R.string.format_minutes_ago, minutes.toString()))
//        else
//            String.format(context.getString(R.string.format_minute_ago, minutes.toString()))
    }

    private fun formatSecondsAgo(context: Context, milliseconds: Long): String {
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        return context.getString(R.string.just_now)
//        return if (seconds > AppConst.NUMBER.ONE)
//            String.format(context.getString(R.string.format_seconds_ago, seconds.toString()))
//        else
//            String.format(context.getString(R.string.format_second_ago, seconds.toString()))
    }

    internal fun formatSecondsToStringForResendOtp(seconds: Long): String =
        String.format("%02d:%02d", seconds / 60, seconds % 60)

    internal fun isPatternMatches(regex: String, inputString: String): Boolean {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(inputString)

        return matcher.matches()
    }

    internal fun formatToApiEncodedDate(decodedDate: String): String {
        val date = simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.API_DECODED_DATE)
        }.parse(decodedDate)

        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.API_ENCODED_DATE)
        }.format(date)
    }

    internal fun formatToApiDecodedDate(encodedDate: String): String {
        val date = simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.API_ENCODED_DATE)
        }.parse(encodedDate)

        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.API_DECODED_DATE)
        }.format(date)
    }

    internal fun getTimestampForDate(
        day: Int,
        month: Int,
        year: Int
    ): String {
        val date = simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.API_FORMAT_DATE)
        }.parse("$year-${month + 1}-$day")

        return simpleDateFormat.apply {
            applyPattern(DateTimeConst.PATTERN.API_ENCODED_DATE)
        }.format(date)
    }

    internal fun updateDateInCalendarInstance(
        calendar: Calendar,
        day: Int,
        month: Int,
        year: Int
    ): Calendar =
        calendar.apply {
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
        }

    internal fun updateTimeInCalendarInstance(
        calendar: Calendar,
        hourOfDay: Int,
        min: Int,
        sec: Int,
        milliSec: Int
    ): Calendar =
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, sec)
            set(Calendar.MILLISECOND, milliSec)
        }

    internal fun getCurrentYear(): Int =
        getCalendarInstance().get(Calendar.YEAR)

    internal fun getCurrentMonth(): Int =
        getCalendarInstance().get(Calendar.MONTH)

    internal fun getCurrentDay(): Int =
        getCalendarInstance().get(Calendar.DAY_OF_MONTH)

    private fun getCalendarInstance(): Calendar =
        Calendar.getInstance().apply { timeInMillis = getCurrentTimestampInMilliseconds() }

    private fun convertTimestampInUTC(milliseconds: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = milliseconds

        val simpleDateFormat: SimpleDateFormat =
            SimpleDateFormat(DateTimeConst.PATTERN.FULL_DATE_TIME, Locale.US)

        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        LogHelper.debug(tag = TAG, msg = "Default --- ${Date(simpleDateFormat.format(Date(milliseconds))).time}")
        return Date(simpleDateFormat.format(Date(milliseconds))).time
    }

    internal fun formatTimestampInUTCTime(seconds: Long): String {
        if (seconds.toInt() == 0) return ""

        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.timeInMillis = convertSecondsToMilliseconds(seconds)

        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(DateTimeConst.PATTERN.CALENDAR_TIME)

        return simpleDateFormat.format(Date(cal.timeInMillis))
    }

    internal fun getCurrentDate(): String {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.timeInMillis = getCurrentTimestampInMilliseconds()

        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(DateTimeConst.PATTERN.CALENDAR_DATE)

        return simpleDateFormat.format(Date(cal.timeInMillis))
    }
}