import Header from "../Block/Header.jsx";
import Footer from "../Block/Footer.jsx";
import PropTypes from "prop-types";

const StandardPage = ({children}) => {
    return (
        <div className="flex flex-col min-h-screen bg-red-500">
            <Header/>
            <main className="flex-grow flex items-center justify-center bg-red-400">
                {children}
            </main>
            <Footer />
        </div>
    );
}

StandardPage.propTypes = {
    children: PropTypes.node,
}

export default StandardPage;