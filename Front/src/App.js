import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Transferir from "./pages/Transferir";
import Transferencia from "./pages/Tranferencia";
import Home from "./pages/Home";
import Deposito from "./pages/Deposito";
import Saque from "./pages/Saque";
import Limite from "./pages/Limite";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/Cadastrar" element={<Transferir />} />
        <Route path="/Transferencia" element={<Transferencia />} />
        <Route path="/Depositar" element={<Deposito />} />
        <Route path="/Sacar" element={<Saque />} />
        <Route path="/AlterarLimite" element={<Limite />} />
      </Routes>
    </Router>
  );
}
