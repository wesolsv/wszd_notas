import Nota from "./Nota"
import { Link } from 'react-router-dom';

const ListNotas = () => {
    return (
        <div>
            <h1>Listagem de notas</h1>
            <ul>
                <li>
                    <Link to="/list-notas/nota">Nota</Link>
                    <Nota />  
                </li>
            </ul>
        </div>
    )
}

export default ListNotas