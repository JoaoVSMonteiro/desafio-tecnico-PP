package com.transferencia.desafio_tecnico.repository;

import com.transferencia.desafio_tecnico.model.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {


}
