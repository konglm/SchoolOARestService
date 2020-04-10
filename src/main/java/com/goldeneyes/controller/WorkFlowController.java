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

package com.goldeneyes.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldeneyes.pojo.WorkFlow;
import com.goldeneyes.pojo.WorkFlowList;
import com.goldeneyes.service.WorkFlowService;
import com.goldeneyes.util.CommonTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/flow")
public class WorkFlowController {
	@Resource
	WorkFlowService workFlowService;

	/**
	 * 1.新增流程
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/addWorkFlow")
	public void addWorkFlow(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("schoolId") || !jsonInput.has("flowName") || !jsonInput.has("approveManId")
				|| !jsonInput.has("flowNote") || !jsonInput.has("apprvoveManName")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			String schoolId = "";
			String flowName = "";
			JSONArray approveManIds = new JSONArray();
			JSONArray apprvoveManNames = new JSONArray();
			String flowNote = "";
			List<Long> approveManIdsInList = new ArrayList<Long>();
			List<String> apprvoveManNamesInList = new ArrayList<String>();
			try {
				schoolId = jsonInput.getString("schoolId");
				flowName = jsonInput.getString("flowName");
				approveManIds = jsonInput.getJSONArray("approveManId");
				apprvoveManNames = jsonInput.getJSONArray("apprvoveManName");
				flowNote = jsonInput.getString("flowNote");
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {
				// 先判断数据库中是否已经有同名的流程，参数学校标识和流程名称
				int workFlowCount = workFlowService.getWorkFlowCountByCondition(schoolId, flowName);
				if (workFlowCount > 0) {
					CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1016").toString());
					return;
				}
				approveManIdsInList = CommonTool.getListLongFromJsonArray(approveManIds);
				apprvoveManNamesInList = CommonTool.getListFromJsonArray(apprvoveManNames);

				success = workFlowService.addWorkFlow(schoolId, flowName, approveManIdsInList, apprvoveManNamesInList,
						flowNote);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());
			} else {
				jsonData.put("Result", success);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}

	/**
	 * 2.修改流程
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/setWorkFlow")
	public void setWorkFlow(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("workFlowId") || !jsonInput.has("flowName") || !jsonInput.has("approveManId")
				|| !jsonInput.has("flowNote") || !jsonInput.has("apprvoveManName") || !jsonInput.has("status")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			int workFlowId = 0;
			String flowName = "";
			JSONArray approveManIds = new JSONArray();
			JSONArray apprvoveManNames = new JSONArray();
			String flowNote = "";
			int status = 0;
			List<Long> approveManIdsInList = new ArrayList<Long>();
			List<String> apprvoveManNamesInList = new ArrayList<String>();
			try {
				workFlowId = Integer.parseInt(jsonInput.getString("workFlowId"));
				flowName = jsonInput.getString("flowName");
				approveManIds = jsonInput.getJSONArray("approveManId");
				apprvoveManNames = jsonInput.getJSONArray("apprvoveManName");
				flowNote = jsonInput.getString("flowNote");
				status = Integer.parseInt(jsonInput.getString("status"));
			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {

				// 这是编辑，也就是修改，需要通过改后的流程名称查找对应记录的标识，看是修改的原有记录还是数据库中的其他记录
				WorkFlow workFlow = workFlowService.getSchoolIdByRecordId(workFlowId);
				int workFlowCount = workFlowService.getWorkFlowCountByCondition(workFlow.getSchoolid(), flowName);
				// System.out.println("workFlowCount==="+workFlowCount);
//				System.out.println("0000000000000000");
				WorkFlow tableRecord = workFlowService.getTableRecordByCondition(workFlow.getSchoolid(), flowName);
				// System.out.println("tableRecordId==="+tableRecordId+"\nworkFlowId==="+workFlowId);
				// 如果count>0且看改后的是不是本条记录

//				System.out.println("111111111111");
				if (workFlowCount > 0 && tableRecord.getTabid() != workFlowId) {
					CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1016").toString());
					return;
				}
//				System.out.println("2222222222");
				// 看修改后的人数，若改后的人数大于或小于原有的人数则删除原来的该流程下的审批人，重新新增，否则修改之
//				int modifiedCount = approveManIds.length();
//				// 原来的该流程下的审批人数
//				int rawCount = workFlowService.getWorkFlowListByWorkFlowId(workFlowId).size();
				approveManIdsInList = CommonTool.getListLongFromJsonArray(approveManIds);
				apprvoveManNamesInList = CommonTool.getListFromJsonArray(apprvoveManNames);

					
					success = workFlowService.setWorkFlow(workFlowId, flowName, approveManIdsInList, apprvoveManNamesInList,
							flowNote, status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());
			} else {
				jsonData.put("Result", success);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}

	/**
	 * 3.删除流程
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/delWorkFlow")
	public void delWorkFlow(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("workFlowId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			int workFlowId = 0;

			try {
				workFlowId = Integer.parseInt(jsonInput.getString("workFlowId"));

			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			int success = 0;
			try {
				// 此种情况是流程没被使用的情况下，如果被使用则不执行删除操作
				success = workFlowService.deleteWorkFlow(workFlowId);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
			if (success == 0) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1002").toString());
			} else {
				jsonData.put("Result", success);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			}
		}
	}

	/**
	 * 4.获取流程列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getWorkFlow")
	public void getWorkFlow(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("schoolId") || !jsonInput.has("flowName") || !jsonInput.has("page_number")
				|| !jsonInput.has("page_size")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			String schoolId = "";
			int pageIndex = 0;
			int pageSize = 0;
			String flowName = "";
			try {
				pageIndex = Integer.parseInt(jsonInput.getString("page_number"));
				pageSize = Integer.parseInt(jsonInput.getString("page_size"));
				schoolId = jsonInput.getString("schoolId");
				flowName = jsonInput.getString("flowName");

			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}
			if ((pageIndex <= 0) || (pageSize < 0)) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1006").toString());
				return;
			}
			int totalCnt = 0;
			int totalPage = 0;
			List<WorkFlow> workFlows = new ArrayList<WorkFlow>();
			List<WorkFlowList> workFlowLists = new ArrayList<WorkFlowList>();
			try {
				// System.out.println(00000000000);
				totalCnt = workFlowService.getCountByCondition(schoolId, flowName);
				totalPage = CommonTool.getTotalPage(totalCnt, pageSize);
				if (pageSize == 0) {// 整页获取
					workFlows = workFlowService.getWorkFlowDataByPagination(schoolId, flowName, 1, totalCnt);
				} else {
					workFlows = workFlowService.getWorkFlowDataByPagination(schoolId, flowName, pageIndex, pageSize);
				}
				JSONArray jsonArray = new JSONArray();
				// 将输出的日期格式化
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				for (WorkFlow workFlow : workFlows) {
					JSONObject jsonobj = new JSONObject();
					// 以下是每一个流程记录的属性
					jsonobj.put("WorkFlowId", workFlow.getTabid());
					jsonobj.put("FlowNote", workFlow.getFlownote());
					jsonobj.put("FlowName", workFlow.getFlowname());
					jsonobj.put("UpdateTime", simpleDateFormat.format(workFlow.getUpdatetime()));
					jsonobj.put("Status", workFlow.getStatus());
					// 若是同一条流程记录的话，则需将该流程记录下的多个节点全部取出，之后拼接成字符串作为ApproveList的值
					workFlowLists = workFlowService.getWorkFlowListByWorkFlowId(workFlow.getTabid());
					// 取出每个审批人的名字
					String approveList = "";
					String approveListIds = "";
					for (WorkFlowList workFlowList : workFlowLists) {
						String everyMan = workFlowList.getManname();
						approveList = approveList + everyMan + "-->";
						Long everyManId = workFlowList.getManid();
						approveListIds = approveListIds + everyManId + ",";
					}
					// 将拼接后的字符串的最后一个逗号去掉
					approveList = approveList.substring(0, approveList.length() - 1 - 1 - 1);
					approveListIds = approveListIds.substring(0, approveListIds.length() - 1);
					jsonobj.put("ApproveList", approveList);
					jsonobj.put("approveListIds", approveListIds);
					jsonArray.put(jsonobj);
				}
				jsonData.put("TotalCnt", totalCnt);
				jsonData.put("TotalPage", totalPage);
				jsonData.put("Data", jsonArray);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
		}
	}

	/**
	 * 5.获取选择用流程列表（必须有效的）
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getSelWorkFlow")
	public void getSelWorkFlow(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("schoolId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			String schoolId = "";

			try {
				schoolId = jsonInput.getString("schoolId");

			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			List<WorkFlow> workFlows = new ArrayList<WorkFlow>();
			try {
				workFlows = workFlowService.getSelWorkFlowByCondition(schoolId);
				JSONArray jsonArray = new JSONArray();
				for (WorkFlow workFlow : workFlows) {
					JSONObject jsonobj = new JSONObject();
					// 以下是每一个流程记录的属性
					jsonobj.put("WorkFlowId", workFlow.getTabid());
					jsonobj.put("FlowName", workFlow.getFlowname());

					jsonArray.put(jsonobj);
				}
				jsonData.put("Data", jsonArray);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
		}
	}

	/**
	 * 6.通过ID获取流程审批人
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getWorkFlowListById")
	public void getWorkFlowListById(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收参数用
		JSONObject jsonInput = new JSONObject();

		// 接收APP端发来的json请求
		String requestStr = "";
		try {
			requestStr = (String) request.getAttribute("requestStr");
			jsonInput = JSONObject.fromObject(requestStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}

		if (!jsonInput.has("workFlowId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
		} else {
			int workFlowId = 0;
			try {
				workFlowId = Integer.parseInt(jsonInput.getString("workFlowId"));

			} catch (Exception e) {
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;
			}

			List<WorkFlowList> workFlowLists = new ArrayList<WorkFlowList>();
			try {
				workFlowLists = workFlowService.getWorkFlowListByWorkFlowId(workFlowId);
				JSONArray jsonArray = new JSONArray();
				for (WorkFlowList workFlowList : workFlowLists) {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("ManId", String.valueOf(workFlowList.getManid()));
					jsonobj.put("ManName", workFlowList.getManname());
					jsonobj.put("OrderId", workFlowList.getOrderid());

					jsonArray.put(jsonobj);
				}
				jsonData.put("Data", jsonArray);
				// 在这里输出，手机端就拿到web返回的值了
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1013").toString());
				return;
			}
		}
	}

}
