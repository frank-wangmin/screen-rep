package com.ysten.local.bss.common.web.controller;

import com.ibm.icu.text.SimpleDateFormat;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExportController {
    private boolean isFinished = false;

    /**
     * 导出data至excel中
     * @param heads
     *            表头部分
     * @param data
     *            表体部分
     * @param excludes
     *            排除掉的字段Collection
     * @param clazz
     *            data部分的类的Class实例
     * @param fileName
     *            导出Excel名称
     * @param response
     *            http返回对象
     * @throws Exception
     *             抛出的异常
     */
    protected void export(List<String> heads, List<?> data, Collection<String> excludes, Class<?> clazz,
            String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("upload");
        fileName = path + "\\" + fileName + ".xls";
        // System.out.println(fileName);
        WritableWorkbook wwb = null;
        FileOutputStream fos = null;

        try {
            isFinished = false;
            fos = new FileOutputStream(fileName);
            wwb = Workbook.createWorkbook(fos);
            /*
             * 创建一个工作表、sheetName为工作表的名称、"0"为第一个工作表
             * 打开Excel的时候会看到左下角默认有3个sheet、"sheet1、sheet2、sheet3"这样
             * 代码中的"0"就是sheet1、其它的一一对应。 createSheet(sheetName,
             * 0)一个是工作表的名称，另一个是工作表在工作薄中的位置
             */
            WritableSheet ws = wwb.createSheet("Sheet1", 0);
            //
            this.writeExcelHead(ws, heads);
            ExportData ed = this.convertData(data, excludes, clazz, heads.size());
            while (!isFinished) {
                Thread.sleep(1000);
            }
            if (ed != null) {
                this.writeExcelBody(ws, ed.getData());
            }

            // 写入Exel工作表
            wwb.write();
        } finally {
            // 关闭Excel工作薄对象
            if (wwb != null) {
                wwb.close();
            }
            // 关闭文件流
            if (fos != null) {
                fos.close();
            }
        }
        File file = new File(fileName);
        this.exportTemplate(file, response);
        file.delete();
    }

    /**
     * 生成Excel表头部分
     * 
     * @param ws
     *            Excel对象
     * @param heads
     *            表头数据
     * @throws RowsExceededException
     * @throws WriteException
     */
    private void writeExcelHead(WritableSheet ws, List<String> heads) throws RowsExceededException, WriteException {
        if (heads == null || heads.isEmpty()) {
            return;
        }
        // ws.addCell(new Label(0, 0, heads.get(0)));
        for (int i = 0; i < heads.size(); i++) {
            // for(int i=1;i<heads.size();i++){
            /*
             * 添加单元格(Cell)内容addCell() 添加Label对象Label()
             * 数据的类型有很多种、在这里你需要什么类型就导入什么类型 如：jxl.write.DateTime
             * 、jxl.write.Number、jxl.write.Label Label(i, 0, columns[i])
             * 其中i为列、0为行、columns[i]为数据
             * 合起来就是说将columns[i]添加到第一行(行、列下标都是从0开始)第i列、样式为什么"色"内容居中
             */
            // ws.addCell(new Label(i, 1, heads.get(i)));
            ws.addCell(new Label(i, 0, heads.get(i)));
        }
    }

    private ExportData convertData(List<?> data, Collection<String> excludes, Class<?> clazz, int column) {
        // 获取cpu各数，也即同时启动线程数
        int threadsNum = Runtime.getRuntime().availableProcessors();
        // int threadsNum = 2;
        CyclicBarrier barrier = new CyclicBarrier(threadsNum, new Runnable() {
            @Override
            public void run() {
                isFinished = true;
            }
        });
        ExecutorService es = Executors.newFixedThreadPool(threadsNum);
        int threadSize = data.size() / threadsNum;
        ExportData ed = new ExportData();
        ed.setData(new String[data.size()][column]);
        if (threadSize == 0) {
            barrier = new CyclicBarrier(1, new Runnable() {
                @Override
                public void run() {
                    isFinished = true;
                }
            });
            es.execute(new ExportJob(barrier, ed, data, clazz, excludes, 0, data.size()));
        } else {
            for (int i = 0; i < threadsNum; i++) {
                if (i == threadsNum - 1) {
                    // System.out.println(Thread.currentThread().getName() +
                    // " | begin :" + i*threadSize + "| end :" + data.size());
                    es.execute(new ExportJob(barrier, ed, data, clazz, excludes, i * threadSize, data.size()));
                } else {
                    // System.out.println(Thread.currentThread().getName() +
                    // " | begin :" + i*threadSize + "| end :" + (i+1) *
                    // threadSize);
                    es.execute(new ExportJob(barrier, ed, data, clazz, excludes, i * threadSize, (i + 1) * threadSize));
                }
            }
        }
        return ed;
    }

    /**
     * 生成表体部分
     * 
     * @param ws
     *            Excel对象
     * @param _data
     *            表体数据
     * @throws WriteException
     * @throws RowsExceededException
     */
    private void writeExcelBody(WritableSheet ws, String[][] _data) throws RowsExceededException, WriteException {
        for (int i = 0; i < _data.length; i++) {
            String[] dataRow = _data[i];
            for (int j = 0; j < dataRow.length; j++) {
                ws.addCell(new Label(j, i + 1, dataRow[j]));
            }
        }
    }

    public void exportTemplate(String fileName, HttpServletResponse response) throws Exception {
        this.exportTemplate(new File(fileName), response);
    }

    public void exportTemplate(File file, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        // 导出文件名称设定
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(file.getName().getBytes("gb2312"), "ISO8859-1"));
        OutputStream out = response.getOutputStream();
        InputStream is = new FileInputStream(file);
        int num;
        byte[] buffer = new byte[1024];
        while ((num = is.read(buffer)) > 0) {
            out.write(buffer, 0, num);
        }
        // out.close();
        is.close();
    }

}

