package com.hackingismakingisengineering.dcma.model;

import net.sf.mpxj.Duration;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskContainer;

import java.util.Date;
import java.util.List;

public class DCMAReport {

    private String title;
    private Date creationDate;
    private Date updateDate;
    private Date exportDate;
    private String user;
    private String programFormat;

    private Program mProgram;

    private int numTasks;

    private TaskContainer tasks;


    public DCMAReport(Program mProgram) {
        this.mProgram = mProgram;
        run();
    }

    public boolean run(){

        tasks = mProgram.getProject().getTasks();
        numTasks = tasks.size();

        TaskContainer failingLeadsTest = leadsTest();
        reportTestToConsole(failingLeadsTest);


        return true;
    }

    private void reportTestToConsole(TaskContainer failingTestTasks) {

        for(Task t : failingTestTasks){
            System.out.println(t.toString());
        }
    }


    //Logic – Is the schedule logical? Schedule logic involves schedule tasks; Are all the predecessor, successor tasks concurrent? Missing links need to be resolved because without schedule logic an accurate Critical Path will not be possible.
    private boolean logicTest(){

        return true;
    }

    //Leads – are not allowed in scheduling as they are confusing and disrupt the flow of the schedule. Leads are often replaced with positive lags, but this isn’t always the best alternative. It is better to have shorter known scopes of work tasks connected by FS relationships, with no lags.
    private TaskContainer leadsTest(){

        TaskContainer failingTasks = null;

        for(Task t : tasks){

            List<Relation> relationships = t.getPredecessors();

            relationships.addAll(t.getSuccessors());

            for(Relation relation : relationships){
                Duration lag = relation.getLag();
                if(lag.getDuration()!=0){

                    failingTasks.add(t);
                }

            }
        }
        return failingTasks;
    }

    //Lags – The DCMA does allow positive lags but has set a limit for use in a schedule. The limit for lags is no more than 5% of activity relationships. The best option is to replace lags with tasks describing the effort or process, such as cure time. Lags are limited to 5% in order to support schedule clarity.
    private boolean lagsTest(){

        return true;
    }

    //FS Relationships – Even though Primavera P6 and Deltek Acumen Fuse both support all relationship types, the DCMA assessment states 90% (or more) of schedule dependencies should be Finish-Start (FS). Start-Start (SS) are acceptable, but building a whole schedule using SS is obviously unacceptable.
    private boolean fsRelationshipTest(){

        return true;
    }

    //Hard Constraints – can really affect logic and disable a schedule from being logic driven. The DCMA assessment states that hard constraints should be limited to 5% of uncompleted tasks. Constraints of any type are discouraged and a schedule should work without any.
    private boolean hardConstraintsTest(){

        return true;
    }

    //High Float – activities may not be linked properly and can cause stress on the Critical Path. Total Float values are limited to 44 days, therefore review tasks that have greater than 2 months total float and limit their usage to 5% of incomplete tasks.
    private boolean highFloatTest(){

        return true;
    }

    //Negative Float – Schedules that have negative float tasks are already behind. Ideally the DCMA says avoid having negative float in your schedule. If there is negative float, make sure it is accompanied with a documented plan to mitigate being late.
    private boolean negativeFloatTest(){

        return true;
    }

    //High Duration Tasks – Limit long duration tasks to 5% of incomplete tasks. Task durations should be no longer than two months in order to support schedule updating and reporting efforts. Break long activities down into a series of shorter ones for more detail.
    private boolean highDurationTaskTest(){

        return true;
    }

    //Invalid Dates – Forecasted (future work) work should not be in the past and actual (completed work) work should not be in the future. Invalid dates are not allowed under any circumstance; this will avoid illogical situations where future work is planned for the past and completed work happened in the future.
    private boolean invalidDateTest(){

        return true;
    }

    //Resources – Resource loading is not a requirement, but the DCMA like schedules to be resource and cost-loaded. If you follow this path make sure the resource loaded schedule is completely loaded. All activities except milestones must have a cost or associated resource.
    private boolean resourcesTest(){

        return true;
    }

    //Missed Tasks – This check looks at how many activities have finished late compared to the baseline date, monitoring excessive slippage. Only 5% of activities can slip from their finish baseline dates. This metric is a conservative and retrospective measure of schedule progress, but it’s a good generic check to see if your project will deliver on time or not.
    private boolean missedTaskTest(){

        return true;
    }

    //Critical Path Test – ensures the schedule has one continuous linkage from project start to finish. It tests the integrity of a schedule’s Critical Path, looking for fluidity driven by good logic linking.
    private boolean criticalPathTest(){

        return true;
    }

    //Critical Path Length Index – the Critical Path Length Index (CPLI) is a forward looking gauge that assesses required efficiency to complete the project on schedule. It measures the ratio of the project critical path length and the project total float to the project critical path length. The critical path length is the time in work days from the current date to the completion of the project. The target number is 1.0 and schedule’s that have a CLPI less than 0.95 require further review.
    private boolean criticalPathLengthTest(){

        return true;
    }

    //Baseline Execution Index – the Baseline Execution Index (BEI) is an early warning indicator that a schedule is in trouble of not meeting the deadline. Most scheduling software doesn’t have a BEI variable, but it is possible to compute the ratio yourself or purchase an additional scheduling software supplement. The BEI ratios advanced, nontrivial, and purposeful warning makes the computation worth the effort. A BEI of 1.0 means that the schedule is on the right track.
    private boolean baselineExecutionIndexTest(){

        return true;
    }


    @Override
    public String toString() {
        return "DCMAReport{" +
                "title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", exportDate=" + exportDate +
                ", user='" + user + '\'' +
                ", mProgram=" + mProgram +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Program getmProgram() {
        return mProgram;
    }

    public void setmProgram(Program mProgram) {
        this.mProgram = mProgram;
    }
}
