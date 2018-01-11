import javax.swing.*;

import db.db;

import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuestConsum extends JFrame implements ActionListener
{
	private JButton query,bill,cancel;
	private JTextField GuestNumber,GuestName;
	
	public GuestConsum()
	{
		super("结账");
		JPanel cont = new JPanel(new GridLayout(3,1));
		this.setSize(450,200);
		this.setLocation(300, 300);
		Container contain = getContentPane();
        contain.setLayout(new BoxLayout(contain,BoxLayout.Y_AXIS));
		
		JPanel FirstLine=new JPanel();
		FirstLine.add(new JLabel("客人身份证号："));
		GuestNumber=new JTextField(20);
		FirstLine.add(GuestNumber);
		query=new JButton("查找");
		FirstLine.add(query);
		cont.add(FirstLine);
		
		JPanel SecondLine=new JPanel();
		SecondLine.add(new JLabel("客人姓名："));
		GuestName=new JTextField(10);
		GuestName.setEditable(false);
		SecondLine.add(GuestName);
		cont.add(SecondLine);
		
		JPanel ThirdLine=new JPanel();
		bill=new JButton("结账");
		ThirdLine.add(bill);
		cancel=new JButton("取消");
		ThirdLine.add(cancel);
		cont.add(ThirdLine);
		
		contain.add(cont);
		
		query.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				db dbcon;
				try
				{
					dbcon=new db();
					
					ResultSet rs = dbcon.executeQuery("select GName from CheckIn,Guests "
													  + "where CheckIn.IDNumber ="+"'"+GuestNumber.getText()+"'"+"and CheckIn.IDNumber=Guests.IDNumber");
					if(rs.next()) //找到预定
					{
						GuestName.setText(rs.getString(1));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "该客户没有入住！");
					}
					rs.close();
					dbcon.closeConn();
				}
				catch(SQLException sqle)
				 {
					 System.out.println(sqle.toString());
				 }
			}
			
		});
		
		bill.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				db dbcon;
				try
				{
					dbcon=new db();
					
					String callProc="{call Pro_CalConsum(?,?)}";           //调用数据库中的存储过程的语句
					CallableStatement cstmt = dbcon.cstmt(callProc);    //引用CallableStatement
					cstmt.setString(1, GuestNumber.getText());               
					cstmt.registerOutParameter(2, java.sql.Types.FLOAT);
					boolean bool=cstmt.execute();                       //调用存储过程
					float i=cstmt.getInt(2);                              //获取结果数据                                   
					String s;
					s="物品消费：\n";
					ResultSet rs = dbcon.executeQuery("select IName,ICount,IPrice from RoomItem,ItemCost where RoomItem.INo=ItemCost.INo and IDNumber="+"'"+GuestNumber.getText()+"'");
					while(rs.next())
					{
						s+=rs.getString(1);
						s+="x";
						s+=rs.getString(2);
						s+="   单价"+rs.getString(3)+"\n";
					}
					
					s+="服务消费：\n";
					ResultSet rs2 = dbcon.executeQuery("select SName,SCount,SCount from RoomServer,ServerCost where RoomServer.SNo=ServerCost.SNo and IDNumber="+"'"+GuestNumber.getText()+"'");
					while(rs2.next())
					{
						s+=rs2.getString(1);
						s+="x";
						s+=rs2.getString(2);
						s+="   单价"+rs.getString(3)+"\n";
					}
					
					s+="住房费：\n";
					ResultSet rs4 = dbcon.executeQuery("select Type,CheckIndays from CheckIn,Rooms where IDNumber="+"'"+GuestNumber.getText()+"'"+"and CheckIn.RNo=Rooms.RNo");
					while(rs4.next())
					{
						s+=rs4.getString(1)+rs4.getString(2)+"天 \n";
					}
					
					s+="押金：";
					ResultSet rs3 = dbcon.executeQuery("select Deposit from CheckIn where IDNumber="+"'"+GuestNumber.getText()+"'");
					while(rs3.next())
						s+=rs3.getString(1)+"\n";
					if(i>=0)
					{
						s+="还需付款： "+Float.toString(i);
					}
					else
					{
						s+="退还押金："+Float.toString(-i);
					}
					//修改客房状态
					PreparedStatement prestate3=dbcon.PreparedStatement("update Rooms Set State='空闲' from Rooms,CheckIn where Rooms.RNo=CheckIn.RNo and IDNumber="+"'"+GuestNumber.getText()+"'");
					prestate3.executeUpdate();
					//删除入住信息
					PreparedStatement prestate4=dbcon.PreparedStatement("delete from CheckIn where IDNumber="+"'"+GuestNumber.getText()+"'");
					prestate4.executeUpdate();
					
					JOptionPane.showMessageDialog(null, s); //弹出对话框
					dispose();
					dbcon.closeConn();
				}
				catch(SQLException sqle)
				{
					 System.out.println(sqle.toString());
				}
			}
			
		});
	}
	
	public static void main(String args[])
	{
		GuestConsum w = new GuestConsum();
		w.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
}
