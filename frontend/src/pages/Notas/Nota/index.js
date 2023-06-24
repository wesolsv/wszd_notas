import { Link } from 'react-router-dom';

const Nota = () => {
    return (
        <div>
            <h1>Nota 1</h1>
            <Link to="/list-notas">Return to List</Link>
        </div>
    )
}

export default Nota