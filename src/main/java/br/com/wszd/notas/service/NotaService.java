package br.com.wszd.notas.service;

import br.com.wszd.notas.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;
}

