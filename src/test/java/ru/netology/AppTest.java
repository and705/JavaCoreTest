package ru.netology;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static ru.netology.Main.parseCSV;
import static ru.netology.Main.parseXML;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class AppTest {
//    @BeforeAll
//    public void beforeAll(){
//        System.out.println("Start Testing");
//    }
//    @BeforeEach
//    public void beforeEach(){
//        System.out.println("Next Test");
//    }
//    @AfterEach
//    public void afterEach(){
//        System.out.println("Test End");
//    }
//    @AfterAll
//    public void afterAll(){
//        System.out.println("End Testing");
//    }




    @Test
    public void writeStringOk() {
        System.out.println("writeStringOk");
        String testJson = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";
        String testFileName = "test.json";


        Assertions.assertDoesNotThrow(() -> Main.writeString(testJson, testFileName));
    }

    @Test
    public void parseCsvTestNotNull(){
        System.out.println("parseCsvTest");
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> list = Main.parseCSV(columnMapping, fileName);

        Assertions.assertNotNull(list);

    }

    @Test
    public void parseXmlTest(){
        System.out.println("parseXmlTest");
        List<Employee> listFromXML = parseXML("data.xml");

        Assertions.assertEquals(2, listFromXML.size());

    }

//    @Test
//    public void fileCreated() {
//        System.out.println("fileCreated");
//        File test = new File("test.json");
//        Assertions.assertTrue(test.exists());
//        test.delete();
//    }

    //    @Test
//    public void ageNotChange() {
//        System.out.println("AgeNotChange");
//        long id = 2;
//        String firstName = "Inav";
//        String lastName = "Petrov";
//        String country = "RU";
//        int age = 23;
//        Employee employee = new Employee(id, firstName, lastName, country, age);
//
//        Assertions.assertEquals(age, employee.age);
//
//
//    }

}
