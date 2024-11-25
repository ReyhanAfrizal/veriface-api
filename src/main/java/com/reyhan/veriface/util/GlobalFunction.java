package com.reyhan.veriface.util;


/*
IntelliJ IDEA 2024.2.3 (Community Edition)
Build #IC-242.23339.11, built on September 25, 2024
@Author Reyhan a.k.a. Reyhan Afrizal
Java Developer
Created on 11/21/2024 1:28 PM
@Last Modified 11/21/2024 1:28 PM
Version 1.0
*/


import com.reyhan.veriface.handler.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalFunction {

    public static ResponseEntity<Object> dataBerhasilDisimpan(){
        return new ResponseHandler().
                generateResponse("DATA BERHASIL DISIMPAN",
                        HttpStatus.CREATED,
                        null,null);
    }

    public static ResponseEntity<Object> dataGagalDisimpan(String errorCode){
        return new ResponseHandler().
                generateResponse("DATA GAGAL DISIMPAN",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null,errorCode);
    }

    public static ResponseEntity<Object> dataGagalDihapus(String errorCode){
        return new ResponseHandler().
                generateResponse("DATA GAGAL DIHAPUS",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null,errorCode);
    }

    public static ResponseEntity<Object> dataBerhasilDiubah(){
        return new ResponseHandler().
                generateResponse("DATA BERHASIL DIUBAH",
                        HttpStatus.OK,
                        null,null);
    }
    public static ResponseEntity<Object> dataBerhasilDihapus(){
        return new ResponseHandler().
                generateResponse("DATA BERHASIL DIHAPUS",
                        HttpStatus.OK,
                        null,null);
    }

    public static ResponseEntity<Object> dataTidakDitemukan(){
        return new ResponseHandler().
                generateResponse("DATA TIDAK DITEMUKAN",
                        HttpStatus.NOT_FOUND,
                        null,"G-01-002");
    }

    public static ResponseEntity<Object> akunBelumAktif(){
        return new ResponseHandler().
                generateResponse("AKUN BELUM AKTIF",
                        HttpStatus.BAD_REQUEST,
                        null,"G-01-003");
    }

    public static ResponseEntity<Object> usernameSudahTerdaftar(){
        return new ResponseHandler().
                generateResponse("USERNAME SUDAH TERDAFTAR",
                        HttpStatus.BAD_REQUEST,
                        null,"G-01-004");
    }

    public static Map<String,Object> convertClassToObject(Object object){
        Map<String, Object> map = new LinkedHashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
//                LoggingFile.exceptionStringz("GlobalFunction","convertClassToObject",e, OtherConfig.getFlagLogging());
            }
        }
        return map;
    }

    public static Map<String,Object> convertClassToObject(Object object,String strNull){
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object)==null?"-":field.get(object));
            } catch (IllegalAccessException e) {
//                LoggingFile.exceptionStringz("GlobalFunction","convertClassToObject",e, OtherConfig.getFlagLogging());
            }
        }
        return map;
    }

    /** saya hardcode pattern nya karena kebutuhan report aja , jadi tidak perlu memasukkan pattern saat menggunakan functional ini */
    public static String formatingDateDDMMMMYYYY(){
        /** mengambil current time saat ini **/
        return new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new Date());
    }

    //    public static void generateExcelFile(Object obj, List<Object> lobj){
//        Map<String,Object> map = GlobalFunction.convertClassToObject(obj);
//        List<String> listTampungSebentar = new ArrayList<>();
//        for (Map.Entry<String,Object> entry : map.entrySet()) {
//            listTampungSebentar.add(entry.getKey());
//        }
//        int intListTampungSebentar = listTampungSebentar.size();
//        String [] headerArr = new String[intListTampungSebentar];
//        String [] loopDataArr = new String[intListTampungSebentar];
//        for (int i = 0; i < intListTampungSebentar; i++) {
//            headerArr[i] = GlobalFunction.camelToStandar(String.valueOf(listTampungSebentar.get(i))).toUpperCase();//BIASANYA JUDUL KOLOM DIBUAT HURUF BESAR DENGAN FORMAT STANDARD
//            loopDataArr[i] = listTampungSebentar.get(i);
////            map.get("noHp");
////            map.get("NO_HP");
//        }
//        String[][] strBody = new String[listRespUser.size()][intListTampungSebentar];
//        for (int i = 0; i < listRespUser.size(); i++) {
//            map = GlobalFunction.convertClassToObject(listRespUser.get(i));
//            for (int j = 0; j < intListTampungSebentar; j++) {
//                strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
//            }
//        }
//        new ExcelWriter(strBody, headerArr,"sheet-1", response);
//    }
    public static String camelToStandar(String str)
    {
        StringBuilder sb = new StringBuilder();
        char c = str.charAt(0);
        sb.append(Character.toLowerCase(c));
        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                sb.append(' ').append(Character.toLowerCase(ch));
            }
            else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}

