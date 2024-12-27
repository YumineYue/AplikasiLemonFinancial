package org.example.demo1;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class LoginPage extends Application {

    private List<Transaksi> transaksiList = new ArrayList<>(); // To store the list of transactions

    @Override
    public void start(Stage primaryStage) {
        // Label for username and password
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("label");
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label");

        // TextField for username input
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.getStyleClass().add("text-field");

        // PasswordField for password input
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.getStyleClass().add("password-field");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");

        // Action when login button is clicked
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Check username and password (example: hardcoded)
            if (username.equals("admin") && password.equals("admin123")) {
                System.out.println("Login Successful!");
                showMainMenu(primaryStage);
            } else {
                System.out.println("Login Failed! Invalid username or password.");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.", ButtonType.OK);
                alert.show();
            }
        });

        // Layout for the login form
        VBox loginForm = new VBox(10);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
        loginForm.getStyleClass().add("login-form");

        // Set preferred width for the form to make it more flexible
        loginForm.setPrefWidth(300); // Form width will adjust based on window size

        // Image for the left side (using the path provided)
        ImageView imageView = new ImageView(new Image("file:C:/Users/USER/Downloads/ernest-porzi-Z-Y6I45f9kQ-unsplash.jpg"));
        imageView.setPreserveRatio(true);  // Preserve aspect ratio
        imageView.setFitWidth(250);  // Adjust width of the image
        imageView.getStyleClass().add("login-image");

        // Layout for the entire login page (HBox)
        HBox loginLayout = new HBox(50);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setFillHeight(true);  // Allow HBox to stretch vertically
        loginLayout.getChildren().addAll(imageView, loginForm);
        loginLayout.getStyleClass().add("login-container");

        // Bind form width to window width
        loginForm.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.3)); // 30% of the window width
        imageView.fitWidthProperty().bind(primaryStage.widthProperty().multiply(0.4)); // 40% of the window width

        // Scene for login
        Scene scene = new Scene(loginLayout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Link the CSS file

        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Maximize the window at startup (optional)
        primaryStage.show();
    }

    private void showMainMenu(Stage primaryStage) {
        // Create Buttons for the Main Menu
        Button dashboardButton = new Button("Dashboard");
        Button transaksiButton = new Button("Transaksi");
        Button laporanKeuanganButton = new Button("Laporan Keuangan");
        Button targetKeuanganButton = new Button("Target Keuangan");
        Button eksportDataButton = new Button("Eksport Data");
        Button logoutButton = new Button("Logout");

        // Set actions for each button
        dashboardButton.setOnAction(event -> showDashboard(primaryStage));
        transaksiButton.setOnAction(event -> showTransactionPage(primaryStage));
        laporanKeuanganButton.setOnAction(event -> showFinancialReportPage(primaryStage));
        targetKeuanganButton.setOnAction(event -> showTargetKeuanganPage(primaryStage));
        eksportDataButton.setOnAction(event -> showExportDataPage(primaryStage));
        logoutButton.setOnAction(event -> {
            start(new Stage()); // Go back to login page
            primaryStage.close();
        });

        // Style buttons inline to make them modern and responsive
        setButtonStyle(dashboardButton);
        setButtonStyle(transaksiButton);
        setButtonStyle(laporanKeuanganButton);
        setButtonStyle(targetKeuanganButton);
        setButtonStyle(eksportDataButton);
        setButtonStyle(logoutButton);

        // Create VBox for vertical navigation bar
        VBox navBar = new VBox(20);
        navBar.setStyle(
                "-fx-background-color: #2c3e50;" + // Dark background for the sidebar
                        "-fx-padding: 20px;" +
                        "-fx-alignment: center;"
        );
        navBar.getChildren().addAll(
                dashboardButton, transaksiButton, laporanKeuanganButton,
                targetKeuanganButton, eksportDataButton, logoutButton
        );

        // Set the width for the sidebar
        navBar.setMinWidth(200);

        // Create StackPane to center the entire layout
        StackPane centerLayout = new StackPane();
        centerLayout.getChildren().add(navBar);

        // Create the main layout (add navBar to the center and content to the center of the screen)
        StackPane mainLayout = new StackPane();
        mainLayout.getChildren().add(centerLayout);

        // Set the scene size to be responsive
        Scene mainMenuScene = new Scene(mainLayout, 800, 600);
        mainMenuScene.widthProperty().addListener((observable, oldWidth, newWidth) -> adjustLayoutForWidth(mainLayout, newWidth.doubleValue()));
        mainMenuScene.heightProperty().addListener((observable, oldHeight, newHeight) -> adjustLayoutForHeight(mainLayout, newHeight.doubleValue()));

        // Set stage properties
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    private void setButtonStyle(Button button) {
        button.setStyle(
                "-fx-background-color: #3498db;" + // Blue background
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-padding: 12px 20px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-min-width: 180px;" +
                        "-fx-cursor: hand;" +
                        "-fx-transition: all 0.3s ease;"
        );

        // Hover effect
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 5px; -fx-cursor: hand;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 20px; -fx-border-radius: 5px; -fx-cursor: hand;"));
    }

    private void adjustLayoutForWidth(StackPane mainLayout, double newWidth) {
        if (newWidth < 768) {
            // Adjust for smaller screens
            mainLayout.setPrefWidth(newWidth);
        }
    }

    private void adjustLayoutForHeight(StackPane mainLayout, double newHeight) {
        if (newHeight < 600) {
            // Adjust for smaller height
            mainLayout.setPrefHeight(newHeight);
        }
    }



    private void showTransactionPage(Stage primaryStage) {
        // Form to add a new transaction
        Label tanggalLabel = new Label("Tanggal:");
        DatePicker tanggalPicker = new DatePicker();
        tanggalPicker.setValue(LocalDate.now());

        Label jumlahLabel = new Label("Jumlah (Rp):");
        TextField jumlahField = new TextField();
        jumlahField.setPromptText("Masukkan jumlah");

        Label keteranganLabel = new Label("Keterangan:");
        TextField keteranganField = new TextField();
        keteranganField.setPromptText("Masukkan keterangan");

        // Checkboxes for income (pendapatan) or expense (pengeluaran)
        CheckBox pendapatanCheckBox = new CheckBox("Pendapatan");
        CheckBox pengeluaranCheckBox = new CheckBox("Pengeluaran");

        // Button to add a new transaction
        Button tambahButton = new Button("Tambah Transaksi");

        // Action when "Tambah Transaksi" button is clicked
        tambahButton.setOnAction(event -> {
            LocalDate tanggal = tanggalPicker.getValue();
            String jumlah = jumlahField.getText();
            String keterangan = keteranganField.getText();

            boolean isPendapatan = pendapatanCheckBox.isSelected();
            boolean isPengeluaran = pengeluaranCheckBox.isSelected();

            if (isPendapatan && isPengeluaran) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pilih hanya satu jenis transaksi (Pendapatan atau Pengeluaran).", ButtonType.OK);
                alert.show();
            } else {
                // Adding a new transaction to the list
                Transaksi transaksi = new Transaksi(tanggal, jumlah, keterangan, isPendapatan, isPengeluaran);
                transaksiList.add(transaksi);
                System.out.println("Transaksi Baru:");
                System.out.println("Tanggal: " + tanggal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                System.out.println("Jumlah: Rp " + jumlah);
                System.out.println("Keterangan: " + keterangan);
                System.out.println("Pendapatan: " + isPendapatan);
                System.out.println("Pengeluaran: " + isPengeluaran);

                // Refresh the transaction table
                refreshTransactionTable(primaryStage);

                // Reset form after adding
                clearForm(tanggalPicker, jumlahField, keteranganField, pendapatanCheckBox, pengeluaranCheckBox);
            }
        });

        // Filter by date

        // Table to display transactions
        TableView<Transaksi> transactionTable = new TableView<>();
        TableColumn<Transaksi, String> dateColumn = new TableColumn<>("Tanggal");
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTanggal().toString()));

        TableColumn<Transaksi, String> amountColumn = new TableColumn<>("Jumlah");
        amountColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>("Rp " + cellData.getValue().getJumlah()));

        TableColumn<Transaksi, String> descriptionColumn = new TableColumn<>("Keterangan");
        descriptionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getKeterangan()));

        // TableColumn for actions (Only delete now)
        TableColumn<Transaksi, String> actionColumn = new TableColumn<>("Aksi");
        actionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>("Hapus"));
        actionColumn.setCellFactory(param -> new TableCell<Transaksi, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button deleteButton = new Button("Hapus");

                    // Hapus (Delete) Button Action
                    deleteButton.setOnAction(event -> {
                        Transaksi transaksi = getTableRow().getItem();
                        if (transaksi != null) {
                            // Remove the transaction from the list and table
                            transaksiList.remove(transaksi);
                            transactionTable.getItems().remove(transaksi);
                        }
                    });

                    HBox actionBox = new HBox(10, deleteButton);
                    setGraphic(actionBox);
                }
            }
        });

        transactionTable.getColumns().addAll(dateColumn, amountColumn, descriptionColumn, actionColumn);
        transactionTable.getItems().addAll(transaksiList);

        // Layout for transaction form and filter
        VBox transaksiLayout = new VBox(10);
        transaksiLayout.setAlignment(Pos.CENTER);
        transaksiLayout.getChildren().addAll(
                tanggalLabel, tanggalPicker, jumlahLabel, jumlahField,
                keteranganLabel, keteranganField, pendapatanCheckBox, pengeluaranCheckBox, tambahButton, transactionTable);

        // Main layout with sidebar on the left and transaction content on the right
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(createSidebar(primaryStage));  // Sidebar
        borderPane.setCenter(transaksiLayout);  // Transaction content

        // Scene for transaction page
        Scene transaksiScene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Tambah Transaksi");
        primaryStage.setScene(transaksiScene);
        primaryStage.show();
    }

    private void clearForm(DatePicker tanggalPicker, TextField jumlahField, TextField keteranganField, CheckBox pendapatanCheckBox, CheckBox pengeluaranCheckBox) {
        // Reset the form fields
        tanggalPicker.setValue(LocalDate.now());
        jumlahField.clear();
        keteranganField.clear();
        pendapatanCheckBox.setSelected(false);
        pengeluaranCheckBox.setSelected(false);
    }

    // Function to refresh the transaction table after adding a transaction
    private void refreshTransactionTable(Stage primaryStage) {
        TableView<Transaksi> transactionTable = new TableView<>();
            transactionTable.getItems().clear();
            transactionTable.getItems().addAll(transaksiList);

        // The rest of the code to refresh the table goes here (similar to the code in showTransactionPage)
    }

    private void showFinancialReportPage(Stage primaryStage) {
        // Header
        Label headerLabel = new Label("Laporan Keuangan");
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Total Pendapatan dan Pengeluaran
        int totalPendapatan = transaksiList.stream()
                .filter(Transaksi::isPendapatan)
                .mapToInt(t -> Integer.parseInt(t.getJumlah()))
                .sum();
        int totalPengeluaran = transaksiList.stream()
                .filter(Transaksi::isPengeluaran)
                .mapToInt(t -> Integer.parseInt(t.getJumlah()))
                .sum();
        int labaRugi = totalPendapatan - totalPengeluaran;

        Label pendapatanLabel = new Label("Total Pendapatan: Rp " + totalPendapatan);
        Label pengeluaranLabel = new Label("Total Pengeluaran: Rp " + totalPengeluaran);
        Label labaRugiLabel = new Label("Laba/Rugi: Rp " + labaRugi);

        // Tabel Laporan
        TableView<Transaksi> laporanTable = new TableView<>();
        TableColumn<Transaksi, String> dateColumn = new TableColumn<>("Tanggal");
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTanggal().toString()));

        TableColumn<Transaksi, String> typeColumn = new TableColumn<>("Tipe");
        typeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(
                cellData.getValue().isPendapatan() ? "Pendapatan" : "Pengeluaran"));

        TableColumn<Transaksi, String> amountColumn = new TableColumn<>("Jumlah");
        amountColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>("Rp " + cellData.getValue().getJumlah()));

        TableColumn<Transaksi, String> descriptionColumn = new TableColumn<>("Keterangan");
        descriptionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getKeterangan()));

        laporanTable.getColumns().addAll(dateColumn, typeColumn, amountColumn, descriptionColumn);
        laporanTable.getItems().addAll(transaksiList);

        // Layout
        VBox laporanLayout = new VBox(10);
        laporanLayout.setAlignment(Pos.CENTER);
        laporanLayout.getChildren().addAll(headerLabel, pendapatanLabel, pengeluaranLabel, labaRugiLabel, laporanTable);

        // Main layout with sidebar and laporan content
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(createSidebar(primaryStage)); // Sidebar
        borderPane.setCenter(laporanLayout); // Laporan content

        // Scene for laporan keuangan page
        Scene laporanScene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Laporan Keuangan");
        primaryStage.setScene(laporanScene);
        primaryStage.show();
    }

    // Function to show financial targets page
    private void showTargetKeuanganPage(Stage primaryStage) {
        // Form for adding a new financial target
        Label namaTargetLabel = new Label("Nama Target:");
        TextField namaTargetField = new TextField();
        namaTargetField.setPromptText("Masukkan nama target");

        Label jumlahTargetLabel = new Label("Jumlah Target (Rp):");
        TextField jumlahTargetField = new TextField();
        jumlahTargetField.setPromptText("Masukkan jumlah target");

        Label tanggalMulaiLabel = new Label("Tanggal Mulai:");
        DatePicker tanggalMulaiPicker = new DatePicker();

        Label tanggalSelesaiLabel = new Label("Tanggal Selesai:");
        DatePicker tanggalSelesaiPicker = new DatePicker();

        // Button to save the new target
        Button simpanButton = new Button("Simpan Target");

        // List to store targets
        List<TargetKeuangan> targetList = new ArrayList<>();

        // Table for completed targets
        TableView<TargetKeuangan> completedTargetsTable = new TableView<>();
        TableColumn<TargetKeuangan, String> namaColumn = new TableColumn<>("Nama Target");
        namaColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNamaTarget()));

        TableColumn<TargetKeuangan, String> jumlahColumn = new TableColumn<>("Jumlah Target");
        jumlahColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>("Rp " + cellData.getValue().getJumlahTarget()));

        TableColumn<TargetKeuangan, String> progressColumn = new TableColumn<>("Progress (%)");
        progressColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProgress() + "%"));

        completedTargetsTable.getColumns().addAll(namaColumn, jumlahColumn, progressColumn);

        // Action for saving the target
        simpanButton.setOnAction(event -> {
            String namaTarget = namaTargetField.getText();
            String jumlahTarget = jumlahTargetField.getText();
            LocalDate tanggalMulai = tanggalMulaiPicker.getValue();
            LocalDate tanggalSelesai = tanggalSelesaiPicker.getValue();

            if (namaTarget.isEmpty() || jumlahTarget.isEmpty() || tanggalMulai == null || tanggalSelesai == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Semua field harus diisi!", ButtonType.OK);
                alert.show();
                return;
            }

            TargetKeuangan newTarget = new TargetKeuangan(namaTarget, jumlahTarget, tanggalMulai, tanggalSelesai);
            targetList.add(newTarget);

            // Update the progress bar and table
            refreshTargetDisplay(targetList, completedTargetsTable);

            namaTargetField.clear();
            jumlahTargetField.clear();
            tanggalMulaiPicker.setValue(null);
            tanggalSelesaiPicker.setValue(null);
        });

        // Layout for adding a target
        VBox addTargetLayout = new VBox(10);
        addTargetLayout.getChildren().addAll(
                namaTargetLabel, namaTargetField,
                jumlahTargetLabel, jumlahTargetField,
                tanggalMulaiLabel, tanggalMulaiPicker,
                tanggalSelesaiLabel, tanggalSelesaiPicker,
                simpanButton
        );

        // Section for active targets and their progress
        VBox activeTargetLayout = new VBox(10);
        Label activeTargetLabel = new Label("Target Aktif:");
        ProgressBar progressBar = new ProgressBar(0);

        activeTargetLayout.getChildren().addAll(activeTargetLabel, progressBar);

        // Section for completed targets
        VBox completedTargetsLayout = new VBox(10);
        Label completedTargetsLabel = new Label("Target Selesai:");
        completedTargetsLayout.getChildren().addAll(completedTargetsLabel, completedTargetsTable);

        // Main layout combining all sections
        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(addTargetLayout, activeTargetLayout, completedTargetsLayout);

        // Add sidebar and content to main layout
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(createSidebar(primaryStage));  // Sidebar
        borderPane.setCenter(mainLayout);

        // Scene for target keuangan page
        Scene targetKeuanganScene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Target Keuangan");
        primaryStage.setScene(targetKeuanganScene);
        primaryStage.show();
    }

    // Refresh the target display to update the progress bar and completed targets table
    private void refreshTargetDisplay(List<TargetKeuangan> targetList, TableView<TargetKeuangan> completedTargetsTable) {
        // Dummy logic to simulate progress calculation
        double progress = Math.random();
        ProgressBar progressBar = new ProgressBar(progress);

        // Populate the completed targets table
        completedTargetsTable.getItems().clear();
        completedTargetsTable.getItems().addAll(targetList);
    }

    // Class to represent financial targets
    class TargetKeuangan {
        private String namaTarget;
        private String jumlahTarget;
        private LocalDate tanggalMulai;
        private LocalDate tanggalSelesai;
        private int progress;

        public TargetKeuangan(String namaTarget, String jumlahTarget, LocalDate tanggalMulai, LocalDate tanggalSelesai) {
            this.namaTarget = namaTarget;
            this.jumlahTarget = jumlahTarget;
            this.tanggalMulai = tanggalMulai;
            this.tanggalSelesai = tanggalSelesai;
            this.progress = (int) (Math.random() * 100);  // Simulate random progress
        }

        public String getNamaTarget() {
            return namaTarget;
        }

        public String getJumlahTarget() {
            return jumlahTarget;
        }

        public LocalDate getTanggalMulai() {
            return tanggalMulai;
        }

        public LocalDate getTanggalSelesai() {
            return tanggalSelesai;
        }

        public int getProgress() {
            return progress;
        }
    }

    // Function to show export data page
    private void showExportDataPage(Stage primaryStage) {
        // Checkboxes for selecting data to export
        CheckBox transaksiCheckBox = new CheckBox("Data Transaksi");
        CheckBox laporanKeuanganCheckBox = new CheckBox("Laporan Keuangan");
        CheckBox targetKeuanganCheckBox = new CheckBox("Target Keuangan");

        // Date pickers for selecting date range
        Label periodeLabel = new Label("Periode Data:");
        DatePicker tanggalMulaiPicker = new DatePicker();
        DatePicker tanggalAkhirPicker = new DatePicker();

        // File format selection
        Label fileFormatLabel = new Label("Pilih Format File:");
        RadioButton excelRadioButton = new RadioButton("Excel");
        RadioButton pdfRadioButton = new RadioButton("PDF");
        RadioButton csvRadioButton = new RadioButton("CSV");
        ToggleGroup fileFormatGroup = new ToggleGroup();
        excelRadioButton.setToggleGroup(fileFormatGroup);
        pdfRadioButton.setToggleGroup(fileFormatGroup);
        csvRadioButton.setToggleGroup(fileFormatGroup);
        excelRadioButton.setSelected(true); // Default selection

        // Export button
        Button eksporButton = new Button("Ekspor");

        // Layout for checkboxes
        VBox checkboxLayout = new VBox(10, transaksiCheckBox, laporanKeuanganCheckBox, targetKeuanganCheckBox);

        // Layout for date pickers
        HBox datePickerLayout = new HBox(10, new Label("Tanggal Mulai:"), tanggalMulaiPicker, new Label("Tanggal Akhir:"), tanggalAkhirPicker);
        datePickerLayout.setAlignment(Pos.CENTER_LEFT);

        // Layout for file format selection
        HBox fileFormatLayout = new HBox(10, excelRadioButton, pdfRadioButton, csvRadioButton);
        fileFormatLayout.setAlignment(Pos.CENTER_LEFT);

        // Export button action
        eksporButton.setOnAction(event -> {
            boolean isTransaksiSelected = transaksiCheckBox.isSelected();
            boolean isLaporanKeuanganSelected = laporanKeuanganCheckBox.isSelected();
            boolean isTargetKeuanganSelected = targetKeuanganCheckBox.isSelected();
            LocalDate tanggalMulai = tanggalMulaiPicker.getValue();
            LocalDate tanggalAkhir = tanggalAkhirPicker.getValue();
            String fileFormat = ((RadioButton) fileFormatGroup.getSelectedToggle()).getText();

            if (!isTransaksiSelected && !isLaporanKeuanganSelected && !isTargetKeuanganSelected) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pilih setidaknya satu jenis data untuk diekspor!", ButtonType.OK);
                alert.show();
                return;
            }

            if (tanggalMulai == null || tanggalAkhir == null || tanggalMulai.isAfter(tanggalAkhir)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Periode tanggal tidak valid!", ButtonType.OK);
                alert.show();
                return;
            }

            // Perform export based on selected file format
            List<String> dataToExport = generateDummyData();  // Replace with actual data
            switch (fileFormat) {
                case "Excel":
                    try {
                        exportToExcel(primaryStage, dataToExport);
                    } catch (IOException e) {
                        showError("Error exporting to Excel: " + e.getMessage());
                    }
                    break;
                case "PDF":
                    try {
                        exportToPDF(primaryStage, dataToExport);
                    } catch (Exception e) {
                        showError("Error exporting to PDF: " + e.getMessage());
                    }
                    break;
                case "CSV":
                    try {
                        exportToCSV(primaryStage, dataToExport);
                    } catch (IOException e) {
                        showError("Error exporting to CSV: " + e.getMessage());
                    }
                    break;
            }
        });

        // Main layout
        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(
                new Label("Pilih Data untuk Diekspor:"),
                checkboxLayout,
                periodeLabel,
                datePickerLayout,
                fileFormatLabel,
                fileFormatLayout,
                eksporButton
        );

        // Add sidebar and content to main layout
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(createSidebar(primaryStage));  // Sidebar
        borderPane.setCenter(mainLayout);

        // Scene for export data page
        Scene exportDataScene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Ekspor Data");
        primaryStage.setScene(exportDataScene);
        primaryStage.show();
    }

    // Helper method to show errors
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    // Dummy data generation for export
    private List<String> generateDummyData() {
        return List.of("Record 1", "Record 2", "Record 3", "Record 4");
    }

    private void exportToExcel(Stage stage, List<String> data) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

        // Show the save file dialog
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data");

            int rowNum = 0;
            for (String record : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(record);
            }

            // Writing the file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
            workbook.close();
        }
    }

    private void exportToPDF(Stage stage, List<String> data) throws DocumentException, FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            for (String record : data) {
                document.add(new Paragraph(record));
            }

            document.close();
        }
    }

    private void exportToCSV(Stage stage, List<String> data) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String record : data) {
                    writer.write(record);
                    writer.newLine();
                }
            }
        }
    }
    // Function to show dashboard with a graph
    private void showDashboard(Stage primaryStage) {
        // Create a chart (e.g., BarChart) to display the transactions
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Jenis Transaksi");
        yAxis.setLabel("Jumlah (Rp)");

        // Creating data series for the chart
        XYChart.Series<String, Number> seriesPendapatan = new XYChart.Series<>();
        seriesPendapatan.setName("Pendapatan");
        XYChart.Series<String, Number> seriesPengeluaran = new XYChart.Series<>();
        seriesPengeluaran.setName("Pengeluaran");

        // Sum up the transactions for Pendapatan and Pengeluaran
        int pendapatanTotal = 0;
        int pengeluaranTotal = 0;
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.isPendapatan()) {
                pendapatanTotal += Integer.parseInt(transaksi.getJumlah());
            }
            if (transaksi.isPengeluaran()) {
                pengeluaranTotal += Integer.parseInt(transaksi.getJumlah());
            }
        }

        // Add data to the chart
        seriesPendapatan.getData().add(new XYChart.Data<>("Pendapatan", pendapatanTotal));
        seriesPengeluaran.getData().add(new XYChart.Data<>("Pengeluaran", pengeluaranTotal));

        chart.getData().addAll(seriesPendapatan, seriesPengeluaran);

        // Creating layout for the dashboard
        VBox dashboardLayout = new VBox(15);
        dashboardLayout.setAlignment(Pos.CENTER);
        dashboardLayout.getChildren().addAll(chart);

        // Main layout with sidebar on the left and dashboard content on the right
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(createSidebar(primaryStage));  // Sidebar
        borderPane.setCenter(dashboardLayout);  // Dashboard content

        // Scene for dashboard
        Scene dashboardScene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(dashboardScene);
        primaryStage.show();
    }

    // Sidebar for navigation
    private VBox createSidebar(Stage primaryStage) {
        Button dashboardButton = new Button("Dashboard");
        Button transaksiButton = new Button("Transaksi");
        Button laporanKeuanganButton = new Button("Laporan Keuangan");
        Button targetKeuanganButton = new Button("Target Keuangan");
        Button eksportDataButton = new Button("Eksport Data");
        Button logoutButton = new Button("Logout");

        VBox sidebar = new VBox(15);
        sidebar.setStyle("-fx-background-color: #2f4f4f; -fx-padding: 10px;");
        sidebar.setPrefWidth(200);
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.getChildren().addAll(
                dashboardButton, transaksiButton, laporanKeuanganButton,
                targetKeuanganButton, eksportDataButton, logoutButton);

        dashboardButton.setOnAction(event -> showDashboard(primaryStage)); // Show dashboard
        transaksiButton.setOnAction(event -> showTransactionPage(primaryStage)); // Show transaksi page
        laporanKeuanganButton.setOnAction(event -> showFinancialReportPage(primaryStage));
        targetKeuanganButton.setOnAction(event -> showTargetKeuanganPage(primaryStage));
        eksportDataButton.setOnAction(event -> showExportDataPage(primaryStage));
        logoutButton.setOnAction(event -> {
            start(new Stage());  // Return to the login page
            primaryStage.close();
        });

        return sidebar;
    }

    // Transaksi class to represent each transaction
    class Transaksi {
        private LocalDate tanggal;
        private String jumlah;
        private String keterangan;
        private boolean pendapatan;
        private boolean pengeluaran;

        public Transaksi(LocalDate tanggal, String jumlah, String keterangan, boolean pendapatan, boolean pengeluaran) {
            this.tanggal = tanggal;
            this.jumlah = jumlah;
            this.keterangan = keterangan;
            this.pendapatan = pendapatan;
            this.pengeluaran = pengeluaran;
        }

        public LocalDate getTanggal() {
            return tanggal;
        }

        public String getJumlah() {
            return jumlah;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public boolean isPendapatan() {
            return pendapatan;
        }

        public boolean isPengeluaran() {
            return pengeluaran;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
