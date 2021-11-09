package com.example.demo.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.Config;
/**
 * コンフィグ機能マッパー
 */
@Mapper
public interface ConfigMapper {

	/**
	 * システム構成を取り出す
	 * @param key
	 * @return Map<String,String>
	 */
	Map<String,String> getConfigMap(String id);

	/**
	 * システム構成を取り出す
	 * @param key
	 * @return Map<String,String>
	 */
	Config getConfig(String id);

	/**
	 * システム構成の設定値を変更する
	 * @param Config
	 * @return
	 */
	int updateConfig(Config config);
}
