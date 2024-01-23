package com.example.medicalinfo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.medicalinfo.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.birthdatelayer.setOnClickListener{
            val listner = OnDateSetListener{_, year, month, dayOfMonth ->
                binding.birthdateTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this,
                listner,
                2000,
                1,
                1
            ).show()
        }

        binding.cautionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.cautionEditText.isVisible = isChecked
        }
        binding.cautionEditText.isVisible = binding.cautionCheckBox.isChecked

        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }

    }
    private fun saveData(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()){
            putString(NAME, binding.nameEditText.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(CONTACT, binding.contactEditText.text.toString())
            putString(BIRTHDATE, binding.birthdateTextView.text.toString())
            putString(CAUTION, getCaution())
            apply()
        }

        Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()

    }

    private fun getBloodType(): String{
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if(binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }

    private fun getCaution(): String {
        return if(binding.cautionCheckBox.isChecked) binding.cautionEditText.text.toString() else ""
    }
}