package com.whx.excel.Dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author wuhaixin on 2019/04/06
 */
@Data
public class PriceDto {

    private String id; //序号

    private String imei; //imei/序列号

    private String model; //型号

    private String blackWhite; //ID黑白

    private String lock; //激活锁

    private BigDecimal price;  //价格

    private String modelNotBlank; //型号，没有空格的
}
