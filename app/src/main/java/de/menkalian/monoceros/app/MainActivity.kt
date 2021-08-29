package de.menkalian.monoceros.app

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // @formatter:off
        val previewImage             = findViewById<ImageView>(R.id.main_img_preview)

        val amountSlider             = findViewById<Slider>(R.id.main_slider_amount)
        val btnPrint                 = findViewById<FloatingActionButton>(R.id.main_btn_print)

        val titleTemplate            = findViewById<AutoCompleteTextView>(R.id.main_input_title_template)
        val yearTemplate             = findViewById<AutoCompleteTextView>(R.id.main_input_year_template)
        val bigCommentTemplate       = findViewById<AutoCompleteTextView>(R.id.main_input_big_comment_template)
        val smallCommentTemplate     = findViewById<AutoCompleteTextView>(R.id.main_input_small_comment_template)

        val titleValue               = findViewById<EditText>(R.id.main_input_title_var)
        val yearValue                = findViewById<EditText>(R.id.main_input_year_var)
        val bigCommentValue          = findViewById<EditText>(R.id.main_input_big_comment_var)
        val smallCommentValue        = findViewById<EditText>(R.id.main_input_small_comment_var)

        val switchEnableBigComment   = findViewById<SwitchMaterial>(R.id.main_switch_enable_big_comment)
        val switchEnableSmallComment = findViewById<SwitchMaterial>(R.id.main_switch_enable_small_comment)

        val bigCommentInputLayout    = findViewById<LinearLayout>(R.id.main_lay_big_comment)
        val smallCommentInputLayout  = findViewById<LinearLayout>(R.id.main_lay_small_comment)
        // @formatter:on

        viewModel.setOnPreviewUpdated {
            previewImage.setImageBitmap(it)
        }

        amountSlider.value = viewModel.currentConfig.amount.toFloat()
        amountSlider.addOnChangeListener { _, value, _ ->
            viewModel.currentConfig.amount = value.toInt()
        }

        btnPrint.setOnClickListener {
            viewModel.startPrintingCurrentConfig {
                if (it) {
                    Toast.makeText(this, "Druck erfolgreich an den Server übermittelt.", Toast.LENGTH_LONG).show()
                } else {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Druck fehlgeschlagen: Ungültige Antwort vom Server erhalten.")
                        .setPositiveButton("Ok") { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
                        .show()
                }
            }
        }

        val titleAdapter = newTitleAdapter()
        titleTemplate.setAdapter(titleAdapter)
        titleTemplate.addTextListener(titleAdapter) { template ->
            viewModel.setTitleTemplate(template)
        }
        if (titleTemplate.text.isNullOrBlank())
            titleTemplate.setText(titleAdapter.getItem(0)?.toString() ?: "", false)

        val yearAdapter = newYearAdapter()
        yearTemplate.setAdapter(yearAdapter)
        yearTemplate.addTextListener(yearAdapter) { template ->
            viewModel.setYearTemplate(template)
        }
        if (yearTemplate.text.isNullOrBlank())
            yearTemplate.setText(yearAdapter.getItem(0)?.toString() ?: "", false)

        val bigCommentAdapter = newCommentAdapter(1)
        bigCommentTemplate.setAdapter(bigCommentAdapter)
        if (bigCommentTemplate.text.isNullOrBlank())
            bigCommentTemplate.setText(bigCommentAdapter.getItem(0)?.toString() ?: "", false)
        bigCommentTemplate.addTextListener(bigCommentAdapter) { template ->
            viewModel.setBigCommentTemplate(template)
        }

        val smallCommentAdapter = newCommentAdapter(2)
        smallCommentTemplate.setAdapter(smallCommentAdapter)
        if (smallCommentTemplate.text.isNullOrBlank())
            smallCommentTemplate.setText(smallCommentAdapter.getItem(0)?.toString() ?: "", false)
        smallCommentTemplate.addTextListener(smallCommentAdapter) { template ->
            viewModel.setSmallCommentTemplate(template)
        }

        titleValue.addTextChangedListener(VariableSetterTextChangeListener("Monoceros.Daten.Sorte"))
        titleValue.setText(viewModel.currentConfig.variables["Monoceros.Daten.Sorte"] ?: "")
        yearValue.addTextChangedListener(VariableSetterTextChangeListener("Monoceros.Daten.Jahr"))
        yearValue.setText(viewModel.currentConfig.variables["Monoceros.Daten.Jahr"] ?: "")
        bigCommentValue.addTextChangedListener(VariableSetterTextChangeListener("Monoceros.Daten.Text.001"))
        bigCommentValue.setText(viewModel.currentConfig.variables["Monoceros.Daten.Text.001"] ?: "")
        smallCommentValue.addTextChangedListener(VariableSetterTextChangeListener("Monoceros.Daten.Text.002"))
        smallCommentValue.setText(viewModel.currentConfig.variables["Monoceros.Daten.Text.002"] ?: "")

        switchEnableBigComment.isChecked = viewModel.currentConfig.bigCommentTemplate != null
        switchEnableBigComment.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                bigCommentInputLayout.visibility = View.VISIBLE
                viewModel.setBigCommentEnabled(true)
                bigCommentTemplate.setText(bigCommentTemplate.text, false)
            } else {
                bigCommentInputLayout.visibility = View.GONE
                viewModel.setBigCommentEnabled(false)
            }
        }
        bigCommentInputLayout.visibility = if (switchEnableBigComment.isChecked) View.VISIBLE else View.GONE

        switchEnableSmallComment.isChecked = viewModel.currentConfig.smallCommentTemplate != null
        switchEnableSmallComment.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                smallCommentInputLayout.visibility = View.VISIBLE
                viewModel.setSmallCommentEnabled(true)
                smallCommentTemplate.setText(smallCommentTemplate.text, false)
            } else {
                smallCommentInputLayout.visibility = View.GONE
                viewModel.setSmallCommentEnabled(false)
            }
        }
        smallCommentInputLayout.visibility = if (switchEnableSmallComment.isChecked) View.VISIBLE else View.GONE

        viewModel.viewModelScope.launch {
            if (!viewModel.checkHealth()) {
                MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle("Verbindung zum Server fehlgeschlagen. Druck ist nicht möglich.")
            } else {
                Toast.makeText(this@MainActivity, "Server verfügbar. Einsatzbereit!", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun AutoCompleteTextView.addTextListener(adapter: TemplateAdapter, onTemplate: (String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                for (i in 0 until adapter.count) {
                    if (adapter.getItem(i).toString() == s.toString()) {
                        onTemplate(adapter.getItem(i)?.getTemplate() ?: "")
                        break
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun newTitleAdapter() = newAdapter(TemplateFactory.getTitleTemplates())
    private fun newYearAdapter() = newAdapter(TemplateFactory.getYearTemplates())
    private fun newCommentAdapter(commentNo: Int) = newAdapter(TemplateFactory.getCommentTemplates(commentNo))

    private fun newAdapter(items: Collection<TemplateFactory.TemplateEntry<String, String>>): TemplateAdapter {
        val templateAdapter = TemplateAdapter(this, R.layout.support_simple_spinner_dropdown_item)
        templateAdapter.addAll(items)
        return templateAdapter
    }

    inner class VariableSetterTextChangeListener(val varKey: String) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.setVariable(varKey, s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}