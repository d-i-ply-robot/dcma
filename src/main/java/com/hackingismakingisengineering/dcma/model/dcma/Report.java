package com.hackingismakingisengineering.dcma.model.dcma;

import net.sf.mpxj.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report {

    private String title;
    private Date creationDate;
    private Date updateDate;
    private Date exportDate;
    private String user;
    private String programFormat;

    ProjectFile projectFile;

    private int numTasks;

    private TaskContainer tasks;
    private Date mStatusDate;

    private ArrayList<TestReport> testReportsArrayList;

    public ArrayList<TestReport> getTestReportsArrayList() {
        return testReportsArrayList;
    }

    public void setTestReportsArrayList(ArrayList<TestReport> testReportsArrayList) {
        this.testReportsArrayList = testReportsArrayList;
    }

    public Report(ProjectFile project) {
        this.projectFile = project;
        run();
    }

    public TestReport getTestReport() {
        return testReport;
    }

    public void setTestReport(TestReport testReport) {
        this.testReport = testReport;
    }

    private TestReport testReport;



    public boolean run(){



        tasks = this.projectFile.getTasks();
        numTasks = tasks.size();

        mStatusDate = this.projectFile.getProjectProperties().getStatusDate();


        testReportsArrayList = new ArrayList<>();

        testReportsArrayList.add(new TestReport("Logic test", logicTest(), DCMATestType.RELATIONSHIP, TestThresholds.FIVE, numTasks));
        testReportsArrayList.add(new TestReport("Lead test", leadsTest(), DCMATestType.RELATIONSHIP, TestThresholds.ZERO, numTasks));
        testReportsArrayList.add(new TestReport("Lags test", lagsTest(), DCMATestType.RELATIONSHIP, TestThresholds.FIVE, numTasks));
        testReportsArrayList.add(new TestReport("Relationship Types test", fsRelationshipTest(), DCMATestType.RELATIONSHIP, TestThresholds.TEN, numTasks));
        testReportsArrayList.add(new TestReport("Hard Constraint test", hardConstraintsTest(), DCMATestType.DATE, TestThresholds.FIVE, numTasks));
        testReportsArrayList.add(new TestReport("High Float test", highFloatTest(), DCMATestType.LOGIC, TestThresholds.FIVE, numTasks));
        testReportsArrayList.add(new TestReport("Negative Float test", negativeFloatTest(), DCMATestType.LOGIC, TestThresholds.FIVE, numTasks));
        testReportsArrayList.add(new TestReport("High Duration test", highDurationTaskTest(), DCMATestType.DURATION, TestThresholds.FIVE, numTasks));
        testReportsArrayList.add(new TestReport("Invalid Dates test", invalidDateTest(), DCMATestType.PROGRESS, TestThresholds.ZERO, numTasks));
        testReportsArrayList.add(new TestReport("Resources test", resourcesTest(), DCMATestType.RESOURCES, TestThresholds.ZERO, numTasks));
        testReportsArrayList.add(new TestReport("Missed Tasks test", missedTaskTest(), DCMATestType.PROGRESS, TestThresholds.FIVE, numTasks));

        //TODO:
        //testReportsArrayList.add(new TestReport("Critical Path test", missedTaskTest(), DCMATestType.PROGRESS, TestThresholds.FIVE, numTasks));

        testReportsArrayList.add(new TestReport("Critical Path Length Index test", criticalPathLengthTest(), DCMATestType.PROGRESS, TestThresholds.NINTY_FIVE, numTasks));
        testReportsArrayList.add(new TestReport("Baseline Execution Index test", baselineExecutionIndexTest(), DCMATestType.PROGRESS, TestThresholds.FIVE, numTasks));


        return true;
    }

    private void reportTestToConsole(ArrayList<Task> failingTestTasks) {

        for(Task t : failingTestTasks){
            System.out.println(t.toString());
        }
    }


    //Logic – Is the schedule logical? Schedule logic involves schedule tasks; Are all the predecessor, successor tasks concurrent? Missing links need to be resolved because without schedule logic an accurate Critical Path will not be possible.
    private ArrayList<Task> logicTest(){

        TaskContainer failingTasks;
        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks){

            List<Relation> predecessorRelationships = t.getPredecessors();
            List<Relation> successorRelationships = t.getSuccessors();



            if(predecessorRelationships.size() ==0 || successorRelationships.size() ==0){

                failedTasks.add(t);

            }
        }

        return failedTasks;

    }

    //Leads – are not allowed in scheduling as they are confusing and disrupt the flow of the schedule. Leads are often replaced with positive lags, but this isn’t always the best alternative. It is better to have shorter known scopes of work tasks connected by FS relationships, with no lags.
    private ArrayList<Task> leadsTest(){

        TaskContainer failingTasks;
        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks){

            List<Relation> relationships = t.getPredecessors();

            relationships.addAll(t.getSuccessors());

            for(Relation relation : relationships){
                Duration lag = relation.getLag();
                if(lag.getDuration()<0){

                    failedTasks.add(t);
                }

            }
        }

        return failedTasks;
    }

    //Lags – The DCMA does allow positive lags but has set a limit for use in a schedule. The limit for lags is no more than 5% of activity relationships. The best option is to replace lags with tasks describing the effort or process, such as cure time. Lags are limited to 5% in order to support schedule clarity.
    private ArrayList<Task> lagsTest(){

        TaskContainer failingTasks;
        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks){

            List<Relation> relationships = t.getPredecessors();

            relationships.addAll(t.getSuccessors());

            for(Relation relation : relationships){
                Duration lag = relation.getLag();
                if(lag.getDuration()>0){

                    failedTasks.add(t);
                }

            }
        }

        return failedTasks;

    }

    //FS Relationships – Even though Primavera P6 and Deltek Acumen Fuse both support all relationship types, the DCMA assessment states 90% (or more) of schedule dependencies should be Finish-Start (FS). Start-Start (SS) are acceptable, but building a whole schedule using SS is obviously unacceptable.
    private ArrayList<Task> fsRelationshipTest(){

        TaskContainer failingTasks;
        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks){

            List<Relation> relationships = t.getPredecessors();

            relationships.addAll(t.getSuccessors());

            for(Relation relation : relationships){
                Duration lag = relation.getLag();

                RelationType relationType = relation.getType();

                if(relationType != RelationType.FINISH_START){

                    failedTasks.add(t);
                }

            }
        }
        return failedTasks;
    }

    //Hard Constraints – can really affect logic and disable a schedule from being logic driven. The DCMA assessment states that hard constraints should be limited to 5% of uncompleted tasks. Constraints of any type are discouraged and a schedule should work without any.
    private ArrayList<Task> hardConstraintsTest(){

        TaskContainer failingTasks;
        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks) {

            if (t.getConstraintType() != ConstraintType.AS_SOON_AS_POSSIBLE) {
                failedTasks.add(t);
            }
        }
            return failedTasks;
    }

    //High Float – activities may not be linked properly and can cause stress on the Critical Path. Total Float values are limited to 44 days, therefore review tasks that have greater than 2 months total float and limit their usage to 5% of incomplete tasks.
    private ArrayList<Task> highFloatTest(){

        TaskContainer failingTasks;
        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks) {

            double freeSlack = t.getFreeSlack().getDuration();

            if (freeSlack >= DCMAConfig.HIGHFLOATLIMIT ){
                failedTasks.add(t);
            }
        }
        return failedTasks;
    }

    //Negative Float – Schedules that have negative float tasks are already behind. Ideally the DCMA says avoid having negative float in your schedule. If there is negative float, make sure it is accompanied with a documented plan to mitigate being late.
    private ArrayList<Task> negativeFloatTest(){

        TaskContainer failingTasks;
        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks) {

            double freeSlack = t.getFreeSlack().getDuration();

            if (freeSlack < 0 ){
                failedTasks.add(t);
            }
        }
        return failedTasks;
    }

    //High Duration Tasks – Limit long duration tasks to 5% of incomplete tasks. Task durations should be no longer than two months in order to support schedule updating and reporting efforts. Break long activities down into a series of shorter ones for more detail.
    private ArrayList<Task> highDurationTaskTest(){

        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks) {

            double duration = t.getDuration().getDuration();

            if (duration >= DCMAConfig.HIGH_DURATION_LIMIT){
                failedTasks.add(t);
            }
        }
        return failedTasks;
    }

    //Invalid Dates – Forecasted (future work) work should not be in the past and actual (completed work) work should not be in the future. Invalid dates are not allowed under any circumstance; this will avoid illogical situations where future work is planned for the past and completed work happened in the future.
    private ArrayList<Task> invalidDateTest(){

        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks) {

            if(t.getActualStart()!=null && mStatusDate!= null) {
                if (mStatusDate.compareTo(t.getActualStart()) < 0 &&
                        mStatusDate.compareTo(t.getActualStart()) > 0) {

                    failedTasks.add(t);
                }
            }
        }
        return failedTasks;
    }

    //Resources – Resource loading is not a requirement, but the DCMA like schedules to be resource and cost-loaded. If you follow this path make sure the resource loaded schedule is completely loaded. All activities except milestones must have a cost or associated resource.
    private ArrayList<Task> resourcesTest(){

        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks) {

            if (t.getResourceAssignments()==null){
                failedTasks.add(t);
            }
        }
        return failedTasks;
    }

    //Missed Tasks – This check looks at how many activities have finished late compared to the baseline date, monitoring excessive slippage. Only 5% of activities can slip from their finish baseline dates. This metric is a conservative and retrospective measure of schedule progress, but it’s a good generic check to see if your project will deliver on time or not.
    private ArrayList<Task> missedTaskTest(){

        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks) {

            if(t.getBaselineStart()!=null) {
                if (t.getBaselineStart().compareTo(mStatusDate) > 0
                        && t.getEarlyStart().compareTo(mStatusDate) < 0) {
                    failedTasks.add(t);
                }
            }
        }
        return failedTasks;
    }

    //Critical Path Test – ensures the schedule has one continuous linkage from project start to finish. It tests the integrity of a schedule’s Critical Path, looking for fluidity driven by good logic linking.
    //TODO
    private ArrayList<Task> criticalPathTest(){

        return null;
    }

    //Critical Path Length Index – the Critical Path Length Index (CPLI) is a forward looking gauge that assesses required efficiency to complete the project on schedule. It measures the ratio of the project critical path length and the project total float to the project critical path length. The critical path length is the time in work days from the current date to the completion of the project. The target number is 1.0 and schedule’s that have a CLPI less than 0.95 require further review.
    //TODO
    private Double criticalPathLengthTest(){

        projectFile.getProjectProperties().getStartDate();
        projectFile.getProjectProperties().getFinishDate();
        projectFile.getProjectProperties().getStatusDate();

        projectFile.getProjectProperties().getCriticalSlackLimit();

        return 0d;
    }

    //Baseline Execution Index – the Baseline Execution Index (BEI) is an early warning indicator that a schedule is in
    // trouble of not meeting the deadline. Most scheduling software doesn’t have a BEI variable, but it is possible to
    // compute the ratio yourself or purchase an additional scheduling software supplement. The BEI ratios advanced,
    // nontrivial, and purposeful warning makes the computation worth the effort. A BEI of 1.0 means that the schedule
    // is on the right track. The final metric, Baseline Execution Index (BEI), is another indicator intended to measure
    // performance against the baseline plan. Put differently, it measures the throughput with which the project team is
    // accomplishing tasks.
    //
    // It is calculated by dividing the total number of tasks completed by the total number of tasks
    // baselined to have been completed as of the project status date. A BEI of 1.00 indicates the project team is
    // executing on plan, with greater than 1.00 indicating ahead of schedule and below 1.00 indicating behind schedule.
    // DCMA considers a BEI below 0.95 to be indicative of a potential issue requiring further investigation.
    private Double baselineExecutionIndexTest(){

        ArrayList<Task> failedTasks= new ArrayList<Task>();

        for(Task t : tasks){
            if(t.getBaselineFinish()!=null){
            if(t.getBaselineFinish().compareTo(mStatusDate)<0 && t.getPercentageComplete().intValue() != 100 ) {
                failedTasks.add(t);
            }
            }
        }

        return Double.valueOf(failedTasks.size())/(Double.valueOf(tasks.size()));

    }


    @Override
    public String toString() {

        /*
        return "Report{" +
                "title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", exportDate=" + exportDate +
                ", user='" + user + '\'' +

                '}';
         */

        String report = "";

        for(TestReport testReport :testReportsArrayList){

            report += testReport.toString();

        }
        return report;

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


}
