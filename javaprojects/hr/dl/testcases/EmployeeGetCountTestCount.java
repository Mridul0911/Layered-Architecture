import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
import java.text.*;
class EmployeeGetCountTestCase
{
public static void main(String gg[])
{
try
{
System.out.println("number of Employee "+new EmployeeDAO().getCount());
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());

}


}
}

