package com.safetynet.alerts.model.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Person
{
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email;

}
