package com.whx.excel;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author wuhaixin on 2019/04/06
 */
public class Test {

    public static void main(String arg[]){
        int result = 0;
        File file = null;
        String path = null;
        JFileChooser fileChooser = new JFileChooser("F:\\");
//        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
//        System.out.println(fsv.getHomeDirectory());                //得到桌面路径
//        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
        fileChooser.setDialogTitle("请选择文件...");
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        Component chatFrame = null;
        result = fileChooser.showOpenDialog(chatFrame);
        if (JFileChooser.APPROVE_OPTION == result) {
            path=fileChooser.getSelectedFile().getPath();
            System.out.println("path: "+path);
        }

    }
}
