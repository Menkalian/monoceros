package de.menkalian.monoceros.app

object TemplateFactory {
    fun getTitleTemplates() = listOf(
        "Marmelade" to "Sorte: {{Monoceros.Daten.Sorte}}",
        "Eingemachtes" to "{{Monoceros.Daten.Sorte}}",
                                    )
        .map { TemplateEntry(it) }
        .toList()

    fun getYearTemplates() = listOf(
        "Herstellungsjahr" to "Herstellungsjahr: {{Monoceros.Daten.Jahr}}",
        "Jahr" to "Jahr: {{Monoceros.Daten.Sorte}}",
        "Haltbarkeit" to "Haltbar bis {{Monoceros.Daten.Sorte}}",
                                    )
        .map { TemplateEntry(it) }
        .toList()

    fun getCommentTemplates(i: Int) = listOf(
        "Homemade" to "Homemade {{Monoceros.Daten.Text.00$i}}",
        "Aus dem eigenen Garten" to "Aus dem eigenen Garten {{Monoceros.Daten.Text.00$i}}",
        "Freitext" to "{{Monoceros.Daten.Text.00$i}}",
                                    )
        .map { TemplateEntry(it) }
        .toList()

    class TemplateEntry<A, B>(val pair: Pair<A, B>) {
        fun getTemplate() = pair.second

        override fun toString(): String {
            return "${pair.first}"
        }
    }
}
