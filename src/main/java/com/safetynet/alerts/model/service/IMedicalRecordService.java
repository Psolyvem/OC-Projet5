package com.safetynet.alerts.model.service;

import com.safetynet.alerts.model.bean.MedicalRecord;

import java.util.ArrayList;

public interface IMedicalRecordService
{
	public ArrayList<MedicalRecord> getMedicalRecords();
	public MedicalRecord getMedicalRecordByName(String firstName, String lastName);
	public void createMedicalRecord(MedicalRecord medicalRecord);
	public void modifyMedicalRecord(MedicalRecord medicalRecord);
	public void deleteMedicalRecord(MedicalRecord medicalRecord);
}
