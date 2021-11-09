package com.example.demo.common.maxautocalc;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.common.maxautocalc.impl.MaxCalcInterface;
import com.example.demo.dto.TIntervalValues;

/**
 * カウント制にしたハマリ次の計算クラス
 * @author jm2og
 *
 */
public class FitConsectiveMaxCalc implements MaxCalcInterface {
	List<TIntervalValues> tags;
	List<TIntervalValues> maxList;
	List<TIntervalValues> stepdownList;
	TIntervalValues tiv;
	TIntervalValues maxTiv;
	TIntervalValues stepdownTiv;
	int index;

	//基準値を生成する
	public List<TIntervalValues> createTags(List<TIntervalValues> values, int consective) {
		tags = new ArrayList<TIntervalValues>();
		for(int c = 0; c<consective; c++) {
			tags.add(values.get(values.size()-(c+consective)));
		}
		return tags;
	}

	//基準値を比較する
	public boolean compareTagValue(List<TIntervalValues> values, int idx, List<TIntervalValues> tags, int consective) {
		if(values.get(idx-1).getIntervalValue() > tags.get(consective-1).getIntervalValue()) {
			return true;
		}
		return false;
	}

	//基準値より大きい間隔値をMAX値リストにして返却する
	public List<TIntervalValues> createMaxValueList(List<TIntervalValues> values, List<TIntervalValues> tags, int consective) {
		maxList = new ArrayList<TIntervalValues>();
		for(int idx=values.size();idx>=consective;idx--) {
			if(compareTagValue(values, idx-1, tags, consective)) {
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
	public TIntervalValues createStepDownMaxValue(List<TIntervalValues> values, List<TIntervalValues> tags, int consective, int stepdown) {
		stepdownList = new ArrayList<TIntervalValues>();
		maxTiv = null;
		while(jugdeTags(tags, consective)) {
			//MAX値リストを生成
			maxList = createMaxValueList(values, tags, consective);
			//MAX値リストから最も大きい値を抽出する
			maxTiv = getMaxValueInList(maxList);
			//最も大きい値を段下げリストに保存する
			stepdownList.add(maxTiv);
			//段下げリストからMAX値を取る
			stepdownTiv = getMaxValueInStepdownList(stepdownList ,stepdown);
			if(maxTiv.getIntervalValue() == stepdownTiv.getIntervalValue()) {
				break;
			}
			//ハマリ次の基準値tag1を下げる
			tags.get(consective-1).setIntervalValue(
					subTag(tags.get(consective-1).getIntervalValue()));
		}
		return maxTiv;
	}

	//基準値の終了判定を行う
	public boolean jugdeTags(List<TIntervalValues> tags, int consective) {
		if(tags.get(consective-1).getIntervalValue() >= 0) {
			return true;
		}
		return false;
	}

	//基準値を0未満にならないように減算する
	public int subTag(int tag) {
		if(tag - 1 < 0) {
			return 0;
		}
		return tag - 1;
	}

	//段下げリストからMAX値を返却する
	public TIntervalValues getMaxValueInStepdownList(List<TIntervalValues> stepdownList, int stepdown) {
		index = 0;
		while(stepdown > 0) {
			for(int c=0; c+1 < stepdownList.size(); c++) {
				if(stepdownList.get(c).getIntervalValue() != stepdownList.get(c+1).getIntervalValue()) {
					stepdown--;
					index = c;
					break;
				}
			}
		}
		return stepdownList.get(index+1);
	}
}
