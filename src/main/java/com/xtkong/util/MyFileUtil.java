package com.xtkong.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by tangye on 2018/11/14.
 */
public class MyFileUtil {

    public static void CopyTo(File source, File dest) throws Exception{
        FileUtils.copyFile(source, dest);
    }

    public static String FileMD5(String filePath) throws Exception
    {
        String result=null;
        try
        {
            result=DigestUtils.md5Hex(new FileInputStream(filePath));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public static String FileMD5(byte[] bytes) throws Exception
    {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            ByteArrayInputStream bais=new ByteArrayInputStream(bytes);
            //FileInputStream fis = new FileInputStream(f);
            while ((len = bais.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return bi.toString(16);
    }

}
