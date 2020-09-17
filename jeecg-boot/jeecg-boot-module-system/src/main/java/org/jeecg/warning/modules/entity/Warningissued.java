package org.jeecg.warning.modules.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 预警发布
 * @Author: jeecg-boot
 * @Date:   2020-09-14
 * @Version: V1.0
 */
@Data
@TableName("warningissued")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="warningissued对象", description="预警发布")
public class Warningissued implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**预警等级*/
	@Excel(name = "预警等级", width = 15)
    @ApiModelProperty(value = "预警等级")
    private java.lang.Integer wlever;
	/**预警类型 */
	@Excel(name = "预警类型 ", width = 15)
    @ApiModelProperty(value = "预警类型 ")
    private java.lang.Integer wtype;
	/**区域*/
	@Excel(name = "区域", width = 15)
    @ApiModelProperty(value = "区域")
    private java.lang.Integer region;
	/**信息来源*/
	@Excel(name = "信息来源", width = 15)
    @ApiModelProperty(value = "信息来源")
    private java.lang.String infosource;
	/**信息标题*/
	@Excel(name = "信息标题", width = 15)
    @ApiModelProperty(value = "信息标题")
    private java.lang.String infotitle;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date timestart;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date timeend;
	/**上报*/
	@Excel(name = "上报", width = 15)
    @ApiModelProperty(value = "上报")
    private java.lang.String sender;
	/**上报公司*/
	@Excel(name = "上报公司", width = 15)
    @ApiModelProperty(value = "上报公司")
    private java.lang.String sendercompany;
	/**策略ID*/
	@Excel(name = "策略ID", width = 15)
    @ApiModelProperty(value = "策略ID")
    private java.lang.Integer sid;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
}
