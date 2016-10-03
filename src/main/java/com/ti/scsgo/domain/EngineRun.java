package com.ti.scsgo.domain;

import com.ti.sc.scsgo.Engine;
import com.ti.sc.scsgo.GroupSetup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author a0284021
 */
public class EngineRun {

    final private List<GroupSetup> groupSetup = new ArrayList<>();
    private String dateStr;
    private final double excessManpower;
    private final double totalManpower;

    public static EngineRun run(Engine e) {
        e.run();
        EngineRun engineRun = new EngineRun(e.getExcessManpower(), e.getTotalManpower());
        engineRun.addAll(e.getGroupSetups());
        return engineRun;
    }

    private EngineRun(double excessManpower, double totalManpower) {
        this.excessManpower = excessManpower;
        this.totalManpower = totalManpower;
    }

    public double getExcessManpower() {
        return excessManpower;
    }

    public double getTotalManpower() {
        return totalManpower;
    }

    public List<GroupSetup> getGroupSetup() {
        return groupSetup;
    }

    private void addAll(Collection<? extends GroupSetup> groupSetup) {
        if (groupSetup != null && groupSetup.size() > 0) {
            this.groupSetup.addAll(groupSetup);
        }
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

}
