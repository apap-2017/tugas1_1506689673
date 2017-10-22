package com.example.model;

import com.example.service.KeluargaService;
import com.example.service.PendudukService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendudukModel
{
	private int id;
	private String nik;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private int jenis_kelamin;
	private int is_wni;
	private int id_keluarga;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private int is_wafat;
	
	@Builder.Default private String alamat = "Alamat Null";
	@Builder.Default private String rt = "00";
	@Builder.Default private String rw = "00";
	@Builder.Default private String nama_kelurahan = "Kelurahan Null";
	@Builder.Default private String nama_kecamatan = "Kecamatan Null";
	@Builder.Default private String nama_kota = "Kota Null";
	@Builder.Default private String kode_pos = "00000";
	
	public void generateNik(KeluargaService keluargaDAO, PendudukService pendudukDAO) {
		String[] split = this.getTanggal_lahir().split("-");
		String tahun = split[0];
		String tahunLahir = tahun.substring(2);
		String bulanLahir = split[1];
		String tanggalLahir = split[2];
		
		KeluargaModel keluarga = keluargaDAO.selectKeluargaById(this.getId_keluarga());
		
		int gender = this.getJenis_kelamin();
		
		if(gender == 1) {
			int temp = Integer.parseInt(tanggalLahir);
			temp += 40;
			tanggalLahir = temp + "";
		}
		
		long nik = Long.parseLong(keluarga.getNkk().substring(0, 6) + tanggalLahir + bulanLahir + tahunLahir + "0001");
		
		while (true) {
			PendudukModel checker = pendudukDAO.selectPenduduk("" + nik);
			if (checker != null) {
				nik++;
			} else {
				break;
			}
		}
		
		this.setNik("" + nik);
	}

}
