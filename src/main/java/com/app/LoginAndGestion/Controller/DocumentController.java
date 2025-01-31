package com.app.LoginAndGestion.Controller;

import com.app.LoginAndGestion.Model.Task;
import com.app.LoginAndGestion.Model.UserLogin;
import com.app.LoginAndGestion.Service.TaskService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/proyectos")
public class DocumentController {

    @Autowired
    private TaskService  taskService;

    @GetMapping("/download/excel/{name}/{rango}")
    public ResponseEntity<ByteArrayResource> downloadExcel(@PathVariable String name, @PathVariable String rango) throws IOException {
        List<Task> tasks = taskService.getTasksByDateRangeAndProjectName(name, rango);
        System.out.println(tasks.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Proyectos_" + name);

        // Estilos para encabezados
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);

        // Crear encabezados
        String[] headers = {
                "ID", "Creation Date", "Description", "Horas",
                "Last Update Date", "Name", "Project Name", "Status"
        };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Formato para fechas
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Crear filas de contenido
        int rowNum = 1;
        for (Task task : tasks) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(task.getId());
            row.createCell(1).setCellValue(task.getCreationDate() != null ? dateFormat.format(task.getCreationDate()) : "");
            row.createCell(2).setCellValue(task.getDescription());
            row.createCell(3).setCellValue(task.getHoras());
            row.createCell(4).setCellValue(task.getLastUpdateDate() != null ? dateFormat.format(task.getLastUpdateDate()) : ""); // ← Corregido
            row.createCell(5).setCellValue(task.getName());
            row.createCell(6).setCellValue(task.getProjectName());
            row.createCell(7).setCellValue(task.getStatus()); // ← Corregido (antes era 8)
        }

        // Ajustar tamaño de columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribir el archivo y cerrarlo
        workbook.write(baos);
        workbook.close();

        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=proyectos_" + name + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
