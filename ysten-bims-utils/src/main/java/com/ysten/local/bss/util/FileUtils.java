package com.ysten.local.bss.util;

import com.ysten.local.bss.util.bean.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 * Created by frank on 14-8-22.
 */
public class FileUtils {

    public static File saveFileFromInputStream(InputStream inputStream, String path, String fileName) throws IOException {
        File _file = new File(path);
        if (!_file.exists()) {
            _file.mkdirs();
        }
        File file = new File(path + File.separator + fileName);
        FileOutputStream fs = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 1024];
        int byteread;
        while ((byteread = inputStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
            fs.flush();
        }
        fs.close();
        inputStream.close();
        return file;
    }

    public static void uploadMultipartFiles(String path, MultipartFile... files) throws IOException{
        for (MultipartFile multipartFile : files) {
            if (multipartFile != null && StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
                FileUtils.saveFileFromInputStream(multipartFile.getInputStream(), path, multipartFile.getOriginalFilename());
            }
        }
    }

    public static String getDeviceCodesFromUploadFile(MultipartFile deviceCodeFile, String path) throws IOException {
        String fileName = deviceCodeFile.getOriginalFilename() + "_" + new Date().getTime();
        File targetFile = saveFileFromInputStream(deviceCodeFile.getInputStream(), path, fileName);
        if (targetFile != null) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(targetFile), Constant.UTF_ENCODE);
            BufferedReader bufferedReader = new BufferedReader(read);
            StringBuffer stringBuffer = new StringBuffer();
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (StringUtils.isBlank(lineTxt)) continue;
                lineTxt = StringUtils.trim(lineTxt);
                stringBuffer.append(lineTxt + ",");
            }
            read.close();
            return stringBuffer.toString();
        }
        return "";
    }

    public static void compress(String path,String fileName){
        
    }
}
