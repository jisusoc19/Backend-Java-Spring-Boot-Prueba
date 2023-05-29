package com.imc.makers.Imc.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imc.makers.Imc.api.models.entity.ImcAdultos;
import com.imc.makers.Imc.models.dao.IimcAdultoDao;

@Service
public class ImcAdultoServiceImplement implements IimcAdultoService {
	@Autowired
	private IimcAdultoDao imcAdultodao;
	@Override
	@Transactional(readOnly = true)
	public List<ImcAdultos> findAll() {
		// TODO Auto-generated method stub
		return (List<ImcAdultos>) imcAdultodao.findAll();
	}
	@Override
	@Transactional
	public ImcAdultos save(ImcAdultos imcAdultos) {
		// TODO Auto-generated method stub
		return imcAdultodao.save(imcAdultos);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		imcAdultodao.deleteById(id);
		
	}
	@Override
	@Transactional(readOnly = true)
	public ImcAdultos findById(Long id) {
		// TODO Auto-generated method stub
		return imcAdultodao.findById(id).orElse(null);
	}
	@Override
	@Transactional(readOnly = true)
	public Page<ImcAdultos> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return imcAdultodao.findAll(pageable);
	}

}
