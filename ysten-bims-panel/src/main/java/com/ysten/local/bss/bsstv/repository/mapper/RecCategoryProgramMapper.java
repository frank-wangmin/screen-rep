package com.ysten.local.bss.bsstv.repository.mapper;

import com.ysten.local.bss.bsstv.domain.RecCategoryProgram;

public interface RecCategoryProgramMapper {



	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rec_category_program
	 * @mbggenerated  Wed Oct 15 14:22:49 CST 2014
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rec_category_program
	 * @mbggenerated  Wed Oct 15 14:22:49 CST 2014
	 */
	int insert(RecCategoryProgram record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rec_category_program
	 * @mbggenerated  Wed Oct 15 14:22:49 CST 2014
	 */
	int insertSelective(RecCategoryProgram record);


	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rec_category_program
	 * @mbggenerated  Wed Oct 15 14:22:49 CST 2014
	 */
	RecCategoryProgram selectByPrimaryKey(Long id);



	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rec_category_program
	 * @mbggenerated  Wed Oct 15 14:22:49 CST 2014
	 */
	int updateByPrimaryKeySelective(RecCategoryProgram record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rec_category_program
	 * @mbggenerated  Wed Oct 15 14:22:49 CST 2014
	 */
	int updateByPrimaryKey(RecCategoryProgram record);
}