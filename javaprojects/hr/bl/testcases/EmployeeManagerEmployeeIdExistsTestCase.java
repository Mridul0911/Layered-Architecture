import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
class EmployeeManagerEmployeeIdExistsTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
System.out.printf(employeeId+" exist :"+employeeManager.employeeIdExists(employeeId));
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
