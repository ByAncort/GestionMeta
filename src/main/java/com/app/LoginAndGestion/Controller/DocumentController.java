package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.DTO.TaskDTO;
import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Repository.TaskRepository;
import com.app.LoginAndGestion.Service.DocumentService;
import com.app.LoginAndGestion.Service.TaskService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/proyectos")
public class DocumentController {

    @Autowired
    private TaskService  taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private DocumentService documentService;

    @GetMapping("/download/excel/{name}")
    public ResponseEntity<ByteArrayResource> downloadExcel(
            @PathVariable String name,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date comienzo,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin
            ) throws IOException {
        if("all".equals(name)){
            name=null;
        }
        List<TaskDTO> tasks = taskRepository.findAllForDocument(name,comienzo,fin)
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
        byte[] excelBytes = documentService.generateExcel(tasks);

        ByteArrayResource resource = new ByteArrayResource(excelBytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tasks.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelBytes.length)
                .body(resource);
    }

}
