package com.ksi.consultoria.repository;

import com.ksi.consultoria.models.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    @Query("SELECT COUNT(f) > 0 FROM Filme f WHERE f.Title = ?1")
    Boolean existsByTitulo(String title);
}
