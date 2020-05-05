package com.example.myfirstapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.paypal.android.sdk.payments.*
import java.math.BigDecimal

class GetPaid(private val context: Context, private val amount: String) {
    private val CLIENT_ID =
        "AcQOdV-jAazuimcSZtQ6SOQfg0sWRSna5cNjIAFKEHqkbKjrnM7FCydRbzLiEDfvnyLWc2BHTKZOjnr1"
    private val config = PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(CLIENT_ID)

    fun initPayment() {
        val payPalPayment = PayPalPayment(
            BigDecimal(amount),
            "USD",
            "Pay Now",
            PayPalPayment.PAYMENT_INTENT_SALE
        )
        val intent = Intent(context, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment)
        (context as Activity).startActivityForResult(intent, 420)
    }

    fun initService() {
        val intent = Intent(context, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        context.startService(intent)
    }

    fun destService() {
        context.stopService(Intent(context, PayPalService::class.java))
    }

}