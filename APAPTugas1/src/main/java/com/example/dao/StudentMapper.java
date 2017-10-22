package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface StudentMapper
{
    @Select("select npm, name, gpa from student where npm = #{npm}")
    @Results(value = {
    		@Result(property="npm", column="npm"),
    		@Result(property="name", column="name"),
    		@Result(property="gpa", column="gpa"),
    		@Result(property="courses", column="npm",
		    		javaType = List.class,
		    		many=@Many(select="selectCourses"))
    })
    PendudukModel selectStudent (@Param("npm") String npm);

    @Select("select npm, name, gpa from student")
    @Results(value = {
    		@Result(property="npm", column="npm"),
    		@Result(property="name", column="name"),
    		@Result(property="courses", column="npm",
    				javaType = List.class,
    				many=@Many(select="selectCourses"))
    })
    List<PendudukModel> selectAllStudents ();

    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
    void addStudent (PendudukModel student);

    @Delete("DELETE FROM student WHERE npm = #{npm}")
    void deleteStudent (@Param("npm") String npm);

    @Update("UPDATE student SET name = #{name}, gpa = #{gpa} WHERE npm = #{npm}")
    void updateStudent (PendudukModel student);
    
    @Select("select course.id_course, name, credits " +
    		"from studentcourse join course " +
    		"on studentcourse.id_course = course.id_course " +
    		"where studentcourse.npm = #{npm}")
    List<KeluargaModel> selectCourses (@Param("npm") String npm);
}

