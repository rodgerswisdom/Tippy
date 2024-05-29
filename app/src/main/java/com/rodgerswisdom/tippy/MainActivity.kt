package com.rodgerswisdom.tippy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    private lateinit var etBaseAmount: EditText
    private lateinit var tvPercentageLabel: TextView
    private lateinit var etSeekBarTip: SeekBar
    private lateinit var tvtipAmount: TextView
    private lateinit var tvtotalAmount: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etBaseAmount = findViewById(R.id.etBaseAmount)
        tvPercentageLabel = findViewById(R.id.tvPercentageLabel)
        etSeekBarTip = findViewById(R.id.etSeekBarTip)
        tvtipAmount = findViewById(R.id.tipAmount)
        tvtotalAmount = findViewById(R.id.totalAmount)

//        Initialize Tip Percentage by progress

        etSeekBarTip.progress = INITIAL_TIP_PERCENT
        tvPercentageLabel.text = "$INITIAL_TIP_PERCENT%"

        // Read the percentage when seekbar os moved

        etSeekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvPercentageLabel.text = "$progress%"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?){

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?){

            }

        })

        etBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }
        })
    }

    private fun computeTipAndTotal() {
//        1.Get value of base and Tip Percent
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = etSeekBarTip.progress
//        2. Compute the tip and Total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
//        3. Update the UI
        tvtipAmount.text = tipAmount.toString()
        tvtotalAmount.text = totalAmount.toString()
    }
}