import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.UUID;

// DatabaseConnection Class
class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_management";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "Nithin@328"; // Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// Patient Class
class Patient {
    private String id;
    private String name;
    private int age;
    private String ailment;

    public Patient(String id, String name, int age, String ailment) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.ailment = ailment;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAilment() {
        return ailment;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name + ", Age: " + age + ", Ailment: " + ailment;
    }
}

// Doctor Class
class Doctor {
    private String id;
    private String name;
    private String specialization;

    public Doctor(String id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return "Doctor ID: " + id + ", Name: " + name + ", Specialization: " + specialization;
    }
}

// Appointment Class
class Appointment {
    private String id;
    private Patient patient;
    private Doctor doctor;
    private String date;

    public Appointment(String id, Patient patient, Doctor doctor, String date) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + id + ", Patient: " + patient + ", Doctor: " + doctor + ", Date: " + date;
    }
}

// HospitalManagementSystem Class
 class HospitalManagementSystem {
    private Connection connection;

    public HospitalManagementSystem() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HospitalManagementSystem system = new HospitalManagementSystem();
        SwingUtilities.invokeLater(() -> system.createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));

        JButton addPatientButton = new JButton("Add Patient");
        JButton addDoctorButton = new JButton("Add Doctor");
        JButton scheduleAppointmentButton = new JButton("Schedule Appointment");
        JButton listPatientsButton = new JButton("List Patients");
        JButton listDoctorsButton = new JButton("List Doctors");
        JButton listAppointmentsButton = new JButton("List Appointments");
        JButton deletePatientButton = new JButton("Delete Patient");
        JButton deleteDoctorButton = new JButton("Delete Doctor");
        JButton deleteAppointmentButton = new JButton("Delete Appointment");

        panel.add(addPatientButton);
        panel.add(addDoctorButton);
        panel.add(scheduleAppointmentButton);
        panel.add(listPatientsButton);
        panel.add(listDoctorsButton);
        panel.add(listAppointmentsButton);
        panel.add(deletePatientButton);
        panel.add(deleteDoctorButton);
        panel.add(deleteAppointmentButton);

        frame.add(panel, BorderLayout.CENTER);

        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });

        addDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctor();
            }
        });

        scheduleAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduleAppointment();
            }
        });

        listPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPatients();
            }
        });

        listDoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listDoctors();
            }
        });

        listAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAppointments();
            }
        });

        deletePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient();
            }
        });

        deleteDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDoctor();
            }
        });

        deleteAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAppointment();
            }
        });

        frame.setVisible(true);
    }

    private void addPatient() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField ailmentField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Age:", ageField,
            "Ailment:", ailmentField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Patient", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = UUID.randomUUID().toString(); // Generate unique ID
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String ailment = ailmentField.getText();

            try {
                String query = "INSERT INTO patients (id, name, age, ailment) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, id);
                statement.setString(2, name);
                statement.setInt(3, age);
                statement.setString(4, ailment);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Patient added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding patient.");
            }
        }
    }

    private void addDoctor() {
        JTextField nameField = new JTextField();
        JTextField specializationField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Specialization:", specializationField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Doctor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = UUID.randomUUID().toString(); // Generate unique ID
            String name = nameField.getText();
            String specialization = specializationField.getText();

            try {
                String query = "INSERT INTO doctors (id, name, specialization) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, id);
                statement.setString(2, name);
                statement.setString(3, specialization);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Doctor added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding doctor.");
            }
        }
    }

    private void scheduleAppointment() {
        JTextField patientIdField = new JTextField();
        JTextField doctorIdField = new JTextField();
        JTextField dateField = new JTextField();

        Object[] message = {
            "Patient ID:", patientIdField,
            "Doctor ID:", doctorIdField,
            "Date:", dateField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Schedule Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = generateAppointmentId();
            String patientId = patientIdField.getText();
            String doctorId = doctorIdField.getText();
            String date = dateField.getText();

            try {
                String query = "INSERT INTO appointments (id, patient_id, doctor_id, date) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, id);
                statement.setString(2, patientId);
                statement.setString(3, doctorId);
                statement.setString(4, date);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Appointment scheduled successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error scheduling appointment.");
            }
        }
    }

    private String generateAppointmentId() {
        return UUID.randomUUID().toString();
    }

    private void listPatients() {
        StringBuilder sb = new StringBuilder();
        try {
            String query = "SELECT * FROM patients";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                sb.append("No patients found.");
            } else {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String ailment = resultSet.getString("ailment");
                    sb.append("Patient ID: ").append(id).append(", Name: ").append(name)
                            .append(", Age: ").append(age).append(", Ailment: ").append(ailment)
                            .append("\n");
                }
            }
            showMessageDialog("List of Patients", sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching patients.");
        }
    }

    private void listDoctors() {
        StringBuilder sb = new StringBuilder();
        try {
            String query = "SELECT * FROM doctors";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                sb.append("No doctors found.");
            } else {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String specialization = resultSet.getString("specialization");
                    sb.append("Doctor ID: ").append(id).append(", Name: ").append(name)
                            .append(", Specialization: ").append(specialization)
                            .append("\n");
                }
            }
            showMessageDialog("List of Doctors", sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching doctors.");
        }
    }

    private void listAppointments() {
        StringBuilder sb = new StringBuilder();
        try {
            String query = "SELECT * FROM appointments " +
                           "JOIN patients ON appointments.patient_id = patients.id " +
                           "JOIN doctors ON appointments.doctor_id = doctors.id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                sb.append("No appointments found.");
            } else {
                while (resultSet.next()) {
                    String id = resultSet.getString("appointments.id");
                    String patientName = resultSet.getString("patients.name");
                    String doctorName = resultSet.getString("doctors.name");
                    String date = resultSet.getString("appointments.date");
                    sb.append("Appointment ID: ").append(id).append(", Patient: ").append(patientName)
                            .append(", Doctor: ").append(doctorName).append(", Date: ").append(date)
                            .append("\n");
                }
            }
            showMessageDialog("List of Appointments", sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching appointments.");
        }
    }

    private void deletePatient() {
        String patientId = JOptionPane.showInputDialog("Enter Patient ID to delete:");
        if (patientId != null && !patientId.isEmpty()) {
            try {
                String query = "DELETE FROM patients WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, patientId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Patient deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "No such patient found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting patient.");
            }
        }
    }

    private void deleteDoctor() {
        String doctorId = JOptionPane.showInputDialog("Enter Doctor ID to delete:");
        if (doctorId != null && !doctorId.isEmpty()) {
            try {
                String query = "DELETE FROM doctors WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, doctorId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Doctor deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "No such doctor found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting doctor.");
            }
        }
    }

    private void deleteAppointment() {
        String appointmentId = JOptionPane.showInputDialog("Enter Appointment ID to delete:");
        if (appointmentId != null && !appointmentId.isEmpty()) {
            try {
                String query = "DELETE FROM appointments WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, appointmentId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Appointment deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "No such appointment found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting appointment.");
            }
        }
    }

    private void showMessageDialog(String title, String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.PLAIN_MESSAGE);
    }
}
