package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import com.example.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("SELECT * FROM kota WHERE id=#{id}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKota", column="kode_kota"),
			@Result(property="namaKota", column="nama_kota")
	})
	KotaModel selectKota(@Param(value="id") int id);
	
	@Select("SELECT * FROM kota")
    List<KotaModel> selectAllKota();
}
