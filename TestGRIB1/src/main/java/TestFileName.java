import java.io.File;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// класс отвечает за проверку первого условия задачи
public class TestFileName {
    private List<Date> dateList = new ArrayList<Date>();
    private List<Date> earlinessList = new ArrayList<Date>();

    void checkMinDate(String minDate) {
        Date dateMin;
        try {
            dateMin = new SimpleDateFormat("yyyyMMddHHmm").parse(minDate);

            for (Date date : dateList) {
                if (date.getTime() < dateMin.getTime()) {
                    System.out.println("Дата налача " + date + " меньше даты начала самого раннего прогноза ");
                } else if (date.getTime() == dateMin.getTime()) {
                    break;
                } else System.out.println("Неверная дата начала самого раннего прогноза");
            }
        } catch (Exception e) {
            System.out.println("Неверная дата начала самого раннего прогноза");
        }
    }

    void checkMaxDate(String maxDate) {
        Date dateMax;
        try {
            dateMax = new SimpleDateFormat("yyyyMMddHHmm").parse(maxDate);

            for (int i = dateList.size(); i < 0; i--) {
                if (dateList.get(i).getTime() > dateMax.getTime()) {
                    System.out.println("Дата налача" + dateList.get(i) + " больше даты начала самого позднего прогноза ");
                } else if (dateList.get(i).getTime() == dateMax.getTime()) {
                    break;
                } else System.out.println("Неверная дата начала самого позднего прогноза");
            }
        } catch (Exception e) {
            System.out.println("Неверная дата начала самого позднего прогноза");
        }
    }

    void checkStepDate(String stepDate) {
        Long sd = new Long(Long.parseLong(stepDate) * 3600000);
        for (int i = 0; i < dateList.size() - 1; i++) {
            if (!((dateList.get(i + 1).getTime() - dateList.get(i).getTime()) == sd ||
                    (dateList.get(i + 1).getTime() - dateList.get(i).getTime()) == 0)) {
                System.out.println("Шаг начала прогноза между датами " + dateList.get(i) + " и " + dateList.get(i + 1) + " неверный");
            }
        }

    }


    void checkMinEarliness(String minEarliness) {
        Date earlinessMin;
        try {
            earlinessMin = new SimpleDateFormat("HHHmm").parse(minEarliness);

            for (Date date : earlinessList) {
                if (date.getTime() < earlinessMin.getTime()) {
                    System.out.println("Заблаговременность " + date + " меньше минимальной заблаговременности");
                } else if (date.getTime() == earlinessMin.getTime()) {
                    break;
                } else System.out.println("Неверное значение минимальной заблаговременности");
            }
        } catch (Exception e) {
            System.out.println("Неверное значение минимальной заблаговременности");
        }
    }

    void checkMaxEarliness(String maxEarliness) {
        Date earlinessMax;
        try {
            earlinessMax = new SimpleDateFormat("HHHmm").parse(maxEarliness);

            for (int i = earlinessList.size(); i < 0; i--) {
                if (earlinessList.get(i).getTime() > earlinessMax.getTime()) {
                    System.out.println("Заблаговременность " + earlinessList.get(i) + " больше максимальной заблаговременности");
                } else if (earlinessList.get(i).getTime() == earlinessMax.getTime()) {
                    break;
                } else System.out.println("Неверное значение максимальной заблаговременности");
            }
        } catch (Exception e) {
            System.out.println("Неверное значение максимальной заблаговременности");
        }
    }

    void checkStepEarliness(String stepEarliness) {

        Long sd = new Long(Long.parseLong(stepEarliness) * 60000);
        for (int i = 0; i < earlinessList.size() - 1; i++) {
            if (!((earlinessList.get(i + 1).getTime() - earlinessList.get(i).getTime()) == sd ||
                    (earlinessList.get(i + 1).getTime() - earlinessList.get(i).getTime()) == 0)) {
                System.out.println("Шаг заблаговременности между " + earlinessList.get(i) + " и " + earlinessList.get(i + 1) + " неверный");
            }
        }

    }

    void createLists(File[] files) {
        if (files.length == 0) {
            System.out.println("Папка пустая...");
        }

        for (File file : files) {
            String fileName = file.getName();
            try {
                dateList.add(new SimpleDateFormat("yyyyMMddHHmm").parse(fileName.substring(fileName.length() - 20, fileName.length() - 8)));
                earlinessList.add(new SimpleDateFormat("HHHmm").parse(fileName.substring(fileName.length() - 7, fileName.length() - 4) +
                        fileName.substring(fileName.length() - 3, fileName.length() - 1)));
            } catch (ParseException e) {
                System.out.println("Неправильное имя файла " + fileName);
            }
        }
        Collections.sort(dateList);
        Collections.sort(earlinessList);
    }

    File[] getArrayFiles(File myFolder) {
        if (myFolder == null) {
            System.out.println("Папка не найденна...");
        }
        return myFolder.listFiles();
    }
}
