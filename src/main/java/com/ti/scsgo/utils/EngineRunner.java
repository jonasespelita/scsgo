package com.ti.scsgo.utils;

import com.ti.sc.scsgo.Engine;
import com.ti.sc.scsgo.GroupSetup;
import com.ti.scsgo.domain.EngineRun;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author a0284021
 */
public class EngineRunner {

    public static EngineRun run(File file) throws FileNotFoundException {
        // read file and get req values

        final List<GroupSetup> grpSetupLst = new ArrayList<>();
        int manP;
        LocalDate date = null;
        try (Scanner scanner = new Scanner(file)) {
            // manpower 
            manP = scanner.nextInt();
            System.out.println("manP = " + manP);
            // date
            date = LocalDate.parse(scanner.next(), DateTimeFormatter.BASIC_ISO_DATE);
            try {

                while (scanner.hasNext()) {
                    final String ln = scanner.next();
                    GroupSetup groupSetup = parseLine(ln);
                    grpSetupLst.add(groupSetup);
                }
            } catch (Exception e) {
                //TODO do LOG
                System.out.println("WARNING ERROR ");
                e.printStackTrace();
            }
        }

        // sort according to demand
        grpSetupLst.sort(
                (o1, o2) -> Double.compare(o1.getDemand(), o2.getDemand()));

        // set values here
        Engine e = new Engine(grpSetupLst, manP);
        final EngineRun engineRun = EngineRun.run(e);
        engineRun.setDate(date);

        return engineRun;
    }

    private static GroupSetup parseLine(final String ln) throws NumberFormatException {
        final String[] lsSplt = ln.split(",");
        double demand = Double.valueOf(lsSplt[0]);
        String grpName = lsSplt[1];
        double eqCount = Double.valueOf(lsSplt[2]);
        double pph = Double.valueOf(lsSplt[3]);
        double epp = Double.valueOf(lsSplt[4]);
        final GroupSetup groupSetup = new GroupSetup(grpName, demand, eqCount, pph, epp);
        return groupSetup;
    }

    private EngineRunner() {
    }

}
