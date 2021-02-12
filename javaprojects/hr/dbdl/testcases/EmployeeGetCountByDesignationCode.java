import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
import java.text.*;
class EmployeeGetCountByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
System.out.println("number of Employee with designation code"+designationCode+"is: "+new EmployeeDAO().getCountByDesignation(designationCode));
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());

}


}
}

