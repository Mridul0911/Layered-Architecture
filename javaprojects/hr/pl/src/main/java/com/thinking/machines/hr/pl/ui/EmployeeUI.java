package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
public class EmployeeUI extends JFrame
{
private JLabel titleLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JLabel searchErrorLabel;
private JButton clearSearchTextFieldButton;
private JTable employeeTable;
private EmployeeModel employeeModel;
private JScrollPane scrollPane;
private Container container;
public EmployeeUI()
{
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{
employeeModel=new EmployeeModel();
titleLabel=new JLabel("Employee");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
searchErrorLabel=new JLabel("not found");
clearSearchTextFieldButton=new JButton();
employeeTable=new JTable(employeeModel);
scrollPane=new JScrollPane(employeeTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();

}
private void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
employeeTable.setFont(dataFont);
	

container.setLayout(null);
int lm,tm;
lm=0;tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchErrorLabel.setBounds(lm+10+100+400+10-75,tm+10+20+10,100,20);
searchLabel.setBounds(lm+10,tm+10+40+10,100,30);
searchTextField.setBounds(lm+10+100+5,tm+10+40+10,400,30);
clearSearchTextFieldButton.setBounds(lm+10+100+400+10,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,565,300);
employeeTable.setRowHeight(35);

container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);


int w,h;
w=600;
h=680;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));


}
private void addListeners()
{

}
}