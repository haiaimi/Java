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
	private String EmplyeeNo;  //Ա�����
	private JTextField cardNumber; 
	private JTextField phoneNumber;
	private JTextField guestName;
	private JTextField days,money;
	private JPanel room;
	private JComboBox RoomType,RemainNo;
	private JButton CheckIn,cancel,query;
	
	boolean IsReserved;    //��ǰ�ͻ��Ƿ�ԤԼ
	
	public CheckIn(String ENo)
	{
		super("�Ǽ���ס");
		EmplyeeNo=ENo;
		this.setSize(440,240);
        this.setLocation(200,140);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Container contain = getContentPane();
        contain.setLayout(new BoxLayout(contain,BoxLayout.Y_AXIS));
        
        JPanel cont = new JPanel(new GridLayout(6,1));
        
        JPanel FirstLine=new JPanel();
        FirstLine.add(new JLabel("����ͻ����֤��"));
        cardNumber = new JTextField(20);
		FirstLine.add(cardNumber);
		query = new JButton("����");
		FirstLine.add(query);
		cont.add(FirstLine);
		
		JPanel SecondLine=new JPanel();
		SecondLine.add(new JLabel("������"));
		guestName=new JTextField(6);
		SecondLine.add(guestName);
		SecondLine.add(new JLabel("�ֻ���"));
		phoneNumber = new JTextField(11);
		SecondLine.add(phoneNumber);
		cont.add(SecondLine);
		
		JPanel ThirdLine=new JPanel();
		RemainNo=new JComboBox();
		RemainNo.addItem("0000");
		ThirdLine.add(new JLabel("ѡ�񷿼����ͣ�"));
		RoomType=new JComboBox();
		RoomType.addItem("˫��");
		RoomType.addItem("����");
		RoomType.addItem("��");
		ThirdLine.add(RoomType);
		ThirdLine.add(new JLabel("               "));
		ThirdLine.add(new JLabel("ʣ�෿�䣺"));
		ThirdLine.add(RemainNo);
		cont.add(ThirdLine);
		
		JPanel ForthLine=new JPanel();
		ForthLine.add(new JLabel("��ס������"));
		days = new JTextField(5);
		ForthLine.add(days);
		ForthLine.add(new JLabel("Ѻ��"));
		money = new JTextField(5);
		ForthLine.add(money);
		cont.add(ForthLine);
		
		JPanel FifthLine=new JPanel();
		CheckIn = new JButton("�Ǽ�");
		cancel = new JButton("ȡ��");
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
					if(rs.next()) //�ҵ�Ԥ��
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
						JOptionPane.showMessageDialog(null, "�ÿͻ�û��ԤԼ��");
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
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");  //�������ڸ�ʽ
				db dbcon;
				try
				{
					dbcon=new db();
					//��������ͱ����������
					if(!IsReserved)
					{
						String sql1="insert into Guests values (?,?,?)";
						PreparedStatement prestate1=dbcon.PreparedStatement(sql1);
						prestate1.setString(1, guestName.getText());
						prestate1.setString(2, cardNumber.getText());
						prestate1.setString(3, phoneNumber.getText());
						prestate1.executeUpdate();
					}
					
					//�������ݵ���ס����
					String sql2="insert into CheckIn values (?,?,?,?,?,?)";
					PreparedStatement prestate2=dbcon.PreparedStatement(sql2);
					prestate2.setString(1, cardNumber.getText());
					prestate2.setString(2, RemainNo.getSelectedItem().toString());
					prestate2.setString(3, EmplyeeNo);
					prestate2.setInt(4, Integer.parseInt(days.getText()));
					prestate2.setString(5, df.format(new Date()));
					prestate2.setFloat(6, Float.parseFloat(money.getText()));
					prestate2.executeUpdate();
					
					//�޸Ŀͷ�״̬
					PreparedStatement prestate3=dbcon.PreparedStatement("update Rooms Set State='�п�' where RNo='"+RemainNo.getSelectedItem().toString()+"'");
					prestate3.executeUpdate();
					
					//ɾ��ԤԼ����Ϣ
					if(IsReserved)
					{
						PreparedStatement prestate4=dbcon.PreparedStatement("delete from Book where IDNumber='"+cardNumber.getText()+"'");
						prestate4.executeUpdate();
					}
					
					JOptionPane.showMessageDialog(null, "��ס�ɹ���");
					dispose();
					
					//dbcon.closeConn();
				}
				catch(SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, "�ÿͻ�����ס��");
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
			
			ResultSet rs = dbcon.executeQuery("select RNo from Rooms where State='����' and type ="+"'"+(String)RoomType.getSelectedItem()+"'");
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
