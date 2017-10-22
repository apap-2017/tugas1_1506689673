package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("SELECT * FROM kecamatan WHERE id = #{id}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="id_kota", column="id_kota"),
			@Result(property="nama_kecamatan", column="nama_kecamatan"),
			@Result(property="kode_kecamatan", column="kode_kecamatan")	
	})
	KecamatanModel selectKecamatan(@Param(value="id") int id);
	
	@Select("SELECT * FROM kecamatan")
    List<KecamatanModel> selectAllKecamatan();
	
	@Select("select * from kecamatan where id_kota = #{id_kota}")
	List<KecamatanModel> selectKecamatanByIdKota (@Param("id_kota") int id_kota);
}
