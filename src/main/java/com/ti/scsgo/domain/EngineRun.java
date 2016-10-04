package com.ti.scsgo.domain;

import com.ti.sc.scsgo.Engine;
import com.ti.sc.scsgo.GroupSetup;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author a0284021
 */
public class EngineRun implements Comparable {

    final private List<GroupSetup> groupSetups;
    private LocalDate date;
    private final double excessManpower;
    private final double totalManpower;
    private String fileName;

    private double optManCnt;
    private double optManCntDmd;
    private double equipCnt;

    private double potManOut;
    private double potEqOut;

    private double totalDemand;
    private double totalOut;

    public static EngineRun run(Engine e) {
        e.run();
        EngineRun engineRun = new EngineRun(e.getExcessManpower(), e.getTotalManpower());
        engineRun.addAllSetup(e.getGroupSetups());

        final double[] sums
                = engineRun.groupSetups
                .stream()
                .map(setup -> {
                    // extract manpower and equipments from each setup
                    return new double[]{
                        setup.getMaxManpower(), //0
                        setup.getEquipments(), //1
                        setup.getSuggestedMinManpower(), //2
                        setup.getEquipments() * setup.getPPW(), //3
                        setup.getManpower() * setup.getEPP() * setup.getPPW(),//4
                        setup.getDemand(),//5
                        setup.getTotalOutput()}; //6
                })
                .reduce((x, y) -> {
                    // sum all values
                    for (int i = 0; i < x.length; i++) {
                        x[i] += y[i];
                    }
                    return x;
                }).get();

        engineRun.optManCnt = sums[0];
        engineRun.equipCnt = sums[1];
        engineRun.optManCntDmd = sums[2];
        engineRun.potEqOut = sums[3];
        engineRun.potManOut = sums[4];
        engineRun.totalDemand = sums[5];
        engineRun.totalOut = sums[6];
        return engineRun;
    }

    public double getTotalDemand() {
        return totalDemand;
    }

    public double getTotalOut() {
        return totalOut;
    }

    public List<GroupSetup> getGroupSetups() {
        return groupSetups;
    }

    public double getPotManOut() {
        return potManOut;
    }

    public double getPotEqOut() {
        return potEqOut;
    }

    public double getOptManCntDmd() {
        return optManCntDmd;
    }

    public double getOptManCnt() {
        return optManCnt;
    }

    public double getEquipCnt() {
        return equipCnt;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    private EngineRun(double excessManpower, double totalManpower) {
        this.excessManpower = excessManpower;
        this.totalManpower = totalManpower;
        groupSetups = new ArrayList<>();

    }

    public double getExcessManpower() {
        return excessManpower;
    }

    public double getTotalManpower() {
        return totalManpower;
    }

    private void addAllSetup(Collection<? extends GroupSetup> groupSetup) {
        if (groupSetup != null && groupSetup.size() > 0) {
            this.groupSetups.addAll(groupSetup);
        }
    }

    public String getDateStr() {
        return date.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        EngineRun er = (EngineRun) o;
        return new CompareToBuilder()
                .append(this.date, er.date)
                .toComparison();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
