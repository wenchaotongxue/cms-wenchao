package com.zhangwenchao.cms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangwenchao.cms.common.CmsConstant;
import com.zhangwenchao.cms.common.JsonResult;
import com.zhangwenchao.cms.pojo.Comlain;
import com.zhangwenchao.cms.pojo.User;
import com.zhangwenchao.cms.service.ComlainService;
import com.zhangwenchao.cms.service.TousuService;
import com.zhangwenchao.common.utils.StringUtils;


@Controller
@RequestMapping("/tousu/")
public class TousuController {
	@Autowired
	private TousuService tousuService;
	@Autowired
	private ComlainService comlainService;
	
	/**
	 * 
	* @Title: add
	* @Description: 添加投诉的内容信息
	* @param @param comlain
	* @param @param session
	* @param @return    参数
	* @return JsonResult    返回类型
	* @throws
	 */
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public @ResponseBody JsonResult add(Comlain comlain,HttpSession session) {
		User userInfo = (User)session.getAttribute(CmsConstant.UserSessionKey);
		if(userInfo==null) {
			return JsonResult.fail(CmsConstant.unLoginErrorCode, "用户未登录");
		}
		//获取用户的id
		comlain.setUserId(userInfo.getId());
		//调用工具类判断路径是否空
		String StringUtilss ="http://";
		if(StringUtilss.equals(comlain.getUrlip())&&!StringUtils.isBlank(comlain.getUrlip())) {
			 boolean result = comlainService.add(comlain);
			 if(result) {
				 
				 return JsonResult.sucess();
			 }
			
		}else {
			return JsonResult.fail(10000, "路径错错误");
		}
		return JsonResult.fail(10000, "未知错误");
		
	}
	
	
	
	
	
	
	/**
	 * @Title: add   
	 * @Description: 添加评论 
	 * @param: @param comment
	 * @param: @return      
	 * @return: JsonResult      
	 * @throws
	 */
	/*@RequestMapping(value="add",method=RequestMethod.POST)
	public @ResponseBody JsonResult add(Tousu tousu,HttpSession session) {
		User userInfo = (User)session.getAttribute(CmsConstant.UserSessionKey);
		if(userInfo==null) {
			return JsonResult.fail(CmsConstant.unLoginErrorCode, "用户未登录");
		}
		tousu.setUserId(userInfo.getId());
		boolean result = tousuService.add(tousu);
		if(result) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(10000, "未知错误");
	}*/
}
