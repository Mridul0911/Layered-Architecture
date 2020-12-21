import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{
String employeeId="A10000001";
String name="Mukesh gupta";
DesignationInterface designation=new Designation();
designation.setCode(3);
Date dateOfBirth=new Date();
boolean isIndian=false;
BigDecimal basicSalary=new BigDecimal("200000");
String panNumber="A1245";
String aadharCardNumber="U12345";
EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);	
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.printf("employee updated");
}
catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getMessage());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}
