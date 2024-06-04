import HeroBackgroundSection from "./HeroBackgroundSection.jsx";
import {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import {useAtom} from "jotai";
import axios from "axios";
import * as atoms from "../Component/Atoms.jsx";
import api from "../Component/API.js";


const ConfirmationBlock = () => {
    const navigate = useNavigate();
    const [status, setStatus] = useState(null);
    const [isPaid,setIsPaid] = useAtom(atoms.isPaidAtom);
    const [customerEmail, setCustomerEmail] = useState('');
    const [clientSecret] = useAtom(atoms.clientSecretAtom);


    useEffect(() => {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const sessionId = urlParams.get('session_id');

        api.get(`/order/session-status?session_id=${sessionId}`)
            .then((response) => {
                setStatus(response.data.status);
                setCustomerEmail(response.data.customerEmail)
                setIsPaid(response.data.isPaid)
            })


    }, []);

    if (status === "open") {
      return navigate("/order/checkout");
    }
    if (status === 'complete') {
        return (
            <HeroBackgroundSection
                customerEmail={customerEmail}
            />
        );
    }
    return null;
}

export default ConfirmationBlock ;

