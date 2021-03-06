/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：
 * 文件功能描述：
 *
 * 
 * 创建标识：
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.service.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.goldeneyes.service.OutInterfaceService;
import com.goldeneyes.util.CommonTool;
import com.goldeneyes.util.HttpUtil;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Service("outInterfaceService")
public class OutInterfaceServiceImpl implements OutInterfaceService {
	@Value("${cloudApiIP}")
    private String cloudApiIP;
	@Value("${signKey}")
    private String signKey;
	
	public static void main(String[] args){
		OutInterfaceServiceImpl oi = new OutInterfaceServiceImpl();
		try {
			String token = oi.tokenUser("PT0001", "oa#", "", "ZmE2ODZjMzAtMjQzNy00ZjE5LWE0OWEtZjQwZGFjZjViMWM3");
			System.out.println(token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public String tokenUser(String platform_code, String app_code, String access, String access_token) throws Exception {
		// TODO Auto-generated method stub
		String urls = "access=" + access + "&access_token=" + access_token + "&app_code=" + app_code + "&cls_id=0&grd_id=0&platform_code=" + platform_code
				 + "&stu_id=0&sub_code=0";
		String sign = "";
		try {
			sign=CommonTool.getSign(urls,signKey);
			System.out.println("接口生成的sign：" + sign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jObj = new JSONObject();
		jObj.put("access", access);		
		jObj.put("access_token", access_token);
		jObj.put("app_code", app_code);
		jObj.put("cls_id", 0);
		jObj.put("grd_id", 0);
		jObj.put("platform_code", platform_code);
		jObj.put("stu_id", 0);
		jObj.put("sub_code", 0);
		jObj.put("sign", sign);
		System.out.println(jObj.toString());
		System.out.println(cloudApiIP + "/api/acl/integratedVerification");
		String outJson = "";
		try {
			outJson = HttpUtil.postHttpJson(cloudApiIP + "/api/acl/integratedVerification",
					jObj.toString());
			System.out.println(outJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject responseData = JSONObject.fromObject(outJson);
		System.out.println("responseData----------" + responseData);
		
		return responseData.getString("code");
	}
}
