import {EmbeddedCheckout, EmbeddedCheckoutProvider} from "@stripe/react-stripe-js";
import {useState} from "react";
import {loadStripe} from "@stripe/stripe-js";
import {useAtom} from "jotai";
import {clientSecretAtom} from "../Component/Atoms.jsx";


const stripePubKey = 'pk_test_51KtS1vIBvoqhBs9FLAT83yqmV0oLxIprG0qQuGaLfvKZcu3c9ZVZGATygdBDOkTjLxoFPsq06sqijzLJagg65YJ400GnDhe2av';
const PaymentBlock = () => {
    const [stripePromise] = useState(() => loadStripe(stripePubKey))
    const [clientSecret] = useAtom(clientSecretAtom);
    const options = {clientSecret};

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