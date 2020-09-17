package org.jeecg.warning.modules.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.warning.modules.entity.Strategy;
import org.jeecg.warning.modules.entity.Template;
import org.jeecg.warning.modules.service.IStrategyService;
import org.jeecg.warning.modules.service.ITemplateService;
import org.jeecg.warning.modules.service.impl.TemplateServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 策略ID
 * @Author: yonggan.tian
 * @Date:   2020-09-14
 * @Version: V1.0
 */
@Api(tags="策略ID")
@RestController
@RequestMapping("/warning/strategy")
@Slf4j
public class StrategyController extends JeecgController<Strategy, IStrategyService> {
	@Autowired
	private IStrategyService strategyService;
	@Autowired
	private ITemplateService templateService;
	/**
	 * 分页列表查询
	 *
	 * @param strategy
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "策略ID-分页列表查询")
	@ApiOperation(value="策略ID-分页列表查询", notes="策略ID-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Strategy strategy,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Strategy> queryWrapper = QueryGenerator.initQueryWrapper(strategy, req.getParameterMap());
		Page<Strategy> page = new Page<Strategy>(pageNo, pageSize);
		IPage<Strategy> pageList = strategyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param strategy
	 * @return
	 */
	@AutoLog(value = "策略ID-添加")
	@ApiOperation(value="策略ID-添加", notes="策略ID-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Strategy strategy) {
		strategyService.save(strategy);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param strategy
	 * @return
	 */
	@AutoLog(value = "策略ID-编辑")
	@ApiOperation(value="策略ID-编辑", notes="策略ID-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Strategy strategy) {
		strategyService.updateById(strategy);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "策略ID-通过id删除")
	@ApiOperation(value="策略ID-通过id删除", notes="策略ID-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		strategyService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "策略ID-批量删除")
	@ApiOperation(value="策略ID-批量删除", notes="策略ID-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.strategyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "策略ID-通过id查询")
	@ApiOperation(value="策略ID-通过id查询", notes="策略ID-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Strategy strategy = strategyService.getById(id);
		if(strategy==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(strategy);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param strategy
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Strategy strategy) {
        return super.exportXls(request, strategy, Strategy.class, "策略ID");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Strategy.class);
    }

    /**
     *  获取所有的模板信息
     * @return
     */
    @RequestMapping(value="/queryTemplates",method = RequestMethod.GET)
    public Result<?> queryTemplates(){
    	List<Template> templates = templateService.list();
    	//return Result.OK(JSONArray.toJSONString(templates));
    	return Result.OK(templates);
    }
}
