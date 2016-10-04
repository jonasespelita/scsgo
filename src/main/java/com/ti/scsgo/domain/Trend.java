package com.ti.scsgo.domain;

/**
 *
 * @author Jonas
 */
public class Trend {

    private String week;
    private double totalDemand;
    private double totalOutput;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public double getTotalDemand() {
        return totalDemand;
    }

    public void setTotalDemand(double totalDemand) {
        this.totalDemand = totalDemand;
    }

    public double getTotalOutput() {
        return totalOutput;
    }

    public void setTotalOutput(double totalOutput) {
        this.totalOutput = totalOutput;
    }

}
