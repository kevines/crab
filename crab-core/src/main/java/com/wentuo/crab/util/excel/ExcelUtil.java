package com.wentuo.crab.util.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuwenbo on 2018/12/4 17:09.
 */
public class ExcelUtil {





    /**
     * 导出 Excel ：一个 sheet，带表头
     *
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param object    映射实体类，Excel 模型
     */
    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                                     String fileName, BaseRowModel object)  {
        ServletOutputStream out  = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition","attachment;filename=" + new String((fileName + ".xlsx").getBytes(),
                    "ISO-8859-1"));
            ExcelWriter writer = EasyExcelFactory.getWriterWithTempAndHandler(null,out,ExcelTypeEnum.XLSX,true,
                    new AfterWriteHandlerImpl());
            Sheet sheet = new Sheet(1, 0, object.getClass());
            sheet.setSheetName(fileName);
            writer.write(list, sheet);
            writer.finish();
            out.close();
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
