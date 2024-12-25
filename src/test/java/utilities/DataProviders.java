package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//Data Provider 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx";  // taking xl file from testdata
		ExcelUtility xlutil= new ExcelUtility(path);  // creating object for ExcelUtility file
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1", 1);
		
		String logindata[][]=new String[totalrows][totalcols]; // created for 2 dimensional array to store 
		
		for(int i=1;i<=totalrows;i++) // reading two dimensional array values i row 
		{
			for(int j=0;j<totalcols;j++) // j column
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j); // 1 0
			}
		}
		return logindata; // returning two dimensional array
				
	}

}
