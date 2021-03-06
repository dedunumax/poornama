package com.poornama.api.reporting;

import com.poornama.api.logging.GlobalLogger;
import com.poornama.api.presentation.PlainDataTableGenerator;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dedunu
 */
public class DoubleTableHelper extends TableHelper {
    private static Logger log = GlobalLogger.getLogger();
    private static String className = DoubleTableHelper.class.getName();

    /**
     * Returns data for bar charts as a String
     *
     * @param dataTable     HashMap&lt;Integer, HashMap&lt;String, Double&gt;&gt;
     * @param startDate     Date
     * @param endDate       Date
     * @param labelMap      Map&lt;Integer, String&gt;
     * @param calendarField int
     * @return String
     */
    public String getChartColumns(HashMap<Integer, HashMap<String, Double>> dataTable, Date startDate, Date endDate, Map<Integer, String> labelMap, int calendarField) {
        log.debug("[" + className + "] getChartColumns() : started");
        String result = "";
        result = getAxis(startDate, endDate, calendarField);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<Integer, String> mapEntry : labelMap.entrySet()) {
            result = result + "[ ";

            HashMap<String, Double> tempHashMap;

            result = result + "\'" + mapEntry.getValue() + "\',";
            tempHashMap = dataTable.get(mapEntry.getKey());

            Date tempDate = startDate;
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTime(tempDate);

            while (tempDate.before(endDate)) {
                if (tempHashMap != null) {
                    if (tempHashMap.get(simpleDateFormat.format(tempDate)) != null) {
                        result = result + tempHashMap.get(simpleDateFormat.format(tempDate)) + ",";
                    } else {
                        result = result + "0,";
                    }
                } else {
                    result = result + "0,";
                }

                tempCalendar.setTime(tempDate);
                tempCalendar.add(calendarField, 1);
                tempDate = tempCalendar.getTime();
            }

            if (result.length() > 0 && result.charAt(result.length() - 1) == ',') {
                result = result.substring(0, result.length() - 1);
            }

            result = result + " ],";

        }

        if (result.length() > 0 && result.charAt(result.length() - 1) == ',') {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    /**
     * Returns date for pie charts as a String
     *
     * @param dataTable     HashMap&lt;Integer, HashMap&lt;String, Double&gt;&gt;
     * @param startDate     Date
     * @param endDate       Date
     * @param labelMap      Map&lt;Integer, String&gt;
     * @param calendarField int
     * @return String
     */
    public String getPieChartColumns(HashMap<Integer, HashMap<String, Double>> dataTable, Date startDate, Date endDate, Map<Integer, String> labelMap, int calendarField) {
        log.debug("[" + className + "] getChartColumns() : started");
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<Integer, String> mapEntry : labelMap.entrySet()) {
            result = result + "[ ";

            HashMap<String, Double> tempHashMap;

            result = result + "\'" + mapEntry.getValue() + "\',";
            tempHashMap = dataTable.get(mapEntry.getKey());

            Date tempDate = startDate;
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTime(tempDate);

            while (tempDate.before(endDate)) {
                if (tempHashMap != null) {
                    if (tempHashMap.get(simpleDateFormat.format(tempDate)) != null) {
                        result = result + tempHashMap.get(simpleDateFormat.format(tempDate)) + ",";
                    } else {
                        result = result + "0,";
                    }
                } else {
                    result = result + "0,";
                }

                tempCalendar.setTime(tempDate);
                tempCalendar.add(calendarField, 1);
                tempDate = tempCalendar.getTime();
            }

            if (result.length() > 0 && result.charAt(result.length() - 1) == ',') {
                result = result.substring(0, result.length() - 1);
            }

            result = result + " ],";

        }

        if (result.length() > 0 && result.charAt(result.length() - 1) == ',') {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    /**
     * Returns the report as a table
     *
     * @param dataTable HashMap&lt;Integer, HashMap&lt;String, Double&gt;&gt;
     * @param labelMap  Map&lt;Integer, String&gt;
     * @param axisList  List&lt;String&gt;
     * @return String
     */
    public String getTable(HashMap<Integer, HashMap<String, Double>> dataTable, Map<Integer, String> labelMap, List<String> axisList) {
        log.debug("[" + className + "] getTable() : started");
        String result;
        PlainDataTableGenerator plainDataTableGenerator = new PlainDataTableGenerator();
        result = plainDataTableGenerator.getStartTable();


        String dataArray[] = new String[axisList.size() + 1];
        int i = 1;
        dataArray[0] = "";
        for (String cellString : axisList) {
            dataArray[i] = cellString;
            i++;
        }

        result = result + plainDataTableGenerator.getTableHeader(dataArray);
        result = result + plainDataTableGenerator.getStartTableBody();

        for (Map.Entry<Integer, String> mapEntry : labelMap.entrySet()) {
            HashMap<String, Double> tempHashMap;
            i = 1;

            tempHashMap = dataTable.get(mapEntry.getKey());
            dataArray[0] = mapEntry.getValue();

            for (String cellString : axisList) {
                if (tempHashMap == null) {
                    dataArray[i] = "0.00";
                } else {
                    if (tempHashMap.get(cellString) == null) {
                        dataArray[i] = "0.00";
                    } else {
                        dataArray[i] = String.valueOf(tempHashMap.get(cellString));
                    }
                }
                i++;
            }

            result = result + plainDataTableGenerator.getTableBodyRow(dataArray);
        }

        result = result + plainDataTableGenerator.getEndTableBody();
        result = result + plainDataTableGenerator.getEndTable();

        return result;
    }

}
