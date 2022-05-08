package edu;

import java.util.Date;

public class LogEntery {
    private String ipAdress;
    private Date accesTime;
    private String request;
    private int statusCode;//number refers weather the site found or not
    private int bytesReturned;//number of bytes that is returned to the user who made the request
    private String contant;
    private String date;

    // public LogEntery(String content) {
    //     this.contant=content;

    // }
    public LogEntery(String ip, String date,String req, int statusNum, int bytesNum) {
        this.ipAdress=ip;
        this.date=date;
        this.request=req;
        this.statusCode=statusNum;
        this.bytesReturned=bytesNum;

    }
    //getters for the private attributes
    public String getIP() {
        return ipAdress;
    }
    public Date getAccestime(){
        return accesTime;
    }
    public String getRequest(){
        return request;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public int getBytesReturned() {
        return bytesReturned;
    }
    public String getContant() {
        return contant;
    }
    public String getDateStr() {
        return date;
    }

    public String toString(){
        return ipAdress+" "+date+" "+request+" "+statusCode+" "+bytesReturned;
    }
    public static void main(String[] args) {
        // LogEntery le =  new LogEntery("1.2.3.4", "30/Sep/2015:07:59:46 -0400", "open Google", 200, 500);
        // System.out.println(le);
    }

}

