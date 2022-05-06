package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        //ЗАДАЧА 1 CSV в JSON
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json, "data.json");

        //ЗАДАЧА 2 XML в JSON
        List<Employee> listFromXML = parseXML("data.xml");
        String json2 = listToJson(listFromXML);
        writeString(json2, "data.json2");
    }


    public static void writeString(String json, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    //ЗАДАЧА 1
    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> parseCSV = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {

            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            parseCSV = csv.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseCSV;
    }

    //ЗАДАЧА 2
    private static List<Employee> parseXML(String s) {
        List<Employee> employees = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(s));

            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    NodeList elementList = element.getChildNodes();

                    Employee employee = new Employee();
                    for (int k = 0; k < elementList.getLength(); k++) {
                        Node nodeTest = elementList.item(k);
                        String name = nodeTest.getNodeName();
                        String value = nodeTest.getTextContent();

                        if (name.equals("id")) {
                            employee.id = Long.parseLong(value);
                        }
                        if (name.equals("firstName")) {
                            employee.firstName = value;
                        }
                        if (name.equals("lasttName")) {
                            employee.lastName = value;
                        }
                        if (name.equals("country")) {
                            employee.country = value;
                        }
                        if (name.equals("age")) {
                            employee.age = Integer.parseInt(value);
                        }
                    }
                    employees.add(employee);
                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return employees;
    }
}