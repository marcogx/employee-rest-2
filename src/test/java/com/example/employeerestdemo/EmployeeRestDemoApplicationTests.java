package com.example.employeerestdemo;

import com.example.employeerestdemo.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRestDemoApplicationTests {

    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup().build();

//    @InjectMocks
//    private EmployeeRestController employeeRestController;

//    @Before
//    public void init(){
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(employeeRestController)
//                .addFilters()
//                .build();
//    }

    @Test
    public void test_get_by_id_success() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Zitao");
        employee.setLastName("Huang");
        employee.setAge(80);
        mockMvc.perform(get("localhost:8080/employee/{id}", 1))
                .andExpect(status().isOk());

    }

//    @Test
//    public void test_put_by_id_success() throws Exception {
//        Employee employee = new Employee();
//        employee.setFirstName("Zitao");
//        employee.setLastName("Huang");
//        employee.setAge(80);
//        mockMvc.perform(get("localhost:8080/employee/{id}", 1))
//                .andExpect(status().isOk());
//
//    }

}
