package com.example.myfirstapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var getPaid: GetPaid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        monster01.setOnClickListener { viewMonsterImage(R.drawable.monster01) }
        setPaymentButtonListener()

    }

    private fun setPaymentButtonListener() {
        btn_init_paypal.setOnClickListener {
            getPaid = GetPaid(this, amount = "20")
            getPaid.initService()
            getPaid.initPayment()
        }
    }

    private fun viewMonsterImage(monsterId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.setAction(Intent.ACTION_VIEW)

        val uri = Uri.parse("http://com.example.myfirstapp/view?id=$monsterId")
        intent.data = uri

        intent.putExtra("monsterId", monsterId)

        startActivity(intent)
    }

    class UserInfo {
        companion object {
            var mobile: String = ""
            var itemID: Int = 0
            var qty: Int = 0
            var client_id: String =
                "AcQOdV-jAazuimcSZtQ6SOQfg0sWRSna5cNjIAFKEHqkbKjrnM7FCydRbzLiEDfvnyLWc2BHTKZOjnr1"
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 420 && resultCode == Activity.RESULT_OK) {
            val confirmation: PaymentConfirmation? =
                data!!.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
            if (confirmation != null) {
                try {

                    if (confirmation.toJSONObject().getJSONObject("response").getString("state").equals(
                            "approved"
                        )
                    ) {
                        Toast.makeText(this, "Payment success!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Error processing your payment", Toast.LENGTH_LONG)
                            .show()
                    }

                } catch (exc: Exception) {
                    Log.e("payment_error", exc.toString())
                }
            } else {
                Toast.makeText(this, "PAyment Cancelled", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        getPaid.destService()
    }


}
