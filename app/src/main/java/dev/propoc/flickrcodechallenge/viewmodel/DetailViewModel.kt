package dev.propoc.flickrcodechallenge.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class DetailViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDate(dateString: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME

        var parsedDate: ZonedDateTime? = null
        try {
            parsedDate = ZonedDateTime.parse(dateString, formatter)
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            return "Invalid date"
        }

        val outFormat = DateTimeFormatter.ofPattern("dd MMM, yyyy", Locale.getDefault())

        return parsedDate.format(outFormat)
    }
}