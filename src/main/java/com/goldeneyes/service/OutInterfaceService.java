/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：OutInterfaceService
 * 文件功能描述：外部接口调用封装
 *
 * 
 * 创建标识：konglm20180124
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.service;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public interface OutInterfaceService {

	public String tokenUser(String platform_code, String app_code, String access, String access_token) throws Exception;

}
