import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LandingPage from "./Page/LandingPage.jsx";
import PaymentPage from "./Page/PaymentPage.jsx";
import ConfirmationPage from "./Page/ConfirmationPage.jsx";
import './index.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage/>} />
        <Route path="/order/checkout" element={<PaymentPage/>} />
        <Route path="/confirmation" element={<ConfirmationPage/>} />
      </Routes>
    </Router>
  );
}

export default App;
