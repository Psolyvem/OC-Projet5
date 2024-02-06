package com.safetynet.alerts.model.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class JsonReaderTest
{
	@Test
	void readDataTest()
	{
		assertThat(JsonReader.getInstance().readData()).isNotNull();

	}
	@Test
	void writeDataTest()
	{
		assertDoesNotThrow(() ->JsonReader.getInstance().writeData());
	}

	@Test
	void jsonDateToJavaDateTest()
	{
		String jsonDate = "01/01/1982";
		LocalDate javaDate = LocalDate.of(1982,1,1);

		String jsonDate2 = "11/21/1982";
		LocalDate javaDate2 = LocalDate.of(1982,11,21);

		assertThat(javaDate).isEqualTo(JsonReader.getInstance().jsonDateToJavaDate(jsonDate));
		assertThat(javaDate2).isEqualTo(JsonReader.getInstance().jsonDateToJavaDate(jsonDate2));

	}

	@Test
	void javaDateToJsonDateTest()
	{
		String jsonDate = "01/01/1982";
		LocalDate javaDate = LocalDate.of(1982,1,1);

		String jsonDate2 = "11/21/1982";
		LocalDate javaDate2 = LocalDate.of(1982,11,21);

		assertThat(jsonDate).isEqualTo(JsonReader.getInstance().javaDateToJsonDate(javaDate));
		assertThat(jsonDate2).isEqualTo(JsonReader.getInstance().javaDateToJsonDate(javaDate2));

	}
}
