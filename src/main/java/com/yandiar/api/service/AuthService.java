/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.service;

import com.yandiar.api.common.AppServer;
import com.yandiar.api.common.TokenUtil;
import com.yandiar.api.model.response.ResponseModel;
import com.yandiar.api.model.dto.UserDto;
import com.yandiar.api.model.UserToken;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Slf4j
@Service
public class AuthService {

    @Autowired
    private PlsqlService plsqlService;

    public ResponseModel login(String iduser, String password, String appsource) {
        ResponseModel res = new ResponseModel();
        UserDto userDto = new UserDto();
        try {

            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("in_id_user", iduser.toLowerCase());
            mapParam.put("in_password", password);
            Map<String, Object> mapParamOut = AppServer.getMapResultStandarWithData();

            Map<String, Object> mapResult = plsqlService.executePackageWithSchemaAndDeclareParam("kct", "pkg_user_kct", "getValidasiLogin", mapParamOut, mapParam);
            res = AppServer.setResultMessage(mapResult);
            log.info("result : {}", res);
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
                }

                String token = TokenUtil.buildJWTToken(new UserToken(userDto.getUserName(), userDto.getMail(), userDto.getUnitId()));
                userDto.setToken(token);

                mapParam = new HashMap<>();
                mapParam.put("in_id_user", iduser.toLowerCase());
                mapParam.put("in_appsource", appsource);
                mapParam.put("in_token", token);
                mapParamOut = new HashMap<>();
                mapParamOut.put("OUT_MESSAGE", "VARCHAR");
                mapParamOut.put("RETURN", "INTEGER");
                mapResult = plsqlService.executePackageWithSchemaAndDeclareParam("kct", "pkg_user_kct", "setTokenLogin", mapParamOut, mapParam);
                res = AppServer.setResultMessage(mapResult);
                if (res.isSuccess()) {
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

    public ResponseModel getValidasiToken(String iduser, String appsource, String token) {
        ResponseModel res = new ResponseModel();
        UserDto userDto = new UserDto();
        try {

            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("in_id_user", iduser.toLowerCase());
            mapParam.put("in_appsource", appsource);
            mapParam.put("in_token", token);
            Map<String, Object> mapParamOut = AppServer.getMapResultStandarWithData();

            Map<String, Object> mapResult = plsqlService.executePackageWithSchemaAndDeclareParam("kct", "pkg_user_kct", "getValidasiToken", mapParamOut, mapParam);
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

    public ResponseModel logout(String username, String token) {
        ResponseModel res = new ResponseModel();
        try {

            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("in_id_user", username.toLowerCase());
            mapParam.put("in_token", token);
            Map<String, Object> mapParamOut = new HashMap<>();
            mapParamOut.put("OUT_MESSAGE", "VARCHAR");
            mapParamOut.put("RETURN", "INTEGER");

            Map<String, Object> mapResult = plsqlService.executePackageWithSchemaAndDeclareParam("kct", "pkg_user_kct", "setLogout", mapParamOut, mapParam);
            res = AppServer.setResultMessage(mapResult);

        } catch (Exception e) {
            e.printStackTrace();
            res.setSuccess(false);
            res.setMessage("EX :" + e.getMessage());
        }
        return res;
    }

    public ResponseModel setForceLogout(String email) {
        ResponseModel res = new ResponseModel();
        try {

            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("in_email", email);

            Map<String, Object> mapParamOut = new HashMap<>();
            mapParamOut.put("OUT_MESSAGE", "VARCHAR");
            mapParamOut.put("RETURN", "INTEGER");

            Map<String, Object> mapResult = plsqlService.executePackageWithSchemaAndDeclareParam("secman", "pkg_user_online_new", "setForceLogout", mapParamOut, mapParam);
            res = AppServer.setResultMessage(mapResult);

        } catch (Exception e) {
            e.printStackTrace();
            res.setSuccess(false);
            res.setMessage("EX :" + e.getMessage());
        }
        return res;
    }

}
