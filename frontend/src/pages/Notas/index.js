import Nota from "./Nota"
import { Link } from 'react-router-dom';

const ListNotas = () => {
    return (
        <div>
            <h1>Listagem de notas</h1>
            <ul>
                <li>
                    <Nota />
                    <Link to="/list-notas/nota">Nota Teste</Link>
                </li>
            </ul>
        </div>
    )
}

export default ListNotas