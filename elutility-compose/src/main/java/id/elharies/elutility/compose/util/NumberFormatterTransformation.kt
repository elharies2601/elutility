package id.elharies.elutility.compose.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class NumberFormatterTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        if (original.isEmpty()) return TransformedText(text, OffsetMapping.Identity)

        val reversed = original.reversed()
        val formattedReversed = StringBuilder()
        val reversedToTransformed = mutableListOf<Int>()
        val transformedToReversed = mutableListOf<Int>()
        var transformedOffset = 0

        // Bangun formatted reversed string dan mapping
        for (i in reversed.indices) {
            formattedReversed.append(reversed[i])
            reversedToTransformed.add(transformedOffset)
            transformedToReversed.add(i)
            transformedOffset++

            if ((i + 1) % 3 == 0 && i != reversed.lastIndex) {
                formattedReversed.append('.')
                reversedToTransformed.add(transformedOffset)
                transformedToReversed.add(-1)
                transformedOffset++
            }
        }

        val formatted = formattedReversed.reversed().toString()

        return TransformedText(
            AnnotatedString(formatted),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset >= original.length) return formatted.length

                    val virtualIndex = original.length - 1 - offset
                    val dotCount = virtualIndex / 3
                    return offset + dotCount + if (virtualIndex % 3 == 0 && virtualIndex > 0) 1 else 0
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset >= formatted.length) return original.length

                    var virtualOffset = 0
                    var realOffset = 0
                    while (virtualOffset < offset && realOffset < formatted.length) {
                        if (formatted[realOffset] == '.') {
                            virtualOffset++
                        } else {
                            realOffset++
                            virtualOffset++
                        }
                    }
                    return realOffset.coerceAtMost(original.length)
                }
            }
        )
    }
}