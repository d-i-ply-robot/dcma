package com.hackingismakingisengineering.dcma;

import com.hackingismakingisengineering.dcma.model.DCMAReport;
import com.hackingismakingisengineering.dcma.model.Program;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mspdi.schema.Project;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.UniversalProjectReader;

public class ProgramApplication {

    public static void main(String[] args) {

        ProjectFile project =null;

        ProjectReader reader = new UniversalProjectReader();
        try {
            project = reader.read("sample.mpp");
        } catch (MPXJException e) {
            e.printStackTrace();
        }


        Program programToBeTested = new Program(project);

        DCMAReport dcmaReport = new DCMAReport(programToBeTested);

        dcmaReport.getDcmaTestOutput().toString();

    }
}
