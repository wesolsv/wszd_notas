import React from 'react';
import logoImage from '../../assets/img/logonotes.svg' // Caminho para o arquivo de imagem da logo

const Logo = () => {
  return (
    <div>
      {<img src={logoImage} alt="Logo" />}
    </div>
  );
};

export default Logo;
