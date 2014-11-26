package com.ysten.local.bss.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * ZIP压缩和解压缩
 * Created by frank on 14-11-7.
 */
public class ZipCompress {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        ZipCompress main = new ZipCompress("E:\\data\\801_720p_237.zip");
       /* main.createZipOut();
        main.packToolFiles("E:\\data\\801_720p_237", "");
        main.closeZipOut();*/

        main = new ZipCompress("E:/data/801_720p_237.zip");
        main.createZipOut();
        main.packToolFiles("E:/data/801_720p_237", "");
        main.closeZipOut();

        /*Main.unZipToFolder("c:/test1.zip", "c:/test1");

        Main.unZipToFolder("c:/test2.zip", "c:/test2");*/
    }
    private OutputStream out = null;
    private BufferedOutputStream bos = null;
    private ZipArchiveOutputStream zaos = null;
    private String zipFileName = null;

    public ZipCompress(String zipname) {
        this.zipFileName = zipname;
    }

    public void createZipOut() throws FileNotFoundException, IOException {
        File f = new File(zipFileName);
        out = new FileOutputStream(f);
        bos = new BufferedOutputStream(out);
        zaos = new ZipArchiveOutputStream(bos);
        zaos.setEncoding("UTF-8");

    }

    public void closeZipOut() throws Exception {
        zaos.flush();
        zaos.close();

        bos.flush();
        bos.close();

        out.flush();
        out.close();
    }

    /**
     * 把一个目录打包到zip文件中的某目录
     * @param dirpath   目录绝对地址
     * @param pathName  zip中目录
     */
    public void packToolFiles(String dirpath, String pathName) throws FileNotFoundException, IOException {
        if (StringUtils.isNotEmpty(pathName)) {
            pathName = pathName + File.separator;
        }
        packToolFiles(zaos, dirpath, pathName);
    }

    /**
     * 把一个目录打包到一个指定的zip文件中
     * @param dirpath           目录绝对地址
     * @param pathName       zip文件抽象地址
     */
    public void packToolFiles(ZipArchiveOutputStream zaos, String dirpath, String pathName) throws FileNotFoundException, IOException {

        ByteArrayOutputStream tempbaos = new ByteArrayOutputStream();
        BufferedOutputStream tempbos = new BufferedOutputStream(tempbaos);

        File dir = new File(dirpath);
        //返回此绝对路径下的文件
        File[] files = dir.listFiles();
        if (files == null || files.length < 1) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            //判断此文件是否是一个文件夹
            if (files[i].isDirectory()) {
                packToolFiles(zaos, files[i].getAbsolutePath(), pathName + files[i].getName() + File.separator);
            } else {
                zaos.putArchiveEntry(new ZipArchiveEntry(pathName + files[i].getName()));
                IOUtils.copy(new FileInputStream(files[i].getAbsolutePath()), zaos);
                zaos.closeArchiveEntry();

            }

        }

        tempbaos.flush();
        tempbaos.close();

        tempbos.flush();
        tempbos.close();
    }

    /**
     * 把一个zip文件解压到一个指定的目录中
     * @param zipfilename   zip文件抽象地址
     * @param outputdir     目录绝对地址
     */
    public static void unZipToFolder(String zipfilename, String outputdir) throws IOException {
        File zipfile = new File(zipfilename);
        if (zipfile.exists()) {
            outputdir = outputdir + File.separator;
            FileUtils.forceMkdir(new File(outputdir));

            ZipFile zf = new ZipFile(zipfile, "GBK");
            Enumeration zipArchiveEntrys = zf.getEntries();
            while (zipArchiveEntrys.hasMoreElements()) {
                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                if (zipArchiveEntry.isDirectory()) {
                    FileUtils.forceMkdir(new File(outputdir + zipArchiveEntry.getName() + File.separator));
                } else {
                    IOUtils.copy(zf.getInputStream(zipArchiveEntry), FileUtils.openOutputStream(new File(outputdir + zipArchiveEntry.getName())));
                }
            }
        } else {
            throw new IOException("指定的解压文件不存在：\t" + zipfilename);
        }
    }
}
