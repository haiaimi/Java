import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResFrame extends JFrame implements ActionListener
{
	private JButton query;
	private JButton add;
	private JButton decrease;
	private JToolBar tool;
	
	private JList list_res; 
	String res[]= {"ֽ��","����","��ԡ¶"};
	
	public ResFrame() 
	{
		super("��Ʒ����");
		this.setSize(400,300);
        this.setLocation(200,140);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        query = new JButton("����");
        add = new JButton("���");
        decrease = new JButton("����");
        
        tool = new JToolBar();
        tool.add(query);
        tool.add(add);
        tool.add(decrease);
        tool.setRollover(true);
        
        this.add(tool,BorderLayout.PAGE_START);
        
        list_res = new JList(res);
        this.add(list_res);
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String arg[])
	{
		new ResFrame();
	}
}
