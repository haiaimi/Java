import javax.swing.*;

import db.db;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckIn extends JFrame implements ActionListener
{
	private String EmplyeeNo;  //员工编号
	private JTextField cardNumber; 
	private JTextField phoneNumber;
	private JTextField guestName;
	private JTextField days,money;
	private JPanel room;
	private JComboBox RoomType,RemainNo;
	private JButton CheckIn,cancel,query;
	
	boolean IsReserved;    //当前客户是否预约
	
	public CheckIn(String ENo)
	{
		super("登记入住");
		EmplyeeNo=ENo;
		this.setSize(440,240);
        this.setLocation(200,140);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Container contain = getContentPane();
        contain.setLayout(new BoxLayout(contain,BoxLayout.Y_AXIS));
        
        JPanel cont = new JPanel(new GridLayout(6,1));
        
        JPanel FirstLine=new JPanel();
        FirstLine.add(new JLabel("输入客户身份证："));
        cardNumber = new JTextField(20);
		FirstLine.add(cardNumber);
		query = new JButton("查找");
		FirstLine.add(query);
		cont.add(FirstLine);
		
		JPanel SecondLine=new JPanel();
		SecondLine.add(new JLabel("姓名："));
		guestName=new JTextField(6);
		SecondLine.add(guestName);
		SecondLine.add(new JLabel("手机："));
		phoneNumber = new JTextField(11);
		SecondLine.add(phoneNumber);
		cont.add(SecondLine);
		
		JPanel ThirdLine=new JPanel();
		RemainNo=new JComboBox();
		RemainNo.addItem("0000");
		ThirdLine.add(new JLabel("选择房间类型："));
		RoomType=new JComboBox();
		RoomType.addItem("双床");
		RoomType.addItem("单床");
		RoomType.addItem("大床");
		ThirdLine.add(RoomType);
		ThirdLine.add(new JLabel("               "));
		ThirdLine.add(new JLabel("剩余房间："));
		ThirdLine.add(RemainNo);
		cont.add(ThirdLine);
		
		JPanel ForthLine=new JPanel();
		ForthLine.add(new JLabel("入住天数："));
		days = new JTextField(5);
		ForthLine.add(days);
		ForthLine.add(new JLabel("押金："));
		money = new JTextField(5);
		ForthLine.add(money);
		cont.add(ForthLine);
		
		JPanel FifthLine=new JPanel();
		CheckIn = new JButton("登记");
		cancel = new JButton("取消");
		FifthLine.add(CheckIn);
		FifthLine.add(cancel);
		cont.add(FifthLine);
		
		contain.add(cont);
		
		RoomType.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				ActiveRoomType();
			}
		});

		query.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				db dbcon;
				try
				{
					dbcon=new db();
					
					ResultSet rs = dbcon.executeQuery("select Book.*,PNumber,Type,GName from Book,Guests,Rooms "
													  + "where Book.IDNumber ="+"'"+cardNumber.getText()+"'"+"and Book.IDNumber=Guests.IDNumber and Rooms.RNo=Book.RNo");
					if(rs.next()) //找到预定
					{
						phoneNumber.setText(rs.getString(7));
						RoomType.setSelectedItem(rs.getString(8).trim());
						guestName.setText(rs.getString(9));
						ActiveRoomType();
						RemainNo.addItem(rs.getString(2));
						RemainNo.setSelectedItem(rs.getString(2));
						days.setText(rs.getString(4));
						IsReserved=true;
					}
					else
					{
						IsReserved=false;
						JOptionPane.showMessageDialog(null, "该客户没有预约！");
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
		
		CheckIn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");  //设置日期格式
				db dbcon;
				try
				{
					dbcon=new db();
					//首先向宾客表中添加数据
					if(!IsReserved)
					{
						String sql1="insert into Guests values (?,?,?)";
						PreparedStatement prestate1=dbcon.PreparedStatement(sql1);
						prestate1.setString(1, guestName.getText());
						prestate1.setString(2, cardNumber.getText());
						prestate1.setString(3, phoneNumber.getText());
						prestate1.executeUpdate();
					}
					
					//插入数据到入住表中
					String sql2="insert into CheckIn values (?,?,?,?,?,?)";
					PreparedStatement prestate2=dbcon.PreparedStatement(sql2);
					prestate2.setString(1, cardNumber.getText());
					prestate2.setString(2, RemainNo.getSelectedItem().toString());
					prestate2.setString(3, EmplyeeNo);
					prestate2.setInt(4, Integer.parseInt(days.getText()));
					prestate2.setString(5, df.format(new Date()));
					prestate2.setFloat(6, Float.parseFloat(money.getText()));
					prestate2.executeUpdate();
					
					//修改客房状态
					PreparedStatement prestate3=dbcon.PreparedStatement("update Rooms Set State='有客' where RNo='"+RemainNo.getSelectedItem().toString()+"'");
					prestate3.executeUpdate();
					
					//删除预约表信息
					if(IsReserved)
					{
						PreparedStatement prestate4=dbcon.PreparedStatement("delete from Book where IDNumber='"+cardNumber.getText()+"'");
						prestate4.executeUpdate();
					}
					
					JOptionPane.showMessageDialog(null, "入住成功！");
					dispose();
					
					//dbcon.closeConn();
				}
				catch(SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, "该客户已入住！");
					System.out.println(sqle.toString());
				}
			}
			
		});
		ActiveRoomType();
		this.setVisible(true);
	}
	
	public void ActiveRoomType()
	{
		db dbcon;
		try
		{
			dbcon=new db();
			
			ResultSet rs = dbcon.executeQuery("select RNo from Rooms where State='空闲' and type ="+"'"+(String)RoomType.getSelectedItem()+"'");
			RemainNo.removeAllItems();
			while(rs.next())
			{
				RemainNo.addItem(rs.getString(1));
			}
			rs.close();
			dbcon.closeConn();
		}
		catch(SQLException sqle)
		 {
			 System.out.println(sqle.toString());
		 }
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String args[])
	{
		new CheckIn("21002");
	}

}
