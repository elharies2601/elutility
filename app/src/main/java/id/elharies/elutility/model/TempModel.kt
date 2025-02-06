package id.elharies.elutility.model

import id.elutility.core.ext.getCurrentTime
import id.elutility.core.ext.toString
import java.io.Serializable

data class TempModel(
    val date: String = getCurrentTime().toString("dd MMMM yyyy"),
    val number: Double = 0.0
): Serializable
