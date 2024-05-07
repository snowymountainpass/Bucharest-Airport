package com.clockworkcode.backend.service

import com.clockworkcode.backend.dto.PaymentDTO
import com.stripe.Stripe
import com.stripe.Stripe.apiKey
import com.stripe.model.Price
import com.stripe.model.Product
import com.stripe.model.checkout.Session
import com.stripe.param.PriceCreateParams
import com.stripe.param.ProductCreateParams
import com.stripe.param.checkout.SessionCreateParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import kotlin.math.abs

@Service
class OrderService {
    @get:Value(value = "\${STRIPE_SECRET_KEY}")
     val apiKey:String = ""

    fun createProduct(paymentDTO: PaymentDTO):Product {

        val durationInMinutes:Long = abs(Duration.between(paymentDTO.entryTime,paymentDTO.departureTime).toMinutes())

        val params:ProductCreateParams = ProductCreateParams.builder()//
            .setName("Parking Space Cost")//
            .setDescription(
                ((((("Car " + paymentDTO.carLicensePlate) +
                        " spent " + durationInMinutes.toString() + " minutes (" + paymentDTO.entryTime) + " to "
                        + paymentDTO.departureTime) + ") at the "
                        + paymentDTO.airportName) ) + ".")//
            .build()
        return Product.create(params)
    }

    fun createPrice(paymentDTO:PaymentDTO,product:Product):Price{
        val params:PriceCreateParams =
        PriceCreateParams.builder() //
            .setUnitAmount(paymentDTO.cost*100) //amount * 100 euro cents
            .setCurrency("eur")//
            .setNickname(/* nickname = */ product.description)
            .setProduct(/* product = */ product.id)
            .build()
        return Price.create(params)
    }

    fun createSession(paymentDTO:PaymentDTO):Session{
        Stripe.apiKey=apiKey
        val product:Product = createProduct(paymentDTO)
        val price:Price = createPrice(paymentDTO,product)

        val lineItem:SessionCreateParams.LineItem = SessionCreateParams.LineItem.builder()//
            .setQuantity(1L)//
            .setPrice(/* price = */ price.id)//
            .build()

        val params:SessionCreateParams = SessionCreateParams.builder()//
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)//
            .setMode(SessionCreateParams.Mode.PAYMENT)//
            .setUiMode(SessionCreateParams.UiMode.EMBEDDED)//
            .setReturnUrl("http://localhost:3000/return?session_id={CHECKOUT_SESSION_ID}")//
            .addLineItem(lineItem)//
            .build()
        
        return Session.create(params)
    }

}