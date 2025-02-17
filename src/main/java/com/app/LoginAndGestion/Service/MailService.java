package com.app.LoginAndGestion.Service;

import com.app.LoginAndGestion.DTO.TaskDTO;
import com.app.LoginAndGestion.DTO.TaskLineDTO;
import com.app.LoginAndGestion.Model.TaskLine;
import com.app.LoginAndGestion.Model.User;
import com.app.LoginAndGestion.Repository.TaskRepository;
import jakarta.mail.internet.MimeMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.report.mail}")
    private String reportMail;
    @Value("${spring.report.mailjefe}")
    private String JefeArea;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String mailUsername) {
        this.mailSender = mailSender;
        this.mailUsername = mailUsername;
    }

    public void SendGmail(String to,String subject,String body) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");


            helper.setFrom(mailUsername);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
            System.out.println("Correo enviado correctamente a " + to);
        } catch (Exception ex) {
            System.err.println("Error al enviar el correo: " + ex.getMessage());
        }
    }
    public void reportNotificationTask(){
        List<TaskDTO> tasks= taskRepository.findAllForDocument(null,null,null)
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());


        List<TaskDTO> tareasPendientes = tasks.stream()
                .filter(task -> !"Completada".equals(task.getStatus()))
                .collect(Collectors.toList());

        if (tareasPendientes.isEmpty()) {
            System.out.println("No hay tareas pendientes para reportar.");
            return;
        }

        JSONArray tareasArray = new JSONArray();

        for (TaskDTO task : tareasPendientes) {
            Set<String> responsablesSet = new HashSet<>();
            for (User res : task.getResponsables()) {
                responsablesSet.add(res.getUsername());
            }
            JSONArray responsablesArray = new JSONArray(responsablesSet);

            // Iteramos cada línea de tarea
            for (TaskLineDTO tl : task.getTaskLines()) {
                JSONObject taskJson = new JSONObject();
                taskJson.put("name", task.getName());
                taskJson.put("proyecto", task.getProjectName());
                taskJson.put("descripcion", task.getDescription());
                taskJson.put("responsables", responsablesArray);
                taskJson.put("namesubtarea", tl.getDescription());
                taskJson.put("horas", tl.getHours());

                tareasArray.put(taskJson);
            }
        }

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("tareas", tareasArray);

        System.out.println(jsonResult.toString(4));


            try {
                StringBuilder body = new StringBuilder();
                body.append("<html><body>");
                body.append("<h3>Hola,</h3>");
                body.append("<p>Aqui tiene el resumen de las tareas pendientes:</p>");


                body.append("<table border='1' cellpadding='5' cellspacing='0'>");
                body.append("<tr>")
                        .append("<th>Nombre del proyecto</th>")
                        .append("<th>Tarea</th>")
                        .append("<th>Descripción</th>")
                        .append("<th>responsable</th>")
                        .append("<th>Nombre Subtarea</th>")
                        .append("<th>Horas</th>")
                        .append("</tr>");


                for (String keyStr : jsonResult.keySet()) {
                    Object keyvalue = null;
                    try {
                         keyvalue = jsonResult.get(keyStr);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    System.out.println("key: "+ keyStr + " value: " + keyvalue);
//                    Set<String> responsable = new HashSet<>();
//                    for (User res: task.getResponsables()){
//                        responsable.add(res.getUsername());
//                    }

                    JSONArray jsonArray = (JSONArray) keyvalue;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        JSONArray responsablesArray = jsonObject.getJSONArray("responsables");
                        StringBuilder responsablesBuilder = new StringBuilder();
                        for (int j = 0; j < responsablesArray.length(); j++) {
                            if (j > 0) {
                                responsablesBuilder.append(", ");
                            }
                            responsablesBuilder.append(responsablesArray.getString(j));
                        }
                        String responsables = responsablesBuilder.toString();

                        body.append("<tr>")
                                .append("<td>").append(jsonObject.getString("proyecto")).append("</td>")
                                .append("<td>").append(jsonObject.getString("name")).append("</td>")
                                .append("<td>").append(jsonObject.getString("descripcion")).append("</td>")
                                .append("<td>").append(jsonObject.getString("namesubtarea")).append("</td>")
                                .append("<td>").append(jsonObject.getDouble("horas")).append("</td>")
                                .append("<td>").append(responsables).append("</td>")
                                .append("</tr>");
                    }
                }

                body.append("</table>");
                body.append("<p>Saludos,<br>Departamento TI</p>");
                body.append("</body></html>");
                SendGmail(JefeArea, "Resumen de Tareas Pendientes", body.toString());
                SendGmail(reportMail, "Resumen de Tareas Pendientes", body.toString());
            } catch (Exception ex) {
                System.err.println("Error al enviar correo a " + reportMail + ": " + ex.getMessage());
            }

    }

    public void notificacionTask(){

        List<TaskDTO> tasks= taskRepository.findAllForDocument(null,null,null)
                .stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());

        Map<String, List<TaskDTO>> tareasPorUsuario = new HashMap<>();

        for(TaskDTO task : tasks){
            if (!"Completada".equals(task.getStatus())){
                for(String email: task.getMails()){
                    tareasPorUsuario.computeIfAbsent(email, k -> new ArrayList<>()).add(task);
                }
            }
        }

        tareasPorUsuario.forEach((email, tareasUsuario) -> {
            try {
                StringBuilder body = new StringBuilder();
                body.append("<html><body>");
                body.append("<h3>Hola,</h3>");
                body.append("<p>Aqui tienes el resumen de tus tareas pendientes:</p>");
                body.append("<table border='1' cellpadding='5' cellspacing='0'>");
                body.append("<tr>")
                        .append("<th>Nombre del proyecto</th>")
                        .append("<th>Tarea</th>")
                        .append("<th>Descripción</th>")
                        .append("<th>responsable</th>")
                        .append("<th>Nombre SubTarea</th>")
                        .append("<th>Horas</th>")
                        .append("</tr>");

                for (TaskDTO task : tareasUsuario) {
                    // Obtener los responsables
                    List<String> responsables = new ArrayList<>();
                    for (User user : task.getResponsables()) {
                        String username = user.getUsername();
                        if (!username.isEmpty()) {
                            responsables.add(username);
                        }
                    }
                    String responsable = responsables.toString().replace("]", "").replace("[", "");

                    // Obtener las líneas de tareas
                    Set<TaskLineDTO> taskLines = task.getTaskLines();

                    // Si no hay líneas de tareas, agregar una fila con valores por defecto
                    if (taskLines.isEmpty()) {

                        body.append("<tr>")
                                .append("<td>").append(task.getProjectName()).append("</td>")
                                .append("<td>").append(task.getName()).append("</td>")
                                .append("<td>").append(task.getDescription()).append("</td>")

                                .append("<td>").append(responsable).append("</td>")
                                .append("<td>").append("").append("</td>")  // Subtarea vacía
                                .append("<td>").append(0.0).append("</td>")  // Horas vacías
                                .append("</tr>");
                    } else {
                        // Si hay líneas de tareas, agregar una fila por cada línea
                        for (TaskLineDTO taskLine : taskLines) {
                            String nameSubtarea = taskLine.getDescription();
                            double horas = taskLine.getHours();

                            body.append("<tr>")
                                    .append("<td>").append(task.getProjectName()).append("</td>")
                                    .append("<td>").append(task.getName()).append("</td>")
                                    .append("<td>").append(task.getDescription()).append("</td>")
                                    .append("<td>").append(responsable).append("</td>")
                                    .append("<td>").append(Optional.ofNullable(nameSubtarea).orElse("")).append("</td>")
                                    .append("<td>").append(Optional.ofNullable(horas).orElse(0.0)).append("</td>")
                                    .append("</tr>");
                        }
                    }
                }

                body.append("</table>");
                body.append("<p>Por favor, revisa y actualiza el estado de estas tareas.</p>");
                body.append("<p>Saludos,<br>Departamento TI</p>");
                body.append("</body></html>");

                SendGmail(email, "Recordatorio de Tareas Pendientes", body.toString());
            } catch (Exception ex) {
                System.err.println("Error al enviar correo a " + email + ": " + ex.getMessage());
            }
        });
    }


}

