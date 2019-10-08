package com.controller;

import com.entity.FirstLinkman;
import com.entity.Progress;
import com.entity.Project;
import com.service.ProgressService;
import com.service.ProjectService;
import com.util.PaggingBean;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class fileController {
     Logger logger = LoggerFactory.getLogger(fileController.class);

    @Autowired
    ProjectService projectService;

    @Autowired
    ProgressService progressService;

    @RequestMapping(value = "/export")
    public void Export(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<Project> projectList = projectService.findAllProject();

        // 设置要导出的文件的名字
        String fileName = "项目"  + new Date() + ".xls";

        // 新增数据行，并且设置单元格数据
        int rowNum = 1;

        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = { "id", "编号", "项目名", "描述" , "说明" , "创建人" , "创建时间" , "修改时间"};
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (Project item : projectList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(item.getId());
            row1.createCell(1).setCellValue(item.getNumber());
            row1.createCell(2).setCellValue(item.getName());
            row1.createCell(3).setCellValue(item.getNote());
            row1.createCell(4).setCellValue(item.getCreateUserName());
            row1.createCell(5).setCellValue(item.getCreatedAt());
            row1.createCell(6).setCellValue(item.getUpdatedAt());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @RequestMapping(value = "/export2007", method = RequestMethod.GET)
    public void export2007(HttpServletRequest request, HttpServletResponse response) {
        PaggingBean paggingBean = new PaggingBean();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("name",request.getParameter("name"));
        params.put("number",request.getParameter("number"));
        params.put("stime",request.getParameter("stime"));
        paggingBean.setPageSize(999);
        Integer[] ids = projectService.findProjectIds(params);
        List<Project> projectList = projectService.findProByIds(ids,paggingBean);
        System.out.println(projectList);
        XSSFWorkbook wb = null;
        try {
            // excel模板路径
            InputStream basePath = this.getClass().getClassLoader().getResourceAsStream("model.xlsx");
            // String  basePath2 = request.getSession().getServletContext().getRealPath("/");
            //String excel = basePath2 + "/model.xlsx";
            logger.info("export2007");
            //logger.info(basePath);
            //logger.info("excel"+excel);
            //System.out.println("basePath--->"+basePath);
            //System.out.println("basePath2--->"+basePath2);
            //String excel =  "model.xlsx";
            //File fi = new File(basePath);
            // 读取excel模板
            wb = new XSSFWorkbook(basePath);
            // 读取了模板内所有sheet内容
            XSSFSheet sheet = wb.getSheetAt(0);
            // 在相应的单元格进行赋值
            int rowIndex = 2;
            int j = 1;
            if(projectList!=null) {
                for (Project project : projectList) {

                    int i = rowIndex;
                    List<Progress> progresses = progressService.findProgressByProId(project.getId());
                    List<FirstLinkman> firstLinkmen = project.getFirstLinkmen();
                    String[] linkNames =null;
                    String[] linkDuty =null;
                    String[] linkPhones = null;
                    if (firstLinkmen != null) {
                        linkNames = new String[firstLinkmen.size()];
                        linkDuty = new String[firstLinkmen.size()];
                         linkPhones = new String[firstLinkmen.size()];
                        for (int m = 0; m < firstLinkmen.size(); m++) {
                            linkNames[m] = firstLinkmen.get(m).getName();
                            linkDuty[m] = firstLinkmen.get(m).getDuty();
                            linkPhones[m] = firstLinkmen.get(m).getPhone();
                        }
                    }
                        XSSFRow row = sheet.getRow(rowIndex);
                        if (null == row) {
                            row = sheet.createRow(rowIndex);
                        }
                        XSSFCell cell0 = row.getCell(0);
                        if (null == cell0) {
                            cell0 = row.createCell(0);
                        }
                        cell0.setCellValue(j);// 序号

                        XSSFCell cell1 = row.getCell(1);
                        if (null == cell1) {
                            cell1 = row.createCell(1);
                        }
                        cell1.setCellValue(project.getName());// 项目主体

                        XSSFCell cell2 = row.getCell(2);
                        if (null == cell2) {
                            cell2 = row.createCell(2);
                        }
                        cell2.setCellValue(project.getNumber());// 项目编号

                        XSSFCell cell3 = row.getCell(3);
                        if (null == cell3) {
                            cell3 = row.createCell(3);
                        }
                        cell3.setCellValue(project.getNote());// 简介

                        XSSFCell cell4 = row.getCell(4);
                        if (null == cell4) {
                            cell4 = row.createCell(4);
                        }
                        cell4.setCellValue(project.getPalName());// 负责人

                        for (Progress progress : progresses
                        ) {
                            XSSFRow row2 = sheet.getRow(i);

                            XSSFCell cell5 = row2.getCell(5);
                            if (null == cell5) {
                                cell5 = row2.createCell(5);
                            }
                            cell5.setCellValue(progress.getContent());// 进展

                            XSSFCell cell6 = row2.getCell(6);
                            if (null == cell6) {
                                cell6 = row2.createCell(6);
                            }
                            cell6.setCellValue(progress.getCreatedAt());// 跟踪时间

                            XSSFCell cell7 = row2.getCell(7);
                            if (null == cell7) {
                                cell7 = row2.createCell(7);
                            }
                            cell7.setCellValue(progress.getStatusText());// 跟踪状态

                            XSSFCell cell8 = row2.getCell(8);
                            if (null == cell8) {
                                cell8 = row2.createCell(8);
                            }
                            cell8.setCellValue(progress.getAfterUserNames());// 跟踪人
                            i++;
                        }
                        XSSFCell cell9 = row.getCell(9);
                        if (null == cell9) {
                            cell9 = row.createCell(9);
                        }
                        cell9.setCellValue(linkNames==null?null:strParse(linkNames));// 联系人名字

                        XSSFCell cell10 = row.getCell(10);
                        if (null == cell10) {
                            cell10 = row.createCell(10);
                        }
                        cell10.setCellValue(linkDuty==null?null:strParse(linkDuty));// 联系人职务

                        XSSFCell cell11 = row.getCell(11);
                        if (null == cell11) {
                            cell11 = row.createCell(11);
                        }
                        cell11.setCellValue(linkPhones==null?null:strParse(linkPhones));// 联系人号码

                        XSSFCell cell12 = row.getCell(12);
                        if (null == cell12) {
                            cell12 = row.createCell(12);
                        }
                        cell12.setCellValue(project.getDescribe());// 备注
                        rowIndex = i ;
                        rowIndex++;
                        j++;
                    }
            }
            String fileName = "项目列表";
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream sout = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(sout);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (Exception e) {
                logger.error("导出excel出现异常:", e);
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }

        } catch (Exception e) {
            logger.error("导出excel出现异常:", e);
        }

    }

    public String strParse(String[] lytype){

        StringBuilder sb = new StringBuilder();

         if (lytype != null && lytype.length > 0) {
            for (int i = 0; i < lytype.length; i++) {
                if (i < lytype.length - 1) {
                    sb.append(lytype[i] + ",");
                } else {
                    sb.append(lytype[i]);
                }
            }
         }
        return  sb.toString();
    }
    @RequestMapping(value = "/downPage", method = RequestMethod.GET)
    public String checkProject(HttpServletRequest request, Integer pageIndex, Model model){
        Map<String,Object> param = new HashMap<String, Object>();
        Map<String,Object> result = new HashMap<String,Object>();
        param.put("name",request.getParameter("name"));
        param.put("number",request.getParameter("number"));
        param.put("stime",request.getParameter("stime"));
        System.out.println(param);
        PaggingBean paggingBean = new PaggingBean();
        if(pageIndex!=null){
            paggingBean.setPageIndex(pageIndex);
        }
        Integer[] ids = projectService.findProjectIds(param);
        List<Project> projectList = projectService.findProByIds(ids,paggingBean);
        model.addAttribute("data",projectList);
        model.addAttribute("page",paggingBean);
        model.addAttribute("name",request.getParameter("name"));
        model.addAttribute("number",request.getParameter("number"));
        model.addAttribute("stime",request.getParameter("stime"));

        return "export";
    }
}
