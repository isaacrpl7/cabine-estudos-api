package com.example.estudosapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.estudosapi.exceptions.ConflictException;
import com.example.estudosapi.exceptions.NotFoundException;
import com.example.estudosapi.model.Cabine;
import com.example.estudosapi.model.SalaEstudo;
import com.example.estudosapi.model.enums.EnumStatusCabine;
import com.example.estudosapi.repository.SalaEstudoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class SalaEstudoService {
    
    @Autowired
    private SalaEstudoRepository repository;

    @Autowired
    private CabineService cabineService;

    @PostConstruct
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private void preCadastro(){
        Cabine cabine = null;

        try {
            cabine = cabineService.findById(1L);
        } catch (Exception e) {
            cabine = new Cabine();
            cabine.setStatus(EnumStatusCabine.DISPONIVEL);
            
            SalaEstudo sala = new SalaEstudo();
            sala.setNome("LCC-2");
            sala.getCabines().add(cabine);
    
            cabine.setSalaEstudo(sala);
    
            repository.save(sala);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<SalaEstudo> findAll(){
        return repository.findAll();
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public SalaEstudo findById(Long id){
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("SalaEstudo não encontrada com este id."));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Cabine> listarCabines(Long id){
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("SalaEstudo não encontrada com este id."))
        .getCabines();
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SalaEstudo cadastrar(SalaEstudo entity){
        return repository.save(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SalaEstudo cadastrarCabine(Long idSala, Cabine entity){
        SalaEstudo sala = findById(idSala);

        entity.setSalaEstudo(sala);
        entity.setStatus(EnumStatusCabine.DISPONIVEL);
        sala.getCabines().add(entity);

        return repository.save(sala);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SalaEstudo adicionarCabine(Long idSala, Long idCabine){
        SalaEstudo sala = findById(idSala);
        Cabine cabine = cabineService.findById(idCabine);

        if(cabine.getSalaEstudo() != null)
            throw new ConflictException("A cabine informada já possui sala");

        cabine.setSalaEstudo(sala);
        sala.getCabines().add(cabine);
        return repository.save(sala);
    }
}
