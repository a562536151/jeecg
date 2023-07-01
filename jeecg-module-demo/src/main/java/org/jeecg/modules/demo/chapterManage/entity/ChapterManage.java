package org.jeecg.modules.demo.chapterManage.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 用章管理
 * @Author: jeecg-boot
 * @Date:   2023-05-04
 * @Version: V1.0
 */
@ApiModel(value="chapter_manage对象", description="用章管理")
@Data
@TableName("chapter_manage")
public class ChapterManage implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**用章申请号*/
	@Excel(name = "用章申请号", width = 15)
    @ApiModelProperty(value = "用章申请号")
    private String chapterNumber;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15)
    @ApiModelProperty(value = "审核状态")
    @Dict(dicCode = "bpm_status")
    private String auditStatus;
	/**事项类别*/
	@Excel(name = "事项类别", width = 15)
    @ApiModelProperty(value = "事项类别")
    @Dict(dicCode = "event_category")
    private String itemCategory;
	/**流程类型*/
	@Excel(name = "流程类型", width = 15)
    @ApiModelProperty(value = "流程类型")
    @Dict(dicCode = "process_type")
    private String processType;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String contactPerson;
	/**联系人编码*/
	@Excel(name = "联系人编码", width = 15)
    @ApiModelProperty(value = "联系人编码")
    private String contactPersonCode;

    @Excel(name = "申请部门", width = 15)
    @ApiModelProperty(value = "申请部门")
    private String applyDepartment;

	/**用章种类*/
	@Excel(name = "用章种类", width = 15)
    @ApiModelProperty(value = "用章种类")
    @Dict(dicCode = "chapter_category")
    private String sealType;
	/**文件份数*/
	@Excel(name = "文件份数", width = 15)
    @ApiModelProperty(value = "文件份数")
    private String fileCopies;
	/**用章事由*/
	@Excel(name = "用章事由", width = 15)
    @ApiModelProperty(value = "用章事由")
    private String sealReason;
	/**联系方式*/
	@Excel(name = "联系方式", width = 15)
    @ApiModelProperty(value = "联系方式")
    private String contactInformation;
	/**逻辑删除*/
	@Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private String izDeleted;

    @Excel(name = "申请人", width = 15)
    @ApiModelProperty(value = "申请人")
    private String applyPerson;

    private String projectHeadId;



    @Excel(name = "审核状态", width = 15)
    @ApiModelProperty(value = "审核状态")
    @Dict(dicCode = "bpmStatus")
    private String bpmStatus;

}
