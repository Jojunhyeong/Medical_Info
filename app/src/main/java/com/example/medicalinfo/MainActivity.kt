package com.example.medicalinfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.medicalinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivityButton.setOnClickListener{
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.contactLayer.setOnClickListener{
           with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.contactValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataUiUpdate()
    }

    private fun getDataUiUpdate(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)){
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.birthdateValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.contactValueTextView.text = getString(CONTACT, "미정")
            val caution = getString(CAUTION, "")

            binding.cautionTextView.isVisible = caution.isNullOrEmpty().not()
            binding.cautionValueTextView.isVisible = caution.isNullOrEmpty().not()
            if(!caution.isNullOrEmpty()) {
                binding.cautionValueTextView.text = caution

            }

        }
    }
    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()){
            clear()
            apply()
            getDataUiUpdate()
        }
        Toast.makeText( this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }
}