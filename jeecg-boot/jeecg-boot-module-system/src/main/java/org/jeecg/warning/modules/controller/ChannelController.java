package org.jeecg.warning.modules.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.ResolverUtil.Test;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.system.entity.SysRole;
import org.jeecg.warning.modules.entity.Channel;
import org.jeecg.warning.modules.service.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 渠道
 * @Author: yonggan.tian
 * @Date:   2020-09-14
 * @Version: V1.0
 */
@Api(tags="渠道")
@RestController
@RequestMapping("/warning/channel")
@Slf4j
public class ChannelController extends JeecgController<Channel, IChannelService> {
	@Autowired
	private IChannelService channelService;
	
	/**
	 * 分页列表查询
	 *
	 * @param channel
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * list?_t=1600145277&column=createTime&order=desc&field=id,,,channelname,region,
	 * linkman,status,remark,action&pageNo=1&pageSize=10
	 * @return
	 */
	@AutoLog(value = "渠道-分页列表查询")
	@ApiOperation(value="渠道-分页列表查询", notes="渠道-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Channel channel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Channel> queryWrapper = QueryGenerator.initQueryWrapper(channel, req.getParameterMap());
		Page<Channel> page = new Page<Channel>(pageNo, pageSize);
		IPage<Channel> pageList = channelService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param channel
	 * @return
	 */
	@AutoLog(value = "渠道-添加")
	@ApiOperation(value="渠道-添加", notes="渠道-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Channel channel) {
		Result<Channel> result = new Result<Channel>();
			
			System.out.println("-----------------"+channel.toString()+"-------------------------");
		
		try {
			channel.setCreateTime(new Date());
			channelService.save(channel);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	
	@GetMapping("test")
	public String Test(@RequestBody String test) {
		
		System.out.println("测试后台数据成功了！！！"+test+"--------------------");
		return "测试后台数据";
	}
	
	/**
	 *  编辑
	 *
	 * @param channel
	 * @return
	 */
	@AutoLog(value = "渠道-编辑")
	@ApiOperation(value="渠道-编辑", notes="渠道-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Channel channel) {
		channelService.updateById(channel);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "渠道-通过id删除")
	@ApiOperation(value="渠道-通过id删除", notes="渠道-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		channelService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "渠道-批量删除")
	@ApiOperation(value="渠道-批量删除", notes="渠道-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.channelService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "渠道-通过id查询")
	@ApiOperation(value="渠道-通过id查询", notes="渠道-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Channel channel = channelService.getById(id);
		if(channel==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(channel);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param channel
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Channel channel) {
        return super.exportXls(request, channel, Channel.class, "渠道");
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
        return super.importExcel(request, response, Channel.class);
    }

}
