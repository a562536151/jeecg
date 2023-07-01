package org.jeecg.modules.demo.cc.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @Description: 销售管理
 * @Author: jeecg-boot
 * @Date:   2023-05-01
 * @Version: V1.0
 */
@Data
@TableName("aa")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="aa对象", description="销售管理")
public class Aa implements Serializable {
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
	/**EE*/


	/**EE*/
	@Excel(name = "EE", width = 15)
    @ApiModelProperty(value = "EE")
    private java.lang.String itemCategory;


    /**
     * 分类名称
     */
    @ApiModelProperty(value = "name")
    private String name;
    /**
     * 父分类id
     */
    @ApiModelProperty(value = "parentCid")
    private String parentCid;
    /**
     * 层级
     */
    @ApiModelProperty(value = "catLevel")
    private String catLevel;

    /**
     * 排序
     */
    @ApiModelProperty(value = "sort")
    private Integer sort;

    /**
     * 子目录节点
     */
    @TableField(exist =false) //表示该字段不是数据库中的字段
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Aa> children;

}
