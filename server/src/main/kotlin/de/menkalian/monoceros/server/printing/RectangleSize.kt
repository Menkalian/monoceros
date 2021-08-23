package de.menkalian.monoceros.server.printing

data class RectangleSize(val width: Float, val height: Float) {
    fun getWidth(orientation: Orientation) = when (orientation) {
        Orientation.HORIZONTAL -> width
        Orientation.VERTICAL   -> height
    }

    fun getHeight(orientation: Orientation) = when (orientation) {
        Orientation.HORIZONTAL -> height
        Orientation.VERTICAL   -> width
    }
}
