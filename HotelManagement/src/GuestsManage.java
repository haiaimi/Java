import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuestsManage extends JFrame implements ActionListener
{
	private JButton des;
	private JButton checkin;
	private JButton consume;
	private JToolBar tool;
	
	private JList list_guests; 
	private String str[]= {"","","",""};
	
	public GuestsManage()
	{
		super("���͹���");
		this.setSize(400,300);
        this.setLocation(200,140);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
        des = new JButton("ԤԼ");
        checkin = new JButton("��ס");
        consume = new JButton("����");
        
        tool = new JToolBar();
        tool.add(des);
        tool.add(checkin);
        tool.add(consume);
        tool.setRollover(true);
        
        this.add(tool,BorderLayout.PAGE_START);
        
        this.setVisible(true);
        list_guests = new JList(str);
        this.add(list_guests);
        this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String args[])
	{
		new GuestsManage();
	}
}
