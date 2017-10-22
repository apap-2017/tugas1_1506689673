package com.example.service;

import java.util.List;

import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	
	void updatePenduduk(PendudukModel penduduk);
	
	List<PendudukModel> selectPendudukByKeluarga(int id_keluarga);
	
	void setWafatPenduduk(PendudukModel penduduk);
	
	List<PendudukModel> selectPendudukByIdKelurahan(String id_kelurahan);

}
