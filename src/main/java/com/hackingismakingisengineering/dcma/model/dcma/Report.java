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

        //TODO: Convert to ENUM
        testReportsArrayList.add(new TestReport("Logic test", logicTest(), DCMATestType.RELATIONSHIP, TestThresholds.FIVE, numTasks, "The logic check looks to ensure all incomplete activities have defined predecessors and successors. (DCMA max 5%, often use 0%)"));
        testReportsArrayList.add(new TestReport("Lead test", leadsTest(), DCMATestType.RELATIONSHIP, TestThresholds.ZERO, numTasks, "A lead is a negative lag between two tasks. Using leads can have adverse effects on the project total float, therefore impeding the ability to determine the true critical path. It is better to decompose activities to a level of detail in which traditional finish-to-start relationships can be used. (DCMA max 0%)"));
        testReportsArrayList.add(new TestReport("Lags test", lagsTest(), DCMATestType.RELATIONSHIP, TestThresholds.FIVE, numTasks, "Lags can be confusing if the reason for them isn’t immediately clear. Better to represent a lag with an explicitly named task. (DCMA max 5%)"));
        testReportsArrayList.add(new TestReport("Relationship Types test", fsRelationshipTest(), DCMATestType.RELATIONSHIP, TestThresholds.TEN, numTasks, "Ideally, all tasks in the schedule would be sequenced finish-to-start to have the clearest understanding of the critical path.(DCMA min 90%) Start-to-start or finish-to-finish relationships may be used in cases where that is the true nature of the dependency. Start-to-start and finish-to-finish relationships should not be used, however, simply to schedule activities in parallel if they do not truly depend on each other. In such instances, it is better to give the two parallel activities the same finish-to-start predecessor(s). "));
        testReportsArrayList.add(new TestReport("Hard Constraint test", hardConstraintsTest(), DCMATestType.DATE, TestThresholds.FIVE, numTasks, "Constraints in general should be used sparingly, allowing for task dates to be the natural result of dependencies and activity duration. When necessary, however, soft constraints (Start No Earlier Than and Finish No Earlier Than) are preferable because they allow the schedule to continue to be logic driven. By contrast, hard constraints (Start No Later Than, Finish No Later Than, Must Start On, and Must Finish On) artificially prevent the schedule from shifting to the right. This has the potentially disastrous effect of obscuring the possibility of late performance before it is too late to take corrective action. (DCMA max 5%, often use 0%)"));
        testReportsArrayList.add(new TestReport("High Float test", highFloatTest(), DCMATestType.LOGIC, TestThresholds.FIVE, numTasks,"DCMA defines high float as total float of 44 days (2 working months) or greater. Intuitively, one would assume that activities with high float would be a good thing, a form of schedule margin so to speak. However, high float is more often the result of missing dependencies. It is the rare activity that can slip more than two months without impacting the project completion date. (DCMA max 5%)"));
        testReportsArrayList.add(new TestReport("Negative Float test", negativeFloatTest(), DCMATestType.LOGIC, TestThresholds.FIVE, numTasks, "Negative float occurs when the project schedule is forecasting a missed deadline, or when a hard constraint is holding a task further to the left than it would otherwise be. (DCMA max 0%)"));
        testReportsArrayList.add(new TestReport("High Duration test", highDurationTaskTest(), DCMATestType.DURATION, TestThresholds.FIVE, numTasks, "Part of the planning process is decomposing work packages to a level where the activities are discrete enough to track and manage. DCMA considers any incomplete activity with a baseline duration of greater than 44 days to be in violation of this metric. (DCMA max 5%)"));
        testReportsArrayList.add(new TestReport("Invalid Dates test", invalidDateTest(), DCMATestType.PROGRESS, TestThresholds.ZERO, numTasks, "The invalid date check applies only during execution of the project. A task is said to have invalid dates if it has forecast start/finish dates in the past or actual start/finish dates in the future, with respect to the project status date. The threshold for this metric is zero."));
        testReportsArrayList.add(new TestReport("Resources test", resourcesTest(), DCMATestType.RESOURCES, TestThresholds.ZERO, numTasks, "Ideally, all project schedule activities should have resources assigned to them. In practice, not all organizations resource load their project schedules. Additionally, there are occasions where tasks with durations greater than zero are representative of time but have no work associated with them, such as procurement lead-time or customer review of deliverables. This is one of the more flexible metrics of the 14-point assessment. In the case of organizations that resource load their schedules, it is still a good metric to evaluate to ensure no activities were missed during the resource estimation process."));
        testReportsArrayList.add(new TestReport("Missed Tasks test", missedTaskTest(), DCMATestType.PROGRESS, TestThresholds.FIVE, numTasks, "The missed tasks metric is indicative of schedule performance against the baseline plan. It is the percentage of tasks which were planned to have finished as of the project status date, which have actual or forecast finish dates later than their baseline finish dates. It does not include tasks which are currently forecasting late if those tasks have baseline finish dates after the status date. In that way, it is purely retrospective. The DCMA threshold for missed tasks is 5%, though recovery at a certain point becomes unlikely after a project falls too far behind."));

        //TODO:
        //testReportsArrayList.add(new TestReport("Critical Path test", missedTaskTest(), DCMATestType.PROGRESS, TestThresholds.FIVE, numTasks));

        testReportsArrayList.add(new TestReport("Critical Path Length Index test", criticalPathLengthTest(), DCMATestType.PROGRESS, TestThresholds.NINTY_FIVE, numTasks, "The Critical Path Length Index (CPLI) is a measure of required schedule efficiency to complete a project. It is defined as the sum of the remaining project duration (number of working days on the current critical path) and total float, divided by the remaining project duration. Total float in this instance is the variance between the forecast and baseline finish date of the Project Finish milestone. A CPLI of 1.00 indicates that the project must execute exactly to plan for the remainder of the project. A CPLI above 1.00 indicates that there is remaining schedule margin, while a CPLI below 1.00 indicates that the team must overachieve to meet the baseline finish date. DCMA considers a CPLI below 0.95 to be indicative of a potential issue requiring further investigation."));
        testReportsArrayList.add(new TestReport("Baseline Execution Index test", baselineExecutionIndexTest(), DCMATestType.PROGRESS, TestThresholds.FIVE, numTasks, "The final metric, Baseline Execution Index (BEI), is another indicator intended to measure performance against the baseline plan. Put differently, it measures the throughput with which the project team is accomplishing tasks. It is calculated by dividing the total number of tasks completed by the total number of tasks baselined to have been completed as of the project status date. A BEI of 1.00 indicates the project team is executing on plan, with greater than 1.00 indicating ahead of schedule and below 1.00 indicating behind schedule. DCMA considers a BEI below 0.95 to be indicative of a potential issue requiring further investigation."));


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

            if(t.getActualStart()!=null) {
                if (mStatusDate.compareTo(t.getActualStart()) < 0 &&
                        mStatusDate.compareTo(t.getActualFinish()) > 0) {

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

    public String getProgramFormat() {
        return programFormat;
    }

    public void setProgramFormat(String programFormat) {
        this.programFormat = programFormat;
    }

    public ProjectFile getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(ProjectFile projectFile) {
        this.projectFile = projectFile;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    public TaskContainer getTasks() {
        return tasks;
    }

    public void setTasks(TaskContainer tasks) {
        this.tasks = tasks;
    }

    public Date getmStatusDate() {
        return mStatusDate;
    }

    public void setmStatusDate(Date mStatusDate) {
        this.mStatusDate = mStatusDate;
    }

    public ArrayList<TestReport> getTestReportsArrayList() {
        return testReportsArrayList;
    }

    public void setTestReportsArrayList(ArrayList<TestReport> testReportsArrayList) {
        this.testReportsArrayList = testReportsArrayList;
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
