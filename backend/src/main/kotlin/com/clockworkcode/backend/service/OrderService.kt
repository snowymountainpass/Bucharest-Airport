package com.clockworkcode.backend.service

import com.clockworkcode.backend.dto.PaymentDTO
import com.stripe.Stripe
import com.stripe.model.Price
import com.stripe.model.Product
import com.stripe.model.checkout.Session
import com.stripe.param.PriceCreateParams
import com.stripe.param.ProductCreateParams
import com.stripe.param.checkout.SessionCreateParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Service
class OrderService {
    @Value("\${STRIPE_SECRET_KEY}")
    lateinit var apiKey:String

    fun createProduct(paymentDTO: PaymentDTO):Product {

        val durationInMinutes:Long = abs(Duration.between(paymentDTO.entryTime,paymentDTO.departureTime).toMinutes())
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        val params:ProductCreateParams = ProductCreateParams.builder()//
            .setName("Parking Space Cost")//
            .setDescription(
                ((((("Car " + paymentDTO.carLicensePlate) +
                        " spent " + durationInMinutes.toString() + " minutes (" + paymentDTO.entryTime.format(formatter)) + " to "
                        + paymentDTO.departureTime!!.format(formatter)) + ") at the "
                        + paymentDTO.airportName) ) + ".")//
            .setStatementDescriptor(paymentDTO.carLicensePlate)//
            .build()
        return Product.create(params)
    }

    fun createPrice(paymentDTO:PaymentDTO,product:Product):Price{
        val params:PriceCreateParams =
        PriceCreateParams.builder() //
            .setUnitAmount((paymentDTO.cost*100).toLong()) //amount * 100 euro cents
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
            .setReturnUrl("http://localhost:3000/confirmation?session_id={CHECKOUT_SESSION_ID}")//
            .addLineItem(lineItem)//
            .putMetadata("licensePlate",product.statementDescriptor)
            .build()

        return Session.create(params)
    }

}