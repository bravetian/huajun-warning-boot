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
 * @Description: 渠道参数
 * @Author: jeecg-boot
 * @Date:   2020-09-14
 * @Version: V1.0
 */
@Data
@TableName("channelparame")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="channelparame对象", description="渠道参数")
public class Channelparame implements Serializable {
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
	/**渠道ID*/
	@Excel(name = "渠道ID", width = 15)
    @ApiModelProperty(value = "渠道ID")
    private java.lang.Integer cid;
	/**ckey*/
	@Excel(name = "ckey", width = 15)
    @ApiModelProperty(value = "ckey")
    private java.lang.String ckey;
	/**value*/
	@Excel(name = "value", width = 15)
    @ApiModelProperty(value = "value")
    private java.lang.String value;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;
	/**父级节点*/
	@Excel(name = "父级节点", width = 15)
    @ApiModelProperty(value = "父级节点")
    private java.lang.String pid;
	/**是否有子节点*/
	@Excel(name = "是否有子节点", width = 15)
    @ApiModelProperty(value = "是否有子节点")
    private java.lang.String hasChild;
}
