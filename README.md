# TQS_97880

# Create project:
mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

# To test (and get report):
mvn test jacoco:report

# To see report:
firefox target/site/jacoco/index.html     



On Labs:
2.
    2) SuT -> The Http Client 




mvn archetype:generate -DgroupId=tqs.lab2.integration -DartifactId=integration -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false



# Notes on Spring tests

Test Controller:
    @WebMvcTest(<classofController>.class)
    class blablabla{
        @Autowired
        private MockMvc mvc;

        @MockBean
        private classofService service;
    }

Test Service:

    @ExtendWith(MockitoExtension.class)
    class blabalabla{
        @Mock
        private <repository> repo;

        @InjectMocks
        private <service> serv;

        @BeforeEach
        pub void setup(){
            #Fake repository behavious
        }
    }

Test Repository:

    @DataJpaTest
    class yes{
        @Autowired
        private TestEntityManager entityManager;
    
        @Autowired
        private EmployeeRepository employeeRepository;

    }