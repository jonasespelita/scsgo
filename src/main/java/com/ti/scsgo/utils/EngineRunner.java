package com.ti.scsgo.utils;

import com.ti.sc.scsgo.Engine;
import com.ti.sc.scsgo.GroupSetup;
import com.ti.scsgo.domain.EngineRun;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author a0284021
 */
public class EngineRunner {

    public static EngineRun run(File file) throws FileNotFoundException {
        // read file and get req values

        final List<GroupSetup> grpSetup = new ArrayList<>();
        int manP;
        try (Scanner scanner = new Scanner(file)) {
            // manpower 
            manP = scanner.nextInt();
            System.out.println("manP = " + manP);
            // date
            String dateStr = scanner.next();
            System.out.println("dateStr = " + dateStr);

            try {
                while (scanner.hasNext()) {
                    final String ln = scanner.next();
                    GroupSetup groupSetup = parseLine(ln);
                    grpSetup.add(groupSetup);
                }
            } catch (Exception e) {
                //TODO do LOG
                System.out.println("WARNING ERROR ");
                e.printStackTrace();
            }
        }

        // set values here
        Engine e = new Engine(grpSetup, manP);
        final EngineRun engineRun = EngineRun.run(e);

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
