import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
class EmployeeManagerIsDesignationAllotedTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]); 
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
System.out.printf("Designation with designation code "+designationCode+" exist :"+employeeManager.designationAlloted(designationCode));
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
