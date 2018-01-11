import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;

import db.db;

public class Reservation extends JFrame implements ActionListener
{
	private String EmplyeeNo;        //Ա�����
	private JTextField cardNumber; 
	private JTextField NameField;
	private JTextField phoneNumber;
	private JPanel Card;
	private JPanel SecondLine;
	//private JPanel Phone;

	private JPanel room;
	private JComboBox RoomType,RemainNo;
	private JTextField RemainRooms,year,StayDays; 
	
	private JPanel CheckInDate;    
	private JComboBox month,day,hours;
	private JButton reserve,cancel;
	
	
	public Reservation(String ENo) 
	{
		super("ԤԼ");
		EmplyeeNo=ENo;
		this.setSize(400,220);
		this.setResizable(false);
        this.setLocation(200,140);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Container contain = getContentPane();
        contain.setLayout(new BoxLayout(contain,BoxLayout.Y_AXIS));
        
        JPanel cont = new JPanel(new GridLayout(5,1));
        
        //���֤��������
        Card=new JPanel();
        Card.add(new JLabel("���֤��       "));
        cardNumber = new JTextField(27);
		Card.add(cardNumber);
		cont.add(Card);
		
		//��������
		SecondLine=new JPanel(new GridLayout(1,2));
		JPanel Name=new JPanel();
		Name.add(new JLabel("������             "));
		NameField = new JTextField(8);
		Name.add(NameField);
		SecondLine.add(Name);
		cont.add(SecondLine);
	
		//�����ֻ���
		JPanel Phone=new JPanel();
		Phone.add(new JLabel("�ֻ���    "));
		phoneNumber = new JTextField(11);
		Phone.add(phoneNumber);
		SecondLine.add(Phone);
		cont.add(SecondLine);
		
		//ѡ�񷿼�����
		room=new JPanel();
		//RemainRooms=new JTextField(10);
		//RemainRooms.setEditable(false);
		RemainNo=new JComboBox();
		RemainNo.addItem("0000");
		room.add(new JLabel("ѡ�񷿼����ͣ�          "));
		RoomType=new JComboBox();
		RoomType.addItem("˫��");
		RoomType.addItem("����");
		RoomType.addItem("��");
		room.add(RoomType);
		room.add(new JLabel("                     "));
		room.add(new JLabel("ʣ�෿�䣺"));
		room.add(RemainNo);
		cont.add(room);
		
		//Ԥ����סʱ��
		CheckInDate=new JPanel();
		CheckInDate.add(new JLabel("��סʱ�䣺"));
		year = new JTextField(4);
		month = new JComboBox();
		int i;
		for (i = 1; i <= 12; i++)
			month.addItem(i);
		day = new JComboBox();
		for (i = 1; i <= 31; i++)
			day.addItem(i);
		hours=new JComboBox();
		for(i=1;i<=24;i++)
			hours.addItem(i);
		
		CheckInDate.add(year);
		CheckInDate.add(new JLabel("-"));
		CheckInDate.add(month);
		CheckInDate.add(new JLabel("-"));
		CheckInDate.add(day);
		CheckInDate.add(hours);
		
		CheckInDate.add(new JLabel("��ס������"));
		StayDays=new JTextField(2);
		CheckInDate.add(StayDays);
		
		cont.add(CheckInDate);
//		cont.add(new JLabel("��ס������"));
//		days = new JTextField(5);
//		cont.add(days);
//		
		JPanel ButtonPanel=new JPanel();
		reserve = new JButton("ԤԼ");
		cancel = new JButton("ȡ��");
		ButtonPanel.add(reserve);
		ButtonPanel.add(cancel);
		cont.add(ButtonPanel);
		contain.add(cont);
		
		RoomType.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				ActiveRoomType();
			}
			
		});
		
		reserve.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");  //�������ڸ�ʽ
				db dbcon;
				try
				{
					dbcon=new db();
					//��������ͱ����������
					String sql1="insert into Guests values (?,?,?)";
					PreparedStatement prestate1=dbcon.PreparedStatement(sql1);
					prestate1.setString(1, NameField.getText());
					prestate1.setString(2, cardNumber.getText());
					prestate1.setString(3, phoneNumber.getText());
					prestate1.executeUpdate();
					
					//�������ݵ�Ԥ������
					String CheckInTime=year.getText()+"-"+month.getSelectedItem().toString()+"-"+day.getSelectedItem().toString()+" "+hours.getSelectedItem().toString()+":00";
					String sql2="insert into Book values (?,?,?,?,?,?)";
					PreparedStatement prestate2=dbcon.PreparedStatement(sql2);
					prestate2.setString(1, cardNumber.getText());
					prestate2.setString(2, RemainNo.getSelectedItem().toString());
					prestate2.setString(3, EmplyeeNo);
					prestate2.setInt(4, Integer.parseInt(StayDays.getText()));
					prestate2.setString(5, df.format(new Date()));
					prestate2.setString(6, CheckInTime);
					prestate2.executeUpdate();
					
					//�޸Ŀͷ�״̬
					PreparedStatement prestate3=dbcon.PreparedStatement("update Rooms Set State='ԤԼ' where RNo='"+RemainNo.getSelectedItem().toString()+"'");
					prestate3.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "ԤԼ�ɹ���");
					//dispose();
					
					//dbcon.closeConn();
					System.out.println(CheckInTime);
				}
				catch(SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, "�ÿͻ��Ѿ�ԤԼ��");
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
			//RemainRooms.setText(rs.getString("remain"));
			rs.close();
			dbcon.closeConn();
		}
		catch(SQLException sqle)
		 {
			 System.out.println(sqle.toString());
		 }
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String args[])
	{
		new Reservation("21001");
	}

}
