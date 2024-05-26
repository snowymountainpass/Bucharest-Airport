import {EmbeddedCheckout, EmbeddedCheckoutProvider} from "@stripe/react-stripe-js";
import {useEffect, useState} from "react";
import {loadStripe} from "@stripe/stripe-js";
import axios from "axios";
import {useAtom} from "jotai";
import {licensePlateAtom} from "../Component/SearchBar.jsx";


const stripePubKey = 'pk_test_51KtS1vIBvoqhBs9FLAT83yqmV0oLxIprG0qQuGaLfvKZcu3c9ZVZGATygdBDOkTjLxoFPsq06sqijzLJagg65YJ400GnDhe2av';
const PaymentBlock = () => {
    const [stripePromise] = useState(() => loadStripe(stripePubKey))
    const [clientSecret, setClientSecret] = useState("");
    const [licensePlate] = useAtom(licensePlateAtom);
    const options = {clientSecret};

    useEffect(() => {
        // Create a Checkout Session as soon as the page loads
        axios.post('http://localhost:8080/order/checkout', {
            licensePlate: licensePlate
        }, {
            headers: {
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept"
            }
        })
            .then(function (response) {
                setClientSecret(response.data.clientSecret);
            })
            .catch(function (error) {
                console.log('Error fetching data:', error);
            })
    }, []);

    return (

            <div id="checkout" className="checkout-page">
                {clientSecret && (
                    <EmbeddedCheckoutProvider
                        stripe={stripePromise}
                        options={options}
                    >
                        <div id="checkout" className="">
                            <EmbeddedCheckout/>
                        </div>
                    </EmbeddedCheckoutProvider>
                )}
            </div>

    );

}

export default PaymentBlock;