package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.enums.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private static EmployeeManager  employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructure(); 
}
private void populateDataStructure() throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
if(dlEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}
else
{
employee.setGender(GENDER.FEMALE);
}
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
designationCodeWiseEmployeesMap.put(new Integer(designation.getCode()),ets);
}
else
{
ets.add(employee);
}

}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId!=null)
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","EmployeeId should be nil/empty");
}
}
if(name==null)
{
blException.addException("name","Name is required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name is required");
}
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid Designation");
}
}
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","DateOfBirth required");
}
if(gender==' ')
{
blException.addException("gender","Gender required");
}
if(basicSalary==null)
{
blException.addException("basicSalary","BasicSalary required");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","BasicSalary can not be nagative");
}
}
if(panNumber==null)
{
blException.addException("panNumber","PANNumber required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PANNumber required");
}
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","AadharCardNumber required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","AadharCardNumber required");
}
}
if(panNumber!=null && panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))
{
blException.addException("panNumber","PANNumber "+panNumber+" exists");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase()))
{
blException.addException("aadharCardNumber","AadharCardNumber "+aadharCardNumber+" exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee;
dlEmployee=new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
employeesSet.add(dsEmployee);
employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}


}
catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId==null)
{
blException.addException("employeeId","EmployeeId required");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blException.addException("employeeId","EmployeeId required");
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid employeeId"+employeeId);
throw blException;
}
}
}
if(name==null)
{
blException.addException("name","Name is required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name is required");
}
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid Designation");
}
}
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","DateOfBirth required");
}
if(gender==' ')
{
blException.addException("gender","Gender required");
}
if(basicSalary==null)
{
blException.addException("basicSalary","BasicSalary required");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","BasicSalary can not be nagative");
}
}
if(panNumber==null)
{
blException.addException("panNumber","PANNumber required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PANNumber required");
}
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","AadharCardNumber required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","AadharCardNumber required");
}
}
if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId))
{
blException.addException("panNumber","PANNumber"+panNumber+"exists");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface ee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId))
{
blException.addException("aadharCardNumber","AadharCardNumber"+aadharCardNumber+"exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
int oldDesignationCode=dsEmployee.getDesignation().getCode();

EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee;
dlEmployee=new EmployeeDTO();
dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.update(dlEmployee);


dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);

employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());


employeesSet.add(dsEmployee);
employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
if(oldDesignationCode!=dsEmployee.getDesignation().getCode())
{
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(dsEmployee);

ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}
}
catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeEmployee(String employeeId) throws BLException
{
if(employeeId==null)
{
BLException blException=new BLException();
blException.addException("employeeId","EmployeeId required");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
BLException blException=new BLException();
blException.addException("employeeId","EmployeeId required");
throw blException;
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
BLException blException=new BLException();
blException.addException("employeeId","Invalid employeeId"+employeeId);
throw blException;
}
}
}
try
{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(dsEmployee.getEmployeeId());
employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeesMap.remove(dsEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
}
catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
EmployeeInterface employee;
employee=this.employeeIdWiseEmployeesMap.get(employeeId.trim().toUpperCase());
if(employee==null)
{
BLException blException;
blException=new BLException();
blException.addException("employeeId","Invalid employeeId"+employeeId);
throw blException;
}
EmployeeInterface e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
DesignationInterface designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
e.setDesignation(designation);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
EmployeeInterface employee;
employee=this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(employee==null)
{
BLException blException;
blException=new BLException();
blException.addException("panNumber","Invalid PAN Number"+panNumber);
throw blException;
}
EmployeeInterface e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
DesignationInterface designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
e.setDesignation(designation);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
EmployeeInterface employee;
employee=this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(employee==null)
{
BLException blException;
blException=new BLException();
blException.addException("aadharCardNumber","Invalid Aadhar Card Number"+aadharCardNumber);
throw blException;
}
EmployeeInterface e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
DesignationInterface designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
e.setDesignation(designation);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public int getEmployeeCount()
{
return employeesSet.size(); 
}
public boolean employeeIdExists(String employeeId)
{
return employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}
public boolean employeePANNumberExists(String panNumber)
{
return panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
return aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees=new TreeSet<>();
EmployeeInterface e;
DesignationInterface designation;
for(EmployeeInterface employee:employeesSet)
{
e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
e.setDesignation(designation);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
employees.add(e);
}
return employees;
}
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(!(designationManager.designationCodeExists(designationCode)))
{
BLException blException=new BLException();
blException.setGenericException("Invalid Designation code"+designationCode);
throw blException;
}
Set<EmployeeInterface> employees=new TreeSet<>();
Set<EmployeeInterface> ets;
ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
return employees;
}
EmployeeInterface e;
DesignationInterface designation;
for(EmployeeInterface employee:ets)
{
e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
e.setDesignation(designation);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
employees.add(e);
}
return employees;
}
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null) return 0;
return ets.size();
}
public boolean designationAlloted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}
}