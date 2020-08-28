package com.hackingismakingisengineering.dcma;

import com.hackingismakingisengineering.dcma.model.dcma.Report;
import com.hackingismakingisengineering.dcma.model.Program;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.reader.UniversalProjectReader;

public class ProgramApplication {

    public static void main(String[] args) {

        ProjectFile projectFile =null;

        ProjectReader reader = new UniversalProjectReader();
        try {
            projectFile = reader.read("sample.mpp");
        } catch (MPXJException e) {
            e.printStackTrace();
        }


        Program programToBeTested = new Program(projectFile);

        System.out.println(programToBeTested.getReport().toString());



    }
}
