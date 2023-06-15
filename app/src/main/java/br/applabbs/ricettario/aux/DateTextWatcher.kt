package br.applabbs.ricettario.aux

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTextWatcher(private val editText: EditText) : TextWatcher {
    private val mask = "##/##/####"
    private val dateFormatter = SimpleDateFormat("MMddyyyy", Locale.getDefault())
    private var isFormatting = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) {
            return
        }

        isFormatting = true

        val editable = editText.text
        val formattedText = applyMask(editable.toString())

        editText.removeTextChangedListener(this)
        editable.replace(0, editable.length, formattedText)
        editText.addTextChangedListener(this)

        isFormatting = false
    }

    private fun applyMask(text: String): String {
        val filteredText = text.replace("[^\\d.]".toRegex(), "")
        val formattedText = StringBuilder()
        var index = 0

        for (i in mask.indices) {
            if (index >= filteredText.length) {
                break
            }

            if (mask[i] == '#') {
                formattedText.append(filteredText[index])
                index++
            } else {
                formattedText.append(mask[i])
            }
        }

        return formattedText.toString()
    }

    fun getSelectedDate(): Date? {
        val text = editText.text.toString().replace("[^\\d.]".toRegex(), "")

        return try {
            val parsedDate = dateFormatter.parse(text)
            parsedDate
        } catch (e: ParseException) {
            null
        }
    }
}
