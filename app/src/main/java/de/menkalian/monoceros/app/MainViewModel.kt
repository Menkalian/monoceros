package de.menkalian.monoceros.app

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.menkalian.monoceros.app.printing.PrintInformation
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val client = MonocerosClient()
    val currentConfig = PrintInformation(1, "", "", null, null, hashMapOf())

    private var onPreviewUpdated: ((img: Bitmap) -> Unit)? = null

    var selectedTitleTemplateIndex: Int = 0
    var selectedYearTemplateIndex: Int = 0
    var selectedBigCommentTemplateIndex: Int = 0
    var selectedSmallCommentTemplateIndex: Int = 0

    suspend fun checkHealth() = client.checkHealth()

    fun setTitleTemplate(template: String) {
        currentConfig.titleTemplate = template
        updatePreview()
    }

    fun setYearTemplate(template: String) {
        currentConfig.yearTemplate = template
        updatePreview()
    }

    fun setBigCommentEnabled(enable: Boolean) {
        if (enable)
            currentConfig.bigCommentTemplate = ""
        else
            currentConfig.bigCommentTemplate = null
        updatePreview()
    }

    fun setBigCommentTemplate(template: String) {
        if(currentConfig.bigCommentTemplate == null)
            return
        currentConfig.bigCommentTemplate = template
        updatePreview()
    }

    fun setSmallCommentEnabled(enable: Boolean) {
        if (enable)
            currentConfig.smallCommentTemplate = ""
        else
            currentConfig.smallCommentTemplate = null
        updatePreview()
    }

    fun setSmallCommentTemplate(template: String) {
        if(currentConfig.smallCommentTemplate == null)
            return
        currentConfig.smallCommentTemplate = template
        updatePreview()
    }

    fun setVariable(name: String, value: String) {
        currentConfig.variables[name] = value
        updatePreview()
    }

    fun setOnPreviewUpdated(action: ((img: Bitmap) -> Unit)?) {
        onPreviewUpdated = action
        updatePreview()
    }

    private fun updatePreview() {
        return
//        if (onPreviewUpdated != null) {
//            TODO()
//        }
    }

    fun startPrintingCurrentConfig(onResult: (success: Boolean) -> Unit) {
        val configCopy = currentConfig.copy()
        viewModelScope.launch {
            if (client.printData(configCopy)) {
                Log.i("MainViewModel", "Printing successful.")
                onResult(true)
            } else {
                Log.w("MainViewModel", "Printing failed.")
                onResult(false)
            }
        }
    }
}