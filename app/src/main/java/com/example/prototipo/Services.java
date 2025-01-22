package com.example.prototipo;


public class Services {

    public String dateFormat(String date){
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);

        return day + "/" + month + "/" + year;
    }

    public String formatarData(String data){
        String day = data.substring(0, 2);
        String month = data.substring(3, 5);
        String year = data.substring(6, 10);

        return year + "-" + month + "-" + day;
    }

}
