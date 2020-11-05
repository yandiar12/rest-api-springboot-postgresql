/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.controller;

import com.yandiar.api.common.AppServer;
import com.yandiar.api.model.HeaderConstant;
import com.yandiar.api.model.response.ResponseModel;
import com.yandiar.api.model.dto.UserDto;
import com.yandiar.api.model.UserToken;
import com.yandiar.api.model.request.LoginReq;
import com.yandiar.api.service.AuthService;
import com.yandiar.api.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author USER
 */
@CrossOrigin
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Auth API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Auth"})
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserService userService;

    @PostMapping(value = "auth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "API Login", response = ResponseModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = HeaderConstant.APPSOURCE, required = true, paramType = "header", dataType = "string")
    })

    private ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse resp, @RequestBody LoginReq login) throws ServletException {
        
        String appsource = req.getHeader(HeaderConstant.APPSOURCE);
        
        ResponseModel res = authService.login(login.getIduser(), login.getPassword(), appsource);
        if (res.isSuccess()) {
            UserDto dto = new UserDto();
            try {
                dto = AppServer.decodeResponseModel(res, UserDto.class);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            resp.setHeader(HeaderConstant.AUTHORIZATION, dto.getToken());
        }

        return new ResponseEntity<ResponseModel>(res, HttpStatus.OK);
    }
    
    
//    @PostMapping(value = "auth/user/register", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "User Registration", response = ResponseModel.class, notes = "String")
//    @ApiImplicitParam(name = HeaderConstant.APPSOURCE, required = true, paramType = "header", dataType = "string")
//    private ResponseEntity<?> registerUser(HttpServletRequest req, HttpServletResponse resp, @RequestBody RegisterUserReq registerReq) throws ServletException {
//        ResponseModel res = userService.setRegisterUser(registerReq);
//        return new ResponseEntity<ResponseModel>(res, HttpStatus.OK);
//    }

    @PostMapping(value = "auth/secure/validate")
    @ApiOperation(value = "Validate JWT Token", notes = "Called by filter to validate user token",
            response = ResponseModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = HeaderConstant.AUTHORIZATION, required = true, paramType = "header", dataType = "string"),
        @ApiImplicitParam(name = HeaderConstant.APPSOURCE, required = true, paramType = "header", dataType = "string")
    })
    public ResponseEntity validate(HttpServletRequest req, HttpServletResponse resp) {
        String authHeader = String.valueOf(req.getHeader(HeaderConstant.AUTHORIZATION));
        String token = authHeader.substring(7);
        try {
            Claims claim = (Claims) req.getAttribute("claims");
            UserToken user = AppServer.decodeResponseModel(claim.getSubject(), UserToken.class);
            String appsource = req.getHeader(HeaderConstant.APPSOURCE);

            ResponseModel model = authService.getValidasiToken(user.getUserName(), appsource, token);

            if (model.isSuccess()) {
                resp.setHeader(HeaderConstant.AUTHORIZATION, token);
                return ResponseEntity.ok(model);
            } else {
                return new ResponseEntity<>("Invalid token : " + model.getMessage(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed to validate token {}", e.getMessage());
            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping(value = "auth/logout")
    @ApiOperation(value = "API Logout", response = ResponseModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = HeaderConstant.APPSOURCE, required = false, paramType = "header", dataType = "string")
    })
    public ResponseEntity logout(HttpServletRequest req, @RequestParam("username") String username) throws ServletException {
        Claims claim = (Claims) req.getAttribute("claims");
        UserToken dto = new UserToken();
        //String authHeader = String.valueOf(req.getHeader(HeaderConstant.AUTHORIZATION));
        String token = null; //authHeader.substring(7);
        /*try {
            dto = AppServer.decodeResponseModel(claim.getSubject(), UserToken.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Logout : {}", ex.getMessage());
        }*/

        String appsource = req.getHeader(HeaderConstant.APPSOURCE);
        ResponseModel res = authService.logout(username, token);
        res.setSuccess(true);
        return ResponseEntity.ok(res);
    }

    /*@PostMapping(value = "auth/forcelogout")
    @ApiOperation(value = "API Force Logout", response = ResponseModel.class)

    private ResponseEntity<?> setForceLogout(HttpServletRequest req, HttpServletResponse resp, @RequestParam(name = "in_id_user") String in_id_user) throws ServletException {
        ResponseModel res = authService.setForceLogout(in_id_user);
        return new ResponseEntity<ResponseModel>(res, HttpStatus.OK);
    }*/


//    @PostMapping(value = "auth/setUploadFoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ApiOperation(value = "setUploadFoto", response = ResponseModel.class, notes = "String")
//    @ApiImplicitParams({
////	@ApiImplicitParam(name = HeaderConstant.AUTHORIZATION, required = true, paramType = "header", dataType = "string"),
//        @ApiImplicitParam(name = HeaderConstant.APPSOURCE, required = false, paramType = "header", dataType = "string")
//    })
//    private ResponseEntity<?> setUploadFoto(HttpServletRequest req, HttpServletResponse resp,
//            @RequestParam(name = "in_language") String in_language,
//            @RequestParam(name = "in_id_user") String in_id_user,
//            @RequestParam(name = "foto_user") MultipartFile file) throws ServletException {
//        String appsource = req.getHeader(HeaderConstant.APPSOURCE);
//        
//        String fileName = AppServer.storeFile(appProperties.getUploadDir(), appsource, file);
//        
//        String fileDownloadUri = ServletUriComponentsBuilder.fromUriString(appProperties.getBaseUrl())
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//        
//        UploadFileResponse uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
//        ResponseModel res = new ResponseModel();
//        res.setSuccess(true);
//        res.setMessage("Sukses");
//        res.setData(uploadFileResponse);
//        return new ResponseEntity<ResponseModel>(res, HttpStatus.OK);
//    }
    
    /*@GetMapping(value = "/downloadFile/{pathfolder}/{fileName:.+}")
    @ApiOperation(value = "Download file", response = Resource.class)
    public ResponseEntity<Resource> downloadFile(HttpServletRequest req,
            @PathVariable String pathfolder,
            @PathVariable String fileName,
            @RequestParam(required = false) String disposition) {
        String appsource = req.getHeader(HeaderConstant.APPSOURCE);
        // Load file as Resource
        Resource resource = AppServer.loadFileAsResource(appProperties.getUploadDir() + "/" + pathfolder, appsource, fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = req.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        String contentDisposition = StringUtils.isBlank(disposition) ? "inline" : disposition
                + "; filename=\"" + fileName + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }*/

}
