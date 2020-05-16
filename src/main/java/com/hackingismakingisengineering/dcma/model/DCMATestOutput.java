package com.hackingismakingisengineering.dcma.model;

import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;

import java.util.ArrayList;
import java.util.List;

import static com.hackingismakingisengineering.dcma.model.DCMATestThreshold.TEN;
import static com.hackingismakingisengineering.dcma.model.DCMATestType.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class DCMATestOutput {

    public String mTestName;
    public ArrayList<Task> mFailingTestTasks;
    public DCMATestType mTestType;
    public int mNumTasks;
    public double percentFailing;
    public boolean testPassing = TRUE;

    public DCMATestOutput(String testName, ArrayList<Task> failingTestTasks, DCMATestType testType, DCMATestThreshold threshold, int numTasks) {

        mTestName = testName;
        mFailingTestTasks = failingTestTasks;
        mTestType = testType;
        mNumTasks = numTasks;

        percentFailing = failingTestTasks.size()/numTasks;

        //TODO:fix this jazz
        if(percentFailing<0.10){
            testPassing = FALSE;
        }

    }


    @Override
    public String toString() {
        String out = "Test: " + mTestName + "\n";
        out = "result" + (testPassing ? "PASS!" : "FAIL!")+"\n";
        out += "TestTpye: " + mTestType + "\n";

        for(Task t : mFailingTestTasks){

            out += t.toString();

            switch (mTestType) {

                case RELATIONSHIP:

                    List<Relation> relationships = t.getPredecessors();
                    relationships.addAll(t.getSuccessors());

                    out += relationships.toString()+"\n";
                    /*
                    for(Relation r:relationships){

                        out += r.toString();
                    }
                    */

                    break;

                case DURATION:

                    out += t.getDuration().toString()+'\n';
                    out += t.getFreeSlack().toString()+'\n';

                    break;
                case PROGRESS:

                    out += t.getPercentageComplete().toString()+'\n';
                    out += t.getBaselineStart().toString()+'\n';
                    out += t.getBaselineDuration().toString()+'\n';

                    break;
            }


        }

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
