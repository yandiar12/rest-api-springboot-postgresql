/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.service;

import java.io.IOException;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.SqlOutParameter;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

/**
 *
 * @author dhamarsu
 */
@Component
public class PlsqlService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> selectedPackage(String _pack, String _func, Map<String, Object> _params) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withFunctionName(_func);

        List retValue = simpleJdbcCall.executeFunction(ArrayList.class, _params);
        return retValue;
    }

    public List<Map<String, Object>> selectedFunction(String _schema, String _func, Map<String, Object> _params) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName(_func);

        List retValue = simpleJdbcCall.executeFunction(ArrayList.class, _params);
        return retValue;
    }

    public Map<String, Object> executePackageProcedure(String _pack, String _proc, Map<String, Object> _params) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withProcedureName(_proc);

        Map<String, Object> retValue = simpleJdbcCall.execute(_params);
        return retValue;
    }
    
    public Map<String, Object> executePackageProcedureWithoutParam(String _pack, String _proc) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withProcedureName(_proc);

        Map<String, Object> retValue = simpleJdbcCall.execute();
        return retValue;
    }
    
    public Map<String, Object> executePackageFunctionWithoutParamInOut(String _pack, String _func) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withFunctionName(_func);

        Map<String, Object> retValue = simpleJdbcCall.execute();
        return retValue;
    }
    
    public Map<String, Object> executePackageFunctionWithoutParamIn(String _pack, String _func, Map<String, Object> _parammOutTypes) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withFunctionName(_func);

        for (Map.Entry<String, Object> entry : _parammOutTypes.entrySet()) {
            try {
                String key = entry.getKey();
                Object value = entry.getValue();
                simpleJdbcCall.declareParameters(new SqlOutParameter(key, getSqlTypes(value.toString())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        Map<String, Object> retValue = simpleJdbcCall.execute();
        return retValue;
    }
    
    public Map<String, Object> executePackage(String _pack, String _func, Map<String, Object> _params) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withFunctionName(_func);

        Map<String, Object> retValue = simpleJdbcCall.execute(_params);
        return retValue;
    }

    public Map<String, Object> executePackageProcDeclareParam(String _pack, String _proc, Map<String, Object> _parammOutTypes) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withProcedureName(_proc);

        for (Map.Entry<String, Object> entry : _parammOutTypes.entrySet()) {
            try {
                String key = entry.getKey();
                Object value = entry.getValue();
                simpleJdbcCall.declareParameters(new SqlOutParameter(key, getSqlTypes(value.toString())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> retValue = simpleJdbcCall.execute();
        return retValue;
    }

    public Map<String, Object> executePackageWithDeclareParam(String _pack, String _func, Map<String, Object> _parammOutTypes, Map<String, Object> _params) throws SQLException, IOException {
        System.out.println("params " + _params.toString());
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withFunctionName(_func);

        for (Map.Entry<String, Object> entry : _parammOutTypes.entrySet()) {
            try {
                String key = entry.getKey();
                Object value = entry.getValue();
                simpleJdbcCall.declareParameters(new SqlOutParameter(key, getSqlTypes(value.toString())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> retValue = simpleJdbcCall.execute(_params);

        return retValue;
    }

    public Map<String, Object> executePackageWithDeclareParamNoInParam(String _pack, String _func, Map<String, Object> _parammOutTypes) throws SQLException, IOException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(_pack)
                .withFunctionName(_func);

        for (Map.Entry<String, Object> entry : _parammOutTypes.entrySet()) {
            try {
                String key = entry.getKey();
                Object value = entry.getValue();
                simpleJdbcCall.declareParameters(new SqlOutParameter(key, getSqlTypes(value.toString())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> retValue = simpleJdbcCall.execute();

        return retValue;
    }

    public Map<String, Object> executeFunction(String _func, Map<String, Object> _params) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName(_func);

        Map<String, Object> retValue = simpleJdbcCall.execute(_params);
        return retValue;
    }
    
    public Map<String, Object> executePackageWithSchemaName(String _schema, String _pack, String _func, Map<String, Object> _params) throws SQLException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(_schema)
                .withCatalogName(_pack)
                .withFunctionName(_func);

        Map<String, Object> retValue = simpleJdbcCall.execute(_params);
        return retValue;
    }
    
    public Map<String, Object> executePackageWithSchemaAndDeclareParam(String _schema, String _pack, String _func, Map<String, Object> _parammOutTypes, Map<String, Object> _params) throws SQLException, IOException {
        System.out.println("params " + _params.toString());
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(_schema)
                .withCatalogName(_pack)
                .withFunctionName(_func);

        for (Map.Entry<String, Object> entry : _parammOutTypes.entrySet()) {
            try {
                String key = entry.getKey();
                Object value = entry.getValue();
                simpleJdbcCall.declareParameters(new SqlOutParameter(key, getSqlTypes(value.toString())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> retValue = simpleJdbcCall.execute(_params);

        return retValue;
    }
    
    public Map<String, Object> executeFunctionWithSchemaAndDeclareParam(String _schema, String _func, Map<String, Object> _parammOutTypes) throws SQLException, IOException {
        
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(_schema)
                .withFunctionName(_func);

        for (Map.Entry<String, Object> entry : _parammOutTypes.entrySet()) {
            try {
                String key = entry.getKey();
                Object value = entry.getValue();
                simpleJdbcCall.declareParameters(new SqlOutParameter(key, getSqlTypes(value.toString())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> retValue = simpleJdbcCall.execute();

        return retValue;
    }
    
    private int getSqlTypes(String types){
        switch (types) {
            case "CLOB":
                return Types.CLOB;
            case "VARCHAR":
                return Types.VARCHAR;
            case "INTEGER":
                return Types.INTEGER;
            case "NUMBER":
                return Types.DECIMAL;
            case "CURSOR":
                return OracleTypes.CURSOR;    
            default:
                break;
        }
        
        return Types.VARCHAR;
    }
}


