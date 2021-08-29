package de.menkalian.monoceros.app.printing

import kotlinx.serialization.Serializable

@Serializable
data class PrintInformation(
    var amount: Int,
    var titleTemplate: String,
    var yearTemplate: String,
    var bigCommentTemplate: String?,
    var smallCommentTemplate: String?,
    val variables: HashMap<String, String>
)
