import HeroBackgroundSection from "./HeroBackgroundSection.jsx";
import {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";

const ConfirmationBlock = () => {

    const navigate = useNavigate();
    const [status, setStatus] = useState(null);
    // const [customerEmail, setCustomerEmail] = useState('');

    useEffect(() => {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const sessionId = urlParams.get('session_id');

        fetch(`http://localhost:8080/order/session-status?session_id=${sessionId}`)
            .then((res) => res.json())
            .then((data) => {
                setStatus(data.status);
                // setCustomerEmail(data.customer_email);
            });
    }, []);

    if (status === 'open') {
        return (
            navigate('/order/checkout')
            // <Navigate to="'http://localhost:8080/order/checkout"/>
        )
    }
    if (status === 'complete') {
        return (
            <HeroBackgroundSection/>
            // TODO: add toast - https://flowbite.com/docs/components/toast/
        );
    }
    return null;
}

export default ConfirmationBlock ;