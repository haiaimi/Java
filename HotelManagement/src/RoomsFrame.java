import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoomsFrame extends JFrame implements ActionListener
{
	private JList list_rooms; 
	String Rooms[]= {"501","502","503","504"};
	
	public RoomsFrame()
	{
		super("�ͷ�����");
		
		this.setSize(400,300);
        this.setLocation(200,140);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JMenu menu_query = new JMenu("��ѯ");               //�˵�
        JMenuItem menuitem_pre = new JMenuItem("Ԥ��");
        JMenuItem menuitem_used = new JMenuItem("��ס");
        JMenuItem menuitem_rest = new JMenuItem("����");
        menu_query.add(menuitem_pre);
        menuitem_pre.addActionListener(this);
        menu_query.add(menuitem_used);
        menuitem_used.addActionListener(this);
        menu_query.add(menuitem_rest);
        menuitem_rest.addActionListener(this);
        
        JMenu menu_datachange = new JMenu("�����޸�");               //��
        JMenuItem menuitem_add = new JMenuItem("���");
        JMenuItem menuitem_delete = new JMenuItem("ɾ��");
        JMenuItem menuitem_update = new JMenuItem("�޸�");
        menu_datachange.add(menuitem_add);
        menuitem_add.addActionListener(this);
        menu_datachange.add(menuitem_delete);
        menuitem_delete.addActionListener(this);
        menu_datachange.add(menuitem_update);
        menuitem_update.addActionListener(this);
        
        JMenuBar menubar = new JMenuBar();                 //�˵���
        this.setJMenuBar(menubar);     
        menubar.add(menu_query);                            //�˵����м���˵�
        menubar.add(menu_datachange);                            //�˵����м���˵�
        
        list_rooms = new JList(Rooms);
        this.getContentPane().add(list_rooms);
        
        this.setVisible(true);
	}
	
	 public void actionPerformed(ActionEvent e)
	 {
		 
	 }
	
	public static void main(String arg[])
	{
		new RoomsFrame();
	}
	
}
