package br.com.wszd.notas.service;

import br.com.wszd.notas.model.Logs;
import br.com.wszd.notas.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogsService {

    @Autowired
    private LogsRepository repository;

    public void salvarLog(Logs logs){
        repository.save(logs);
    }

}
