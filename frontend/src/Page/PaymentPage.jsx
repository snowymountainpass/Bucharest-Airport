// import StandardPage from "./StandardPage.jsx";
import PaymentBlock from "../Block/PaymentBlock.jsx";
import Header from "../Block/Header.jsx";
import Footer from "../Block/Footer.jsx";

const PaymentPage = () => {
    return (
        <div className="flex flex-col  bg-red-500">
            <Header/>
            <PaymentBlock/>
            <Footer />
        </div>
    );
}

export default PaymentPage;