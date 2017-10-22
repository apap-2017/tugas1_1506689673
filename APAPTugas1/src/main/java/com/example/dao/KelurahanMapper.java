package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("SELECT * FROM kelurahan WHERE id=#{id}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="id_kecamatan", column="id_kecamatan"),
			@Result(property="kode_kelurahan", column="kode_kelurahan"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="kode_pos", column="kode_pos")
	})
	KelurahanModel selectKelurahan(@Param("id") int id);
	
	@Select("SELECT * FROM kelurahan JOIN kecamatan JOIN KOTA ON kelurahan.id_kecamatan = kecamatan.id AND kota.id = kecamatan.id_kota")
    @Results(value = {
    		@Result(property="id", column="kelurahan.id")
    })
    List<KelurahanModel> selectAllKelurahan();
	
	@Select("SELECT * FROM kelurahan WHERE id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectKelurahanByIdKecamatan (@Param("id_kecamatan") String id_kecamatan);

}
