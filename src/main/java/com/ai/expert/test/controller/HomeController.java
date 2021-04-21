package com.ai.expert.test.controller;

import com.ai.expert.test.entity.FileEntity;
import com.ai.expert.test.service.FileService;
import com.ai.expert.test.utils.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("")
public class HomeController {


    @Autowired
    FileService fileService;


    @GetMapping("/")
    public ModelAndView loginView(ModelAndView model) {
        model.setViewName("homepage");
        return model;
    }

    @PostMapping("/file/table")
    public DataTablesOutput<FileEntity> fileListTable(@Valid @RequestBody DataTablesInput input) {
        return fileService.findAll(input);
    }

    @DeleteMapping("/deleteFile/{id}")
    public ResponseEntity<JsonResponseBody> deleteFile(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {

        try {
            fileService.deleteFileEntityById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JsonResponseBody(HttpStatus.NOT_FOUND.value(), "File not found"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), "File delete successfully!"));
    }


    @PostMapping("/processFile/{id}")
    public ResponseEntity<JsonResponseBody> processFile(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {

        try {
            fileService.processFile(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JsonResponseBody(HttpStatus.NOT_FOUND.value(), "An error was encountered during the operation. Please try again"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), "File processed correctly!"));
    }

}
