package com.whx.excel;

import com.whx.excel.Dto.PriceDto;
import com.whx.excel.utils.ExcelUtil;
import com.whx.excel.utils.PriceUtil;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.SocketUtils;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

/**
 * 描述:
 * 选中一个Excel文件，生成一个处理后的Excel文件
 *
 * @author wuhaixin on 2019/04/06
 */
public class ExcelOperater {

    public static void main(String args[]){
        int result = 0;
        String filePath = "";
        String priceSuffix = ""; //价格后两位

        String saveFolder = "D:\\";
        Preferences pref = Preferences.userRoot().node(  "priceUtil");
        String lastPath = pref.get("lastPath", "");
        JFileChooser fileChooser = null;
        if (!lastPath.equals("")) {
            fileChooser = new JFileChooser(lastPath);
        } else{
            fileChooser = new JFileChooser(saveFolder);
        }

//        JFileChooser fileChooser = new JFileChooser("F:\\AAA-Henson\\5month\\5-14");
        fileChooser.setDialogTitle("请选择文件...");
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        result = fileChooser.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == result) {
            filePath=fileChooser.getSelectedFile().getPath();
            System.out.println("path: "+filePath);
            saveFolder = fileChooser.getSelectedFile().getPath();
            pref.put("lastPath",saveFolder);

            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入金额尾数（后两位）：");
            priceSuffix = scanner.next();
            scanner.close();
            execute(filePath, priceSuffix);
        }
        try {
            System.out.println("---------------准备关闭窗口------------");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("---------------出错啦！！！---------------");
        }
        System.out.println("---------------关闭！---------------");
    }

    private static void execute(String filePath, String priceSuffix){
        System.out.println("---------------开始生成Excel-----------");
        List<PriceDto> priceDtoList = ExcelUtil.readExcel(filePath);

        if(StringUtils.isEmpty(priceSuffix)){
            priceSuffix = "50";
        }
        //填充完整数据
        PriceUtil.changeData(priceDtoList, priceSuffix);

        BigDecimal totalMoney = new BigDecimal("0");
        Integer totalLost = 0;
        for (PriceDto priceDto : priceDtoList) {
            if(priceDto.getPrice() != null){
                totalMoney = totalMoney.add(priceDto.getPrice()); //计算总价格
            }
            if("Lost".equals(priceDto.getBlackWhite())){
                totalLost += 1;
            }
        }

        //生成Excel
        Workbook wb = ExcelUtil.createExcel(priceDtoList);
        String fileName = "";

        String substring = filePath.substring(0, filePath.lastIndexOf("."));
        if(totalLost == 0){
            fileName = substring + "Done=" + totalMoney + ".xls";
        }else {
            fileName = substring + "Done-" + totalLost + "lost=" + totalMoney + ".xls";
        }

        try {
            FileOutputStream fout = null;
            fout = new FileOutputStream(fileName);
            wb.write(fout);
            fout.close();
            System.out.println("---------------生成Excel完成！--------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
