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

    final private List<GroupSetup> groupSetup = new ArrayList<>();
    private LocalDate date;
    private final double excessManpower;
    private final double totalManpower;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
    
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
