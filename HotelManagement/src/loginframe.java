
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import db.db;
public class loginframe extends JFrame
{
	private JTextField      userid;
	private JPasswordField  password;
	private JButton  login,register,changepass;
	
	public loginframe()
	{
		super();
		this.setSize(300,150);
		this.setTitle("Ա����¼");
		this.setLocationRelativeTo(getOwner());
		
		Container cont = getContentPane();
		cont.setLayout(new GridLayout(3,2));
		
		cont.add(new JLabel("userid:"));
		userid = new JTextField(10);
		cont.add(userid);
		cont.add(new JLabel("password:"));
		password = new JPasswordField(10);
		cont.add(password);
		
		login = new JButton("��¼");
		register = new JButton("ע��");
		changepass = new JButton("�޸�����");
		cont.add(login);
		cont.add(register);
		
		login.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt)
			{
				String pass = new String(password.getPassword());
				db dbcon;
				
				try 
				{
					dbcon=new db();
					ResultSet rs = dbcon.executeQuery("select password from UserPassword where ENo="+"'"+userid.getText()+"'");
					
					if (rs.next()) 
					{
						if(rs.getString(1).trim().equals(pass))
						{
							JOptionPane.showMessageDialog(null, "��¼�ɹ���");
							dispose();
							MainFrame mf=new MainFrame(userid.getText());
						}
						else
						{
							JOptionPane.showMessageDialog(null, "�������");
						}
					}
					
				
				}
				catch(SQLException e1)
				{
					//JOptionPane.showMessageDialog(null, "��ID�Ѵ��ڣ�");
					System.out.println(e1.toString());
				}
			}
		});
		
		register.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				registerframe rf=new registerframe();
				rf.setVisible(true);
			}
			
		});
	}
	
	public static void main(String[] args)
	{
		loginframe w =new loginframe();
		w.setVisible(true);
	}
}
