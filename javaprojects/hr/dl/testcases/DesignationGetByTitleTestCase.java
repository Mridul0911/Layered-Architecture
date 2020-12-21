import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
class GetByTitleTestCase
{
public static void main(String gg[])
{
String title=gg[0].trim();
try
{
DesignationDTOInterface designationDTO;
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDTO=designationDAO.getByTitle(title);
System.out.printf("code %d ,title %s\n",designationDTO.getCode(),designationDTO.getTitle());
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());

}


}
}

