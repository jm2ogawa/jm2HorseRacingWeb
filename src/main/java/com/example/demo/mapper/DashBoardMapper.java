package com.example.demo.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.PurchaseTargetInfo;
/**
 * ダッシュボード機能 Mapper
 */
@Mapper
public interface DashBoardMapper {
	/**
	 * 購入情報一覧取得
	 * @param
	 * @return 購入情報一覧
	 */
	List<PurchaseTargetInfo> list();
}
