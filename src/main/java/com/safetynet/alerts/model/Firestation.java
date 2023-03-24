package com.safetynet.alerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Firestation
{
	private String address;
	private int station;
}
