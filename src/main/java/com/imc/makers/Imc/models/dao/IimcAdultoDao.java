package com.imc.makers.Imc.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imc.makers.Imc.api.models.entity.ImcAdultos;

public interface IimcAdultoDao extends JpaRepository<ImcAdultos, Long> {
	
}
