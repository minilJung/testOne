package com.ebc.ecard.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

@Service
public class XeCommon {
    private static String OS = System.getProperty("os.name").toLowerCase();

    private String filePathWin = "D:/Files/";
    private String filePathOther = "/Files/";

    @Resource
    S3 s3;

    public String getToDate(String format) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat((format == null || format.equals("") ? "yyyyMMdd" : format));

        return sdf.format(d);
    }

    public String getUuid(boolean upperFlag) {
        String uuid = UUID.randomUUID().toString();
        return (upperFlag)
                ? uuid.toUpperCase()
                : uuid.toLowerCase();
    }

    public String getRandomString(boolean upperFlag, int size) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            boolean isNumber = Math.floor(Math.random() * 26) == 0;

            int weight = (isNumber) ? 10 : 26;
            int startIndex = (isNumber) ? 48 : 97;
            builder.append((char) Math.floor((Math.random() * weight) + startIndex));
        }

        String randomString = builder.toString();
        return (upperFlag)
                ? randomString.toUpperCase()
                : randomString.toLowerCase();
    }

    public List<FileBean> fileUpload(MultipartRequest mr) throws Exception {
        List<FileBean> makeFileList = new ArrayList<FileBean>();

        String dirName = getToDate(null);
        System.out.println("### dirName : " + dirName);

        String filePath = (OS.indexOf("win") == 0 ? filePathWin : filePathOther) + dirName;
        System.out.println("### filePath: " + filePath);

        File dirPath = new File(filePath + "/");
        if (!dirPath.exists()) {
            dirPath.mkdirs();
            if (OS.indexOf("win") != 0) {
                String cmd = "chmod 777 " + filePath;
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec(cmd);
                p.waitFor();
            }
            ;
        }
        ;

        Iterator<String> ite = mr.getFileNames();
        while (ite.hasNext()) {
            String fileKey = ite.next();

            MultipartFile mf = mr.getFile(fileKey);
            String oriFileName = mf.getOriginalFilename();

            if (oriFileName != null && !oriFileName.equals("")) {
                String fileExt = oriFileName.substring(oriFileName.lastIndexOf("."));
                String newFileName = getRandomString(false, 0) + fileExt;

                File file = new File(filePath + "/" + newFileName);

                // 경로에 파일 저장
                mf.transferTo(file);

//				makeFileList.add(new FileBean(newFileName, filePath + "/" + newFileName, oriFileName, fileExt));
            } else {

            }
        }

        return makeFileList;
    }

    public File fileConvert(MultipartFile mf) throws IOException {
        File file = new File(
            System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + mf.getOriginalFilename()
        );

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mf.getBytes());
        fos.close();

        return file;
    }

    public String getS3FilePath(String fileName) {
        return s3.getFilePath(fileName);
    }

    public String getIpAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
