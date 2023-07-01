package org.jeecg.modules.demo.TechDataClassify.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
public class TechClassifyXlsx {
    @Excel(name = "一级学科编码", width = 15)
    @ApiModelProperty(value = "一级学科编码")
    private java.lang.String nameFirstCode;

    @Excel(name = "一级学科", width = 15)
    @ApiModelProperty(value = "一级学科")
    private java.lang.String nameFirst;

    @Excel(name = "二级学科编码", width = 15)
    @ApiModelProperty(value = "二级学科编码")
    private java.lang.String nameSecondCode;

    @Excel(name = "二级学科", width = 15)
    @ApiModelProperty(value = "二级学科")
    private java.lang.String nameSecond;


    @Excel(name = "三级学科编码", width = 15)
    @ApiModelProperty(value = "三级学科编码")
    private java.lang.String nameThirdCode;

    @Excel(name = "三级学科", width = 15)
    @ApiModelProperty(value = "三级学科")
    private java.lang.String nameThird;







}
