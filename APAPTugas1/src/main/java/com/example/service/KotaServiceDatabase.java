package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KotaMapper;
import com.example.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService{
	@Autowired
	KotaMapper kotaMapper;
	
	@Override
	public KotaModel selectKota(int id) {
		log.info("select kota with id {}", id);
		return kotaMapper.selectKota(id);
	}
	
	@Override
	public List<KotaModel> selectAllKota() {
		return kotaMapper.selectAllKota();
	}
}
