package Service;

import Model.LoadDataModel;
import Repository.LoadDataRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
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
            if(header == null){
                return "No data in file";
            }
            List<LoadDataModel> modellist = new ArrayList<>();
            String record[];

            while((record=reader.readNext())!=null){
                LoadDataModel model = new LoadDataModel();
                model.setId(Integer.parseInt(record[0]));
                model.setName(record[1]);
                model.setFullname(record[2]);
                model.setDob(LocalDate.parse(record[3]));
                model.setPositions(record[4]);
                model.setRating(Integer.parseInt(record[5]));
                model.setPotential(Integer.parseInt(record[6]));
                model.setValue_euros(Long.parseLong(record[7]));
                model.setPreffered_foot(record[8]);
                model.setNationality(record[9]);
                model.setNatianal_jersey_number(Integer.parseInt(record[10]));

                modellist.add(model);
            }

            repo.saveAll(modellist);
            return "Data Loaded Successfully";


        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }


    }
}
