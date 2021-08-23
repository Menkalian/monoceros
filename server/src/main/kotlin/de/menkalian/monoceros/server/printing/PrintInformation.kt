package de.menkalian.monoceros.server.printing

data class PrintInformation(
    val amount: Int,
    val titleTemplate: String,
    val yearTemplate: String,
    val bigCommentTemplate: String?,
    val smallCommentTemplate: String?,
    val variables: HashMap<String, String>
)
