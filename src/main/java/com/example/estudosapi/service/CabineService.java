package com.example.estudosapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.estudosapi.exceptions.BadRequestException;
import com.example.estudosapi.exceptions.ConflictException;
import com.example.estudosapi.exceptions.NotFoundException;
import com.example.estudosapi.model.Cabine;
import com.example.estudosapi.model.Reserva;
import com.example.estudosapi.model.dtos.CabineDTO;
import com.example.estudosapi.model.dtos.CabineStatusDTO;
import com.example.estudosapi.model.dtos.ReservaDTO;
import com.example.estudosapi.model.enums.EnumStatusCabine;
import com.example.estudosapi.repository.CabineRepository;
import com.example.estudosapi.repository.ReservaRepository;

@Service
public class CabineService {

    @Autowired
    private CabineRepository repository;

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Cabine> findAll(){
        return repository.findAll();
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public CabineDTO findByIdDTO(Long id){
        Optional<Cabine> cabinee = repository.findById(id);
        if(cabinee.isEmpty()){
            throw new NotFoundException("Cabine não encontrada com este id.");
        }
        CabineDTO dto = new CabineDTO();

        Cabine cabine = cabinee.get();

        dto.setStatus(cabine.getStatus());
        
        if(cabine.getStatus() == EnumStatusCabine.RESERVADA){

            for (Reserva reserva : cabine.getReservas()) {
                if(
                    (LocalDateTime.now().isAfter(reserva.getHorario()) || LocalDateTime.now().isEqual(reserva.getHorario()))
                    && 
                    (LocalDateTime.now().isBefore(reserva.getHorarioFinal()) || LocalDateTime.now().isEqual(reserva.getHorarioFinal()))
                ){
                    
                    dto.setHorarioInicial(reserva.getHorario());
                    dto.setHorarioFinal(reserva.getHorarioFinal());
                }
                    
            }
        }
        
        return dto;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Cabine findById(Long id){
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Cabine não encontrada com este id."));
        
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Cabine cadastrar(Cabine entity){
        entity.setStatus(EnumStatusCabine.DISPONIVEL);
        return repository.save(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Cabine update(Cabine entity){
        return repository.save(entity);
    }

    public Cabine modificarStatus(Long id, CabineStatusDTO dto){
        Cabine cabine = findById(id);

        EnumStatusCabine statusEnviado = dto.getStatus();

        if(statusEnviado.equals(EnumStatusCabine.DISPONIVEL)){
            return liberar(cabine);
        }
        else if(statusEnviado.equals(EnumStatusCabine.RESERVADA)){
            return reservar(cabine, dto.getHorarioReserva());
        }
        else if(statusEnviado.equals(EnumStatusCabine.OCUPADA)){
            return ocupar(cabine);
        }
        else
            throw new BadRequestException("Status inválido.");
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private Cabine reservar(Cabine cabine, LocalDateTime horario){

        if(cabine.getStatus() != EnumStatusCabine.DISPONIVEL)
            throw new ConflictException("A cabine não está disponível para reserva.");
        
        if(horario == null)
            throw new BadRequestException("O horário informado é inválido (null)");

        cabine.setHorarioReserva(horario);
        cabine.setStatus(EnumStatusCabine.RESERVADA);
        return update(cabine);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private Cabine liberar(Cabine cabine){

        if(cabine.getStatus() == EnumStatusCabine.DISPONIVEL)
            throw new ConflictException("A cabine já está disponível.");
        
        cabine.setStatus(EnumStatusCabine.DISPONIVEL);
        return update(cabine);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private Cabine ocupar(Cabine cabine){

        if(cabine.getStatus() == EnumStatusCabine.RESERVADA)
            throw new ConflictException("Esta cabine não pode ser ocupada, pois está reservada.");
        if(cabine.getStatus() == EnumStatusCabine.OCUPADA)
            throw new ConflictException("Esta já está ocupada.");
        
        cabine.setStatus(EnumStatusCabine.OCUPADA);
        return update(cabine);
    }

    public ReservaDTO obterProximaReserva(Long id){
        Cabine cabine = findById(id);
        ReservaDTO dto = new ReservaDTO();

        int size = cabine.getReservas().size();
        if(size == 0)
            return dto;

        Reserva proximaReserva = cabine.getReservas().get(size-1);
        if( proximaReserva.getHorario() != null 
            && proximaReserva.getHorario().plusMinutes(30).isBefore(LocalDateTime.now()))
            return dto;

        for (Reserva reserva : cabine.getReservas()) {
            if(reserva.getHorario() != null
                && reserva.getHorario().isAfter(LocalDateTime.now())){
                dto.setHorario(reserva.getHorario());
                break;
            }
        }
        if(dto.getHorario() == null)
            return dto;

        dto.setUsuario(proximaReserva.getUsuario());
        return dto;
    }

    public List<ReservaDTO> listarReservas(Long id){
        Cabine cabine = findById(id);

        List<Reserva> reservas = cabine.getReservas();
        
        return reservas.stream()
                        .map(x -> new ReservaDTO(x.getHorario(), x.getUsuario()))
                        .collect(Collectors.toList());
    }


    @Scheduled(fixedDelay = 3000)
    @Transactional(readOnly = false)
    protected void fun(){
        Optional<Cabine> a = repository.findById(1L);

        if(a.isEmpty())
            return;

        Cabine cabine = a.get();
        System.out.println("Verificando reservas...");
        for (Reserva reserva : cabine.getReservas()) {

            if( LocalDateTime.now().isBefore(reserva.getHorario())
            &&  LocalDateTime.now().isAfter(reserva.getHorario().minusSeconds(10))){

                cabine.setStatus(EnumStatusCabine.RESERVADA);
            }

        }

        repository.save(cabine);
    }

    @Transactional(readOnly = false)
    public Cabine limparReservas(Long id){
        Cabine cabine = findById(id);
        reservaRepository.deleteAllByCabineId(id);
        cabine.getReservas().clear();

        System.out.println(cabine.getReservas().size());
        return repository.save(cabine);
    }
}
