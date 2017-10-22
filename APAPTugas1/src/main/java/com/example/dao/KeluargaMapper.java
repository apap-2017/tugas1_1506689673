package com.example.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	
	@Select("SELECT * FROM keluarga WHERE nomor_kk = #{nkk}")
	@Results(value = {
			@Result(property="nkk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="rt"),
			@Result(property="rw", column="rw"),
			@Result(property="id_kelurahan", column="id_kelurahan"),
			@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
			@Result(property="id", column="id"),
			@Result(property="anggota_keluarga", column="id", javaType = List.class, many = @Many(select = "selectAnggotaKeluarga"))
	})
	KeluargaModel selectKeluarga(@Param(value="nkk") String nkk);
	
	@Select("SELECT * FROM keluarga WHERE id=#{id}")
	KeluargaModel selectKeluargaById(@Param("id") int id);
	
	@Select("SELECT * FROM penduduk WHERE id_keluarga = #{id_keluarga}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="nik", column="nik"),
			@Result(property="nama", column="nama"),
			@Result(property="tempat_lahir", column="tempat_lahir"),
			@Result(property="tanggal_lahir", column="tanggal_lahir"),
			@Result(property="jenis_kelamin", column="jenis_kelamin"),
			@Result(property="is_wni", column="is_wni"),
			@Result(property="id_keluarga", column="id_keluarga"),
			@Result(property="agama", column="agama"),
			@Result(property="pekerjaan", column="pekerjaan"),
			@Result(property="status_perkawinan", column="status_perkawinan"),
			@Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
			@Result(property="golongan_darah", column="golongan_darah"),
			@Result(property="is_wafat", column="is_wafat")
	})
	List<PendudukModel> selectAnggotaKeluarga(@Param("id_keluarga") int id_keluarga);
	
	@Insert("INSERT INTO keluarga (alamat, nomor_kk, rt, rw, id_kelurahan, is_tidak_berlaku)"
    		+ " VALUES ('${alamat}', '${nomor_kk}', '${rt}', '${rw}', ${id_kelurahan}, 0)")
    void addKeluarga (KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET alamat = #{alamat}, nomor_kk = #{nomor_kk}, rt = #{rt}, rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} "
			+ "WHERE id = #{id}")
    void updateKeluarga (KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET is_tidak_berlaku = 1 where id = #{id}")
	void setTidakBerlaku(@Param("id") String id);
}
