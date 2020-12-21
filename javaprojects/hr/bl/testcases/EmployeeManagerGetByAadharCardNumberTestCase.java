import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
class EmployeeManagerGetByAadharCardNumberTestCase
{
public static void main(String gg[])
{
String aadharCardNumber=gg[0];
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
EmployeeInterface employee;
employee=employeeManager.getEmployeeByAadharCardNumber(aadharCardNumber);
System.out.println("EmployeeId :"+employee.getEmployeeId());
System.out.println("Name:"+employee.getName());
System.out.println("Designation Code:"+employee.getDesignation().getCode());
System.out.println("DateOfBirth:"+employee.getDateOfBirth());
System.out.println("Gender :"+employee.getGender());
System.out.println("Is Indian: "+employee.getIsIndian());
System.out.println("Basic Salary: "+employee.getBasicSalary().toPlainString());
System.out.println("PAN Number: "+employee.getPANNumber());
System.out.println("Aadhar Card Number: "+employee.getAadharCardNumber());
}
catch(BLException blException)
{
List<String> properties=blException.getProperties();
properties.forEach((property)->{
System.out.println(blException.getException(property));
});
}

}
}
