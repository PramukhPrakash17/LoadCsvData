package com.pramukh.loaddatatodb.Controller;

import com.pramukh.loaddatatodb.Service.LoadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class LoadDataController {

    @Autowired
    private LoadDataService service;

    @PostMapping("/load")
    public String LoadData(@RequestParam MultipartFile file){
        System.out.println("Entered");
        if(!checkfile(file)){
            return "Invalid file format";
        }
        return service.loadData(file);
    }

    @GetMapping("/getdata")
    public String getdata(){
        return "Hello";
    }

    private Boolean checkfile(MultipartFile file) {
        if(file.getOriginalFilename().toLowerCase().endsWith(".csv")){
            return true;
        } else {
            return false;
        }
    }
}