package com.cognizant.entities.entity;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "billing")
@Data
@NoArgsConstructor
public class Billing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long billId;

    private Long appointmentId;
    private String patientName;
    private String patientContact;
    private String patientAddress;
    private String doctorName;
    private LocalDate appointmentDate;
    private String paymentStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "billing_items", joinColumns =  @JoinColumn(name = "bill_id"))
    private List<BillingItem> services;

    private double totalAmount;

    public Billing(Long appointmentId, String patientName, String patientContact,String doctorName, String patientGender, LocalDate appointmentDate, List<BillingItem> services, String paymentStatus){
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.patientContact = patientContact;
        this.doctorName = doctorName;
        this.patientAddress = patientGender;
        this.appointmentDate = appointmentDate;
        this.services = services;
        this.paymentStatus = paymentStatus;
        this.totalAmount = services.stream()
        .mapToDouble(BillingItem::getPrice)
        .sum();
    }

	/**
	 * @return the billId
	 */
	public Long getBillId() {
		return billId;
	}

	/**
	 * @param billId the billId to set
	 */
	public void setBillId(Long billId) {
		this.billId = billId;
	}

	/**
	 * @return the appointmentId
	 */
	public Long getAppointmentId() {
		return appointmentId;
	}

	/**
	 * @param appointmentId the appointmentId to set
	 */
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the patientContact
	 */
	public String getPatientContact() {
		return patientContact;
	}

	/**
	 * @param patientContact the patientContact to set
	 */
	public void setPatientContact(String patientContact) {
		this.patientContact = patientContact;
	}

	/**
	 * @return the patientAddress
	 */
	public String getPatientAddress() {
		return patientAddress;
	}

	/**
	 * @param patientAddress the patientAddress to set
	 */
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	/**
	 * @return the appointmentDate
	 */
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	/**
	 * @param appointmentDate the appointmentDate to set
	 */
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the services
	 */
	public List<BillingItem> getServices() {
		return services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(List<BillingItem> services) {
		this.services = services;
	}

	/**
	 * @return the totalAmount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
    


}