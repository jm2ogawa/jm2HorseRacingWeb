package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ConfigDao;
import com.example.demo.dto.Config;

/**
 * システム構成サービス
 */
@Service
public class ConfigService {

	private static final String CONST_CSV_ROAD_ON = "1";
	/**
	 * システム構成DAO
	 */
	@Autowired
	private ConfigDao configDao;

	/**
	 * システム構成IDに一致した設定をクラスで返却する
	 * @param key
	 * @return Config
	 */
	public Config getConfig(String id) {
		return configDao.getConfig(id);
	}

	/**
	 * システム構成IDに一致した設定値を文字列で返却する
	 * @param key
	 * @return Config
	 */
	public String getConfigOfString(String id) {
		return configDao.getConfig(id).getParam();
	}
	/**
	 * システム設定
	 * @param flag
	 * @return boolean
	 */
	public boolean checkCsvRoad(String flag) {
		return flag.equals(CONST_CSV_ROAD_ON) ? true : false;
	}

	/**
	 * レースCSVファイルの読み込み判定をチェックする
	 * @param flag
	 * @return boolean
	 */
	public int changeConfigAtId(Config config) {
		return configDao.updateConfig(config);
	}

}
