package org.jeecg.warning.modules.common;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author yonggan.tian
 * 2020-9-17
 */
@RequestMapping("/tableToDictList")
public class DictTableController {

	@Autowired
	ISysDictService sysdictService;
	/**
	 * 
	 * @param dictCode
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/selectList/{dictCode}",method = RequestMethod.GET)
	@ApiOperation("Select List")
	public Result<?> selectList(@PathVariable String dictCode,@RequestParam(name="param")String params){
		List<DictModel> ls = null;
		String filterSql = "";
		LoginUser sysUser  = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		switch (dictCode) {
		case "item_type":
			break;
		default:
			//filterSql = "is_valid=1 and sys_org_code='"+sysUser.getOrgCode()+"'";
			//ls = sysdictService.queryTableDictItemsByCodeAndFilter("template", "tname", "id", filterSql);
			ls = sysdictService.queryTableDictItemsByCode("template", "tname", "id");
			
			System.out.println("--------" + ls.toString() + "-----------------------");
			break;
		}
		
		
		return Result.ok(ls);
	}
}
