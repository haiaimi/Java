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
	
	private JButton b1,b2,b3;//������������ť
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
	
	private JTable ReserveTable;//�洢ԤԼ��Ϣ
	private JTable CheckInTable;//�洢��ס��Ϣ
	private JTable GoodTbale;//�洢��Ʒ�����Ϣ
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	private JScrollPane scroll3;
	
	//���ô��ھ���
	// �õ���ʾ����Ļ�Ŀ��
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	// ���崰��Ŀ��
	public int windowsWedth = 1000;
	public int windowsHeight = 600;
	// ���ô���λ�úʹ�С
	//this.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight); 

	public MainFrame(String Eno)
	{      
		EmplyeeNo=Eno;
		interfacePanel=new JPanel();
		interfacePanel.setLayout(new BorderLayout(10,10));    
		
		//�����Ǽǰ�ť��ԤԼ��ֱ����ס��
		b1 = new JButton("�����Ǽ�");
		b1.setToolTipText("�����Ǽ�");
		b1.setFocusable(false);
		b1.setHorizontalTextPosition(SwingConstants.CENTER);//���ñ�ǩ���ı������ͼ���ˮƽλ��Ϊ����
		b1.setVerticalTextPosition(SwingConstants.BOTTOM);//���ñ�ǩ���ı������ͼ��Ĵ�ֱλ��Ϊ�ײ�
		
		//���˰�ť
		b2 = new JButton(" ��   ��  ");
		b2.setToolTipText("����");
		b2.setFocusable(false);
		b2.setHorizontalTextPosition(SwingConstants.CENTER);
		b2.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		//��Ʒ������ť
		b3=new JButton("������");
		b3.setToolTipText("������");
		b3.setFocusable(false);
		b3.setHorizontalTextPosition(SwingConstants.CENTER);
		b3.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		//��ť����빤����
		tool = new JToolBar();
		tool.add(b1);
		tool.add(b2);
		tool.add(b3);
		tool.setRollover(true);
		interfacePanel.add(tool, BorderLayout.NORTH);
		//��ť������
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
		
		//�����������Ϣ���    
		//ʵ�������к�˽�е�table �� ������塢���ò��ɶ�     
		//����������ʾ��table 

		tableTabbedPane=new JTabbedPane();//��ǩ���
		try {			
			db dbcon=new db();   //�������ݿ�Ϊ�ˣ���ȡ��ͼ�е�ֵ������table
			
			//ΪԤԼ���ѯ
			ResultSet rs = dbcon.executeQuery("select * from view_ReserveInfo");
			String[] columnNames1 = {"���֤","����","�ֻ���","����Ա��","Ԥ��ʱ��","Ԥ����סʱ��","��ס����"};//��������	
			//��ȡ���������


			String[][] data1;
			data1=new String[50][7];
			int i=0;
			while(rs.next()) {
				String str[]=new String[7];
				str[0]=rs.getString("���֤");
				str[1]=rs.getString("����");
				str[2]=rs.getString("�ֻ���");
				str[3]=rs.getString("����Ա��");
				str[4]=rs.getString("Ԥ��ʱ��");
				str[5]=rs.getString("Ԥ����סʱ��");
				str[6]=rs.getString("��ס����");

				data1[i][0]=str[0];
				data1[i][1]=str[1];
				data1[i][2]=str[2];
				data1[i][3]=str[3];
				data1[i][4]=str[4];
				data1[i][5]=str[5];
				data1[i][6]=str[6];
				
				i++;						
			}
			
			//Ϊtable��ֵ������������
			ReserveTable = new JTable(data1, columnNames1);
			ReserveTable.setPreferredScrollableViewportSize(new Dimension(800, 400));//����table��С
			scroll1 = new JScrollPane(ReserveTable);
			//interfacePanel.add(scroll1, BorderLayout.CENTER);
			tableTabbedPane.addTab("Ԥ����Ϣ",scroll1);//Ϊ��ǩ���󶨶�Ӧ�����

			
			//Ϊ��ס���ѯ
			rs=dbcon.executeQuery("select * from view_CheckInInfo");
			String[] columnNames2 = {"���֤","����","�ֻ���","����Ա��","��סʱ��","��ס����","Ѻ��"};//��������	

			String[][] data2;
			data2=new String[50][7];
			i=0;
			
			while(rs.next()) 
			{
				String str[]=new String[7];
				str[0]=rs.getString("���֤");
				str[1]=rs.getString("����");
				str[2]=rs.getString("�ֻ���");
				str[3]=rs.getString("����Ա��");
				str[4]=rs.getString("��סʱ��");
				str[5]=rs.getString("��ס����");
				str[6]=rs.getString("Ѻ��");

				data2[i][0]=str[0];
				data2[i][1]=str[1];
				data2[i][2]=str[2];
				data2[i][3]=str[3];
				data2[i][4]=str[4];
				data2[i][5]=str[5];
				data2[i][6]=str[6];
				
				i++;						
			}

			//Ϊtable��ֵ������������
			CheckInTable = new JTable(data2, columnNames2);
			CheckInTable.setPreferredScrollableViewportSize(new Dimension(800, 400));//����table��С
			scroll2 = new JScrollPane(CheckInTable);
			tableTabbedPane.addTab("��ס��Ϣ",scroll2);

			
			//ʵ������̬ѡ�
			tableTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			interfacePanel.add(tableTabbedPane,BorderLayout.CENTER);
			
			rs.close();

		} 
		catch (SQLException sqle) 
		{
			System.out.println(sqle.toString());
		}
		
		//����ײ����û����
		//ʵ��������ʼ���������
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
		//Ϊ�����uesePanel�����в���        
		//userPanel ����Ϊ����һ�е����񲼾֣����зֱ��������壬userPanel2.��userPanel
		userPanel.setLayout(new GridLayout(2,1));
		JPanel userPanel2 =new JPanel();        
		JPanel userPanel3 =new JPanel();
		userPanel.add(userPanel2 );
		userPanel.add(userPanel3);

		//��һ�е���� userPanel2 ��������׼��λ���֣������һ����ǩ����Ͽ� 
		userPanel2.add(userLabel);
		userPanel2.add(userComboBox);
		GridBagLayout gridbag=new GridBagLayout();        
		userPanel2.setLayout(gridbag);
		//�Ե�һ�еڶ��������Ͽ���в���Լ��,ʵ����һ������C
		GridBagConstraints c=new GridBagConstraints();
		//����Ͽ�����������ĵı���   
		c.weightx=1;
		c.weighty=1;
		//���������ռ�ĵ�λ��������ʣ���ʱ���������䷽ʽΪˮƽ
		c.fill=GridBagConstraints.HORIZONTAL;
		//��������֮ǰ�ľ��룬��������Ϊ�� �� �� �� 
		c.insets=new Insets(0,10,0,5);
		//������Լ���������Ͽ���
		gridbag.setConstraints(userComboBox,c); 

		//�ڶ��е���� userPanel3���������֣����һ����ǩ��һ�������ı��Ŀ�һ�����Ͱ�ť                              
		userPanel3.setLayout(new FlowLayout());
		userPanel3.add(messageLabel);
		userPanel3.add(messageText);
		userPanel3.add(sendButton);           
		//������ҳ���·����������嵽�û������ȥ
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
