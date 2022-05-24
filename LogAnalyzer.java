package edu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class LogAnalyzer {
    public ArrayList<LogEntery> records;

    public LogAnalyzer() {
        records = new ArrayList<LogEntery>();
    }

    public ArrayList<LogEntery> readFile(File file)
            throws FileNotFoundException, ParseException, NumberFormatException {


        Scanner scan = new Scanner(file);
        String attrtibutes = "";

        while (scan.hasNextLine()) {
            // splitting text to get LogEntery attributes
            String ip = scan.findInLine("\\d+.\\d+.\\d+.\\d+ - -");
            attrtibutes = scan.nextLine();
            String date = attrtibutes.substring(attrtibutes.indexOf("[") + 1, attrtibutes.indexOf("]"));
            String req = attrtibutes.split("\"")[1];

            String status = attrtibutes.substring(attrtibutes.lastIndexOf(" ") - 3, attrtibutes.lastIndexOf(" "));
            int statusNum = Integer.parseInt(status.trim());

            String bytes = attrtibutes.substring(attrtibutes.lastIndexOf(" "));
            int bytesNum = Integer.parseInt(bytes.trim());

            LogEntery le = new LogEntery(ip, date, "\"" + req + "\"", statusNum, bytesNum);
            records.add(le);
        }
        scan.close();
        return records;

    }

    public int FindUniqueIP() {// using HashSet
        HashSet<String> uniqueIp = new HashSet<>();// will NOT store object.ip that appears more than once
        for (LogEntery l : records) { // will take the content of the instence variable records
            uniqueIp.add(l.getIP().replaceAll(" - -", ""));
        }
        System.out.println("Unique IP addresses :\n-------------------");
        System.out.println(uniqueIp);
        System.out.print("With total number of ");
        return uniqueIp.size();
    }

    public void printAllHigherThanNum(int num) {
        HashSet<Integer> status = new HashSet<>();
        System.out.println("\nThe log Enteries that has more than " + num + " status code is :\n-------------------");
        for (LogEntery logEntery : records) {
            if (logEntery.getStatusCode() >= num) {
                status.add(logEntery.getStatusCode());
            }
        }
    }

    public void printAllHigherThanNum2(int num) {
        ArrayList<Integer> status = new ArrayList<>();
        System.out.println(
                "\nThe unique log Enteries that has more than " + num + " status code is :\n-------------------");
        for (LogEntery logEntery : records) {
            if (logEntery.getStatusCode() > num && !status.contains(logEntery.getStatusCode())) {
                status.add(logEntery.getStatusCode());
            }
        }
        System.out.println(status + " of total number of " + status.size());
    }

    public int uniqueIPVisitsOnDay(String someDay) {
        HashSet<String> items = new HashSet<>();
        String month, day;
        for (LogEntery logEntery : records) {
            day = someDay.trim().substring(someDay.length() - 2);
            month = someDay.trim().substring(0, 3);
            if (logEntery.getDateStr().substring(0, 2).equals(day)
                    && logEntery.getDateStr().substring(3, 6).equals(month)) {

                items.add(logEntery.getIP().replaceAll(" - -", ""));
            }
        }
        System.out.println("The IP addresses of visits in " + someDay + " :\n-------------------\n" + items);
        System.out.print("Of total number of ");
        return items.size();

    }

    public int countUniqueIPsInRange(int low, int high) {
        System.out.println("The unique IPs between " + low + " and " + high + ":\n-------------------");
        HashSet<String> ipList = new HashSet<>();
        for (LogEntery logEntery : records) {
            if (logEntery.getStatusCode() >= low && logEntery.getStatusCode() <= high) {
                ipList.add(logEntery.getIP().replaceAll(" - -", ""));
            }
        }
        System.out.println(ipList);
        System.out.print("With ArrayList of size ");
        return ipList.size();
    }

    public HashMap<String, Integer> countVisitsPerIP() {// for printing ip count
        HashMap<String, Integer> ipMap = new HashMap<>();
        System.out.println("\s\sIP\t\tnumber of visits\n\s--------------------------------");
        for (LogEntery logEntery : records) {
            String ipKey = logEntery.getIP().replaceAll(" - -", "");
            if (!ipMap.containsKey(ipKey)) {
                ipMap.put(ipKey, 1);
            } else {
                ipMap.put(ipKey, ipMap.get(ipKey) + 1);
            }
        }
        for (String ipKey : ipMap.keySet()) {
            System.out.println("\s" + ipKey + "\t\t" + ipMap.get(ipKey));
        }
        System.out.println("\nUnique IPs = " + ipMap.size());
        return ipMap;
    }

    public int mostNumberVisitsByIP(HashMap<String, Integer> ipMap) {
        
        int maxVisits = 0;
        for (String key : ipMap.keySet()) {
            if (ipMap.get(key) > maxVisits) {
                maxVisits = ipMap.get(key);
            }
        }
        System.out.print("Max number of visits is ");
        return maxVisits;
    }

    public void iPsMostVisits(HashMap<String, Integer> ipMap) {
        ArrayList<String> list = new ArrayList<>();
        int max = 0;
        String k = "";
        for (String key : ipMap.keySet()) {
            if (ipMap.get(key) >= max) {
                max = ipMap.get(key);
                k = key;

            }
        }
        list.add(k);
        System.out.println(list);
    }

    // in the LogAnalyzer class, write the method iPsForDays, which has no
    // parameters. This method returns a HashMap<String, ArrayList<String>> that
    // uses records and maps days from web logs to an ArrayList of IP addresses that
    // occurred on that day (including repeated IP addresses). A day is in the
    // format “MMM DD” where MMM is the first three characters of the month name
    // with the first letter capital and the others in lowercase, and DD is the day
    // in two digits (examples are “Dec 05” and “Apr 22”). For example, for the file
    // weblog3-short_log, after building this HashMap, if you print it out, you will
    // see that Sep 14 maps to one IP address, Sep 21 maps to four IP addresses, and
    // Sep 30 maps to five IP addresses.

    public HashMap<String, ArrayList<String>> iPsForDays() {
        // maps date to IPs visits on this date
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        String date;

        for (LogEntery logEntery : records) {
            ArrayList<String> ipList = new ArrayList<String>();
            String ip = logEntery.getIP().replaceAll(" - -", "");
            date = logEntery.getDateStr().substring(0, 6);

            if (!map.containsKey(date)) {
                ipList.add(ip);
                map.put(date, ipList);
            } else {
                ipList = map.get(date);
                // System.out.println(dateList);
                if (map.get(date).equals(ipList)) {
                    ipList.add(ip);
                    map.put(date, ipList);
                }
            }
        }
        return map;
    }

    // In the LogAnalyzer class, write the method dayWithMostIPVisits, which has one
    // parameter that is a HashMap<String, ArrayList<String>> that uses records and
    // maps days from web logs to an ArrayList of IP addresses that occurred on that
    // day. This method returns the day that has the most IP address visits. If
    // there is a tie, then return any such day. For example, if you use the file
    // weblog3-short_log, then this method should return the day most visited as Sep
    // 30.
    public void dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
        int max = 0;
        String dayWithMaxIP = "";
        for (String date : map.keySet()) {
            if (map.get(date).size() > max) {
                max = map.get(date).size();
                dayWithMaxIP = date;
            }
        }
        System.out.println("Date with max ip visits is " + dayWithMaxIP);
    }
    // In the LogAnalyzer class, write the method iPsWithMostVisitsOnDay, which has
    // two parameters—the first one is a HashMap<String, ArrayList<String>> that
    // uses records and maps days from web logs to an ArrayList of IP addresses that
    // occurred on that day, and the second parameter is a String representing a day
    // in the format “MMM DD” described above. This method returns an
    // ArrayList<String> of IP addresses that had the most accesses on the given
    // day. For example, if you use the file weblog3-short_log, and the parameter
    // for the day is “Sep 30”, then there are two IP addresses in the ArrayList
    // returned: 61.15.121.171 and 177.4.40.87. Hint: This method should call
    // another method you have written.

    public void iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String day) {
        int Occurences = 0;
        HashMap<String, Integer> maxOccurences = new HashMap<>();
        if (!map.containsKey(day)) {
            System.out.println("Date not found");

        } else {
            String[] array = new String[map.get(day).size()];
            map.get(day).toArray(array);
            int i = 0;
            for (i = 0; i < array.length; i++) {
                if (!maxOccurences.containsKey(array[i])) {
                    maxOccurences.put(array[i], Occurences++);
                }
            }
        }
        iPsMostVisits(maxOccurences);
    }

    public static void main(String[] args) throws IOException, ParseException {
        LogAnalyzer la = new LogAnalyzer();
        File file = new File("short-test_log.txt");

        ArrayList<LogEntery> list = la.readFile(file);
        // print all list content
        for (LogEntery logEntery : list) {
           System.out.println(logEntery.getStatusCode());
        }
        
        la.FindUniqueIP();
        System.out.println("\n");
        // System.out.println(la.FindUniqueIP());
        // la.printAllHigherThanNum2(400);
        // System.out.println("\n"+la.uniqueIPVisitsOnDay("Sep 27"));

        System.out.println(la.countUniqueIPsInRange(200, 299));
        System.out.println("\n");
        HashMap<String, Integer> ipMap = la.countVisitsPerIP();
        System.out.println(la.mostNumberVisitsByIP(ipMap));
        la.iPsMostVisits(ipMap);

        // la.iPsForDays();
        HashMap<String, ArrayList<String>> map = la.iPsForDays();
        for (String ip : map.keySet()) {
            System.out.println(ip + "\t\t" + map.get(ip));
        }

        la.dayWithMostIPVisits(map);
        la.iPsWithMostVisitsOnDay(map, "29/Sep");
    }
}
