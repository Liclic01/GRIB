
import ucar.grib.NoValidGribException;
import ucar.grib.grib1.Grib1Input;
import ucar.grib.grib1.Grib1Product;
import ucar.grib.grib1.Grib1Record;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// Класс отвечает за проверку второго и третьего условия задачи

public class TestFile {
    private ArrayList<Grib1Record> gribRecords;
    private ArrayList<Grib1Product> gribProducts;
    private Grib1Input grib1Input;
    private ucar.unidata.io.RandomAccessFile gribFile;

    void checkFile(String pathFile) throws IOException, NoValidGribException, ParseException {

        gribFile = new ucar.unidata.io.RandomAccessFile(pathFile, "rw");
        grib1Input = new Grib1Input(gribFile);
        gribRecords = new ArrayList<Grib1Record>();
        gribProducts = new ArrayList<Grib1Product>();
        gribRecords = grib1Input.getRecords();
        gribProducts = grib1Input.getProducts();

        try {
            grib1Input.scan(false, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (gribRecords.size() != 3) {
            System.out.println("Файл " + pathFile + " содержит " + gribRecords.size() + " записей");
        }
        for (Grib1Record gribR : gribRecords) {
            String referenceDate = new SimpleDateFormat("yyyyMMddHHmm").format(gribR.getPDS().getPdsVars().getReferenceDate());
            Integer earliness = gribR.getPDS().getPdsVars().getForecastTime();

            if (!(new SimpleDateFormat("yyyyMMddHHmm").parse(pathFile.substring(pathFile.length() - 20, pathFile.length() - 8)).toString().
                    equals(referenceDate))) {
                System.out.println("Дата начала прогноза " + referenceDate +
                        " в записи не соотествует с датой начала прогноза в названии файла " + pathFile);
            }
            if ((Integer.parseInt(pathFile.substring(pathFile.length() - 7, pathFile.length() - 4)) +
                    Integer.parseInt(pathFile.substring(pathFile.length() - 3, pathFile.length() - 1))) != earliness) {
                System.out.println("Заблаговременность " + earliness + " записи не соответствует заблаговременности в названии файла " + pathFile);
            }
            String s = gribR.getPDS().getPdsVars().getLevelName();
            if (!(s.equals("height_above_ground") || s.equals("altitude_above_msl"))) {
                System.out.println("Неизвестная GRIB-запись " + s);
            }


        }

    }

}
