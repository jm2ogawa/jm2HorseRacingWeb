package com.example.demo.common.utils;


import java.util.LinkedHashMap;
import java.util.Map;

import lombok.experimental.UtilityClass;
/**
 * 画面表示部品機能ユーティリティ
 * @author jm2og
 *
 */
@UtilityClass
public class ViewPartsUtil {

	/**
	 * 現在値更新画面のアクシデントプルダウンをマップで返却する
	 * @return
	 */
	public Map<String, String> getAcdPulDown(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("CODE_RESULT","入線");
		map.put("CODE_SCRATCH","除外");
		map.put("CODE_SAME","人気同値");
		return map;
	}
}
