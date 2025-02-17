package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.DTO.TaskDTO;
import com.app.LoginAndGestion.DTO.TaskLineDTO;
import com.app.LoginAndGestion.Model.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    public byte[] generateExcel(List<TaskDTO> tasks) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tasks");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Nombre tarea", "Fecha Creacion", "Last Update Date", "Estado Tarea", "Horas", "Description", "Nombre Proyecto", "Responsables", "Tipo"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            sheet.setColumnWidth(0, 10 * 256);  // Columna "ID" (10 caracteres de ancho)
            sheet.setColumnWidth(1, 30 * 256);  // Columna "Name" (20 caracteres de ancho)
            sheet.setColumnWidth(2, 15 * 256);  // Columna "Creation Date" (15 caracteres de ancho)
            sheet.setColumnWidth(3, 15 * 256);  // Columna "Last Update Date" (15 caracteres de ancho)
            sheet.setColumnWidth(4, 15 * 256);  // Columna "Status" (12 caracteres de ancho)
            sheet.setColumnWidth(5, 10 * 256);  // Columna "Horas" (10 caracteres de ancho)
            sheet.setColumnWidth(6, 30 * 256);  // Columna "Description" (30 caracteres de ancho)
            sheet.setColumnWidth(7, 20 * 256);  // Columna "Project Name" (20 caracteres de ancho)
            sheet.setColumnWidth(8, 20 * 256);  // Columna "Responsables" (20 caracteres de ancho)
            sheet.setColumnWidth(9, 15 * 256);  // Columna "Type" (15 caracteres de ancho)

            sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, columns.length - 1));

            // Fill data rows
            int rowNum = 1;
            for (TaskDTO task : tasks) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(task.getId());
                row.createCell(1).setCellValue(task.getName() != null ? task.getName() : "N/A");
                row.createCell(2).setCellValue(task.getCreationDate() != null ? task.getCreationDate().toString() : "N/A");
                row.createCell(3).setCellValue(task.getLastUpdateDate() != null ? task.getLastUpdateDate().toString() : "N/A");
                row.createCell(4).setCellValue(task.getStatus() != null ? task.getStatus() : "N/A");
                double horas= 0;
                for(TaskLineDTO line : task.getTaskLines()){
                    horas+=line.getHours();
                }
                row.createCell(5).setCellValue(horas);
                row.createCell(6).setCellValue(task.getDescription() != null ? task.getDescription() : "N/A");
                row.createCell(7).setCellValue(task.getProjectName() != null ? task.getProjectName() : "N/A");
                row.createCell(8).setCellValue(
                        task.getResponsables() != null
                                ? task.getResponsables().stream() // Convertir Set<User> a Stream<User>
                                .map(User::getUsername)    // Mapear cada User a su nombre de usuario (String)
                                .collect(Collectors.joining(", ")) // Unir los nombres con ", "
                                : "N/A" // Si task.getResponsables() es nulo
                );                row.createCell(9).setCellValue(task.getType() != null ? task.getType() : "N/A");

            }

            // Write the output to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }


}
