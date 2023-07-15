package com.example.estudosapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.estudosapi.exceptions.BadRequestException;
import com.example.estudosapi.exceptions.ConflictException;
import com.example.estudosapi.exceptions.NotFoundException;
import com.example.estudosapi.model.Cabine;
import com.example.estudosapi.model.Reserva;
import com.example.estudosapi.model.SalaEstudo;
import com.example.estudosapi.model.Usuario;
import com.example.estudosapi.model.dtos.ReservarCabineDTO;
import com.example.estudosapi.model.enums.EnumStatusCabine;
import com.example.estudosapi.repository.ReservaRepository;
import com.example.estudosapi.repository.SalaEstudoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class SalaEstudoService {
    
    @Autowired
    private SalaEstudoRepository repository;

    @Autowired
    private CabineService cabineService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ReservaRepository reservaRepository;

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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SalaEstudo reservarCabine(Long idSala, Long idCabine, ReservarCabineDTO dto){
        SalaEstudo sala = findById(idSala);
        Cabine cabine = null; //cabineService.findById(idCabine);

        for (Cabine cabineSala : sala.getCabines()) {
            if(cabineSala.getId().equals(idCabine))
                cabine = cabineSala;
        }

        if(cabine == null)
            throw new NotFoundException("Cabine de estudo não encontrada com este id.");

    
        List<Reserva> reservas = cabine.getReservas();
        if(reservas.size() != 0){
            if(dto.getHorario().isBefore(LocalDateTime.now())){
                throw new BadRequestException("Horário já passou.");
            }

            Reserva ultimaReserva = reservas.get(reservas.size()-1);
            if(ultimaReserva.getHorario() != null && ultimaReserva.getHorario().plusMinutes(30).isAfter(dto.getHorario())){
                throw new ConflictException("A cabine informada já foi reservada. Tente outro horário");
            }
        }
        
        Usuario usuario = usuarioService.findByEmail(dto.getUsuarioEmail());

        Reserva reserva = new Reserva();
        reserva.setCabine(cabine);
        reserva.setUsuario(usuario);
        reserva.setHorario(dto.getHorario());
        reserva = reservaRepository.save(reserva);

        usuario.getReservas().add(reserva);
        usuarioService.save(usuario); //Atualizar usuario

        cabine.setStatus(EnumStatusCabine.RESERVADA);
        cabine.setHorarioReserva(dto.getHorario());
        cabine.getReservas().add(cabine.getReservas().size(), reserva);

        return repository.save(sala); //Atualizacao na sala de estudo faz cascading na cabine
    }
}
