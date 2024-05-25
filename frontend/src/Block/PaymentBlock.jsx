import {EmbeddedCheckout, EmbeddedCheckoutProvider} from "@stripe/react-stripe-js";
import {useState} from "react";
import {loadStripe} from "@stripe/stripe-js";

const stripePubKey = 'pk_test_51KtS1vIBvoqhBs9FLAT83yqmV0oLxIprG0qQuGaLfvKZcu3c9ZVZGATygdBDOkTjLxoFPsq06sqijzLJagg65YJ400GnDhe2av';
const PaymentBlock = () => {
    const [stripePromise, setStripePromise] = useState(() => loadStripe(stripePubKey))
    const [clientSecret, setClientSecret] = useState("");
    const options = {clientSecret};

    return (
        <div>
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
        </div>
    );

}

export default PaymentBlock;