/**
 * 实际需要到处的数据
 * 
 * @author XuSemon
 */
class ExportData {
    private String[][] data = null;

    public synchronized void add(int row, int column, String value) {
        data[row][column] = value;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
}

class ExportJob implements Runnable {
    private CyclicBarrier barrier;
    private ExportData ed;
    private List<?> data;
    private Class<?> clazz;
    private Collection<String> excludes;
    private int begin;
    private int end;

    public ExportJob(CyclicBarrier barrier, ExportData ed, List<?> data, Class<?> clazz, Collection<String> excludes,
            int begin, int end) {
        this.barrier = barrier;
        this.ed = ed;
        this.data = data;
        this.clazz = clazz;
        this.excludes = excludes;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            // String threadName = Thread.currentThread().getName();
            for (int i = begin; i < end; i++) {
                Thread.sleep(1);
                Object o = data.get(i);
                // 获取类全部属性字段
                Field[] fields = clazz.getDeclaredFields();
                int skip = 0;
                for (int j = 0; j < fields.length; j++) {
                    Field field = fields[j];
                    // 如果是静态字段，则跳过
                    if (Modifier.isStatic(field.getModifiers())) {
                        skip = skip + 1;
                        continue;
                    }
                    String methodName = null;
                    String fieldName = field.getName();
                    // 如果字段名在排除集合之内，则跳过
                    if (excludes != null && excludes.contains(fieldName)) {
                        skip = skip + 1;
                        continue;
                    }
                    // 获取"获取字段值"方法
                    if (field.getType() == Boolean.TYPE) {
                        if (fieldName.startsWith("is")) {
                            methodName = fieldName;
                        } else {
                            methodName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        }
                    } else {
                        methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    }
                    Method method = clazz.getDeclaredMethod(methodName);
                    Object value = method.invoke(o);
                    if (value != null) {
                        if (field.getType().equals(Date.class)) {
                            value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value);
                        } else if (field.getType().isEnum()) {
                            // 如果字段是枚举类型，则调用枚举的getDisplayName方法获取枚举显示值
                            Method enumMethod = field.getType().getDeclaredMethod("getDisplayName");
                            value = enumMethod.invoke(value);
                        }
                    }

                    // 如果字段是类对象，需要重写类的toString()方法。
                    ed.add(i, j - skip, value == null ? "" : value.toString());
                }
                // System.out.println(threadName + "(" + i + ")" + "|" + "Row :"
                // + (i + 1));
            }
            barrier.await();
            // System.out.println(Thread.currentThread().getName() +
            // " Finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public ExportData getEd() {
        return ed;
    }

    public void setEd(ExportData ed) {
        this.ed = ed;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Collection<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(Collection<String> excludes) {
        this.excludes = excludes;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
