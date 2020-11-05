/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandiar.api.exception.FileStorageException;
import com.yandiar.api.model.response.ResponseModel;
import com.yandiar.api.model.UserToken;
import com.yandiar.api.model.response.ResponseModel2;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author USER
 */
public class AppServer {
    public static final String WEB_PORTAL = "WEBPORTAL";
    public static final String JWT_SECRET_KEY = "s3cretK3y";
    
    public static String ObjectToString(Object obj){
        return obj==null?null:obj.toString();
    }
    public static int ObjectToInt(Object obj){
        return obj==null?null:Integer.parseInt(obj.toString());
    }
    
    public static <T> T decodeResponseModel(ResponseModel model, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapperw = new ObjectMapper();
        return (T)mapper.readValue(mapperw.writeValueAsString(model.getData()), clazz);
    }
    
    public static <T> T decodeResponseModel(String model, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T)mapper.readValue(model, clazz);
    }
    
    public static String encodeUserDto(UserToken model){
        ObjectMapper mapperw = new ObjectMapper();
        String ret="";
        try {
            ret = mapperw.writeValueAsString(model);
        } catch (Exception ex) {
            Logger.getLogger(AppServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public static String encodeModel(Object model){
        ObjectMapper mapperw = new ObjectMapper();
        String ret="";
        try {
            ret = mapperw.writeValueAsString(model);
        } catch (Exception ex) {
            Logger.getLogger(AppServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public static Map<String, Object> getMapResultStandarWithData(){
        Map<String, Object> mapParamOut = new HashMap<>();
        mapParamOut.put("OUT_DATA", "CURSOR");
        mapParamOut.put("OUT_MESSAGE", "VARCHAR");
        mapParamOut.put("RETURN", "INTEGER");
        return mapParamOut;
    }
    
    public static Map<String, Object> getMapResultStandarWithDataDetail(){
        Map<String, Object> mapParamOut = new HashMap<>();
        mapParamOut.put("OUT_DATA", "CURSOR");
        mapParamOut.put("OUT_DATA_DETIL", "CURSOR");
        mapParamOut.put("OUT_MESSAGE", "VARCHAR");
        mapParamOut.put("RETURN", "INTEGER");
        return mapParamOut;
    }
    
    public static Map<String, Object> getMapResultStandar(){
        Map<String, Object> mapParamOut = new HashMap<>();
        mapParamOut.put("OUT_DATA", "CURSOR");
        mapParamOut.put("OUT_MESSAGE", "VARCHAR");
        mapParamOut.put("RETURN", "INTEGER");
        return mapParamOut;
    }
    
    public static Map<String, Object> getMapResultNoWithData(){
        Map<String, Object> mapParamOut = new HashMap<>();
        mapParamOut.put("OUT_MESSAGE", "VARCHAR");
        mapParamOut.put("RETURN", "INTEGER");
        return mapParamOut;
    }
    
    public static Map<String, Object> getMapResultBiaya(){
        Map<String, Object> mapParamOut = new HashMap<>();
        mapParamOut.put("OUT_DATA", "CURSOR");
        mapParamOut.put("OUT_DATA_BIAYA", "CURSOR");
        mapParamOut.put("OUT_MESSAGE", "VARCHAR");
        mapParamOut.put("RETURN", "INTEGER");
        return mapParamOut;
    }
    
    public static ResponseModel setResultMessage(Map<String, Object> mapReturn) {
        ResponseModel trm = new ResponseModel();
        try {
            for (Map.Entry<String, Object> entry : mapReturn.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals("RETURN")) {
                    if (value != null) {
                        if (Integer.parseInt(value.toString())==1){
                           trm.setSuccess(true);
                        }else{
                            trm.setSuccess(false);
                        }                        
                    } else {
                        trm.setSuccess(false);
                    }
                }
                if (key.equals("OUT_MESSAGE")) {
                    if (value != null) {
                        trm.setMessage(value.toString());
                    } else {
                        trm.setMessage("");
                    }
                }
                if (key.equals("OUT_DATA")) {
                    if (value != null) {
                        trm.setData(setLowerColumnNameListMap((List<Map<String, Object>>) value));
                    } else {
                        trm.setData(null);
                    }
                }                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            trm.setSuccess(false);
            trm.setMessage(ex.getMessage());
        }
        return trm;
    }
    
    public static ResponseModel2 setResultMessageWithDataDetail(Map<String, Object> mapReturn) {
        ResponseModel2 trm = new ResponseModel2();
        try {
            for (Map.Entry<String, Object> entry : mapReturn.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals("RETURN")) {
                    if (value != null) {
                        if (Integer.parseInt(value.toString())==1){
                           trm.setSuccess(true);
                        }else{
                            trm.setSuccess(false);
                        }                        
                    } else {
                        trm.setSuccess(false);
                    }
                }
                if (key.equals("OUT_MESSAGE")) {
                    if (value != null) {
                        trm.setMessage(value.toString());
                    } else {
                        trm.setMessage("");
                    }
                }
                if (key.equals("OUT_DATA")) {
                    if (value != null) {
                        trm.setData(setLowerColumnNameListMap((List<Map<String, Object>>) value));
                    } else {
                        trm.setData(null);
                    }
                }
                if (key.equals("OUT_DATA_DETIL")) {
                    if (value != null) {
                        trm.setDataDetail(setLowerColumnNameListMap((List<Map<String, Object>>) value));
                    } else {
                        trm.setDataDetail(null);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            trm.setSuccess(false);
            trm.setMessage(ex.getMessage());
        }
        return trm;
    }
    
    public static ResponseModel setResultMessageNoWithData(Map<String, Object> mapReturn) {
        ResponseModel trm = new ResponseModel();
        try {
            for (Map.Entry<String, Object> entry : mapReturn.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals("RETURN")) {
                    if (value != null) {
                        if (Integer.parseInt(value.toString())==1){
                           trm.setSuccess(true);
                        }else{
                            trm.setSuccess(false);
                        }                        
                    } else {
                        trm.setSuccess(false);
                    }
                }
                if (key.equals("OUT_MESSAGE")) {
                    if (value != null) {
                        trm.setMessage(value.toString());
                    } else {
                        trm.setMessage("");
                    }
                }
                               
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            trm.setSuccess(false);
            trm.setMessage(ex.getMessage());
        }
        return trm;
    }
        
    public static Map<String, Object> setLowerColumnNameMap(Map<String, Object> mapInput){
        Map<String, Object> mapReturn = new HashMap<>();
        for (Map.Entry<String, Object> entry : mapInput.entrySet()) {
            mapReturn.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return mapReturn;
    }
    
    public static List<Map<String, Object>> setLowerColumnNameListMap(List<Map<String, Object>> listMapInput){     
        List<Map<String, Object>> lstMap = new ArrayList<>();
        for(Map<String, Object> mapEntry : listMapInput){
            Map<String, Object> mapReturn = new HashMap<>();
            for (Map.Entry<String, Object> entry : mapEntry.entrySet()) {
                mapReturn.put(entry.getKey().toLowerCase(), entry.getValue());
            }
            lstMap.add(mapReturn);
        }
        return lstMap;
    }
    
    public static String storeFile(String id_user, String dir, @Nonnull String appsource, @Nonnull MultipartFile file) {
        // Normalize file name
        String fileName= generateUUIDString() +"_"+ Normalizer.normalize(id_user, Form.NFD).replaceAll("[^A-Za-z0-9]", "") +"."+ FilenameUtils.getExtension(file.getOriginalFilename());
        String contentType = file.getContentType();
        Path fileStorageLocation = Paths.get(dir).toAbsolutePath().normalize();
        //Path path = fileStorageLocation.resolve(appsource);

        if (!fileStorageLocation.toFile().exists()) {
            try {
                Files.createDirectory(fileStorageLocation);
            } catch (Exception e) {
                throw new RuntimeException("Could not create the directory where files will be stored.", e);
            }
        }
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            /*if (!contentType.contains("image/jpeg") && !contentType.contains("video/mp4")) {
                throw new FileStorageException("Sorry! Invalid content type file");
            }*/

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }  
    
    public static Resource loadFileAsResource(String dir, @Nonnull String appSource, @Nonnull String fileName) {
        try {
            Path filePath = Paths.get(dir).toAbsolutePath().normalize().resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    public static String generateUUIDString()
    {
      String tGeneratedUUIDString = null;
      tGeneratedUUIDString = UUID.randomUUID().toString().replace("-", "").toUpperCase();
      return tGeneratedUUIDString;
    }
    
    public static String getPathFile(String baseUrl, String dir, String fileName){
        if (fileName==null){
            return "";
        }
        if (fileName.equals("")){
            return "";
        }
        
        return ServletUriComponentsBuilder.fromUriString(baseUrl)
                .path("/downloadFile/")
                .path(dir + "/")
                .path(fileName)
                .toUriString();
    }
    
    public static void compressedImage(BufferedImage image, String filename) {
        try {
            Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer;
            writer = (ImageWriter) iter.next();

            ImageOutputStream ios = ImageIO.createImageOutputStream(new File(filename));
            writer.setOutput(ios);

            ImageWriteParam iwparam = writer.getDefaultWriteParam();

            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwparam.setCompressionQuality(0.0F);

            writer.write(null, new IIOImage(image, null, null), iwparam);

            ios.flush();
            writer.dispose();
            ios.close();
        } catch (IOException ioE) {
            ioE.printStackTrace();
            throw new FileStorageException("Compress image failed!", ioE);
        }
    }
    
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
