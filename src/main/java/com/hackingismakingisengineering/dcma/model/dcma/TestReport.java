package com.hackingismakingisengineering.dcma.model.dcma;

import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class TestReport {



    private String testDescription;
    public String mTestName;
    public ArrayList<Task> mFailingTestTasks;
    public DCMATestType mTestType;
    public int mNumTasks;
    public Double percentFailing;
    public boolean testPassing = TRUE;


    public Double testThreshold;

    public Double index;

    public TestReport(String testName, ArrayList<Task> failingTestTasks, DCMATestType testType, TestThresholds threshold, int numTasks) {

        mTestName = testName;
        mFailingTestTasks = failingTestTasks;
        mTestType = testType;
        mNumTasks = numTasks;
        this.testThreshold = threshold.getValue();

        percentFailing = (double)failingTestTasks.size()/numTasks;

        if(percentFailing > threshold.getValue()){
            testPassing = FALSE;
        }

    }



    public TestReport(String testName, ArrayList<Task> failingTestTasks, DCMATestType testType, TestThresholds threshold, int numTasks, String testDescriptor) {
        mTestName = testName;
        //mFailingTestTasks = failingTestTasks;
        mTestType = testType;
        mNumTasks = numTasks;

        percentFailing = (double)failingTestTasks.size()/numTasks;

        this.testThreshold = threshold.getValue();

        //TODO:fix this jazz
        if(percentFailing > threshold.getValue()){
            testPassing = FALSE;
        }

        this.testDescription = testDescriptor;
    }


    public TestReport(String testName, Double score, DCMATestType testType, TestThresholds threshold, int numTasks, String testDescriptor) {
        mTestName = testName;
        //mFailingTestTasks = failingTestTasks;
        mTestType = testType;
        mNumTasks = numTasks;

        this.testThreshold = threshold.getValue();

        //TODO:fix this jazz
        /*
        if(index > threshold.getValue()){
            testPassing = FALSE;
        }
        */
        this.testPassing = FALSE;
        this.testDescription = testDescriptor;
    }




    @Override
    public String toString() {
        String out = "Test: " + mTestName + "\n";
        out += "Result: " + (testPassing ? "PASS!" : "FAIL!")+"\n";
        out += "Test Type: " + mTestType + "\n\n";

        if(mFailingTestTasks!=null) {
            for (Task t : mFailingTestTasks) {

                out += t.toString();

                switch (mTestType) {

                    case RELATIONSHIP:

                        List<Relation> relationships = t.getPredecessors();
                        relationships.addAll(t.getSuccessors());

                        out += relationships.toString() + "\n";
                    /*
                    for(Relation r:relationships){

                        out += r.toString();
                    }
                    */

                        break;

                    case DURATION:

                        out += t.getDuration().toString() + '\n';
                        out += t.getFreeSlack().toString() + '\n';

                        break;
                    case PROGRESS:

                        out += t.getPercentageComplete().toString() + '\n';
                        out += t.getBaselineStart().toString() + '\n';
                        out += t.getBaselineDuration().toString() + '\n';

                        break;
                }


            }
        }
        out += "\n\n";

        return out;
    }

    public String getmTestName() { 
        return mTestName;
    }

    public void setmTestName(String mTestName) {
        this.mTestName = mTestName;
    }

    public ArrayList<Task> getmFailingTestTasks() {
        return mFailingTestTasks;
    }

    public void setmFailingTestTasks(ArrayList<Task> mFailingTestTasks) {
        this.mFailingTestTasks = mFailingTestTasks;
    }

    public DCMATestType getmTestType() {
        return mTestType;
    }

    public void setmTestType(DCMATestType mTestType) {
        this.mTestType = mTestType;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public int getmNumTasks() {
        return mNumTasks;
    }

    public void setmNumTasks(int mNumTasks) {
        this.mNumTasks = mNumTasks;
    }

    public Double getPercentFailing() {
        return percentFailing;
    }

    public void setPercentFailing(Double percentFailing) {
        this.percentFailing = percentFailing;
    }

    public boolean isTestPassing() {
        return testPassing;
    }

    public void setTestPassing(boolean testPassing) {
        this.testPassing = testPassing;
    }

    public Double getIndex() {
        return index;
    }

    public void setIndex(Double index) {
        this.index = index;
    }


    public Double getTestThreshold() {
        return testThreshold;
    }

    public void setTestThreshold(Double testThreshold) {
        this.testThreshold = testThreshold;
    }

}
