package com.pramukh.loaddatatodb.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.pramukh.loaddatatodb.Model.LoadDataModel;
import com.pramukh.loaddatatodb.Repository.LoadDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LoadDataService {

    @Autowired
    private LoadDataRepository repo;

    public String loadData(MultipartFile file) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            String[] header = reader.readNext();
            if (header == null) {
                return "No data in file";
            }
            List<LoadDataModel> modellist = new ArrayList<>();
            String record[];

            while ((record = reader.readNext()) != null) {
                System.out.println(Arrays.toString(record));
                LoadDataModel model = new LoadDataModel();

                model.setId(Integer.parseInt(record[0]));
                model.setName(record[1]);
                model.setFullname(record[2]);
                model.setDob(parseDate(record[3])); // Parse date with multiple formats
                model.setPositions(record[4]);
                model.setRating(Integer.parseInt(record[5]));
                model.setPotential(Integer.parseInt(record[6]));
                model.setValue_euros(emptyLong(record[7]));
                model.setPreffered_foot(record[8]);
                model.setNationality(emptyString(record[9]));
                model.setNatianal_jersey_number(emptyInteger(record[10]));

                modellist.add(model);
            }

            repo.saveAll(modellist);
            return "Data Loaded Successfully";

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("M/d/yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        throw new DateTimeParseException("Unable to parse date: " + dateStr, dateStr, 0);
    }

    private Integer emptyInteger(String s)
    {
        if(s!=null && !s.trim().isEmpty())
        {
            return Integer.parseInt(s);
        }
        else{
            return 0;
        }
    }

    private String emptyString(String s)
    {
        if(s!=null && !s.trim().isEmpty())
        {
            return s;
        }
        else{
            return "";
        }
    }

    private Long emptyLong(String s)
    {
        if(s!=null && !s.trim().isEmpty())
        {
            return Long.parseLong(s);
        }
        else{
            return 0L;
        }
    }

}
