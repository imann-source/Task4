package task4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader;
        String temp;
        ArrayList<Counter> counters = new ArrayList<>();
        String[] tempForTime;

        try {
            reader = new BufferedReader(new FileReader(args[0]));      //"src/task4/file1.txt"

            while (reader.ready()) {
                temp = reader.readLine();
                temp = temp.substring(0, temp.length() - 2);
                tempForTime = temp.split(" ");
                DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                Date tDuration = timeFormat.parse(tempForTime[0]);
                long in = tDuration.getTime();
                tDuration = timeFormat.parse(tempForTime[1]);
                long out = tDuration.getTime();
                counters.add(new Counter(in, out));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int count = 0;
        int max = 0;
        int maxCounters = 0;
        long timeIn = 0;
        long timeOut = 0;
        ArrayList<Long> times = fillTime(counters);
        Collections.sort(times);
        TreeMap<Long, Integer> timesWhisMax = new TreeMap<Long, Integer>();
        for (Long time : times) {

            for (Counter counter : counters) {
                if (time >= counter.getIn() && time < counter.getOut()) {
                    count++;
                    timesWhisMax.put(time, count);
                } else if ( time == counter.getOut())
                    timesWhisMax.put(time, count);

            }

            if (count > max) {
                max = count;
            }
            count=0;
            maxCounters = max;

        }
        int match = 0;
        for (Map.Entry<Long,Integer> entry : timesWhisMax.entrySet()){
            if(entry.getValue() == maxCounters && match == 0){
                timeIn = entry.getKey();
                match++;
            }
            if ((entry.getValue() != maxCounters) && match != 0) {
                timeOut = entry.getKey();
                System.out.print(new SimpleDateFormat("HH:mm").format(new Date(timeIn)));
                System.out.println(" " + new SimpleDateFormat("HH:mm").format(new Date(timeOut))+"/n");
                match = 0;
            }
        }
    }



    private static ArrayList<Long> fillTime(List<Counter> counters) {
        ArrayList<Long> timeSet = new ArrayList<Long>();
        for (Counter element : counters) {
            timeSet.add(element.getIn());
            timeSet.add(element.getOut());
        }
        return timeSet;
    }

}
