import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Client {
    public static void main(String[] args) throws ParseException {

        List<Date> dates = new ArrayList<Date>();

        String str_date ="01\\.05\\.2014";
        String end_date ="10\\.10\\.2014";

        DateFormat formatter ;

        formatter = new SimpleDateFormat("dd\\.MM\\.yyyy");
        Date  startDate = (Date)formatter.parse(str_date);
        Date  endDate = (Date)formatter.parse(end_date);
        long interval = 24*1000 * 60 * 60; // 1 hour in millis
        long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
        long curTime = startDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }

        for(int i=0;i<dates.size();i++) {
            Date lDate = (Date) dates.get(i);
            String ds = formatter.format(lDate);
            int numberOfComments = 0;
            File folder = new File("C:\\Users\\a565673\\Documents\\luci\\Rapoarte\\Octombrie\\csvs");
            for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isFile()) {
                    File csvData = new File("C:\\Users\\a565673\\Documents\\luci\\Rapoarte\\Octombrie\\csvs\\" + fileEntry.getName());
                    CSVParser parser = null;

                    String nume = "Ban, Lucian";

                    String pattern = String.format("%s \\d{2}:\\d{2}:\\d{2} \\(GMT \\+.:00\\) %s", ds, nume);

                    try {
                        parser = CSVParser.parse(csvData, Charset.forName("UTF-8"), CSVFormat.newFormat(';'));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (CSVRecord csvRecord : parser) {
                        int fieldNumber = 0;
                        for (String field : csvRecord) {
                            if (!field.trim().isEmpty()) {
                                Pattern r = Pattern.compile(pattern);
                                Matcher m = r.matcher(field);
                                if (m.find()) {
                                    //System.out.println(m.group());
                                    numberOfComments++;
                                }
                            }
                        }
                    }
                }

            }
            ds = ds.replace("\\", "");
            System.out.println("In date: " + ds + " there were " + numberOfComments + " comments");
        }
    }
}
