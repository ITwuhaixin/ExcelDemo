package com.whx.excel.utils;

import com.whx.excel.Dto.PriceDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author wuhaixin on 2019/04/06
 */
public class ExcelUtil {
    /**
     * 根据fileType不同读取excel文件
     *
     * @param path
     * @param path
     * @throws IOException
     */
    public static List<PriceDto> readExcel(String path) {
        String fileType = path.substring(path.lastIndexOf(".") + 1);
        // return a list contains many list
        List<List<String>> lists = new ArrayList<List<String>>();
        List<PriceDto> priceDtoList = new LinkedList<>();
        //读取excel文件
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            //获取工作薄
            Workbook wb = null;
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(is);
            } else {
                return null;
            }

            //读取第一个工作页sheet
            Sheet sheet = wb.getSheetAt(0);
            //第一行为标题,不要，从第二行开始
            for(int i=1; i <= sheet.getLastRowNum();i++){
                PriceDto priceDto = new PriceDto();
                Field[] priceFields = priceDto.getClass().getDeclaredFields();
                Row row = sheet.getRow(i);
                ArrayList<String> list = new ArrayList<String>();
                for (int j=0;j<row.getLastCellNum();j++) {
                    Cell cell = row.getCell(j);
                    //根据不同类型转化成字符串
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String stringCellValue = cell.getStringCellValue();
                    if(!StringUtils.isEmpty(stringCellValue)){
                        try {
                            priceFields[j].setAccessible(true);
                            priceFields[j].set(priceDto, stringCellValue);
                            list.add(stringCellValue);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                lists.add(list);
                priceDtoList.add(priceDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        return lists;
        return priceDtoList;
    }

    public static Workbook createExcel(List<PriceDto> lists){
        //创建新的工作薄
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet();

        String[] titles = {"序号","IMEI/序列号","型号","ID黑白","激活锁","价格"};
        // 创建第一行
        int rowNum = 0;
        Row row0 = sheet.createRow(rowNum++);
        //设置列名
        for(int i=0;i<titles.length;i++){
            Cell cell = row0.createCell(i);
            cell.setCellValue(titles[i]);
        }
        if(lists == null || lists.size() == 0){
            return wb;
        }

        for (PriceDto priceDto : lists) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(priceDto.getId());
            row.createCell(1).setCellValue(priceDto.getImei());
            row.createCell(2).setCellValue(priceDto.getModel());
            row.createCell(3).setCellValue(priceDto.getBlackWhite());
            row.createCell(4).setCellValue(priceDto.getLock());
            if(priceDto.getPrice() != null){
                row.createCell(5).setCellValue(priceDto.getPrice().intValue());
            }
        }

        //设置每行每列的值
//        for (short i = 1; i <= lists.size(); i++) {
//            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
//            // 创建一行，在页sheet上
//            Row row1 = sheet.createRow((short)i);
//            for(short j=0;j<titles.length;j++){
//                // 在row行上创建一个方格
//                Cell cell = row1.createCell(j);
//                cell.setCellValue(lists.get(i-1).get(j));
//            }
//        }
        return wb;

    }


}
