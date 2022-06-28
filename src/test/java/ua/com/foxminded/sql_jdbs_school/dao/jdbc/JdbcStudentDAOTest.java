package ua.com.foxminded.sql_jdbs_school.dao.jdbc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class JdbcStudentDAOTest {
	
    /*
    private static final String TEST_DB_PROP_PATH = "D:/repository/SqlJdbcSchool/"
												  + "src/main/resource/test-db.properties";
	private static final String TEST_TABLES_SCRIPT_PATH = "D:/repository/SqlJdbcSchool/"
														+ "src/main/resource/test-tables.sql";
	private static final String TEST_DATA_PATH = "D:/repository/SqlJdbcSchool/"
											   + "src/main/resource/test-data.sql";
	private static final String DB_URL = "databaseURL";
    private static final String USER_NAME = "databaseUser";
    private static final String USER_PASS = "databaseUserPassword";
    private static final String END_LINE = "\n";
    private static final Integer STUDENT_QUANTITY_HAVING_GROUP_ID = 5;
    
    @InjectMocks
    JdbcStudentDAO universityStudentDao;
    
    @Mock
    DAOConnectionFactory universityConnectionDaoFactory;
	
	@BeforeAll
	static void init() throws SQLException, DAOException, IOException {
		Connection con = null;
		Statement statement = null;
		PreparedStatement prStatement = null;
		
		try {
			FileInputStream testDbInput = new FileInputStream(TEST_DB_PROP_PATH);
			Properties testDbProperties = new Properties();
			testDbProperties.load(testDbInput);
			con = DriverManager.getConnection(testDbProperties.getProperty(DB_URL),
											  testDbProperties.getProperty(USER_NAME),
											  testDbProperties.getProperty(USER_PASS));
			
			Path tablesScriptPath = Paths.get(TEST_TABLES_SCRIPT_PATH);
			String tablesScript = Files.lines(tablesScriptPath)
									   .collect(Collectors.joining(END_LINE));
			statement = con.createStatement();
			statement.execute(tablesScript);
			Path testDataScriptPath = Paths.get(TEST_DATA_PATH);
			String testDataScript = Files.lines(testDataScriptPath)
										 .collect(Collectors.joining(END_LINE));
			prStatement = con.prepareStatement(testDataScript);
			prStatement.execute();
		}  finally {
			con.close();
			statement.close();
			prStatement.close();
		}
	}
	
	@Test
	void readStudentsHavingGroupId_Call_CorrectStudnetQuantity() throws IOException, 
																		SQLException, 
																		DAOException {
		Connection con = null;
		
		try {
			FileInputStream testDbInput = new FileInputStream(TEST_DB_PROP_PATH);
			Properties testDbProperties = new Properties();
			testDbProperties.load(testDbInput);
			con = DriverManager.getConnection(testDbProperties.getProperty(DB_URL),
											  testDbProperties.getProperty(USER_NAME),
											  testDbProperties.getProperty(USER_PASS));
			when(universityConnectionDaoFactory.createConnection()).thenReturn(con);
			assertEquals(STUDENT_QUANTITY_HAVING_GROUP_ID, 
					     universityStudentDao.getStudentsHavingGroupId().size());
		} finally {
			con.close();
		}
	}
	/*
	@Test
	void getById() throws SQLException, IOException, DAOException {
		Connection con = null;
		
		try {
			FileInputStream testDbInput = new FileInputStream(TEST_DB_PROP_PATH);
			Properties testDbProperties = new Properties();
			testDbProperties.load(testDbInput);
			con = DriverManager.getConnection(testDbProperties.getProperty(DB_URL),
											  testDbProperties.getProperty(USER_NAME),
											  testDbProperties.getProperty(USER_PASS));
			when(universityConnectionDaoFactory.createConnection()).thenReturn(con);
			assertEquals(STUDENT_QUANTITY_HAVING_GROUP_ID, 
					     universityStudentDao.().size());
		} finally {
			con.close();
		}
		
	}
	*/
}
