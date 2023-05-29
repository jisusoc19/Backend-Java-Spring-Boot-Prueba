package com.imc.makers.Imc.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imc.makers.Imc.api.models.entity.ImcAdultos;

public interface IimcAdultoService {
	public List<ImcAdultos> findAll();
	public Page<ImcAdultos> findAll(Pageable pageable);
	public ImcAdultos save( ImcAdultos imcAdultos);
	public void delete(Long id);
	public ImcAdultos findById(Long id);

}
