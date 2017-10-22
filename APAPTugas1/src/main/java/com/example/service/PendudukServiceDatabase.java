package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService{
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info("get penduduk with nik {}", nik);
		return pendudukMapper.selectPenduduk(nik);
	}

	@Override
	public List<PendudukModel> selectPendudukByKeluarga(int id_keluarga) {
		log.info("select anggota keluarga with id_keluarga{}", id_keluarga);
		return pendudukMapper.selectPendudukByKeluarga(id_keluarga);
	}
	
	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info("add penduduk with nik {}", penduduk.getNik());
		pendudukMapper.addPenduduk(penduduk);
	}
	
	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		log.info("update penduduk with id {}", penduduk.getId());
		pendudukMapper.updatePenduduk(penduduk);
	}
	
	@Override
	public List<PendudukModel> selectPendudukByIdKelurahan(String id_kelurahan) {
		return pendudukMapper.selectPendudukByIdKelurahan(id_kelurahan);
	}
	
	@Override
	public void setWafatPenduduk(PendudukModel penduduk) {
		
		pendudukMapper.setWafatPenduduk(penduduk.getNik());
		
		int id_keluarga = penduduk.getId_keluarga();
		
		List<PendudukModel> anggotaKeluarga = pendudukMapper.selectPendudukByKeluarga(id_keluarga);
		
		
		int count = 0;
		
		log.info("anggota keluarga size {}", anggotaKeluarga.size());
		
		for(int i = 0; i < anggotaKeluarga.size(); i++) {
			int temp = (anggotaKeluarga.get(i).getIs_wafat());
			if(temp == 1) {
				count++;
			}
		}
		
		log.info("" + count);
		
		if (count == anggotaKeluarga.size()) {
			keluargaMapper.setTidakBerlaku(id_keluarga + "");
		}
	}
}
