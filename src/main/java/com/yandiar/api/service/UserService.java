/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.service;

import com.yandiar.api.common.AppServer;
import com.yandiar.api.model.dto.UserDto;
import com.yandiar.api.model.response.ResponseModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author YAR
 */
@Service
@Slf4j
public class UserService {
    
    @Autowired
    private PlsqlService plsqlService;
    
    public ResponseModel getUserbyId(String iduser) {
        ResponseModel res = new ResponseModel();
        UserDto userDto = new UserDto();
        try {

            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("in_id_user", iduser.toLowerCase());
            Map<String, Object> mapParamOut = AppServer.getMapResultStandarWithData();

            Map<String, Object> mapResult = plsqlService.executePackageWithSchemaAndDeclareParam("kct", "pkg_user_kct", "getDataProfileUser", mapParamOut, mapParam);
            res = AppServer.setResultMessage(mapResult);
            if (res.isSuccess()) {
                if (res.getData() != null) {
                    for (Map<String, Object> map : (List<Map<String, Object>>) res.getData()) {
                        userDto.setMail(AppServer.ObjectToString(map.get("mail")));
                        userDto.setUserName(AppServer.ObjectToString(map.get("user_name")));
                        userDto.setFirstName(AppServer.ObjectToString(map.get("first_name")));
                        userDto.setLastName(AppServer.ObjectToString(map.get("last_name")));
                        userDto.setUnitId(AppServer.ObjectToString(map.get("unitid")));
                        userDto.setCreateBy(AppServer.ObjectToString(map.get("createby")));
                        userDto.setCreateDate(AppServer.ObjectToString(map.get("createdate")));
                        userDto.setStatus(AppServer.ObjectToString(map.get("status")));
                        userDto.setLoginDate(AppServer.ObjectToString(map.get("tglakhirlogin")));
                        userDto.setLogoutDate(AppServer.ObjectToString(map.get("tglakhirlogout")));
                        userDto.setToken(AppServer.ObjectToString(map.get("token")));
                        userDto.setTokenExpiredDate(AppServer.ObjectToString(map.get("tglexpired_token")));
                        userDto.setAppVersion(AppServer.ObjectToString(map.get("app_mobile_version")));
                    }

                    res.setData(userDto);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.setSuccess(false);
            res.setMessage("EX :" + e.getMessage());
        }
        return res;
    }
    
    public ResponseModel getUserRegistrationData(String username, int isFilter, String jenis, String text) {
        ResponseModel res = new ResponseModel();
        try {
            
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("in_id_user", username.toLowerCase());
            mapParam.put("is_filter_cari", isFilter);
            mapParam.put("in_jenis_cari", jenis);
            mapParam.put("in_isi_cari", text);
            
            Map<String, Object> mapParamOut = AppServer.getMapResultStandar();
            Map<String, Object> mapResult = plsqlService.executePackageWithSchemaAndDeclareParam("kct", "pkg_user_kct", "getDataRegistrasiBelumSah", mapParamOut, mapParam);
            res = AppServer.setResultMessage(mapResult);
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage("EX :" + e.getMessage());
            log.error("ERROR : {}", e.getMessage());
        }
        return res;
    }

}
