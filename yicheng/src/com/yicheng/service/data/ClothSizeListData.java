package com.yicheng.service.data;

import java.io.Serializable;
import java.util.List;

import com.yicheng.pojo.ClothSize;
import com.yicheng.util.Utils;

public class ClothSizeListData implements Serializable {

	private static final long serialVersionUID = 831399706980451721L;
	
	private int clothId;
	private int clothColorId;
	
	private List<ClothSize> clothSizes;
	private Integer totalCount;
	
	public int getClothId() {
		return clothId;
	}
	public void setClothId(int clothId) {
		this.clothId = clothId;
	}
	public List<ClothSize> getClothSizes() {
		return clothSizes;
	}
	public int getClothColorId() {
		return clothColorId;
	}

	public void setClothColorId(int clothColorId) {
		this.clothColorId = clothColorId;
	}

	public void setClothSizes(List<ClothSize> clothSizes) {
		this.clothSizes = clothSizes;
	}

	public Integer getTotalCount() {
		if(null == totalCount) {
			totalCount = 0;
			if(null != clothSizes && !clothSizes.isEmpty()) {
				for(ClothSize clothSize : clothSizes) {
					totalCount += clothSize.getCount();
				}
			}
		}
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public ClothSizeListData() {}
	
	public ClothSizeListData(int clothId, int clothColorId, List<ClothSize> clothSizes) {
		this.clothId = clothId;
		this.clothColorId = clothColorId;
		
		if(null != clothSizes) {
			this.clothSizes = Utils.copyFromList(clothSizes);
		}
		
		
	}

}
