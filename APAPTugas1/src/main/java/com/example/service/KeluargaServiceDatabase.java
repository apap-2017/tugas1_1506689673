package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService{
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		log.info("select keluarga with nkk {}", nkk);
		return keluargaMapper.selectKeluarga(nkk);
	}
	
	@Override
	public KeluargaModel selectKeluargaById(int id) {
		log.info("select keluarga with id {}", id);
		return keluargaMapper.selectKeluargaById(id);
	}
	
	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		log.info("add keluarga with nkk {}", keluarga.getNkk());
		keluargaMapper.addKeluarga(keluarga);
	}
	
	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		log.info("update keluarga with id", keluarga.getId());
		keluargaMapper.updateKeluarga(keluarga);
	}
	
	
}
