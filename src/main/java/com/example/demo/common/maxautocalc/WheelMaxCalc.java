package com.example.demo.common.maxautocalc;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.TIntervalValues;

public class WheelMaxCalc {
	List<TIntervalValues> maxList;
	List<TIntervalValues> stepdownList;
	TIntervalValues tiv;
	TIntervalValues maxTiv;
	TIntervalValues stepdownTiv;
	int index;
	int tag;
	int sum;

	//基準値を生成する
	public int createTags(List<TIntervalValues> values, int type) {
		tag = 0;
		for(int c=type;c>0;c--) {
			tag = tag + values.get(values.size()-c).getIntervalValue();
		}
		return tag;
	}

	//間隔値リストの輪を返却する
	public int getSum(List<TIntervalValues> values, int type, int index) {
		sum = 0;
		for(int c=0; c<type; c++) {
			sum = sum + values.get(index-c).getIntervalValue();
		}
		return sum;
	}

	//基準値より大きい間隔値をMAX値リストにして返却する
	public List<TIntervalValues> createMaxValueList(List<TIntervalValues> values, int tags, int type){
		maxList = new ArrayList<TIntervalValues>();
		for(int idx=values.size();idx>=type;idx--) {
			if(getSum(values, idx-1, type) >= tags) {
				maxList.add(values.get(idx));
			}
		}
		return maxList;
	}

	//MAX値リストから最も大きい値を返却する
	public TIntervalValues getMaxValueInList(List<TIntervalValues> maxList) {
		maxTiv = new TIntervalValues();
		for(TIntervalValues tiv : maxList) {
			if(tiv.getIntervalValue() > maxTiv.getIntervalValue()) {
				maxTiv = tiv;
			}
		}
		return maxTiv;
	}

	//段下げのMAX値を生成する
	public TIntervalValues createStepDownMaxValue(List<TIntervalValues> values, List<TIntervalValues> tags, int stepdown, int tag){
		return null;
	}

	//基準値の終了判定を行う
	public boolean jugdeTags(List<TIntervalValues> tags, int consective){
		return false;
	}

	//基準値を0未満にならないように減算する
	public int subTag(int tag){
		return 0;
	}

	//段下げリストからMAX値を返却する
	public TIntervalValues getMaxValueInStepdownList(List<TIntervalValues> stepdownList, int stepdown){
		return null;
	}
}
