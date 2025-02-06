package id.elutility.core.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.textWatcherFormatNumber(
    afterChange: (String) -> Unit = {},
    beforeChange: (String) -> Unit = {},
    onChange: (String) -> Unit
) {
    addTextChangedListener(object : TextWatcher {
        var callOnTextChanged = true

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            s?.let { text -> beforeChange(text.toString()) }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (callOnTextChanged) {
                callOnTextChanged = false
                s?.let {
                    var c = it.toString().toNumberOnly().clearComma()
                    if (c.isNotEmpty()) {
                        c = c.toDouble().toFormat().trim()
                        val min = this@textWatcherFormatNumber.text.toString().length - this@textWatcherFormatNumber.selectionStart
                        val prevLength = this@textWatcherFormatNumber.text.toString().length

                        onChange(c)
                        this@textWatcherFormatNumber.setText(c)

                        var charAddition = this@textWatcherFormatNumber.text.toString().length - prevLength - 1
                        if (charAddition < 0) charAddition = 0

                        if (min > 0) {
//                            val newSelection = (this@textWatcherFormatNumber.text.toString().length + charAddition) - min
                            if (((this@textWatcherFormatNumber.text.toString().length + charAddition) - min > 0) && (text.toString().length > (this@textWatcherFormatNumber.text.toString().length + charAddition) - min)) {
                                this@textWatcherFormatNumber.setSelection((this@textWatcherFormatNumber.text.toString().length + charAddition) - min)
                            } else {
                                this@textWatcherFormatNumber.setSelection(this@textWatcherFormatNumber.text.toString().length)
                            }
                        } else {
                            this@textWatcherFormatNumber.setSelection(this@textWatcherFormatNumber.text.toString().length)
                        }
                    }
                    callOnTextChanged = true
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
            s?.let { text -> afterChange(text.toString()) }
        }
    })
}