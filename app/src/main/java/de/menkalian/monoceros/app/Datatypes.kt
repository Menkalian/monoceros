package de.menkalian.monoceros.app

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class TemplateAdapter(context: Context, resId: Int) : ArrayAdapter<TemplateFactory.TemplateEntry<String, String>>(context, resId) {
    private val filter = NoOpFilter()

    override fun getFilter(): Filter {
        return filter
    }

    private inner class NoOpFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result = FilterResults()
            result.values = (0 until count).map { getItem(it) }.toList()
            result.count = count
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }
    }
}