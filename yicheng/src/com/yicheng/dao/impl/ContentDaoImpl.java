package com.yicheng.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yicheng.dao.ContentDao;
import com.yicheng.pojo.Content;

@Repository
public class ContentDaoImpl extends HibernateDaoBase implements ContentDao {

	@Override
	public int create(Content content) {
		return (Integer) getHibernateTemplate().save(content);
	}

	@Override
	public List<Content> getAll() {
		@SuppressWarnings("unchecked")
		List<Content> contentList = getHibernateTemplate().find("from Content");
		return contentList;
	}

}
