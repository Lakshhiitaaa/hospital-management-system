package com.cognizant.entities.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {
    @Id
    @Column(name = "record_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long recordId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "visit_date")
    private String visitDate;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "prescription")
    private String prescription;

    @Column(name = "notes")
    private String notes;

    public MedicalRecord() {}

    public MedicalRecord(Long recordId, Long patientId, Long doctorId, String visitDate, String diagnosis, String prescription, String notes) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
    }

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getVisitDate() { return visitDate; }
    public void setVisitDate(String visitDate) { this.visitDate = visitDate; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}