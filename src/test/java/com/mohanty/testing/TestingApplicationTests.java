package com.mohanty.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohanty.testing.model.Employee;
import com.mohanty.testing.model.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestingApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void addEmployeeTest() throws Exception {
        Employee employee = new Employee("1", "Ashutosh", "Tech");
        String jsonRequest = objectMapper.writeValueAsString(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.POST, "/test/v1")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andReturn();
        String resultContent = result
                .getResponse()
                .getContentAsString();
        Response response = objectMapper.readValue(resultContent, Response.class);
        Assert.assertTrue(response.isStatus() == Boolean.TRUE);
    }

    @Test
    public void getEmployeeTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.GET, "/test/v1")
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        MvcResult mvcResult = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andReturn();
        String resultContent = mvcResult
                .getResponse()
                .getContentAsString();
        List<Employee> employeeList = Arrays.asList(objectMapper.readValue(resultContent, Employee[].class));
        Assert.assertTrue(employeeList.size() > 0);
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setName("Ashutosh");
        employee.setDept("HR");
        String jsonRequest = objectMapper.writeValueAsString(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(HttpMethod.PUT, "/test/v1")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        MvcResult mvcResult = mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andReturn();
        Response response = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), Response.class);
        Assert.assertTrue(response.isStatus()==Boolean.TRUE);
    }
}
