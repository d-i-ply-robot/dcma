package com.hackingismakingisengineering.dcma.data;

import com.hackingismakingisengineering.dcma.model.Program;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.reader.UniversalProjectReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProgramRepository {
    //TODO: turn this from static to a proper databasae


    private static ArrayList<Program> allPrograms = new ArrayList<>();

            /*
            new Program(null, "Cameron", "CRL", 1),
            new Program(null, "Kelly", "Picton", 1),
            new Program(null, "Garvan", "Wolverton", 2),
            new Program(null, "Rory", "SMW", 3)

             */


    public Program findByTitle(String title) {
        for (Program program : allPrograms) {
            if (title.equals(program.getTitle())) {
                return program;
            }
        }
        return null;
    }

    public ArrayList<Program> getAllPrograms() {
        return allPrograms;
    }

    public ArrayList<Program> getProgramByCategoryId(Long id) {

        ArrayList<Program> programsInCategory = new ArrayList<>();

        for (Program program : allPrograms) {
            if (id.equals(program.getCategoryId())) {
                programsInCategory.add(program);
            }

        }
        return programsInCategory;
    }

    public void addProgram(Program programToBeTested) {
        allPrograms.add(programToBeTested);

    }
}
