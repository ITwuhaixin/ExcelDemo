package com.whx.excel.utils;

import com.whx.excel.Dto.PriceDto;

import javax.swing.text.StyledEditorKit;
import java.math.BigDecimal;
import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author wuhaixin on 2019/04/06
 */
public class PriceUtil {

    public static void changeData(List<PriceDto> list, String priceSuffix){
        for (PriceDto priceDto : list) {
            priceDto.setModelNotBlank(priceDto.getModel().replaceAll(" ",""));
            //把Off的改成Clean
            if("Off".equals(priceDto.getBlackWhite())){
                priceDto.setBlackWhite("Clean");
            }

            if("USD".equalsIgnoreCase(priceSuffix)){
                //根据型号计算价格
                if("Clean".equals(priceDto.getBlackWhite())){

                    if("iPhoneX".equals(priceDto.getModelNotBlank()) || "iPhone8Plus".equals(priceDto.getModelNotBlank())){
                        priceDto.setPrice(new BigDecimal("138")); //美元
                        priceDto.setLock("Off");
                    }else if("iPhone8".equals(priceDto.getModelNotBlank())){
                        priceDto.setPrice(new BigDecimal("123")); //美元
                        priceDto.setLock("Off");
                    }else if("iPhone7Plus".equals(priceDto.getModelNotBlank())){
                        priceDto.setPrice(new BigDecimal("108")); //美元
                        priceDto.setLock("Off");
                    }else if("iPhone7".equals(priceDto.getModelNotBlank()) || "iPhone6sPlus".equals(priceDto.getModelNotBlank())){
                        priceDto.setPrice(new BigDecimal("93")); //美元
                        priceDto.setLock("Off");
                    }else if("iPhone6Plus".equals(priceDto.getModelNotBlank()) || "iPhone6s".equals(priceDto.getModelNotBlank()) || "iPhone6".equals(priceDto.getModelNotBlank())
                            || "iPhoneSE".equals(priceDto.getModelNotBlank()) || "iPhone5s".equals(priceDto.getModelNotBlank()) || "iPhone5c".equals(priceDto.getModelNotBlank())
                            || "iPhone5".equals(priceDto.getModelNotBlank()) || "iPhone4s".equals(priceDto.getModelNotBlank()) || priceDto.getModelNotBlank().contains("5") ){
                        priceDto.setPrice(new BigDecimal("78")); //美元
                        priceDto.setLock("Off");
                    }
                }
            }else{
                //根据型号计算价格
                if("Clean".equals(priceDto.getBlackWhite())){
                    priceDto.setLock("Off");
                    if("iPhoneX".equals(priceDto.getModelNotBlank()) || "iPhone8Plus".equals(priceDto.getModelNotBlank())){
                        priceDto.setPrice(new BigDecimal("9" + priceSuffix));
//                        priceDto.setLock("Off");
                    }else if("iPhone8".equals(priceDto.getModelNotBlank())){
                        priceDto.setPrice(new BigDecimal("8" + priceSuffix));
//                        priceDto.setLock("Off");
                    }else if("iPhone7Plus".equals(priceDto.getModelNotBlank())){
                        priceDto.setPrice(new BigDecimal("7" + priceSuffix));
//                        priceDto.setLock("Off");
                    }else if("iPhone7".equals(priceDto.getModelNotBlank()) || "iPhone6sPlus".equals(priceDto.getModelNotBlank())){
                        priceDto.setPrice(new BigDecimal("6" + priceSuffix));
//                        priceDto.setLock("Off");
                    }else if("iPhone6Plus".equals(priceDto.getModelNotBlank()) || "iPhone6s".equals(priceDto.getModelNotBlank()) || "iPhone6".equals(priceDto.getModelNotBlank())
                            || "iPhoneSE".equals(priceDto.getModelNotBlank()) || "iPhone5s".equals(priceDto.getModelNotBlank()) || "iPhone5c".equals(priceDto.getModelNotBlank())
                            || "iPhone5".equals(priceDto.getModelNotBlank()) || "iPhone4s".equals(priceDto.getModelNotBlank()) || priceDto.getModelNotBlank().contains("5") ){
                        priceDto.setPrice(new BigDecimal("5" + priceSuffix));
//                        priceDto.setLock("Off");
                    }
                }
            }

        }
    }

}
