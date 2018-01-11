

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.text.Position;

import db.db;

public class registerframe extends JFrame {

	private JTextField username,userid;
	private JPasswordField password, passwordagain;
	private JRadioButton sexfemale, sexmale;
	private JPanel sex, birth, fav;
	private JTextField year;
	private JComboBox position; //职位
	private JCheckBox f1, f2, f3;
	private JButton register, cancel;
	private JTextArea remmond;
	private JScrollPane scroll;

	public registerframe() 
	{
		super();
		this.setSize(450, 300);
		this.setTitle("员工注册");
		this.setLocationRelativeTo(getOwner());

		Container contain = getContentPane();
		contain.setLayout(new BoxLayout(contain, BoxLayout.Y_AXIS));

		JPanel cont = new JPanel(new GridLayout(7, 2));

		
		cont.add(new JLabel("员工姓名："));
		username=new JTextField(10);
		cont.add(username);
		
		cont.add(new JLabel("员工号"));
		userid = new JTextField(10);
		cont.add(userid);

		cont.add(new JLabel("密码"));
		password = new JPasswordField(10);
		cont.add(password);

		cont.add(new JLabel("再输一次密码"));
		passwordagain = new JPasswordField(10);
		cont.add(passwordagain);

		cont.add(new JLabel("性别"));
		sexmale = new JRadioButton("男", true);
		sexfemale = new JRadioButton("女");
		ButtonGroup bg = new ButtonGroup();
		bg.add(sexmale);
		bg.add(sexfemale);
		sex = new JPanel(new GridLayout(1, 2));
		sex.add(sexmale);
		sex.add(sexfemale);
		cont.add(sex);
		
		cont.add(new JLabel("工作职位"));
		position = new JComboBox();
		position.addItem("柜台收银");
		position.addItem("卫生清洁");
		cont.add(position);
		
		JPanel cont2 = new JPanel(new GridLayout(1, 2));
		register = new JButton("注册");
		cancel = new JButton("取消");
		cont2.add(register);
		cont2.add(cancel);

		contain.add(cont);
		//contain.add(cont1);
		contain.add(cont2);

		register.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				db dbcon;
				
				try 
				{
				String pass = new String(password.getPassword());
				String passagain = new String(passwordagain.getPassword());
				if (passagain.equals(pass)) 
				{
					dbcon=new db();
					//首先向员工表中添加数据
					String sql1="insert into Emplyee values (?,?,?,?)";
					PreparedStatement prestate1=dbcon.PreparedStatement(sql1);
					prestate1.setString(1, userid.getText());
					prestate1.setString(2, username.getText());
					prestate1.setString(3, (sexmale.isSelected()?sexmale.getText():sexfemale.getText()));
					prestate1.setString(4, position.getSelectedItem().toString());
					prestate1.executeUpdate();
					
					//向密码表中添加数据
					String sql2="insert into UserPassword values (?,?)";
					PreparedStatement prestate2=dbcon.PreparedStatement(sql2);
					prestate2.setString(1, userid.getText());
					//System.out.println(password.getPassword());
					prestate2.setString(2, String.copyValueOf(password.getPassword()));
				
					prestate2.executeUpdate();
					JOptionPane.showMessageDialog(null, "注册成功！");
					dispose();
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "输入密码不一致！");
				}
			}
			catch(SQLException e1)
			{
				//JOptionPane.showMessageDialog(null, "该ID已存在！");
				System.out.println(e1.toString());
			}
		}

		});

	}

	public static void main(String[] args) {
		registerframe w = new registerframe();
		w.setVisible(true);
	}

}