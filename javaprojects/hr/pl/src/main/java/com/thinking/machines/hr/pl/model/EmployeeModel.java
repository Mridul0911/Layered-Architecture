package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.io.image.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;
import javax.swing.table.*;
public class EmployeeModel extends AbstractTableModel
{
private java.util.List<EmployeeInterface> employees;
private EmployeeManagerInterface employeeManager;
private String[] columnTitle;
public EmployeeModel()
{
this.populateDataStructure();

}
private void populateDataStructure()
{
this.columnTitle=new String[3];
columnTitle[0]="S.No.";
columnTitle[1]="Employee-ID";
columnTitle[2]="Name";
try
{
employeeManager = EmployeeManager.getEmployeeManager();
}catch(BLException blException)
{

}
System.out.println("hsahwa");
Set<EmployeeInterface> blEmployee=employeeManager.getEmployees();
System.out.println("hsahwa");
this.employees=new LinkedList<>();
for(EmployeeInterface employee:blEmployee)
{
this.employees.add(employee);
Collections.sort(this.employees,new Comparator<EmployeeInterface>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
}
});
}
}
public int getRowCount()
{

return this.employees.size();
}
public int getColumnCount()
{
return this.columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return this.columnTitle[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0)return rowIndex+1;
else if(columnIndex==1)return this.employees.get(rowIndex).getEmployeeId();
else return this.employees.get(rowIndex).getName();
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0)return Integer.class;
return String.class;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}

//Application specific methods

public void add(EmployeeInterface employee)throws BLException
{
employeeManager.addEmployee(employee);
this.employees.add(employee);
Collections.sort(this.employees,new Comparator<EmployeeInterface>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
}
});
fireTableDataChanged();//to update table;
}

public int indexOfEmployee(EmployeeInterface employee)throws BLException
{
Iterator<EmployeeInterface> iterator=this.employees.iterator();
EmployeeInterface e;
int index=0;
while(iterator.hasNext())
{
e=iterator.next();
if(e.equals(employee))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid employee : "+employee.getName());
throw blException;
}
public int indexOfName(String name,boolean partialLeftSearch)throws BLException
{
Iterator<EmployeeInterface> iterator=this.employees.iterator();
EmployeeInterface e;
int index=0;
while(iterator.hasNext())
{
e=iterator.next();
if(partialLeftSearch)
{
if(e.getName().toUpperCase().startsWith(name.toUpperCase()))return index;
}
else
{
if(e.getName().equalsIgnoreCase(name))return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Inavlid Name :"+name);
throw blException;
}
public void update(EmployeeInterface employee)throws BLException
{
employeeManager.updateEmployee(employee);
this.employees.remove(indexOfEmployee(employee));
this.employees.add(employee);
Collections.sort(this.employees,new Comparator<EmployeeInterface>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
}
});
fireTableDataChanged();//to update table;

}

public void removeEmployee(String employeeId)throws BLException
{
employeeManager.removeEmployee(employeeId);
Iterator<EmployeeInterface> iterator=employees.iterator();
int index=0;
while(iterator.hasNext())
{
if(iterator.next().getEmployeeId()==employeeId)break;
index++;
}
if(index==this.employees.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid Employee Id : "+employeeId);
throw blException;
}
this.employees.remove(index);
fireTableDataChanged();
}
public EmployeeInterface getEmployeeAt(int index)throws BLException
{
if(index<0 || index>=this.employees.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index : "+index);
throw blException;
}
return this.employees.get(index);
}

public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document doc=new Document(pdfDocument);
Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo_icon.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);
Paragraph companyNamePara=new Paragraph();
companyNamePara.add("HRS Corporation");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);
Paragraph reportTitlePara=new Paragraph("List of designation");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);
PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
Paragraph columnTitle1=new Paragraph("S.No.");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(14);
Paragraph columnTitle2=new Paragraph("Designations");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(14);
Paragraph pageNumberParagraph;
PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
Paragraph dataParagraph;
float topTableColumnWidths[]={1,5};
float dataTableColumnWidths[]={1,5};
int sno,x,pageSize;
pageSize=5;
boolean newPage=true;
Table pageNumberTable;
Table topTable;
Table dataTable=null;
Cell cell;
int numberOfPages=this.employees.size()/pageSize;
if((this.employees.size()%pageSize)!=0) numberOfPages++;

EmployeeInterface employee;
int pageNumber=0;
sno=0;
x=0;
while(x<this.employees.size())
{
if(newPage==true)
{
//creating new page header
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidths)); 
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);  
topTable.addCell(cell);
doc.add(topTable);
pageNumberParagraph=new Paragraph("Page : "+pageNumber+"/"+numberOfPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(13);
pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
doc.add(pageNumberTable);
dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage=false;
}
employee=this.employees.get(x);
//adding row to tabel
sno++;
cell=new Cell();
dataParagraph=new Paragraph(String.valueOf(sno));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);

cell=new Cell();
dataParagraph=new Paragraph(employee.getName());
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
dataTable.addCell(cell);
x++;
if(sno%pageSize==0 || x==this.employees.size())
{
//creating footer
doc.add(dataTable);
doc.add(new Paragraph("Software by : Harsh Sharma"));
if(x<this.employees.size())
{
// add new page to document
doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}//loop end
doc.close(); 
}catch(Exception exception)
{
BLException blException=new BLException();
blException.setGenericException(exception.getMessage());
throw blException; 
}
}//funtion ends 



}
