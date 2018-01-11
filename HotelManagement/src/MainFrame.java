import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.sql.*;
import db.db;


public class MainFrame extends JPanel
{
	private String EmplyeeNo;
	private JPanel interfacePanel;
	
	private JButton b1,b2,b3;//工具栏三个按钮
	private JToolBar tool;
	private JPanel userPanel;
	private JLabel userLabel;
	private JComboBox userComboBox;
	private JLabel messageLabel;
	private JButton sendButton;
	private JTextField messageText;      
	private JTabbedPane tableTabbedPane;
	private JScrollPane publicScrollPane;
	private JTextPane publicTextPane;
	private JScrollPane privateScrollPane;
	private JTextPane privateTextPane;
	
	private JTable ReserveTable;//存储预约信息
	private JTable CheckInTable;//存储入住信息
	private JTable GoodTbale;//存储商品库存信息
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	private JScrollPane scroll3;
	
	//设置窗口居中
	// 得到显示器屏幕的宽高
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	// 定义窗体的宽高
	public int windowsWedth = 1000;
	public int windowsHeight = 600;
	// 设置窗体位置和大小
	//this.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight); 

	public MainFrame(String Eno)
	{      
		EmplyeeNo=Eno;
		interfacePanel=new JPanel();
		interfacePanel.setLayout(new BorderLayout(10,10));    
		
		//来宾登记按钮（预约，直接入住）
		b1 = new JButton("来宾登记");
		b1.setToolTipText("来宾登记");
		b1.setFocusable(false);
		b1.setHorizontalTextPosition(SwingConstants.CENTER);//设置标签的文本相对其图像的水平位置为居中
		b1.setVerticalTextPosition(SwingConstants.BOTTOM);//设置标签的文本相对其图像的垂直位置为底部
		
		//结账按钮
		b2 = new JButton(" 结   账  ");
		b2.setToolTipText("结账");
		b2.setFocusable(false);
		b2.setHorizontalTextPosition(SwingConstants.CENTER);
		b2.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		//商品入库管理按钮
		b3=new JButton("入库管理");
		b3.setToolTipText("入库管理");
		b3.setFocusable(false);
		b3.setHorizontalTextPosition(SwingConstants.CENTER);
		b3.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		//按钮添加入工具栏
		tool = new JToolBar();
		tool.add(b1);
		tool.add(b2);
		tool.add(b3);
		tool.setRollover(true);
		interfacePanel.add(tool, BorderLayout.NORTH);
		//按钮监听器
		b1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				CheckInRegister checkIn = new CheckInRegister(EmplyeeNo);
				checkIn.setVisible(true);
			}
		});
		
		b2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				GuestConsum GC=new GuestConsum();
				GC.setVisible(true);
			}
			
		});
		
		//界面中央的信息面板    
		//实例化共有和私有的table 、 滚动面板、设置不可读     
		//声明用于显示的table 

		tableTabbedPane=new JTabbedPane();//标签面板
		try {			
			db dbcon=new db();   //连接数据库为了，获取视图中的值，传给table
			
			//为预约表查询
			ResultSet rs = dbcon.executeQuery("select * from view_ReserveInfo");
			String[] columnNames1 = {"身份证","姓名","手机号","处理员工","预定时间","预定入住时间","入住天数"};//设置列名	
			//获取结果集行数


			String[][] data1;
			data1=new String[50][7];
			int i=0;
			while(rs.next()) {
				String str[]=new String[7];
				str[0]=rs.getString("身份证");
				str[1]=rs.getString("姓名");
				str[2]=rs.getString("手机号");
				str[3]=rs.getString("处理员工");
				str[4]=rs.getString("预定时间");
				str[5]=rs.getString("预定入住时间");
				str[6]=rs.getString("入住天数");

				data1[i][0]=str[0];
				data1[i][1]=str[1];
				data1[i][2]=str[2];
				data1[i][3]=str[3];
				data1[i][4]=str[4];
				data1[i][5]=str[5];
				data1[i][6]=str[6];
				
				i++;						
			}
			
			//为table赋值，并添加入面板
			ReserveTable = new JTable(data1, columnNames1);
			ReserveTable.setPreferredScrollableViewportSize(new Dimension(800, 400));//设置table大小
			scroll1 = new JScrollPane(ReserveTable);
			//interfacePanel.add(scroll1, BorderLayout.CENTER);
			tableTabbedPane.addTab("预定信息",scroll1);//为标签面板绑定对应的面板

			
			//为入住表查询
			rs=dbcon.executeQuery("select * from view_CheckInInfo");
			String[] columnNames2 = {"身份证","姓名","手机号","处理员工","入住时间","入住天数","押金"};//设置列名	

			String[][] data2;
			data2=new String[50][7];
			i=0;
			
			while(rs.next()) 
			{
				String str[]=new String[7];
				str[0]=rs.getString("身份证");
				str[1]=rs.getString("姓名");
				str[2]=rs.getString("手机号");
				str[3]=rs.getString("处理员工");
				str[4]=rs.getString("入住时间");
				str[5]=rs.getString("入住天数");
				str[6]=rs.getString("押金");

				data2[i][0]=str[0];
				data2[i][1]=str[1];
				data2[i][2]=str[2];
				data2[i][3]=str[3];
				data2[i][4]=str[4];
				data2[i][5]=str[5];
				data2[i][6]=str[6];
				
				i++;						
			}

			//为table赋值，并添加入面板
			CheckInTable = new JTable(data2, columnNames2);
			CheckInTable.setPreferredScrollableViewportSize(new Dimension(800, 400));//设置table大小
			scroll2 = new JScrollPane(CheckInTable);
			tableTabbedPane.addTab("入住信息",scroll2);

			
			//实例化动态选项卡
			tableTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			interfacePanel.add(tableTabbedPane,BorderLayout.CENTER);
			
			rs.close();

		} 
		catch (SQLException sqle) 
		{
			System.out.println(sqle.toString());
		}
		
		//界面底部的用户面板
		//实例化并初始化化各组件
		userPanel =new JPanel();                  
		userLabel=new JLabel("    Send to :");
		userComboBox=new JComboBox();
		String users[]={"Public","ClientB","CientA"};
		userComboBox.addItem(users[0]); 
		userComboBox.addItem(users[1]); 
		userComboBox.addItem(users[2]);
		messageLabel=new JLabel("Message:");
		messageText=new JTextField(22);
		sendButton=new JButton("Send");    
		//为下面的uesePanel面板进行布局        
		//userPanel 设置为两行一列的网格布局，两行分别放两个面板，userPanel2.与userPanel
		userPanel.setLayout(new GridLayout(2,1));
		JPanel userPanel2 =new JPanel();        
		JPanel userPanel3 =new JPanel();
		userPanel.add(userPanel2 );
		userPanel.add(userPanel3);

		//第一行的面板 userPanel2 采用网格精准定位布局，并添加一个标签与组合框 
		userPanel2.add(userLabel);
		userPanel2.add(userComboBox);
		GridBagLayout gridbag=new GridBagLayout();        
		userPanel2.setLayout(gridbag);
		//对第一行第二个组件组合框进行布局约束,实例化一个对象C
		GridBagConstraints c=new GridBagConstraints();
		//当组合框被拉伸后所按的的比例   
		c.weightx=1;
		c.weighty=1;
		//当组件框所占的单位行数还有剩余的时候，组件的填充方式为水平
		c.fill=GridBagConstraints.HORIZONTAL;
		//组件与组件之前的距离，参数依次为上 左 下 右 
		c.insets=new Insets(0,10,0,5);
		//将布局约束添加在组合框上
		gridbag.setConstraints(userComboBox,c); 

		//第二行的面板 userPanel3采用流布局，添加一个标签，一个输入文本的框，一个发送按钮                              
		userPanel3.setLayout(new FlowLayout());
		userPanel3.add(messageLabel);
		userPanel3.add(messageText);
		userPanel3.add(sendButton);           
		//放置在页面下方，并添加面板到用户面板中去
		interfacePanel.add(BorderLayout.SOUTH,userPanel);   
		JFrame frame=new JFrame();     
		frame.add(interfacePanel);
		frame.setVisible(true);
		frame.setSize(windowsWedth,windowsHeight);
		frame.setLocation((width - windowsWedth) / 2,(height - windowsHeight) / 2);
	}
		

	public static void main(String[] args)
	{
	    try
	    {	    	
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
		MainFrame m=  new MainFrame("21001");
		m.setVisible(true);
	}
};
