import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.sql.*;
import db.db;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

//import com.njit.personselect;

import java.sql.*;
import db.db;
import java.util.ArrayList;

public class CheckInRegister extends JFrame{
	private String EmplyeeNo;
	private JTable table;
	
	private JButton b1;
	private JButton b2;
	private JToolBar tool;
		
	public CheckInRegister(String Eno) 
	{
		
		EmplyeeNo=Eno;
		this.setSize(900, 400);
		this.setTitle("Ա����Ϣ");
		this.setLocationRelativeTo(getOwner());

		//Ԥ����ס��ť
		b1 = new JButton("Ԥ����ס");
		b1.setToolTipText("Ԥ����ס");
		b1.setFocusable(false);
		b1.setHorizontalTextPosition(SwingConstants.CENTER);
		b1.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		//ֱ����ס��ť
		b2 = new JButton("ֱ����ס");
		b2.setToolTipText("ֱ����ס");
		b2.setFocusable(false);
		b2.setHorizontalTextPosition(SwingConstants.CENTER);
		b2.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		tool = new JToolBar();
		tool.add(b1);
		tool.add(b2);
		tool.setRollover(true);
		getContentPane().add(tool, BorderLayout.NORTH);
		
		
		try 
		{		
			String[] columnNames = {"��������","ʣ������","���ռ۸�"};//��������
				
			db dbcon=new db();//�������ݿ�Ϊ�ˣ���ȡ��ͼ�е�ֵ������table
			ResultSet rs = dbcon.executeQuery("select * from view_RoomState");	
			int r=rs.getRow();
			String[][] data;
			data=new String[8][3];
			int i=0;
			
			while(rs.next()) 
			{
				String str[]=new String[3];
				str[0]=rs.getString("��������");
				str[1]=rs.getString("ʣ������");
				str[2]=rs.getString("���ռ۸�");

				data[i][0]=str[0];
				data[i][1]=str[1];
				data[i][2]=str[2];
				
				i++;						
			}

			//Ϊtable��ֵ������������
			JTable table = new JTable(data, columnNames);
			table.setPreferredScrollableViewportSize(new Dimension(800, 400));//����table��С
			JScrollPane scroll = new JScrollPane(table);
			getContentPane().add(scroll, BorderLayout.CENTER);
			
			rs.close();

		} 
		catch (SQLException sqle)
		{
			System.out.println(sqle.toString());
		}

		b1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Reservation r=new Reservation(EmplyeeNo);
				r.setVisible(true);
				//r.setSize(600, 400);
			}
			
		});
		
		b2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				CheckIn c=new CheckIn(EmplyeeNo);
				c.setVisible(true);
				//c.setSize(500,300);
			}
			
		});
	}
	
	public static void main(String[] args) {	
	    try
	    {	    	
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
		CheckInRegister c=new CheckInRegister("21001");
		c.setVisible(true);
	}
	
}

