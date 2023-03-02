package com.fptu.smarttvapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.fptu.smarttvapp.R
import com.fptu.smarttvapp.core.NetworkUtils
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {
    private var edtCode: EditText? = null
    private var btnEnter: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        initAction()
    }

    private fun initAction() {
        btnEnter!!.setOnClickListener {
            if (!NetworkUtils.isNetworkAvailable(this)) {
                Toasty.info(this, "No internet connection", Toasty.LENGTH_LONG, true)
                    .show()
                return@setOnClickListener
            }
            if (!isValidCode) {
                Toasty.error(this, "Invalid Kode", Toasty.LENGTH_LONG, true)
                    .show()
                return@setOnClickListener
            }
            navigateToDisplayActivity()
        }
    }


    private val isValidCode: Boolean
        get() {
            val code = edtCode!!.text.toString()
            return code.equals("bRuH", ignoreCase = true)
        }

    private fun initData() {}
    private fun initView() {
        edtCode = findViewById(R.id.edtCode)
        btnEnter = findViewById(R.id.btnEnter)
        Toasty.Config.getInstance()
            .setGravity(Gravity.TOP, 0, 0)
            .apply()
    }

    private fun navigateToDisplayActivity() {
        val intent = Intent(this@MainActivity, DisplayActivity::class.java)
        startActivity(intent)
        finish()
    }
}