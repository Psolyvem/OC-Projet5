package com.safetynet.alerts.model.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Firestation
{
	private String address;
	private String station;
}
