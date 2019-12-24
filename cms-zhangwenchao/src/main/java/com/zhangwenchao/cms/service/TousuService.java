package com.zhangwenchao.cms.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangwenchao.cms.dao.TousuDao;
import com.zhangwenchao.cms.pojo.Tousu;
import com.zhangwenchao.common.utils.DateUtils;

@Service
public class TousuService {
	@Autowired
	private TousuDao tousuDao;
	@Autowired
	private ArticleService articleService;
	/**
	 * @Title: add   
	 * @Description: 添加评论   
	 * @param: @param comment
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean add(Tousu tousu) {
		String createdStr = DateUtils.dateTimeFormat.format(new Date());
		tousu.setCreated(createdStr);
		tousuDao.insert(tousu);
		articleService.addTousu(tousu.getArticleId());
		return true;
	}
}
