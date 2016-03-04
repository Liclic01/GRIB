import ucar.grib.NoValidGribException;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class StartApp {
    public static void main(String[] args) {
        TestFileName testFileName = new TestFileName();
        TestFile testFile = new TestFile();
        List<String> values = new ArrayList<String>();
        try {
            /*
            * Результаты будут записываться в файл с полным именем передаваемым через
            * переменную args[1]
            */

            PrintStream realStream = System.out;
            FileOutputStream fileOutputStream = new FileOutputStream(args[1]);
            PrintStream stream = new PrintStream(fileOutputStream);
            System.setOut(stream);

            /*
            * Все нужные параметры буду браться из файла GRIB.properties
            *
            * */
            File file = new File(args[0]);
            FileInputStream fileInputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();

            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                System.out.println(key + " " + value);
                values.add(value);
            }

            File[] files = testFileName.getArrayFiles(new File(values.get(6)));
            testFileName.createLists(files);
            testFileName.checkMinDate(values.get(3));
            testFileName.checkMaxDate(values.get(5));
            testFileName.checkStepDate(values.get(1));
            testFileName.checkMinEarliness(values.get(4));
            testFileName.checkMaxEarliness(values.get(0));
            testFileName.checkStepEarliness(values.get(2));

            for (File file1 : files) {
                testFile.checkFile(file1.getAbsolutePath());
            }


            System.setOut(realStream);

            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NoValidGribException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
