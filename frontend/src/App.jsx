import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LandingPage from "./Page/LandingPage.jsx";
import PaymentPage from "./Page/PaymentPage.jsx";
import "./App.css";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" exact component={LandingPage} />
        <Route path="/order/checkout" exact component={PaymentPage} />
      </Routes>
    </Router>
  );
}

export default App;
