package com.hackingismakingisengineering.dcma.model.dcma;

import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class TestReport {

    public String mTestName;
    public ArrayList<Task> mFailingTestTasks;
    public DCMATestType mTestType;
    public int mNumTasks;
    public Double percentFailing;
    public boolean testPassing = TRUE;

    public Double index;

    public TestReport(String testName, ArrayList<Task> failingTestTasks, DCMATestType testType, TestThresholds threshold, int numTasks) {

        mTestName = testName;
        mFailingTestTasks = failingTestTasks;
        mTestType = testType;
        mNumTasks = numTasks;

        percentFailing = (double)failingTestTasks.size()/numTasks;

        if(percentFailing > threshold.getValue()){
            testPassing = FALSE;
        }

    }

    public TestReport(String testName, Double index, DCMATestType testType, TestThresholds threshold, int numTasks) {

        mTestName = testName;
        //mFailingTestTasks = failingTestTasks;
        mTestType = testType;
        mNumTasks = numTasks;

        this.index = index;

        //TODO:fix this jazz
        if(index > threshold.getValue()){
            testPassing = FALSE;
        }

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
}
