
a) Identify a couple of examples on the use of AssertJ expressive methods chaining.
```
        assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```

b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

```

    // mocking the responses of the repository (i.e., no database will be used)
    // lenient is required because we load more expectations in the setup
    // than those used in some tests. As an alternative, the expectations
    // could move into each test method and be trimmed (no need for lenient then)
    @Mock( lenient = true)
    private EmployeeRepository employeeRepository;
```

c) What is the difference between standard @Mock and @MockBean?
MockBean is used when you need to test something that needs a mock in a Spring Boot context, as in, you need to test functionalities with Spring dependencies. They're much more resource intensive, so @Mock should be preferred when possible.

d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?
When properties need to be overriden (changed) for integration tests to run.

e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

C uses @WebMvcTest whereas D/E use @SpringBootTest, which doesn't slice the test context and is heavier/slower. C loads only the Mvc part of 

C and D use a mock server, where E connects to a real API/server.

C tests specific components (controller), D tests full "inside" functionality (no connection to outside services) and E tests the full functionality of the classes.
