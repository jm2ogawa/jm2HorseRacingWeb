package com.example.demo.common.maxautocalc.impl;

import java.util.List;

import com.example.demo.dto.TIntervalValues;

public interface WheelInterface {

	//基準値を生成する
	List<TIntervalValues> createTags(List<TIntervalValues> values, int consective);

	//基準値を比較する
	boolean compareTagValue(List<TIntervalValues> values, int idx, List<TIntervalValues> tags, int consective);
}
