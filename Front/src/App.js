import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Transferir from "./pages/Transferir";
import Home from "./pages/Home";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/Cadastrar" element={<Transferir />} />
      </Routes>
    </Router>
  );
}
