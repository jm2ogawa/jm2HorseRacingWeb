package com.example.demo.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.master.TRelations;
import com.example.demo.form.DataViewRequestForm;
import com.example.demo.form.dashboard.DashBoardCheckRequestForm;
/**
 * リレーションテーブルマッパー
 */
@Mapper
public interface TRelationsMapper {

	/**
	 * リレーションテーブルにレコードを登録する
	 * @param THistories
	 * @return
	 */
	int insertRelations(@Param("tr")TRelations tr);

	/**
	 * リレーションテーブルを削除フラグを立てて更新する
	 * @param THistories
	 * @return
	 */
	int updateRelations(@Param("tr")TRelations tr);

	/**
	 * リレーションテーブルから当日とレース番号を条件にレコードを取得する
	 * @param
	 * @return
	 */
	List<TRelations> selectRelationsByRacedayOfCheck(@Param("req")DashBoardCheckRequestForm requestForm);

	/**
	 * リレーションテーブルから当日とレース番号を条件にレコードを取得する
	 * @param
	 * @return
	 */
	List<TRelations> selectRelationsByRacedayOfRegister(@Param("req")DataViewRequestForm requestForm);
}
