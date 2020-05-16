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


    private static final List<Program> allPrograms = Arrays.asList(
            new Program(null, "Cameron", "CRL", 1),
            new Program(null, "Kelly", "Picton", 1),
            new Program(null, "Garvan", "Wolverton", 2),
            new Program(null, "Rory", "SMW", 3)
    );

    public Program findByTitle(String title) {
        for (Program program : allPrograms) {
            if (title.equals(program.getTitle())) {
                return program;
            }
        }
        return null;
    }

    public List<Program> getAllPrograms() {
        return allPrograms;
    }

    public List<Program> getProgramByCategoryId(int id) {

        List<Program> programsInCategory = new ArrayList<>();

        for (Program program : allPrograms) {
            if (id == program.getCategoryId()) {
                programsInCategory.add(program);
            }

        }
        return programsInCategory;
    }
}
