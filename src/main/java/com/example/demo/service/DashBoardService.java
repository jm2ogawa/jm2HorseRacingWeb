package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PurchaseTargetInfo;
import com.example.demo.dto.master.TRelations;
import com.example.demo.form.dashboard.DashBoardCheckRequestForm;
import com.example.demo.mapper.DashBoardMapper;
import com.example.demo.mapper.TRelationsMapper;

/**
 * ダッシュボード機能 Service
 */
@Service
public class DashBoardService {

	/**
	 * ダッシュボード機能 Mapper
	 */
	@Autowired
	private DashBoardMapper dashBoardMapper;

	/**
	 * リレーションテーブル Mapper
	 */
	@Autowired
	private TRelationsMapper relationsMapper;

	/**
	 * 購入対象一覧取得
	 * @param
	 * @return 検索結果
	 */
	public List<PurchaseTargetInfo> list() {
	    return dashBoardMapper.list();
	}

	/**
	 * レース当日の行データが更新されたかチェックする
	 * @param
	 * @return 検索結果
	 */
	public String check(DashBoardCheckRequestForm requestForm) throws IndexOutOfBoundsException{
		List<TRelations> relations = relationsMapper.selectRelationsByRacedayOfCheck(requestForm);
		if(relations == null){
			return "0";
		}
		if(relations.size() == 0) {
			return "0";
		}
		if(relations.size() > 1){
			throw new IndexOutOfBoundsException();
		}
	    return relations.get(0).getRownum();
	}

	/**
	 * リレーションテーブル情報をリストで取得する
	 * @param
	 * @return 検索結果
	 */
	public List<TRelations> getRelationsList(DashBoardCheckRequestForm requestForm) {
	    return relationsMapper.selectRelationsByRacedayOfCheck(requestForm);
	}
}
