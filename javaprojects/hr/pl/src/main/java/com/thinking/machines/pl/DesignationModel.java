package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import javax.swing.table.*;
public class DesignationModel extends AbstractTableModel
{
private List<DesignationInterface> designations;
private DesignationManagerInterface designationManager;
private String[] columnTitle;
public DesignationModel()
{
this.populateDataStructures();
}
private void populateDataStructures()
{
this.columnTitle=new String[2];
this.columnTitle[0]="S.No.";
this.columnTitle[1]="Designation";
try
{
designationManager=DesignationManager.getDesignationManager();
}
catch(BLException blException)
{
// what to do ???????????
}
Set<DesignationInterface> blDesignations=designationManager.getDesignations();
this.designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}

});
}
public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return this.columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return this.designations.get(rowIndex).getTitle();
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class; //class.forName(java.lang.Integer)
return String.class;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
}

