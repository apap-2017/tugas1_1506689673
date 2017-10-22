package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;

public interface KecamatanService {
	KecamatanModel selectKecamatan(int id);
	
	List<KecamatanModel> selectAllKecamatan();
	
	List<KecamatanModel> selectKecamatanByIdKota(int id_kota);

}
