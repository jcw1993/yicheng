package com.yicheng.pojo;

import com.yicheng.dao.ClothSizeDao;


public class ClothSize {
	private int id;
	private int clothId;
	private int clothColorId;
	private int sizeType;
	private int count;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClothId() {
		return clothId;
	}
	public void setClothId(int clothId) {
		this.clothId = clothId;
	}
	public int getClothColorId() {
		return clothColorId;
	}
	public void setClothColorId(int clothColorId) {
		this.clothColorId = clothColorId;
	}
	public int getSizeType() {
		return sizeType;
	}
	public void setSizeType(int sizeType) {
		this.sizeType = sizeType;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public ClothSize() {}
	
	public ClothSize(int id) {
		this.id = id;
	}

	public ClothSize(int id, int clothId, int clothColorId, int sizeType, int count) {
		this.id = id;
		this.clothId = clothId;
		this.clothColorId = clothColorId;
		this.sizeType = sizeType;
		this.count = count;
	}
	
	public ClothSize(ClothSize clothSize) {
		id = clothSize.getId();
		clothId = clothSize.getClothId();
		clothColorId = clothSize.getClothColorId();
		sizeType = clothSize.getSizeType();
		count = clothSize.getCount();
	}
	
	public ClothSize(ClothSizeDao dao) {
		id = dao.getInt("id");
		clothId = dao.getInt("cloth_id");
		clothColorId = dao.getInt("cloth_color_id");
		sizeType = dao.getInt("size_type");
		count = dao.getInt("count");
	}
	
	public ClothSizeDao toDao() {
		ClothSizeDao dao = new ClothSizeDao();
		if(id > 0) {
			dao.set("id", id);
		}
		dao.set("cloth_id", clothId);
		dao.set("cloth_color_id", clothColorId);
		dao.set("size_type", sizeType);
		dao.set("count", count);
		return dao;
	}
}
