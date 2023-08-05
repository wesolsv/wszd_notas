import { api } from "../../services/apiClient";
import { useState, useEffect } from 'react';

interface ListItem {
  id: number;
  nome: string;
  categoriaNome: string;
}

interface ListProps {
  items?: ListItem[]; // Não é necessário definir as propriedades obrigatórias
}

export default function ListComponent({ items }: ListProps) {
  
  const [notasLista, setNotasLista] = useState<ListItem[]>([]); 

  useEffect(() => {
    async function fetchNotas() {
      try {
        const response = await api.get('/nota');
        setNotasLista(response.data);
      } catch (error) {
        console.error('Erro ao carregar notas:', error);
      }
    }

    if (!items) {
      fetchNotas();
    } else {
      setNotasLista(items);
    }
  }, [items]);

  return (
    <div className="list-component">
      <div className="list-title">TESTEEEEEEEEE</div>
      {notasLista.map((item, index) => (
        <div className="list-item" key={index}>
          <h4>{item.nome}</h4>
          <span>{item.categoriaNome}</span>
        </div>
      ))}
    </div>
  );
}
