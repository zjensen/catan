package shared.utils;

public class TestClass3 {

	private String testName;
	private int testNum;
	
	
	
	public TestClass3()
	{
		this.testName = "Bob";
		this.testNum = 42;
	}
	
	public int function3()
	{
		System.out.println("function3 from TestClass3");
		return testNum;
	}
	
	public String function4()
	{
		System.out.println("function4 from TestClass3");
		
		return testName;
	}

	public void function5(String testNameIn, int testNumIn)
	{
		System.out.println("function5 from TestClass3");
		
		testName = testNameIn;
		testNum = testNumIn;
		
		System.out.println("New name=" + testName);
		System.out.println("New num =" + testNum);
	}
	
	public String function6(String testNameIn)
	{
		System.out.println("function6 from TestClass3");	
		testName = testNameIn;		
		System.out.println("New name=" + testName);
		return testName;
	}
	
	
	
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public int getTestNum() {
		return testNum;
	}

	public void setTestNum(int testNum) {
		this.testNum = testNum;
	}
	
	
	
	
	
	
		
}
