package com.example.service;

import java.util.List;

import com.example.model.KelurahanModel;

public interface KelurahanService {
	KelurahanModel selectKelurahan(int id);
	
	List<KelurahanModel> selectAllKelurahan();
	
	List<KelurahanModel> selectKelurahanByIdKecamatan(String id_kecamatan);

}
