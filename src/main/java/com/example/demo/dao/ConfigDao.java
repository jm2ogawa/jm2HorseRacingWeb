package com.example.demo.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Config;
import com.example.demo.mapper.ConfigMapper;

/**
 * システム構成DAO
 */
@Repository
public class ConfigDao {

	/**
	 * システム構成マッパー
	 */
	@Autowired
	private ConfigMapper configMapper;

	/**
	 * システムキーに一致した設定を取得する
	 * @param key
	 * @return Map<String,String>
	 */
	public Map<String,String> getConfigMap(String key) {
		return configMapper.getConfigMap(key);
	}

	/**
	 * システムキーに一致した設定を取得する
	 * @param key
	 * @return Config
	 */
	public Config getConfig(String key) {
		return configMapper.getConfig(key);
	}

	/**
	 * システム構成の設定値を変更する
	 * @param Config
	 * @return
	 */
	public int updateConfig(Config config) {
		return configMapper.updateConfig(config);
	}
}
