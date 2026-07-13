package com.alumni.alumniconnectportal.controller;

import com.alumni.alumniconnectportal.model.User;
import com.alumni.alumniconnectportal.repository.UserRepository;
import com.alumni.alumniconnectportal.entity.Student;
import com.alumni.alumniconnectportal.repository.StudentRepository;
import com.alumni.alumniconnectportal.entity.Alumni;
import com.alumni.alumniconnectportal.repository.AlumniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AlumniRepository alumniRepository;

    // REGISTER API
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "User already exists";
        }
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "Admin registration is not allowed";
        }

        userRepository.save(user);
        if ("student".equalsIgnoreCase(user.getRole())) {

            Student student = new Student();
            student.setName(user.getName());
            student.setEmail(user.getEmail());
            student.setPassword(user.getPassword());
            student.setDepartment("");
            student.setGraduationYear(2026);

            studentRepository.save(student);
        }
        return "Registered successfully";
    }

    // LOGIN API
    @PostMapping("/login")
    public User login(@RequestBody User user) {

        System.out.println("===== LOGIN REQUEST =====");
        System.out.println("Entered Email : " + user.getEmail());
        System.out.println("Entered Password : " + user.getPassword());

        User dbUser = userRepository.findByEmail(user.getEmail());

        if (dbUser == null) {
            System.out.println("User not found");
            return null;
        }

        System.out.println("DB Email : " + dbUser.getEmail());
        System.out.println("DB Password : " + dbUser.getPassword());

        if (dbUser.getPassword().equals(user.getPassword())) {
            System.out.println("Login Successful");
            return dbUser;
        }

        System.out.println("Wrong Password");
        return null;
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestBody Map<String, String> data) {

        String email = data.get("email");
        String currentPassword = data.get("currentPassword");
        String newPassword = data.get("newPassword");

        User user = userRepository.findByEmailAndPassword(email, currentPassword);

        if (user == null) {
            return "Current password is incorrect";
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        return "Password Changed Successfully";
    }

    // GET PROFILE
    @GetMapping("/profile/{id}")
    public User getProfile(@PathVariable Long id) {

        return userRepository.findById(id).orElse(null);
    }

    // UPDATE PROFILE
    @PutMapping("/profile/{id}")
    public String updateProfile(@PathVariable Long id,
                                @RequestBody User updatedUser) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return "User not found";
        }

        user.setPhone(updatedUser.getPhone());
        user.setDepartment(updatedUser.getDepartment());
        user.setBatch(updatedUser.getBatch());
        user.setCompany(updatedUser.getCompany());
        user.setDesignation(updatedUser.getDesignation());
        user.setExperience(updatedUser.getExperience());
        user.setSalary(updatedUser.getSalary());
        user.setSkills(updatedUser.getSkills());
        user.setLocation(updatedUser.getLocation());
        user.setLinkedin(updatedUser.getLinkedin());
        user.setYear(updatedUser.getYear());
        user.setCgpa(updatedUser.getCgpa());
        user.setInterests(updatedUser.getInterests());

        userRepository.save(user);

        return "Profile Updated Successfully";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return "User not found";
        }

        Student student = studentRepository.findByEmail(user.getEmail());
        if (student != null) {
            studentRepository.delete(student);
        }

        Alumni alumni = alumniRepository.findByEmail(user.getEmail());
        if (alumni != null) {
            alumniRepository.delete(alumni);
        }

        userRepository.delete(user);

        return "Account Deleted Successfully";
    }
    // GET EMAIL BY NAME
    @GetMapping("/email/{name}")
    public String getEmail(@PathVariable String name) {

        User user = userRepository.findByName(name);

        if (user == null) {
            return "";
        }

        return user.getEmail();
    }

    // ADMIN DASHBOARD COUNTS
    @GetMapping("/admin/dashboard-count")
    public Map<String, Long> getDashboardCounts() {

        Map<String, Long> dashboard = new HashMap<>();

        dashboard.put("totalAlumni", userRepository.countByRole("ALUMNI"));
        dashboard.put("totalStudents", userRepository.countByRole("STUDENT"));
        dashboard.put("totalCompanies", userRepository.countDistinctCompanies());
        dashboard.put("totalLocations", userRepository.countDistinctLocations());

        return dashboard;
    }

    // FILTER DROPDOWNS
    @GetMapping("/admin/filters")
    public Map<String, List<String>> getFilters() {

        Map<String, List<String>> filters = new HashMap<>();

        filters.put("departments", userRepository.getDepartments());
        filters.put("batches", userRepository.getBatches());
        filters.put("companies", userRepository.getCompanies());
        filters.put("locations", userRepository.getLocations());

        return filters;
    }

    // FILTER SEARCH
    @GetMapping("/admin/filter-search")
    public List<User> filterAlumni(

            @RequestParam(defaultValue = "") String department,
            @RequestParam(defaultValue = "") String batch,
            @RequestParam(defaultValue = "") String company,
            @RequestParam(defaultValue = "") String location
    ) {

        return userRepository.filterAlumni(
                department,
                batch,
                company,
                location
        );
    }

    // SEARCH ALUMNI
    @GetMapping("/admin/search")
    public List<User> searchAlumni(@RequestParam String keyword) {

        return userRepository.searchAlumni(keyword);

    }

    @GetMapping("/admin/export-excel")
    public ResponseEntity<byte[]> exportExcel(

            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String department,
            @RequestParam(defaultValue = "") String batch,
            @RequestParam(defaultValue = "") String company,
            @RequestParam(defaultValue = "") String location,
            @RequestParam(defaultValue = "") String columns

    ) throws Exception {


        List<User> alumni;

        if (!keyword.isEmpty()) {

            alumni = userRepository.searchAlumni(keyword);

        } else {

            alumni = userRepository.filterAlumni(
                    department,
                    batch,
                    company,
                    location
            );

        }
        List<String> selectedColumns = List.of(columns.split(","));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        System.out.println("Columns = " + columns);
        System.out.println("Selected = " + selectedColumns);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Alumni");

        // Report Information
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("College Name");
        titleRow.createCell(1).setCellValue("VSB Engineering College");

        Row projectRow = sheet.createRow(1);
        projectRow.createCell(0).setCellValue("Project Name");
        projectRow.createCell(1).setCellValue("Alumni Connect Portal");

        Row reportRow = sheet.createRow(2);
        reportRow.createCell(0).setCellValue("Report Title");
        reportRow.createCell(1).setCellValue("Alumni Search Report");

        Row dateRow = sheet.createRow(3);
        dateRow.createCell(0).setCellValue("Export Date & Time");
        dateRow.createCell(1).setCellValue(java.time.LocalDateTime.now().toString());
        CellStyle headerStyle = workbook.createCellStyle();


        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        headerStyle.setFont(headerFont);

        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row header = sheet.createRow(5);

        int columnIndex = 0;
        for (String column : selectedColumns) {

            Cell cell = header.createCell(columnIndex++);
            cell.setCellValue(column.toUpperCase());
            cell.setCellStyle(headerStyle);

        }
        int rowNum = 6;

        for (User user : alumni) {

            Row row = sheet.createRow(rowNum++);
            int col = 0;

            for (String column : selectedColumns) {

                switch (column) {

                    case "name":
                        row.createCell(col++).setCellValue(user.getName() == null ? "" : user.getName());
                        break;

                    case "email":
                        row.createCell(col++).setCellValue(user.getEmail() == null ? "" : user.getEmail());
                        break;

                    case "phone":
                        row.createCell(col++).setCellValue(user.getPhone() == null ? "" : user.getPhone());
                        break;

                    case "department":
                        row.createCell(col++).setCellValue(user.getDepartment() == null ? "" : user.getDepartment());
                        break;

                    case "batch":
                        row.createCell(col++).setCellValue(user.getBatch() == null ? "" : user.getBatch());
                        break;

                    case "company":
                        row.createCell(col++).setCellValue(user.getCompany() == null ? "" : user.getCompany());
                        break;

                    case "designation":
                        row.createCell(col++).setCellValue(user.getDesignation() == null ? "" : user.getDesignation());
                        break;

                    case "salary":
                        row.createCell(col++).setCellValue(user.getSalary() == null ? "" : user.getSalary());
                        break;

                    case "location":
                        row.createCell(col++).setCellValue(user.getLocation() == null ? "" : user.getLocation());
                        break;

                    case "skills":
                        row.createCell(col++).setCellValue(user.getSkills() == null ? "" : user.getSkills());
                        break;

                    case "linkedin":
                        row.createCell(col++).setCellValue(user.getLinkedin() == null ? "" : user.getLinkedin());
                        break;
                }
            }
        }

        for (int i = 0; i < selectedColumns.size(); i++) {
            sheet.autoSizeColumn(i);
        }
        workbook.write(output);
        workbook.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Alumni_Report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(output.toByteArray());
    }

    @GetMapping("/admin/export-pdf")
    public ResponseEntity<byte[]> exportPdf(

            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String department,
            @RequestParam(defaultValue = "") String batch,
            @RequestParam(defaultValue = "") String company,
            @RequestParam(defaultValue = "") String location,
            @RequestParam(defaultValue = "") String columns

    ) throws Exception {
        List<User> alumni;

        if (!keyword.isEmpty()) {

            alumni = userRepository.searchAlumni(keyword);

        } else {

            alumni = userRepository.filterAlumni(
                    department,
                    batch,
                    company,
                    location
            );

        }

        List<String> selectedColumns = List.of(columns.split(","));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        Document document = new Document();

        PdfWriter.getInstance(document, output);

        document.open();
        Paragraph title = new Paragraph("VSB Engineering College");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("Alumni Search Report"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(selectedColumns.size());
        for (String column : selectedColumns) {
            PdfPCell cell = new PdfPCell(new Phrase(column.toUpperCase()));

            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

            table.addCell(cell);
        }
        for (User user : alumni) {

            for (String column : selectedColumns) {

                switch (column.toLowerCase()) {

                    case "name":
                        table.addCell(user.getName());
                        break;

                    case "email":
                        table.addCell(user.getEmail());
                        break;

                    case "phone":
                        table.addCell(user.getPhone());
                        break;

                    case "department":
                        table.addCell(user.getDepartment());
                        break;

                    case "batch":
                        table.addCell(user.getBatch());
                        break;

                    case "company":
                        table.addCell(user.getCompany());
                        break;

                    case "location":
                        table.addCell(user.getLocation());
                        break;

                    default:
                        table.addCell("");
                }
            }
        }

        document.add(table);
        document.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Alumni_Report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(output.toByteArray());
    }
}